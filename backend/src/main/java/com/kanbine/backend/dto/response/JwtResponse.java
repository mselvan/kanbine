package com.kanbine.backend.dto.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;

    public JwtResponse(String accessToken, Long id, String email) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
    }
}
