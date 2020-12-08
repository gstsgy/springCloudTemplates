package com.guyue.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guyue.common.bean.BaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RoleDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:11
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel("角色")
@TableName(value = "role")
@Data
public class RoleDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("描述")
    @TableField("`desc`")
    private String desc;

}
