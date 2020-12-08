package com.guyue.permission.controller;

import com.guyue.permission.bean.db.OperatorDO;
import com.guyue.common.bean.ResponseBean;
import com.guyue.permission.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:27
 **/
@RestController
@Api(tags = "用户管理")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("新增用户")
    @PostMapping("/user")
    public ResponseBean addUser(@RequestBody OperatorDO operatorDO) {
        return userService.addUser(operatorDO);
    }
    @ApiOperation("删除用户")
    @DeleteMapping("/users")
    public ResponseBean deleteUser(@RequestBody List<OperatorDO> operatorDOs) {
        return userService. deleteUser( operatorDOs);
    }
    @ApiOperation("查询用户")
    @GetMapping("/users")
    public ResponseBean queryUser(OperatorDO operatorDO,Integer pageNum,Integer pageSize) {
        return userService.queryUser( operatorDO, null, pageNum, pageSize);
    }
    @ApiOperation("修改用户")
    @PutMapping("/user")
    public ResponseBean updateUser(@RequestBody OperatorDO operatorDO) {
        return userService.updateUser( operatorDO);
    }
    @ApiOperation("获取用户职位")
    @GetMapping("/userposition")
    public ResponseBean getUserPosition() {
        return userService.getUserPosition();
    }

    @ApiOperation("修改密码")
    @PutMapping("/userpw")
    public ResponseBean updateUserpw(@RequestBody OperatorDO user,@ApiParam("邮箱验证码") Integer mailcode) {
        return userService.updateUserpw(user,mailcode);
    }


    /**
     * 发送邮件
     *
     * @param code 用户名
     * @author hujj
     */
    @ApiOperation("发送邮箱验证码")
    @PostMapping("/mailcode")
    public ResponseBean sendMail(String code) {
        return userService.sendMail(code);

    }


    @ApiOperation("根据用户名查询用户")
    @GetMapping("/user")
    public ResponseBean queryUserByCode(String code) {
        return userService.queryOne(code);
    }


    @ApiOperation("查询用户枚举")
    @GetMapping("/userenum")
    public ResponseBean queryUserEnum(@ApiParam("部门id，注意大小写") Long deptId) {
        return userService.queryUserEnum(deptId);
    }

    /**
     * 重置密码
     *
     * @author guyue
     */
    @PostMapping("/restpassword")
    public ResponseBean restpassword(@RequestBody OperatorDO user) {
        return userService.restpassword(user);
    }
}
