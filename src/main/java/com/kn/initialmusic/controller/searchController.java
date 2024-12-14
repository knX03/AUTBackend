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

    //todo 实时搜索（全文检索）
    @GetMapping("/{searchValue}")
    public Result searchString(@PathVariable String searchValue) {
        return searchService.searchString(searchValue);
    }

    //保存搜索历史
    @GetMapping("/aSaveSearchHS")
    public Result aSaveSearchHS(@RequestParam("searchValue") String searchValue) {
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

    //todo 搜索热度榜（全文搜索权重进行排行）
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

    @GetMapping("/searchALL")
    public Result searchALL(@RequestParam("searchValue") String searchValue) {
        return searchService.searchALL(searchValue);
    }
}
