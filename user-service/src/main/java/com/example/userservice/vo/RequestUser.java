package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "입력 되지 않음")
    @Size(min=2, message = "2글자 이상 입력 바람")
    @Email
    private String email;
    @NotNull(message = "입력 되지 않음")
    @Size(min=2, message = "2글자 이상 입력 바람")
    private String name;

    @NotNull(message = "입력 되지 않음")
    @Size(min=8, message = "8글자 이상 입력 바람")
    private String pwd;
}
