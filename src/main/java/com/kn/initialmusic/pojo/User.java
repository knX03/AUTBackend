package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Integer id;
    private String user_ID;
    private String user_Name;
    private String user_Email;
    private String user_Password;
    private String user_Sex;
    private Integer user_Age;
    private String user_Avatar;
    private String user_Birthday;
    private String user_Introduction;


    public User(String user_Name, String user_Email, String user_Password, String user_Sex, Integer user_Age) {
        this.user_Name = user_Name;
        this.user_Email = user_Email;
        this.user_Password = user_Password;
        this.user_Sex = user_Sex;
        this.user_Age = user_Age;
    }

}
