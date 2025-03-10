package com.kn.initialmusic.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.kn.initialmusic.mapper.SingerMapper;
import com.kn.initialmusic.pojo.*;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.SingerService;
import com.kn.initialmusic.util.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.controller.Code.MAIN_VALUES_NULL;
import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.*;

@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private GenerateIDService generateIDService;

    @Override
    public List<Singer> selectAllSingers() {
        List<Singer> singers = singerMapper.selectAllSingers();
        return singers;
    }

    @Override
    public Singer selectDetailSinger(String singer_ID) {
        Singer singer = singerMapper.selectDetailSinger(singer_ID);
        return singer;
    }

    @Override
    public List<Singer> singerSelector(String langType, String singerType, String alphabet) {
        List<Singer> singers = singerMapper.singerSelector(langType, singerType, alphabet);

        return singers;
    }

    @Override
    public Result getSingerByUser(String user_ID) {
        Result result = new Result();
        Singer singer = singerMapper.getSingerByUser(user_ID);
        if (singer != null) {
            SingerDTO singerDTO = BeanUtil.copyProperties(singer, SingerDTO.class);
            //生成token
            String token = UUID.randomUUID().toString(true);
            //将user信息存到redis
            Map<String, Object> singerMap = BeanUtil.beanToMap(singerDTO);
            String tokenKey = LOGIN_SINGER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey,
                    singerMap);
            stringRedisTemplate.expire(tokenKey, LOGIN_SINGER_TTL, TimeUnit.DAYS);
            result.setCode(SUCCESS);
            result.setData(token);
        } else {
            result.setCode(MAIN_VALUES_NULL);
        }
        return result;
    }

    @Override
    public Boolean sinLogOff(String singer_token) {
        String tokenKey = LOGIN_SINGER_KEY + singer_token;
        Boolean delete = stringRedisTemplate.delete(tokenKey);
        UserHolder.removeUser();
        return delete;
    }

    @Override
    public Result applySinger(Singer singer) {
        Result result = new Result();
        String singerID = generateIDService.generateSingerID();
        singer.setSinger_ID(singerID);
        int flag = singerMapper.applySinger(singer);
        if (flag >= 1) {
            SingerDTO singerDTO = BeanUtil.copyProperties(singer, SingerDTO.class);
            //生成token
            String token = UUID.randomUUID().toString(true);
            //将user信息存到redis
            Map<String, Object> singerMap = BeanUtil.beanToMap(singerDTO);
            String tokenKey = LOGIN_SINGER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey,
                    singerMap);
            stringRedisTemplate.expire(tokenKey, LOGIN_SINGER_TTL, TimeUnit.DAYS);
            result.setCode(SUCCESS);
            result.setData(token);
        } else {
            result.setCode(MAIN_VALUES_NULL);
        }
        return result;
    }

    public Result selectArtistFansData(String singer_ID) {
        Result result = new Result();
        int fansNum = singerMapper.selectArtistFansNum(singer_ID);
        result.setCode(SUCCESS);
        result.data = fansNum;
        return result;
    }

    @Override
    public Result selectArtistWorkData(String singer_ID) {
        Result result = new Result();
        List<Integer> workRawData = singerMapper.selectArtistWorkData(singer_ID);
        //输出：索引0为workNUm，1为AlbumNum
        result.setCode(SUCCESS);
        result.data = workRawData;
        return result;
    }

    @Override
    public Result selectArtistSongData(String singer_ID) {
        List<Song> songs = singerMapper.selectArtistSongData(singer_ID);
        Result result = new Result();
        result.setCode(SUCCESS);
        result.data = songs;
        return result;
    }

    @Override
    public Result selectArtistAlbumData(String singer_ID) {
        List<Album> albums = singerMapper.selectArtistAlbumData(singer_ID);
        Result result = new Result();
        result.setCode(SUCCESS);
        result.data = albums;
        return result;
    }
}
