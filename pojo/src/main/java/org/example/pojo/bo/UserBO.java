package org.example.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserBO",  description = "用户注册")
public class UserBO {
    @Schema(title = "用户名", example = "heeron")
    private String username;
    private String password;
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
