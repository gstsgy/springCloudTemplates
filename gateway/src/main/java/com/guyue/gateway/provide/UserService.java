package com.guyue.gateway.provide;

import com.guyue.gateway.bean.ResponseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 上午8:57
 **/
@FeignClient(name = "permission")
public interface UserService {
    @GetMapping("/user/user")
    ResponseBean queryUserByCode(@RequestParam("code") String code);

    @GetMapping("/rolemenu/usermenutree")
    ResponseBean queryMenuByRole(@RequestParam("type")Integer type,@RequestParam("userId")Long userId);

    @GetMapping("/rolemenu/usermenufuns")
    ResponseBean queryFunsByRole(@RequestParam("type")Integer type,@RequestParam("userId")Long userId);
}
