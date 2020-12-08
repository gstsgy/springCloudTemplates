package com.guyue.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.permission.bean.db.RoleDO;
import com.guyue.permission.bean.db.UserRoleDO;
import com.guyue.permission.service.UserRoleService;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.mapper.OperatorMapper;
import com.guyue.permission.mapper.RoleMapper;
import com.guyue.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * @ClassName UserRoleServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 上午10:49
 **/
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ResponseBean responseBean;

    @Override
    public ResponseBean queryRoleByUser(Long userId) {
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId,userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);

        return responseBean.getSuccess(list);
    }

    @Override
    public ResponseBean queryUserByRole(Long roleId) {
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getRoleId,roleId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);

        return responseBean.getSuccess(list);
    }

    @Override
    public ResponseBean addRoleByUser(List<RoleDO> roles, Long userId) {
        if(roles==null||userId==null){
            return responseBean.getError("参数不可为空");
        }

        // 删除原来的
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId,userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);
        list.forEach(item->{

            userRoleMapper.deleteById(item.getId());
        });
        // 新增
        roles.forEach(item->{
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userId);
            userRoleDO.setRoleId(item.getId());
            userRoleMapper.insert(userRoleDO);
        });
        return responseBean.getSuccess(true);
    }

    @Override
    public ResponseBean addUserByRole(List<OperatorDO> users, Long roleId) {
        if(users==null||roleId==null){
            return responseBean.getError("参数不可为空");
        }

        // 删除原来的
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getRoleId,roleId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);
        list.forEach(item->{

            userRoleMapper.deleteById(item.getId());
        });
        // 新增
        users.forEach(item->{
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(item.getId());
            userRoleDO.setRoleId(roleId);
            userRoleMapper.insert(userRoleDO);
        });
        return responseBean.getSuccess(true);
    }

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public ResponseBean queryRoles() {
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        List<RoleDO> list = roleMapper.selectList(query);
        list.sort(Comparator.comparing(RoleDO::getName));

        return responseBean.getSuccess(list);
    }

    @Override
    public ResponseBean queryUsers() {
        QueryWrapper<OperatorDO> query = new QueryWrapper<>();
        query.lambda().ne(OperatorDO::getId,1);
        List<OperatorDO> list = operatorMapper.selectList(query);
        list.sort(Comparator.comparing(OperatorDO::getNickName));

        return responseBean.getSuccess(list);
    }
}
