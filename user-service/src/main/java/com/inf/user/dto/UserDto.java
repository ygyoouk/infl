package com.inf.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private String  email;      // email
    private String  name;       // 유저명
    private String  pwd;        // 비밀번호
    private String  userId;     // 유저 ID
    private Date    createdAt;  // 가입 시간

    private String encryptedPwd;
}
