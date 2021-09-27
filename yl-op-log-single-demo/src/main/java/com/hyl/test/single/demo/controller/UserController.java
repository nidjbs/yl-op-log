package com.hyl.test.single.demo.controller;

import com.hyl.test.single.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huayuanlin
 * @date 2021/09/27 00:15
 * @desc the class desc
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/v1/test1")
    public void test1() {
        userService.add("测试用户");
    }

    @GetMapping("/v1/test2")
    public void test2() {
        userService.updateName(16,"测试用户修改");
    }

    @GetMapping("/v1/test3")
    public void test3() {
        // 删除
        userService.delete(16);
    }

}
