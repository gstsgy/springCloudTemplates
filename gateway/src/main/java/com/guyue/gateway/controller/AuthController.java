package com.guyue.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guyue.gateway.bean.ResponseBean;
import com.guyue.gateway.bean.UserBean;
import com.guyue.gateway.jwt.JwtModel;
import com.guyue.gateway.jwt.JwtUtil;
import com.guyue.gateway.provide.UserService;

import com.guyue.common.utils.Encrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 描述: 认证接口
 *
 * @Auther: lzx
 * @Date: 2019/7/9 13:53
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private ObjectMapper objectMapper;

    @Autowired
    private ResponseBean responseBean;
    @Autowired
    private UserService userService;


    @Value("${org.my.jwt.effective-time}")
    private String effectiveTime;

    public AuthController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 登陆认证接口
     * @param userDTO
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody UserBean userDTO) throws Exception {
        if(userDTO==null){
            return responseBean.getError("参数不能为空");
        }
        ResponseBean bean = userService.queryUserByCode(userDTO.getCode());
        if(bean.getCode()==-1){
            return bean;
        }else if(bean.getData()==null){
            return responseBean.getError("用户名错误");
        }
        Map<String,Object> map =(Map<String, Object>) bean.getData();
        if(!map.get("passwd").equals(Encrypt.encryptToMD5(userDTO.getPasswd()))){
            return responseBean.getError("密码错误");
        }
        else if((Integer) map.get("status")!=1){
            return responseBean.getError("帐号未启用");
        }


        JwtModel jwtModel = new JwtModel(userDTO.getCode(), userDTO.getPasswd(),Long.parseLong(map.get("id").toString()) );
        int effectivTimeInt = Integer.valueOf(effectiveTime.substring(0,effectiveTime.length()-1));
        String effectivTimeUnit = effectiveTime.substring(effectiveTime.length()-1,effectiveTime.length());
        String jwt = null;
        switch (effectivTimeUnit){
            case "s" :{
                //秒
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 1000L);
                break;
            }
            case "m" :{
                //分钟
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 1000L);
                break;
            }
            case "h" :{
                //小时
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 60L * 1000L);
                break;
            }
            case "d" :{
                //小时
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 24L * 60L * 60L * 1000L);
                break;
            }
        }
        return new ResponseBean(0,jwt,bean.getData());
    }

    /**
     * 为授权提示
     */
    @GetMapping("/unauthorized")
    public ResponseBean unauthorized(){
        return new ResponseBean (HttpStatus.SC_UNAUTHORIZED,"未认证,请重新登陆",null);
    }


    @GetMapping("/menus")
    public ResponseBean getMenus(Integer type, ServerHttpRequest httpRequest) throws Exception {
        //httpRequest.getHeaders()

        String token = httpRequest.getHeaders().getFirst("token");
        //System.out.println("---------------->"+token);
        Long userId = JwtUtil.getUserId(token,objectMapper);

        return userService.queryMenuByRole(type,userId);
    }
    @GetMapping("/funs")
    public ResponseBean getFuns(Integer type, ServerHttpRequest httpRequest) throws Exception {
        //httpRequest.getHeaders()

        String token = httpRequest.getHeaders().getFirst("token");
        //System.out.println("---------------->"+token);
        Long userId = JwtUtil.getUserId(token,objectMapper);

        return userService.queryFunsByRole(type,userId);
    }


   /* *//**
     * jwt 检查注解测试 测试
     * @return
     *//*
    @GetMapping("/testJwtCheck")
    @JwtCheck
    public ResponseBean testJwtCheck(@RequestHeader("Authorization")String token,@RequestParam("name")@Valid String name){

        return new ResponseBean(HttpStatus.SC_OK,"请求成功咯","请求成功咯"+name);

    }*/
}
