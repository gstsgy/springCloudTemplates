package com.guyue.basedata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guyue.basedata.mapper.DictionaryMapper;
import com.guyue.basedata.bean.db.DictionaryDO;
import com.guyue.basedata.service.DictionaryService;
import com.guyue.common.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName DictionaryServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 下午2:23
 **/
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private ResponseBean responseBean;
    @Override
    public ResponseBean addDict(DictionaryDO dictionaryDO) {
        if(dictionaryDO==null||dictionaryDO.getModelCode()==null
                ||dictionaryDO.getDictKey()==null||dictionaryDO.getDictValue()==null){
            return responseBean.getError("参数不能为空");
        }
        QueryWrapper<DictionaryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryDO::getModelCode,dictionaryDO.getModelCode())
                .eq(DictionaryDO::getDictKey,dictionaryDO.getDictKey());


        List<DictionaryDO> list = dictionaryMapper.selectList(queryWrapper);
        if(list.size()>0){
            return responseBean.getError("字典不可重复");
        }
        return responseBean.getSuccess(dictionaryMapper.insert(dictionaryDO)>0);
    }

    @Override
    public ResponseBean deleteDict(List<DictionaryDO> dictionaryDOs) {
        if(dictionaryDOs!=null&&dictionaryDOs.size()>0){
            dictionaryDOs.forEach(item->{
                dictionaryMapper.deleteById(item.getId());

                // 判断是否模块，如果是删除整个模块
                if("9527".equals(item.getModelCode())){
                    QueryWrapper<DictionaryDO> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(DictionaryDO::getModelCode,item.getDictKey());

                    List<DictionaryDO> list = dictionaryMapper.selectList(queryWrapper);
                    list.forEach(it->{
                        dictionaryMapper.deleteById(it.getId());
                    });
                }
            });
        }
        return responseBean.getSuccess(true);
    }

    @Override
    public ResponseBean queryDict(String modelCode,int pageNum,int pageSize) {

        if(modelCode==null||"".equals(modelCode)){
            modelCode = "9527";
        }
        QueryWrapper<DictionaryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryDO::getModelCode,modelCode);

        List<DictionaryDO> list = PageHelper.startPage(pageNum,pageSize).doSelectPage(()->
                dictionaryMapper.selectList(queryWrapper));


        return responseBean.getSuccess(new PageInfo<>(list));
    }

    @Override
    public ResponseBean updateDict(DictionaryDO dictionaryDO) {
        if(dictionaryDO==null||dictionaryDO.getModelCode()==null
                ||dictionaryDO.getDictKey()==null||dictionaryDO.getDictValue()==null){
            return responseBean.getError("参数不能为空");
        }
        if(dictionaryDO.getModelCode().equals("9527")){
            DictionaryDO tmp1 = dictionaryMapper.selectById(dictionaryDO.getId());
            tmp1.setDictValue(dictionaryDO.getDictValue());
            dictionaryMapper.updateById(tmp1);
        }else{
            QueryWrapper<DictionaryDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(DictionaryDO::getModelCode,dictionaryDO.getModelCode())
                    .eq(DictionaryDO::getDictKey,dictionaryDO.getDictKey());


            List<DictionaryDO> list = dictionaryMapper.selectList(queryWrapper);
            if(list.size()>0){
                return responseBean.getError("字典不可重复");
            }
            dictionaryMapper.updateById(dictionaryDO);
        }
        return responseBean.getSuccess(true);
    }

    @Override
    public ResponseBean queryDictEnum(String modelCode) {
        if(modelCode==null||"".equals(modelCode)){
            modelCode = "9527";
        }
        QueryWrapper<DictionaryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryDO::getModelCode,modelCode);

        List<DictionaryDO> list = dictionaryMapper.selectList(queryWrapper);
        List<Map<String,Object>> resultData = list.stream().map(item->{
            HashMap<String,Object> map = new HashMap();
            map.put("key",item.getDictKey().matches("\\d *")?
                    Integer.parseInt(item.getDictKey()):item.getDictKey());
            map.put("value",item.getDictValue());
            return map;
        }).collect(Collectors.toList());
        return responseBean.getSuccess(resultData);
    }
}
