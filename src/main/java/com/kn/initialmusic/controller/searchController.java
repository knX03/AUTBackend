package com.kn.initialmusic.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.Impl.SearchServiceImpl;
import com.kn.initialmusic.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class searchController {

    @Autowired
    private SearchServiceImpl searchService;

    @GetMapping("/{searchValue}")
    public Result searchString(@PathVariable String searchValue) {
        System.out.println(searchValue);
        return searchService.searchString(searchValue);
    }

    @GetMapping("/searchDetail")
    public Result searchDetail(@RequestParam("searchValue") String searchValue) {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        return searchService.cacheSearchHistory(searchValue, user_ID);
    }

    @GetMapping("/searchHistory")
    public Result getSearchHistory() {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        return searchService.getSearchHistory(user_ID);
    }

    //todo 搜索热度榜
    @GetMapping("/hotSearch")
    public Result getHotSearch() {
        return searchService.getHotSearch();
    }

    @PutMapping("/delSearchHistory")
    public Result delSearchHistory() {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        return searchService.delSearchHistory(user_ID);
    }
}
