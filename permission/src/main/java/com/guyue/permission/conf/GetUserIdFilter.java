package com.guyue.permission.conf;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName GetUserIdFilter
 * @Description TODO
 * @Author guyue
 * @Date 2020/11/4 下午4:32
 **/
@WebFilter(urlPatterns = "/*", filterName = "getuseridfilter")
public class GetUserIdFilter implements Filter {

    private static ConcurrentHashMap<Long, String> concurrentHashMap = new ConcurrentHashMap();

    public static String getUser(Long thredId) {
        return concurrentHashMap.get(thredId);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String userId = req.getHeader("userId");
       // System.out.println(userId);
        concurrentHashMap.put(Thread.currentThread().getId(), userId);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
