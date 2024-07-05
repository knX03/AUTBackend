package com.kn.initialmusic.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {

    /**
     * 搜索功能
     *
     * @return
     */
    List<List<String>> searchString(String searchValue);
}
