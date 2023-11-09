package com.barbenheimer.userservice.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String expiresIn;

    private String refreshExpiresIn;

    private String tokenType;

}
