package com.kn.initialmusic.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.util.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.util.RedisConstants.LOGIN_USER_KEY;
import static com.kn.initialmusic.util.RedisConstants.LOGIN_USER_TTL;

@Component
public class loginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    /*public loginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = new RedisTemplate();
    }*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            response.setStatus(401);
            return false;
        }
        //基于token获取用户信息
        String tokenKey = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = redisTemplate.opsForHash().entries(tokenKey);
        if (userMap.isEmpty()) {
            response.setStatus(401);
            return false;
        }
        //转化user对象
        User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);

        UserHolder.saveUser(user);

        redisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.DAYS);

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
