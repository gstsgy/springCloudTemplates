package com.guyue.permission.controller;

import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleMenuController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:39
 **/
@RestController
@Api(tags = "角色菜单相关")
@RequestMapping("/rolemenu")
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("menutree")
    @ApiOperation("获取所有菜单树")
    public ResponseBean queryMenuTree(@ApiParam(value = "菜单类型", required = true) Integer type) {
        return roleMenuService.getAllMenuTree(type);
    }

    @GetMapping("menus")
    @ApiOperation("根据角色获取所有菜单id")
    public ResponseBean getAllMenuByRole(@ApiParam(value = "角色id", required = true)Long roleId,
                                         @ApiParam(value = "菜单类型", required = true) Integer type) {
        return roleMenuService.getAllMenuByRole(roleId,type);
    }

    @PostMapping("rolemenu")
    @ApiOperation("保存角色菜单")
    public ResponseBean saveRoleMenu(@ApiParam(value = "角色id 通过param传", required = true)Long roleId,
                                     @ApiParam(value = "menu ID 通过data传", required = true)
                                     @RequestBody List<Long> menus) {
        return roleMenuService.saveMenu(roleId,menus);
    }


    @GetMapping("usermenutree")
    //@ApiOperation("根据用户获取所有菜单树")
    public ResponseBean queryMenuTree(Integer type,Long userId) {

        return roleMenuService.getAllMenuTree(type,userId);
    }

    @GetMapping("usermenufuns")
    //@ApiOperation("根据用户获取所有菜单树")
    public ResponseBean queryMenuFuns(Integer type,Long userId) {

        return roleMenuService.getAllFuns(type,userId);
    }
}
