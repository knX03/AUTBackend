package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Singer {
    private Integer id;
    private String singer_ID;
    private String singer_Name;
    private Integer singer_Age;
    private String singer_Sex;
    private String singer_Introduction;
    private String singer_Avatar;
    private String singer_Lang;
    private String singer_Type;
}
