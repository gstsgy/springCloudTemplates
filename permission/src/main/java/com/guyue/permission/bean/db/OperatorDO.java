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
 * @ClassName OperatorDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:05
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户")
@Data
@TableName("Operator")
public class OperatorDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "昵称",required = true)
    private String nickName;
    @ApiModelProperty(value = "密码")
    private String passwd;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "生日")
    private String birthday;
    @ApiModelProperty(value = "性别",example = "1",required = true)
    private Integer gender;
    @ApiModelProperty(value = "职位")
    private String position;
    @ApiModelProperty(value = "部门",example = "1")
    private Long deptId;
    @ApiModelProperty(value = "用户名",required = true)
    private String code;
    @ApiModelProperty(value = "是否启用",example = "1",required = true)
    private Integer status=1;
}
