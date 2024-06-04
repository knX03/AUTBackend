package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    private Integer code;
    private Boolean flag;
    private String msg;
    private Object data;

}
