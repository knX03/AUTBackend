package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.SingerMapper;
import com.kn.initialmusic.pojo.Singer;
import com.kn.initialmusic.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

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
}
