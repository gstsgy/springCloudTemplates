package com.guyue.permission.service;

import com.guyue.permission.bean.db.MenuDO;
import com.guyue.common.bean.ResponseBean;

import java.util.List;

/**
 * @ClassName MenuService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午4:16
 **/
public interface MenuService {
    ResponseBean addMenu(MenuDO menuDO);

    void deleteMenu(List<MenuDO> menuDO);

    ResponseBean queryMenu(MenuDO menuDO, Integer pageSize,Integer pageNum);

    ResponseBean updateMenu(MenuDO menuDO);

    List<MenuDO> queryChildren(Long parentId,Integer type);
}
