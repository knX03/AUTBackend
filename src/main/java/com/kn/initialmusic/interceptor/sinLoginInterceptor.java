package com.kn.initialmusic.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.kn.initialmusic.pojo.Singer;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.util.SingerHolder;
import com.kn.initialmusic.util.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.util.RedisConstants.*;

@Component
public class sinLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization-singer");
        if (StrUtil.isBlank(token)) {
            response.setStatus(401);
            return false;
        }
        //基于token获取用户信息
        String tokenKey = LOGIN_SINGER_KEY + token;
        Map<Object, Object> singerMap = redisTemplate.opsForHash().entries(tokenKey);
        if (singerMap.isEmpty()) {
            response.setStatus(401);
            return false;
        }
        //转化user对象
        Singer singer = BeanUtil.fillBeanWithMap(singerMap, new Singer(), false);

        SingerHolder.saveSinger(singer);

        redisTemplate.expire(tokenKey, LOGIN_SINGER_TTL, TimeUnit.DAYS);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
