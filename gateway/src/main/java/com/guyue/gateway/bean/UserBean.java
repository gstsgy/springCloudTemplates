package com.guyue.gateway.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户登录")
public class UserBean {
    @ApiModelProperty("用户登录名")
    private String code;
    @ApiModelProperty("密码")
    private String passwd;
}
