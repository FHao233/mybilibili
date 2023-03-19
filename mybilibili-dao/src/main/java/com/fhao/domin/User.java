package com.fhao.domin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * author: FHao
 * create time: 2023-03-19 12:57
 * description:
 */
@Data
@AllArgsConstructor
public class User {
    private Long id;

    private String phone;

    private String email;

    private String password;

    private String salt;

    private Date createTime;

    private Date updateTime;

    private UserInfo userInfo;

}
