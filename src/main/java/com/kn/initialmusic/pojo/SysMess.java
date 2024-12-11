package com.kn.initialmusic.pojo;

import lombok.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SysMess {
    private String mess_title;
    private String mess_text;
    @DateTimeFormat(pattern = "yyy-MM-dd HH:mm:ss")
    //解决入参时时间格式的调整
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    //解决出参时的时间格式调整
    private Date mess_time;
}
