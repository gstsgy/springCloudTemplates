package com.guyue.common.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName BaseTable
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/10 上午9:45
 **/
@ApiModel
@Data
public abstract class BaseTable {
    @ApiModelProperty("录入时间")
    @TableField(fill = FieldFill.INSERT)
    private Date insertYmd;//

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("录入人Id")
    private String insertId;//

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private Date updateYmd;//


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新人Id")
    private String updateId;

    @ApiModelProperty("备注")
    private String remark;//备用字段
    @ApiModelProperty("备用字段")
    private String remark1;//备用字段
    @ApiModelProperty("备用字段")
    private String remark2;//备用字段
    @ApiModelProperty("备用字段")
    private String remark3;//备用字段
    @ApiModelProperty("备用字段")
    private String remark4;//备用字段
    @ApiModelProperty("备用字段")
    private String remark5;//备用字段
    @ApiModelProperty(value = "显示顺序",example = "0")
    private Integer seq;//
    @ApiModelProperty(value = "是否有效",example = "1")
    private Integer effective = 1;//


}
