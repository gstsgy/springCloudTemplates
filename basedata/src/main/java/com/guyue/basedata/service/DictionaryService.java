package com.guyue.basedata.service;

import com.guyue.basedata.bean.db.DictionaryDO;
import com.guyue.common.bean.ResponseBean;

import java.util.List;

/**
 * @ClassName DictionaryService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 下午2:22
 **/
public interface DictionaryService {
    ResponseBean addDict(DictionaryDO dictionaryDO);

    ResponseBean deleteDict(List<DictionaryDO> dictionaryDO);

    ResponseBean queryDict(String modelCode,int pageNum,int pageSize);

    ResponseBean updateDict(DictionaryDO dictionaryDO);

    ResponseBean queryDictEnum(String modelCode);
}
