package org.example.impl;

import org.example.UserService;
import org.example.enums.Sex;
import org.example.mapper.UsersMapper;
import org.example.pojo.Users;
import org.example.pojo.bo.UserBO;
import org.example.idworker.Sid;
import org.example.util.DateUtil;
import org.example.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private static final String AVATAR = "https://avatar.com/theone";

    @Autowired
    private Sid sid;

    private final UsersMapper usersMapper;
    @Autowired
    @SuppressWarnings("all")
    public UserServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);

        Users result = usersMapper.selectOneByExample(example);
        return result != null;
    }

    @Override
    public Users createUser(UserBO userBO) {
        Users user = new Users();
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        user.setNickname(userBO.getUsername());
        user.setFace(AVATAR);

        user.setId(sid.nextShort());
        user.setBirthday(DateUtil.stringToDate("1991-01-01"));
        user.setSex(Sex.man.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);

        return user;
    }

}
