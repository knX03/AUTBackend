package com.kn.initialmusic.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.mapper.SearchMapper;
import com.kn.initialmusic.pojo.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.controller.Code.MAIN_VALUES_NULL;
import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.*;
import static com.kn.initialmusic.util.songUtil.searchValue;

@Service
public class SearchServiceImpl {

    @Autowired
    private SearchMapper searchMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //todo 实时检索需要重构（使用缓存）
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
        List<List<String>> strings = searchMapper.searchString();
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
        stringRedisTemplate.opsForList().remove(key_searchHistory, 0, searchValue);
        stringRedisTemplate.opsForList().leftPush(key_searchHistory, searchValue);
        result.setCode(SUCCESS);
        return result;
    }

    public Result getSearchHistory(String userID) {
        Result result = new Result();
        String key_searchHistory = CACHE_USER_SEARCH_KEY + userID;
        List<String> strings = stringRedisTemplate.opsForList().range(key_searchHistory, 0, -1);
        if (strings != null) {
            List<String> searchHistory = new ArrayList<>(strings);
            result.setCode(SUCCESS);
            result.setData(searchHistory);
        } else {
            result.setCode(MAIN_VALUES_NULL);
        }
        return result;
    }

    //todo 搜索热度榜(根据热度排列)
    @Transactional
    public Result getHotSearch() {
        Result result = new Result();
        List<String> resHot = new ArrayList<>();
        //热度榜缓存
        String HotJson = stringRedisTemplate.opsForValue().get(CACHE_SEARCH_HOT_KEY);
        if (HotJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(HotJson);
            resHot = JSONUtil.toList(array, String.class);
            result.setCode(SUCCESS);
            result.setData(resHot);
            return result;
        }
        //搜索数据缓存
        String searchJson = stringRedisTemplate.opsForValue().get(CACHE_SEARCH_KEY);
        List<String> searchList = new ArrayList<>();
        if (searchJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(searchJson);
            searchList = JSONUtil.toList(array, String.class);
        } else {
            List<List<String>> strings = searchMapper.searchString();
            for (List<String> list : strings) {
                if (list != null && list.size() > 0) {
                    searchList.addAll(list);
                }
            }
            stringRedisTemplate.opsForValue().set(CACHE_SEARCH_KEY, JSONUtil.toJsonStr(searchList));
        }
        HashSet<Integer> set = new HashSet<>();
        while (resHot.size() <= 14) {
            int index = (int) (Math.random() * searchList.size());
            if (set.add(index)) {
                resHot.add(searchList.get(index));
            }
        }
        stringRedisTemplate.opsForValue().set(CACHE_SEARCH_HOT_KEY, JSONUtil.toJsonStr(resHot),
                CACHE_SEARCH_HOT_KEY_TTL, TimeUnit.DAYS);

        result.setCode(SUCCESS);
        result.setData(resHot);
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

    //todo 具体搜索（考虑使用全文检索）
    public Result searchALL(String searchValue) {
        Result result = new Result();
        ArrayList<Object> lists = new ArrayList<>();
        List<Song> songSearch = searchMapper.songSearch(searchValue);
        List<Singer> singerSearch = searchMapper.singerSearch(searchValue);
        List<Album> albumSearch = searchMapper.albumSearch(searchValue);
        List<SongPlaylists> spSearch = searchMapper.spSearch(searchValue);
        List<User> userSearch = searchMapper.userSearch(searchValue);
        lists.add(songSearch);
        lists.add(singerSearch);
        lists.add(albumSearch);
        lists.add(spSearch);
        lists.add(userSearch);
        result.setCode(SUCCESS);
        result.setData(lists);
        return result;
    }
}
