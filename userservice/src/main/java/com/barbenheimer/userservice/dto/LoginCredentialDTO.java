package com.barbenheimer.userservice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginCredentialDTO {


    @NotEmpty
    private String username;

    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9])", message = "error.validation.authentication.password.requirement.number"),
            @Pattern(regexp = "(?=.*[a-z])", message = "error.validation.authentication.password.requirement.lower_case"),
            @Pattern(regexp = "(?=.*[A-Z])", message = "error.validation.authentication.password.requirement.upper_case"),
            @Pattern(regexp = "(?=\\S+$)", message = "error.validation.authentication.password.requirement.no_whitespace")
    })
    @Size(min = 8, max = 32, message = "error.validation.authentication.password.requirement.length")
    private String password;

}
