package com.example.stampmap.dto;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Data
public class User {
    private int userId;
    @NotBlank
    @Size(min = 1, max = 30)
    @Pattern(regexp="[A-Za-z0-9]+")
    private String userName;
    @NotBlank
    @Size(min = 4, max = 30)
    @Pattern(regexp="[A-Za-z0-9]+")
    private String password;
    private String passwordConfirm;
    public boolean isSamePassword() {
        if (password.equals(passwordConfirm)) {
            return true;
        }
        return false;
    }
}
