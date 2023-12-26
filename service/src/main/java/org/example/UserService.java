package org.example;

import org.example.pojo.Users;
import org.example.pojo.bo.UserBO;

public interface UserService {
    boolean queryUsernameIsExist(String username);
    Users createUser(UserBO userBO);
}
