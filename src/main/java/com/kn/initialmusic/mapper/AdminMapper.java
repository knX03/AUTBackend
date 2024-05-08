package com.kn.initialmusic.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);
}
