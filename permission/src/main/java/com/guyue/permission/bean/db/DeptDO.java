package com.guyue.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guyue.common.bean.BaseTable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName DeptDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 上午9:37
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "部门")
@TableName(value = "dept")
@Data
public class DeptDO extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

}
