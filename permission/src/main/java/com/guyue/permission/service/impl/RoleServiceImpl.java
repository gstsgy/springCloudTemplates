package com.guyue.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.permission.bean.db.RoleDO;
import com.guyue.permission.bean.db.UserRoleDO;
import com.guyue.permission.service.RoleService;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.mapper.RoleMapper;
import com.guyue.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午3:27
 **/
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ResponseBean responseBean;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseBean addRole(RoleDO roleDO) {
        if (roleDO == null || roleDO.getName() == null || roleDO.getName().equals("")) {
            return responseBean.getError("参数不能为空");
        }
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        query.lambda().eq(RoleDO::getName, roleDO.getName());
        int count = roleMapper.selectCount(query);
        if (count > 0) {
            return responseBean.getError("已存在同名角色");
        }

        return responseBean.getSuccess(roleMapper.insert(roleDO) > 0);
    }

    @Override
    public ResponseBean deleteRoles(List<RoleDO> roleDOs) {
        if (roleDOs == null || roleDOs.size() == 0) {
            return responseBean.getError("参数不能为空");
        }
        roleDOs.forEach(item -> {

            RoleDO roleDO1 = roleMapper.selectById(item.getId());

            roleMapper.deleteById(roleDO1.getId());
            // 删除用户角色
            QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(UserRoleDO::getRoleId, item.getId());
            List<UserRoleDO> tmps = userRoleMapper.selectList(queryWrapper);
            tmps.forEach(it -> {
                userRoleMapper.deleteById(it.getId());
            });

        });
        return responseBean.getSuccess(true);
    }

    @Override
    public ResponseBean queryRoles(String name, Integer pageSize, Integer pageNum) {
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        if (name != null && !"".equals(name)) {
            query.lambda().like(RoleDO::getName, name);
        }
       Page result= PageHelper.startPage(pageNum,pageSize).doSelectPage(()->roleMapper.selectList(query));
        return responseBean.getSuccess(new PageInfo<>(result));
    }

    @Override
    public ResponseBean updateRole(RoleDO roleDO) {
        if (roleDO == null || roleDO.getName() == null || roleDO.getName().equals("")) {
            return responseBean.getError("参数不能为空");
        }
        RoleDO roleDO1 = roleMapper.selectById(roleDO.getId());
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        query.lambda().eq(RoleDO::getName, roleDO.getName());
        int count = roleMapper.selectCount(query);
        if (count > 0&&!roleDO1.getName().equals(roleDO.getName())) {
            return responseBean.getError("已存在同名角色");
        }

        roleDO1.setName(roleDO.getName());
        roleDO1.setDesc(roleDO.getDesc());
        return responseBean.getSuccess(roleMapper.updateById(roleDO1) > 0);
    }

    @Override
    public ResponseBean queryRoleEnum() {
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        List<RoleDO> list = roleMapper.selectList(query);
        List<Map> resultData = list.stream().map(item -> {
            Map<String, Object> map = new HashMap();
            map.put("key", item.getId());
            map.put("value", item.getName());
            return map;
        }).collect(Collectors.toList());
        return responseBean.getSuccess(resultData);
    }
}
