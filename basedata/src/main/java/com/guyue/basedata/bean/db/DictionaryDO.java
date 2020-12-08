package com.guyue.basedata.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guyue.common.bean.BaseTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dictionary
 * @Description 数据字典
 * @Author guyue
 * @Date 2020/10/20 下午2:16
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据字典")
@TableName(value = "dictionary")
@Data
public class DictionaryDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("模块名称")
    private String modelCode;
    @ApiModelProperty("字典key")
    private String dictKey;
    @ApiModelProperty("字典value")
    private String dictValue;
}
