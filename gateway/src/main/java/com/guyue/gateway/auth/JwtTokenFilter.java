package com.guyue.gateway.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guyue.gateway.bean.ResponseBean;
import com.guyue.gateway.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 描述: JwtToken 过滤器
 *
 * @Auther: lzx
 * @Date: 2019/7/9 15:49
 */
@Component
//读取 yml 文件下的 org.my.jwt
@ConfigurationProperties("org.my.jwt")
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private String[] skipAuthUrls;

    private ObjectMapper objectMapper;

    public String[] getSkipAuthUrls() {
        return skipAuthUrls;
    }

    public void setSkipAuthUrls(String[] skipAuthUrls) {
        this.skipAuthUrls = skipAuthUrls;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();


        //跳过不需要验证的路径
        if (null != skipAuthUrls) {
            List<Pattern> rules = Arrays.stream(skipAuthUrls).map(Pattern::compile).collect(Collectors.toList());
            for (Pattern p : rules) {
                Matcher m = p.matcher(url);
                if (m.matches()) {
                    return chain.filter(exchange);
                }
            }
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");

        ServerHttpResponse resp = exchange.getResponse();
        if (StringUtils.isBlank(token)) {
            //没有token
            return authErro(resp, "请登陆");
        } else {
            //有token
            try {
                JwtUtil.checkToken(token, objectMapper);
                ServerHttpRequest newrequest = exchange.getRequest();
                try {
                    newrequest=  newrequest.mutate().header("userId",JwtUtil.getUserId(token,objectMapper)+"").build();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }



                // 构建新的ServerWebExchange实例
                ServerWebExchange newExchange = exchange.mutate().request(newrequest).response(exchange.getResponse()).build();


                return chain.filter(newExchange);
            } catch (ExpiredJwtException e) {
                // log.error(e.getMessage(),e);
                if (e.getMessage().contains("Allowed clock skew")) {
                    return authErro(resp, "认证过期");
                } else {
                    return authErro(resp, "认证失败");
                }
            } catch (Exception e) {
                //  log.error(e.getMessage(),e);
                return authErro(resp, "认证失败");
            }
        }
    }

    /**
     * 认证错误输出
     *
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp, String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

       /* ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(-2);
        responseBean.setMessage(mess);*/
        ResponseBean returnData = new ResponseBean(org.apache.http.HttpStatus.SC_UNAUTHORIZED, mess, mess);
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(returnData);
        } catch (JsonProcessingException e) {
            // log.error(e.getMessage(),e);
        }
        // String returnStr = JSON.toJSONString(responseBean);

        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
