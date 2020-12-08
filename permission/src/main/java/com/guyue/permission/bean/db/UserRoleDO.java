package com.guyue.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guyue.common.bean.BaseTable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName UserRoleDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:11
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户角色")
@TableName(value = "User_Role")
@Data
public class UserRoleDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;

}
