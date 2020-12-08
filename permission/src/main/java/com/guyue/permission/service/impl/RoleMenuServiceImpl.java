package com.guyue.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guyue.permission.bean.db.MenuDO;
import com.guyue.permission.bean.db.RoleMenuDO;
import com.guyue.permission.bean.db.UserRoleDO;
import com.guyue.permission.bean.view.MenuTreeVO;
import com.guyue.permission.bean.view.TreeNodeVO;
import com.guyue.permission.service.MenuService;
import com.guyue.permission.service.RoleMenuService;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.mapper.MenuMapper;
import com.guyue.permission.mapper.RoleMenuMapper;
import com.guyue.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName RoleMenuServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:42
 **/
@Service
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    private MenuService menuService;
    @Autowired
    private ResponseBean responseBean;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseBean getAllMenuTree(Integer type) {
        //查询模块
        List<TreeNodeVO> tree = menuService.queryChildren(null, type).stream().
                map(item -> new TreeNodeVO(1, null, item.getName(), item.getId() + "")).
                collect(Collectors.toList());
        // 二级菜单
        tree.forEach(item -> item.setChildren(menuService.queryChildren(Long.parseLong(item.getValue()), null).
                stream().map(it -> new TreeNodeVO(2, item.getValue(), it.getName(), it.getId() + "")).
                collect(Collectors.toList())));
        // 三级菜单
        //systemParamService.
        tree.forEach(item -> item.getChildren().forEach(it ->
                it.setChildren(menuService.queryChildren(Long.parseLong(it.getValue()), null).stream().
                        map(i -> new TreeNodeVO(3, it.getValue(), i.getName(), i.getId() + "")).
                        collect(Collectors.toList()))));
        return responseBean.getSuccess(tree);
    }

    @Override
    public ResponseBean getAllMenuByRole(Long roleId, Integer type) {
        if (roleId == null || roleId == 0) {
            return responseBean.getError("参数不能为空");
        }

        List<MenuDO> list = roleMenuMapper.selectMenuIdByRole(roleId, type);
        return responseBean.getSuccess(list);
    }

    @Override
    public ResponseBean saveMenu(Long roleId, List<Long> menus) {
        if (roleId == null || roleId == 0) {
            return responseBean.getError("参数不可为空");
        }

        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleMenuDO::getRoleId, roleId);
        // 删除旧的
        List<RoleMenuDO> listOld = roleMenuMapper.selectList(queryWrapper);
        listOld.forEach(item -> {
            if (!menus.contains(item.getMenuId())) {

                roleMenuMapper.deleteById(item.getId());
            }
        });
        List<Long> listOldMenu = listOld.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toList());

        // 新增新的
        menus.forEach(item -> {
            if (!listOldMenu.contains(item)) {
                RoleMenuDO roleMenuDO = new RoleMenuDO();
                roleMenuDO.setRoleId(roleId);
                roleMenuDO.setMenuId(item);
                roleMenuMapper.insert(roleMenuDO);
            }
        });
        return responseBean.getSuccess(true);
    }


    @Override
    public ResponseBean getAllMenuTree(Integer type, Long userId) {
        if (userId == 1) {
            //查询模块
            List<MenuTreeVO> tree = menuService.queryChildren(null, type).stream().sorted(Comparator.comparing(MenuDO::getSeq)).
                    map(MenuTreeVO::new).
                    collect(Collectors.toList());
            // 二级菜单
            tree.forEach(item -> item.setChildren(menuService.queryChildren(item.getMenuId(), null).
                    stream().sorted(Comparator.comparing(MenuDO::getSeq)).map(MenuTreeVO::new).
                    collect(Collectors.toList())));
            return responseBean.getSuccess(tree);
        } else {


            // 1  通过用户 查询角色
            QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
            query.lambda().eq(UserRoleDO::getUserId, userId);
            List<UserRoleDO> list = userRoleMapper.selectList(query);


            Map<Long, List<MenuTreeVO>> map = list.stream().flatMap(item -> roleMenuMapper.
                    selectMenuIdByRole(item.getRoleId(), type).stream()).
                    map(MenuDO::getParentId).distinct().map(menuMapper::selectById).filter(Objects::nonNull).
                    map(MenuTreeVO::new).collect(Collectors.groupingBy(MenuTreeVO::getParentid));


            List<MenuTreeVO> menuIdlist = map.keySet().stream().filter(Objects::nonNull).map(menuMapper::selectById).filter(Objects::nonNull).
                    sorted(Comparator.comparingInt(MenuDO::getSeq)).
                    map(MenuTreeVO::new).
                    map(item -> {
                        item.setChildren(map.get(item.getMenuId()).stream().sorted(Comparator.comparingInt(MenuTreeVO::getDisplayorder)).map(it -> {
                            it.setParentLabel(item.getMenuLabel());
                            return it;
                        }).collect(Collectors.toList()));
                        return item;
                    }).collect(Collectors.toList());
            return responseBean.getSuccess(menuIdlist);
        }

    }

    @Override
    public ResponseBean getAllFuns(Integer type, Long userId) {
        if (userId == 1) {
            //查询所有三级菜单
            QueryWrapper<MenuDO> query = new QueryWrapper<>();
            query.lambda().eq(MenuDO::getLevel, 3);
            List<MenuDO> list = menuMapper.selectList(query);

            return responseBean.getSuccess(list.stream().collect(Collectors.toMap(MenuDO::getId,obj->true)));
        } else {


            // 1  通过用户 查询角色
            QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
            query.lambda().eq(UserRoleDO::getUserId, userId);
            List<UserRoleDO> list = userRoleMapper.selectList(query);


            Map map = list.stream().flatMap(item -> roleMenuMapper.
                    selectMenuIdByRole(item.getRoleId(), type).stream()).
                    distinct().filter(Objects::nonNull).
                    collect(Collectors.toMap(MenuDO::getId,obj->true));

            return responseBean.getSuccess(map);
        }
    }
}
