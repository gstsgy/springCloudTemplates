package com.guyue.basedata.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
@Api(tags = "测试")
public class TestController {
    @ApiOperation("测试")
    @GetMapping("t1")
    public String test() {
        return "hello world";
    }
}
