package com.example.cinema.vo;

public class AdminForm {
    private Integer id;
    /**
     * 用户名，不可重复
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    public Integer getId(){return id;}
    public  void setId(Integer id){this.id=id;}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
