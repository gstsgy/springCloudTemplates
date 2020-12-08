package com.guyue.gateway.jwt;

import lombok.Data;

@Data
public class JwtModel {
    private String userName;
    private Long userId;
    private String userpw;

    public JwtModel() {
    }

    public JwtModel(String userName, String userpw,Long userId) {
        this.userName = userName;
        this.userpw = userpw;
        this.userId = userId;
    }

}
