package com.hyl.test.single.demo.service;

/**
 * @author huayuanlin
 * @date 2021/09/27 00:07
 * @desc the interface desc
 */
public interface UserService {



    void add(String userName);



    void delete(Integer userId);



    void updateName(Integer userId, String userName);


}
