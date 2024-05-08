package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Singer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SingerService {

    /**
     * 查询所有歌手
     *
     * @return
     */
    List<Singer> selectAllSingers();

    /**
     * 查询歌手的详情
     *
     * @param singer_ID
     * @return
     */
    Singer selectDetailSinger(String singer_ID);

    /**
     * @param langType   歌手语言
     * @param singerType 歌手类型
     * @param alphabet   歌手首字母
     * @return
     */
    List<Singer> singerSelector(String langType, String singerType, String alphabet);
}
