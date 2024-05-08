package com.kn.initialmusic.pojo;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {

    private Integer id;
    private String admin_ID;
    private String admin_Name;
    private String admin_Email;
    private String admin_Password;
}
