package com.guyue.permission.controller;

import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.permission.bean.db.RoleDO;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserRoleController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 上午10:45
 **/
@RestController
@Api(tags = "用户角色相关")
@RequestMapping("/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("roles")
    @ApiOperation("通过用户查角色")
    public ResponseBean queryRoleByUser(@ApiParam(value = "用户id",required = true) Long userId){
       return  userRoleService.queryRoleByUser(userId);
    }


    @GetMapping("users")
    @ApiOperation("通过角色查用户")
    public ResponseBean queryUserByRole(@ApiParam(value = "角色id",required = true) Long roleId){
        return  userRoleService.queryUserByRole(roleId);
    }


    @PostMapping("roleuser")
    @ApiOperation("给用户加角色")
    public ResponseBean addRoleByUser(@RequestBody List<RoleDO> roles,
                                      @ApiParam(value = "用户id url传递",required = true)Long userId){
        return  userRoleService.addRoleByUser( roles,  userId);
    }


    @PostMapping("userrole")
    @ApiOperation("给角色加用户")
    public ResponseBean addUserByRole(@RequestBody List<OperatorDO> users,
                                      @ApiParam(value = "角色id  url传递",required = true)Long roleId){
        return  userRoleService.addUserByRole( users,  roleId);
    }


    @GetMapping("allroles")
    @ApiOperation("获取所有角色")
    public ResponseBean queryRoles(){
        return  userRoleService.queryRoles();
    }


    @GetMapping("allusers")
    @ApiOperation("获取所有用户")
    public ResponseBean queryUsers(){
        return  userRoleService.queryUsers();
    }

}
