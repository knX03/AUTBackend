package com.kn.initialmusic.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageBean<T> {
    //总记录数
    private int totalCount;
    //当前页数据
    private List<T> rows;

    public PageBean() {
    }

    public PageBean(int totalCount, List<T> rows) {
        this.totalCount = totalCount;
        this.rows = rows;
    }

}
