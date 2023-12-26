package org.example.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.example.UserService;
import org.example.pojo.Users;
import org.example.pojo.bo.UserBO;
import org.example.util.JSONResult;
import org.springframework.web.bind.annotation.*;

@Api(value = "注册相关", tags = "用户管理")
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Resource
    UserService userService;

    @ApiOperation(value = "检查用户名是否存在", tags = {"注册", "GET"})
    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {
        // 1. 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }

        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已存在");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "注册", tags = {"注册", "POST"})
    @PostMapping("/register")
    public JSONResult register(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        // 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }

        // 密码长度不能少于 6 位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能少于 6");
        }

        // 判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次密码输入不一致");
        }

        // 实现注册
        Users user = userService.createUser(userBO);
        return JSONResult.ok(user);
    }
}
