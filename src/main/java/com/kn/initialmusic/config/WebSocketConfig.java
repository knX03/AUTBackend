package com.kn.initialmusic.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.util.UserHolder;
import com.kn.initialmusic.webSocket.MyWebSocketHandler;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.util.RedisConstants.LOGIN_USER_KEY;
import static com.kn.initialmusic.util.RedisConstants.LOGIN_USER_TTL;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws/server")//设置连接路径和处理
                .setAllowedOrigins("*")
                .addInterceptors(new MyWebSocketInterceptor());//设置拦截器
    }

    /**
     * 自定义拦截器拦截WebSocket请求
     */
    class MyWebSocketInterceptor implements HandshakeInterceptor {
        //前置拦截一般用来注册用户信息，绑定 WebSocketSession
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            if (!(request instanceof ServletServerHttpRequest)) return true;
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
            String token = servletRequest.getHeader("Sec-WebSocket-Protocol");
            if (StrUtil.isBlank(token)) {
                servletResponse.setStatus(401);
                return false;
            }
            //基于token获取用户信息
            String tokenKey = LOGIN_USER_KEY + token;
            Map<Object, Object> userMap = redisTemplate.opsForHash().entries(tokenKey);
            if (userMap.isEmpty()) {
                servletResponse.setStatus(401);
                return false;
            }
            //转化user对象
            User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);

            UserHolder.saveUser(user);

            redisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.DAYS);

            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {
            HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
            if (StringUtils.isNotEmpty(httpRequest.getHeader("Sec-WebSocket-Protocol"))) {
                httpResponse.addHeader("Sec-WebSocket-Protocol", httpRequest.getHeader("Sec-WebSocket-Protocol"));
            }
        }

    }
}
