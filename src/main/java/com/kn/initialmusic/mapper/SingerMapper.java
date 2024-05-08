package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SingerMapper {
    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);


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
    List<Singer> singerSelector(@Param("langType") String langType,
                                @Param("singerType") String singerType,
                                @Param("alphabet") String alphabet);

}
