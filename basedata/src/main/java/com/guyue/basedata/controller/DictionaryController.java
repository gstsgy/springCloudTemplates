package com.guyue.basedata.controller;

import com.guyue.basedata.service.DictionaryService;
import com.guyue.basedata.bean.db.DictionaryDO;
import com.guyue.common.bean.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName DictionaryController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 下午2:21
 **/
@RequestMapping("dictionary")
@RestController
@Api(tags = "数据字典")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("新增字典")
    @PostMapping("dict")
    public ResponseBean addDict(@RequestBody DictionaryDO dictionaryDO) {
        return dictionaryService.addDict(dictionaryDO);
    }

    @ApiOperation("删除字典")
    @DeleteMapping("dicts")
    public ResponseBean deleteDict(@RequestBody List<DictionaryDO> dictionaryDOs) {
        return dictionaryService.deleteDict(dictionaryDOs);
    }

    @ApiOperation("查询字典")
    @GetMapping("dicts")
    public ResponseBean queryDict(@ApiParam("模块code") String modelCode,int pageNum,int pageSize) {
        return dictionaryService.queryDict( modelCode,pageNum,pageSize);
    }



    @ApiOperation("查询字典枚举")
    @GetMapping("dictsenum")
    public ResponseBean queryDictEnum(@ApiParam("模块code") String modelCode) {
        return dictionaryService.queryDictEnum( modelCode);
    }

    @ApiOperation("修改字典")
    @PutMapping("dict")
    public ResponseBean updateDict(@RequestBody DictionaryDO dictionaryDO) {
        return dictionaryService.updateDict( dictionaryDO);
    }


}
