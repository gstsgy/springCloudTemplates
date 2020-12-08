package com.guyue.basedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guyue.basedata.bean.db.DictionaryDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DictionaryMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 下午3:00
 **/
@Mapper
@Repository
public interface DictionaryMapper extends BaseMapper<DictionaryDO> {
}
