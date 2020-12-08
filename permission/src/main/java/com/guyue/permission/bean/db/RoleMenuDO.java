package com.guyue.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guyue.common.bean.BaseTable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RoleMenuDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:14
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel("角色菜单")
@TableName(value = "Role_menu")
@Data
public class RoleMenuDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long menuId;

    private Long roleId;
}
