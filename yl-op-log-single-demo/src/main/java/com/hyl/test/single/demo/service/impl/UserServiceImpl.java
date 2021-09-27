package com.hyl.test.single.demo.service.impl;

import com.hyl.op.log.annotations.OpLog;
import com.hyl.test.single.demo.dao.UserMapper;
import com.hyl.test.single.demo.entity.UserDO;
import com.hyl.test.single.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author huayuanlin
 * @date 2021/09/27 00:07
 * @desc the class desc
 */
@Service
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @OpLog(bizType = "添加用户信息")
    @Override
    public void add(String userName) {
        UserDO userDO = new UserDO();
        userDO.setName(userName);
        userMapper.insert(userDO);
    }

    @OpLog(bizType = "删除用户信息", bizIdEl = "#userId")
    @Override
    public void delete(Integer userId) {
        userMapper.deleteById(userId);
    }


    @OpLog(bizType = "更新用户信息", bizIdEl = "#userId")
    @Override
    public void updateName(Integer userId, String userName) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setName(userName);
        userMapper.updateById(userDO);
    }
}
