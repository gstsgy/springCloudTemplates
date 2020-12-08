package com.guyue.permission.controller;

import com.guyue.permission.bean.db.RoleDO;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午3:15
 **/
@RestController
@Api(tags = "角色")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @ApiOperation("新增一个角色")
    @PostMapping("role")
    public ResponseBean addRole(@RequestBody RoleDO roleDO) {
        return roleService.addRole(roleDO);
    }
    @ApiOperation("删除角色")
    @DeleteMapping("roles")
    public ResponseBean deleteRoles(@RequestBody List<RoleDO> roleDOs) {
        return roleService.deleteRoles(roleDOs);
    }

    @ApiOperation("查询角色")
    @GetMapping("roles")
    public ResponseBean queryRole(@ApiParam("角色名称") String name, Integer pageSize, Integer pageNum) {

        return roleService.queryRoles(name,pageSize,pageNum);
    }
    @ApiOperation("修改一个角色")
    @PutMapping("role")
    public ResponseBean updateRole(@RequestBody RoleDO roleDO) {
        return roleService.updateRole(roleDO);
    }
    @ApiOperation("查询角色枚举")
    @GetMapping("roleenum")
    public ResponseBean queryRoleEnum() {

        return roleService.queryRoleEnum();
    }

}
