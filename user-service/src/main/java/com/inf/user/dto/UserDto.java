package com.inf.user.dto;

import com.inf.user.vo.ResponseOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private String  email;      // email
    private String  name;       // 유저명
    private String  pwd;        // 비밀번호
    private String  userId;     // 유저 ID
    private Date    createdAt;  // 가입 시간

    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
