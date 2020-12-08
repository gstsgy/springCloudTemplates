package com.guyue.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guyue.common.bean.BaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName MenuDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:16
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel("菜单")
@TableName(value = "menu")
@Data
public class MenuDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "名称",required = true)
    private String name;
    @ApiModelProperty(value = "菜单级别",required = true)
    private String level;
    @ApiModelProperty("父菜单id")
    private Long parentId;
    @ApiModelProperty("菜单图标")
    private String ico;
    @ApiModelProperty("菜单路由")
    private String path;
    @ApiModelProperty("菜单位置")
    private String posi;
    @ApiModelProperty(value = "菜单类型",required = true)
    private Integer type;

}
