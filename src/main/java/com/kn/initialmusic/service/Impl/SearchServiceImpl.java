package com.kn.initialmusic.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.mapper.SearchMapper;
import com.kn.initialmusic.pojo.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.kn.initialmusic.controller.Code.MAIN_VALUES_NULL;
import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.CACHE_SEARCH_KEY;
import static com.kn.initialmusic.util.RedisConstants.CACHE_USER_SEARCH_KEY;
import static com.kn.initialmusic.util.songUtil.searchValue;

@Service
public class SearchServiceImpl {

    @Autowired
    private SearchMapper searchMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public Result searchString(String searchValue) {
        Result result = new Result();
        List<String> searchList = new ArrayList<>();
        String songJson = stringRedisTemplate.opsForValue().get(CACHE_SEARCH_KEY);
        if (songJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(songJson);
            searchList = JSONUtil.toList(array, String.class);
            result.setCode(SUCCESS);
            result.setData(searchValue(searchList, searchValue));
            return result;
        }
        List<List<String>> strings = searchMapper.searchString(searchValue);
        for (List<String> list : strings) {
            if (list != null && list.size() > 0) {
                searchList.addAll(list);
            }
        }
        stringRedisTemplate.opsForValue().set(CACHE_SEARCH_KEY, JSONUtil.toJsonStr(searchList));
        result.setCode(SUCCESS);
        result.setData(searchValue(searchList, searchValue));
        return result;
    }

    public Result cacheSearchHistory(String searchValue, String userID) {
        Result result = new Result();
        String key_searchHistory = CACHE_USER_SEARCH_KEY + userID;
        stringRedisTemplate.opsForSet().add(key_searchHistory, searchValue);
        result.setCode(SUCCESS);
        return result;
    }

    public Result getSearchHistory(String userID) {
        Result result = new Result();
        String key_searchHistory = CACHE_USER_SEARCH_KEY + userID;
        Set<String> strings = stringRedisTemplate.opsForSet().members(key_searchHistory);
        if (strings != null) {
            List<String> searchHistory = new ArrayList<>(strings);
            result.setCode(SUCCESS);
            result.setData(searchHistory);
        } else {
            result.setCode(MAIN_VALUES_NULL);
        }
        return result;
    }

    public Result getHotSearch() {
        Result result = new Result();
        result.setCode(SUCCESS);
        return result;
    }


    @Transactional
    public Result delSearchHistory(String userID) {
        Result result = new Result();
        String key_searchHistory = CACHE_USER_SEARCH_KEY + userID;
        Boolean delete = stringRedisTemplate.delete(key_searchHistory);
        if (delete) {
            result.setCode(SUCCESS);
        }
        return result;
    }
}
