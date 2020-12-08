package com.guyue.permission.controller;

/*import com.sr.suray.wms.common.dbutil.DbUtilService;
import com.sr.suray.wms.common.sequence.SnowFlake;*/
import com.guyue.common.sequence.SnowFlake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("test")
@RestController
@Api(tags = "测试")
public class TestController {


    @ApiOperation("测试接口1")
    @GetMapping("t1")
    public String test(){
        return "hello world";
    }
    @ApiOperation("测试接口2")
    @GetMapping("t2")
    public String test2(){
        return SnowFlake.nextId()+"";
    }
}
