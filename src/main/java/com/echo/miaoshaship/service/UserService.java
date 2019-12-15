package com.echo.miaoshaship.service;

import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.service.model.UserModel;

public interface UserService {
    //根据id查询用户信息
    public UserModel getUserById(Integer id);

    //用户的注册
    public void registers(UserModel userModel) throws BusinessException;

    /*
    用户的登入 返回一个userModel放在session中
     */
    public UserModel validateLogin(String telphone, String enPassword) throws BusinessException;
}
