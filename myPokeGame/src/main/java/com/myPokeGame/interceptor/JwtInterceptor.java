package com.myPokeGame.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token=request.getHeader("token");
        try {
            log.info("进入token检测");
            return true;
        }catch (Exception e){
            HashMap<String, String> map=new HashMap<>();
            map.put("msg","验证失败："+e);
            String json=JSON.toJSONString(map);
            response.getWriter().println(json);
        }
        return false;
    }
}
