package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    public Integer code;
    public Boolean flag;
    public String msg;
    public Object data;
}
