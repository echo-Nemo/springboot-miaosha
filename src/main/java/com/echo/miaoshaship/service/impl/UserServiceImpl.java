package com.echo.miaoshaship.service.impl;

import com.echo.miaoshaship.dao.UserDOMapper;
import com.echo.miaoshaship.dao.UserPasswordDOMapper;
import com.echo.miaoshaship.dataobject.UserDO;
import com.echo.miaoshaship.dataobject.UserPasswordDO;
import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.service.UserService;
import com.echo.miaoshaship.service.model.UserModel;
import com.echo.miaoshaship.validator.ValidationResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.echo.miaoshaship.validator.ValidatorImpl;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validatorImpl;

    @Override
    public UserModel validateLogin(String telphone, String enPassword) throws BusinessException {
        //根据telphone得到用户
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        //该手机号是否已注册
        if (userDO == null) {
            throw new BusinessException(EmBussinessError.USER_LOGIN_FAIL);
        }
        //判断输入的密码和用户得到的密码是否一致
        if (!enPassword.equals(userPasswordDO.getEncrptPassword())) {
            throw new BusinessException(EmBussinessError.USER_LOGIN_FAIL);
        }
        UserModel userModel = convertFromDataObjcet(userDO, userPasswordDO);
        return userModel;
    }

    @Override
    public void registers(UserModel userModel) throws BusinessException {
        /*
        注册时的各个字段要进行校验
         */
        ValidationResult validationResult = validatorImpl.validate(userModel);
        //有不符合校验的字段
        if (validationResult.isHasErroes()) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }

        //一个手机只能注册一个用户 tel 对应唯一user
        UserDO userDO = convertUerDaoFromUserModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, "手机号已被注册");
        }
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertUserPassFromUserModel(userModel);
        /*
        dao 和pass这两个事务同时成功，必须加上@Transactional
         */
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    //将userModel转化为userDo
    public UserDO convertUerDaoFromUserModel(UserModel userModel) throws BusinessException {

        if (userModel == null) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    //将userModel转化为userPassword
    public UserPasswordDO convertUserPassFromUserModel(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userPasswordDO.getEncrptPassword());
        //通过userId关联查询
        userPasswordDO.setUserId(userModel.getId());
        BeanUtils.copyProperties(userModel, userPasswordDO);
        return userPasswordDO;
    }


    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        //用model层将密码和用户的信息联系到一起
        if (userDO == null) {
            return null;
        }
        //通过userId关联查询
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObjcet(userDO, userPasswordDO);
        return userModel;
    }

    /*
    将数据库中的dao映射成业务逻辑model
     */
    public UserModel convertFromDataObjcet(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();

        //将userDao对象转化位userModel对象
        BeanUtils.copyProperties(userDO, userModel);

        if (userPasswordDO != null) {
            //将加密的密码设置到UserModel对象中
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
