package com.kn.initialmusic.config;

import com.kn.initialmusic.interceptor.crossInterceptor;
import com.kn.initialmusic.interceptor.loginInterceptor;
import com.kn.initialmusic.interceptor.sinLoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("deprecation")
@SpringBootConfiguration
public class SpringMVCConfig implements WebMvcConfigurer {
    @Autowired
    private crossInterceptor crossInterceptor;

    @Autowired
    private loginInterceptor loginInterceptor;

    @Autowired
    private sinLoginInterceptor sinLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns(
                "/album/likeAlbum", "/album/ifCollectAlbum", "/album/collectAlbum", "/album/deleteLikeAlbum",
                "/comment/postComment",
                "/message/delMess", "/message/getUserMessages", "/message/UserMess", "/message/getSysMess",
                "/song/SongByUser", "/song/selectLikeSong", "/song/likeSong", "/song/deleteLikeSong",
                "/songPlaylist/collectPlaylist", "/songPlaylist/ifCollectPlaylist", "/songPlaylist/changePlaylistInfo",
                "/songPlaylist/deleteLikePlaylist", "/songPlaylist/ifMyPlaylist", "/songPlaylist/createPlaylist",
                "/songPlaylist/likePlaylist", "/songPlaylist/createNewPlaylist", "/songPlaylist/DEPlaylist",
                "/user/changeUserInfo", "/user/logOff", "/user/userDetail",
                "/user/userFollowFan", "/user/userUnfollowFan", "/user/ifFollowUser", "/user/followUser",
                "/user/getSumFollowAndFan", "/user/getUserFans", "/user/getUserFollow", "/user/ifMy",
                "/singer/getSingerByUser", "/singer/applySinger",
                "/search/searchDetail", "/search/searchHistory", "/search/delSearchHistory",
                "ws://localhost/message/**");

        registry.addInterceptor(sinLoginInterceptor).addPathPatterns(
                "/album/getUserAlbums", "/file/uploadSong", "/singer/getArtists",
                "/singer/aSelectArtistsFansData", "/singer/aSelectWorkData", "/singer/aWorkBySinger");
    }

}
