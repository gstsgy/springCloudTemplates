package com.guyue.permission.service;

import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.permission.bean.db.RoleDO;
import com.guyue.common.bean.ResponseBean;

import java.util.List;

/**
 * @ClassName UserRoleService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 上午10:46
 **/
public interface UserRoleService {
    ResponseBean queryRoleByUser(Long userId);

    ResponseBean queryUserByRole(Long roleId);

    ResponseBean addRoleByUser(List<RoleDO> roles, Long userId);


    ResponseBean addUserByRole(List<OperatorDO> users, Long roleId);

    ResponseBean queryRoles();

    ResponseBean queryUsers();
}
