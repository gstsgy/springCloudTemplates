package com.guyue.permission.controller;

import com.guyue.permission.bean.db.MenuDO;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午4:16
 **/
@RestController
@Api(tags = "菜单")
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation("新增一个菜单")
    @PostMapping("menu")
    public ResponseBean addMenu(@RequestBody MenuDO menuDO) {
        return menuService.addMenu(menuDO);
    }
    @ApiOperation("删除菜单")
    @DeleteMapping("menus")
    public ResponseBean deleteMenus(@RequestBody List<MenuDO> menuDOS) {
        if(menuDOS==null||menuDOS.size()==0){
            return new ResponseBean(-1,"参数不能为空",null);
        }
        menuService.deleteMenu(menuDOS);
        return new ResponseBean(0,"",true);
    }
    @ApiOperation("查询菜单")
    @GetMapping("menus")
    public ResponseBean queryMenu(MenuDO menuDO, Integer pageSize,Integer pageNum) {

        return menuService.queryMenu( menuDO,  pageSize, pageNum);
    }

    @ApiOperation("修改一个菜单")
    @PutMapping("menu")
    public ResponseBean updateMenu(@RequestBody MenuDO menuDO) {
        return menuService.updateMenu(menuDO);
    }
}
