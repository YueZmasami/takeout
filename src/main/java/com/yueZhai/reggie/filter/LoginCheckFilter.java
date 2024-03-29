package com.yueZhai.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.yueZhai.reggie.common.BaseContext;
import com.yueZhai.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(filterName="loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

       String requestURI= request.getRequestURI();
       // 不需要过滤的路径
       String[] urls=new String[]{
               "/employee/login", "/employee/logout","/backend/**","/front/**","/common/**", "/user/sendMsg","/user/login"
       };
       // 判断是否需要处理，如果和数组中的一样，直接放行
      boolean check=check(urls, requestURI);
      if(check){
          log.info("本次请求 {} 不需要处理",requestURI);
          filterChain.doFilter(request,response);
          return;
      }
      //后台判断用户是否登陆
      if(request.getSession().getAttribute("employee")!=null){
          log.info("用户已登陆，用户ID为：{}", request.getSession().getAttribute("employee"));
         Long empId=(Long) request.getSession().getAttribute("employee");
          BaseContext.setCurrentId(empId);
        filterChain.doFilter(request,response);
        return;
      }
      //前台判断用户是否登陆
        if(request.getSession().getAttribute("user")!=null){
            log.info("用户已登陆，用户ID为：{}", request.getSession().getAttribute("user"));
            Long userId=(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }




        log.info("用户未登陆");
      // 通过输出流的方式向客户端页面响数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

       // log.info("拦截到请求,{}",request.getRequestURI());






    }
    public boolean check(String [] urls, String requestURI){
        for(String url: urls){
            boolean match=PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;

    }
}
