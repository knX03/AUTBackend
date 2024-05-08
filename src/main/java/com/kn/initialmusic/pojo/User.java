package com.kn.initialmusic.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    public User() {
    }

    public User(Integer id, String user_ID, String user_Name, String user_Email, String user_Password, String user_Sex, Integer user_Age, String user_Avatar, String user_Birthday, String user_Introduction) {
        this.id = id;
        this.user_ID = user_ID;
        this.user_Name = user_Name;
        this.user_Email = user_Email;
        this.user_Password = user_Password;
        this.user_Sex = user_Sex;
        this.user_Age = user_Age;
        this.user_Avatar = user_Avatar;
        this.user_Birthday = user_Birthday;
        this.user_Introduction = user_Introduction;
    }

    public User(String user_Name, String user_Email, String user_Password, String user_Sex, Integer user_Age) {
        this.user_Name = user_Name;
        this.user_Email = user_Email;
        this.user_Password = user_Password;
        this.user_Sex = user_Sex;
        this.user_Age = user_Age;
    }

    /*    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }

    public String getUser_Sex() {
        return user_Sex;
    }

    public void setUser_Sex(String user_Sex) {
        this.user_Sex = user_Sex;
    }

    public Integer getUser_Age() {
        return user_Age;
    }

    public void setUser_Age(Integer user_Age) {
        this.user_Age = user_Age;
    }

    public String getUser_Avatar() {
        return user_Avatar;
    }

    public void setUser_Avatar(String user_Avatar) {
        this.user_Avatar = user_Avatar;
    }

    public String getUser_Birthday() {
        return user_Birthday;
    }

    public void setUser_Birthday(String user_Birthday) {
        this.user_Birthday = user_Birthday;
    }

    public String getUser_Introduction() {
        return user_Introduction;
    }

    public void setUser_Introduction(String user_Introduction) {
        this.user_Introduction = user_Introduction;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_ID='" + user_ID + '\'' +
                ", user_Name='" + user_Name + '\'' +
                ", user_Email='" + user_Email + '\'' +
                ", user_Password='" + user_Password + '\'' +
                ", user_Sex='" + user_Sex + '\'' +
                ", user_Age=" + user_Age +
                ", user_Avatar='" + user_Avatar + '\'' +
                ", user_Birthday='" + user_Birthday + '\'' +
                ", user_Introduction='" + user_Introduction + '\'' +
                '}';
    }
}
