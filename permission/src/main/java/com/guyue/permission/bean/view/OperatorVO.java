package com.guyue.permission.bean.view;

import com.guyue.permission.bean.db.OperatorDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OperatorVO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午1:58
 **/
@Data
@ApiModel
public class OperatorVO extends OperatorDO {
    @ApiModelProperty("部门名称")
    private String deptName;
}
