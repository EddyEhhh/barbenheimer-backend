package com.barbenheimer.userservice.controller;

import com.barbenheimer.userservice.dto.LoginCredentialDTO;
import com.barbenheimer.userservice.dto.LoginResponseDTO;
import com.barbenheimer.userservice.dto.LogoutResponseDTO;
import com.barbenheimer.userservice.dto.TokenDTO;
import com.barbenheimer.userservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginCredentialDTO loginCredentialDTO){
        return new ResponseEntity<>(loginService.login(loginCredentialDTO), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(@RequestBody TokenDTO tokenDTO){
        return new ResponseEntity<>(loginService.logout(tokenDTO), HttpStatus.OK);
    }

}
