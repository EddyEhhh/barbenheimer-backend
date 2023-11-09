package com.barbenheimer.userservice.service;


import com.barbenheimer.userservice.dto.LoginCredentialDTO;
import com.barbenheimer.userservice.dto.LoginResponseDTO;
import com.barbenheimer.userservice.dto.LogoutResponseDTO;
import com.barbenheimer.userservice.dto.TokenDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class LoginService {

    @Value("${authentication.server.url.token}")
    private String loginUrl;

    @Value("${authentication.server.url.logout}")
    private String logoutUrl;

    @Value("${authentication.server.url.register}")
    private String registerUrl;

    @Value("${oauth2-client-credentials.client-id}")
    private String clientId;

    @Value("${oauth2-client-credentials.client-secret}")
    private String clientSecret;

    @Value("${oauth2-client-credentials.grant-type}")
    private String grantType;

    private RestTemplate restTemplate;

    @Autowired
    public LoginService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    public LoginResponseDTO login(LoginCredentialDTO loginCredentialDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        map.add("username", loginCredentialDTO.getUsername());
        map.add("password", loginCredentialDTO.getPassword());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);

        ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<Map<String, String>>() {};
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity, responseType);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .accessToken(response.getBody().get("access_token"))
                .expiresIn(response.getBody().get("expires_in"))
                .refreshExpiresIn(response.getBody().get("refresh_expires_in"))
                .refreshToken(response.getBody().get("refresh_token"))
                .tokenType(response.getBody().get("token_type"))
                .build();

        return loginResponseDTO;

    }

    // public RegisterResponseDTO login(RegisterCredentialDTO registerCredentialDTO){
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    //     MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    //     map.add("client_id", clientId);
    //     map.add("client_secret", clientSecret);
    //     map.add("grant_type", grantType);
    //     map.add("username", registerCredentialDTO.getUsername());
    //     map.add("password", registerCredentialDTO.getPassword());

    //     HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);

    //     ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<Map<String, String>>() {};
    //     ResponseEntity<Map<String, String>> response = restTemplate.exchange(loginUrl, HttpMethod.POST, httpEntity, responseType);
    //     LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
    //             .accessToken(response.getBody().get("access_token"))
    //             .expiresIn(response.getBody().get("expires_in"))
    //             .refreshExpiresIn(response.getBody().get("refresh_expires_in"))
    //             .refreshToken(response.getBody().get("refresh_token"))
    //             .tokenType(response.getBody().get("token_type"))
    //             .build();

    //     return loginResponseDTO;

    // }

    public LogoutResponseDTO logout(TokenDTO tokenDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", tokenDTO.getToken());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);

        ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<Map<String, String>>() {};
        ResponseEntity<LogoutResponseDTO> response = restTemplate.postForEntity(logoutUrl, httpEntity, LogoutResponseDTO.class);

        LogoutResponseDTO logoutResponseDTO = new LogoutResponseDTO();
        if(response.getStatusCode().is2xxSuccessful()){
            logoutResponseDTO.setMessage("authentication.logout.success");
        }

        return logoutResponseDTO;
    }


}
