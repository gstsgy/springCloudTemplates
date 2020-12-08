package com.guyue.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.permission.bean.db.MenuDO;
import com.guyue.permission.bean.db.RoleMenuDO;
import com.guyue.permission.service.MenuService;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.mapper.MenuMapper;
import com.guyue.permission.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午4:17
 **/
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private ResponseBean responseBean;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseBean addMenu(MenuDO menuDO) {
        if (menuDO == null) {
            return responseBean.getError("参数不能为空");
        }
        return responseBean.getSuccess(menuMapper.insert(menuDO) > 0);
    }

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void deleteMenu(List<MenuDO> menuDOs) {
        //System.out.println("----------"+menuDOs);
        if (menuDOs != null && menuDOs.size() > 0) {
            menuDOs.forEach(item -> {
                QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(MenuDO::getId, item.getId());
                MenuDO menuDO1 = menuMapper.selectOne(queryWrapper);
                if (menuDO1 != null) {

                    menuMapper.deleteById(menuDO1.getId());

                    //删除菜单角色
                    QueryWrapper<RoleMenuDO> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.lambda().eq(RoleMenuDO::getMenuId, menuDO1.getId());
                    List<RoleMenuDO> roleMenuDOS = roleMenuMapper.selectList(queryWrapper2);
                    roleMenuDOS.forEach(it -> {
                        roleMenuMapper.deleteById(it.getId());
                    });


                    //删除下级菜单
                    QueryWrapper<MenuDO> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.lambda().eq(MenuDO::getParentId, item.getId());
                    List<MenuDO> menuDOS = menuMapper.selectList(queryWrapper1);
                    if (menuDOS != null && menuDOS.size() > 0) {
                        deleteMenu(menuDOS);
                    }
                }
            });
        }
        //return;
    }

    @Override
    public ResponseBean queryMenu(MenuDO menuDO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new QueryWrapper<MenuDO>().lambda();
        if (menuDO != null) {
            if (menuDO.getParentId() != null) {
                queryWrapper.eq(MenuDO::getParentId, menuDO.getParentId());
            }
            if (menuDO.getLevel() != null) {
                queryWrapper.eq(MenuDO::getLevel, menuDO.getLevel());
            }
            if (menuDO.getName() != null) {
                queryWrapper.like(MenuDO::getName, menuDO.getName());
            }
            if (menuDO.getType() != null) {
                queryWrapper.eq(MenuDO::getType, menuDO.getType());
            }
        }
        Page<MenuDO> menuDOIPage = PageHelper.startPage(pageNum,pageSize).doSelectPage(()->
                menuMapper.selectList( queryWrapper));
       // IPage<MenuDO> menuDOIPage = ;

        return responseBean.getSuccess(new PageInfo<>(menuDOIPage));
    }

    @Override
    public ResponseBean updateMenu(MenuDO menuDO) {
        if (menuDO == null) {
            return responseBean.getError("参数不能为空");
        }
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuDO::getId, menuDO.getId());
        MenuDO menuDO1 = menuMapper.selectOne(queryWrapper);
        if (menuDO1 == null) {
            return responseBean.getError("菜单不存在");
        }
        return responseBean.getSuccess(menuMapper.updateById(menuDO) > 0);
    }

    @Override
    public List<MenuDO> queryChildren(Long parentId,Integer type) {
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        if(parentId==null){
            queryWrapper.lambda().isNull(MenuDO::getParentId);
        }else{
            queryWrapper.lambda().eq(MenuDO::getParentId,parentId);
        }
        if(type!=null){
            queryWrapper.lambda().eq(MenuDO::getType,type);
        }

        return menuMapper.selectList(queryWrapper);
    }
}
