package com.example.stampmap.dto;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class User {
    private int userId;
    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String passwordConfirm;
}
