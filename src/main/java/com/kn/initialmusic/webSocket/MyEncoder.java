/*
package com.kn.initialmusic.webSocket;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MyEncoder implements Encoder.Text<ArrayList> {
    private static final Logger log = LoggerFactory.getLogger(MyEncoder.class);

    */
/**
     * 这里的参数要和Encoder.Text<T>保持一致
     *
     * @param list
     * @return
     * @throws EncodeException
     *//*

    @Override
    public String encode(ArrayList list) throws EncodeException {
        */
/*
         * 只需要返回Object序列化后的json字符串就行
         * 你也可以使用gosn，fastJson来序列化
         *//*

        try {
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(list);
        } catch (Exception e) {
            log.info("ServerEncoder编码异常:{}", e.getMessage());
        }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //可忽略
    }

    @Override
    public void destroy() {
        //可忽略
    }
}

*/
