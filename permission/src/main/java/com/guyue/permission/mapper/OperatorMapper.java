package com.guyue.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guyue.permission.bean.db.OperatorDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName OperatorMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:10
 **/
@Mapper
@Repository
public interface OperatorMapper extends BaseMapper<OperatorDO> {
}
