package com.echo.miaoshaship.controller;

import com.echo.miaoshaship.controller.ViewObject.UserVo;
import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.response.CommonReturnType;
import com.echo.miaoshaship.service.impl.UserServiceImpl;
import com.echo.miaoshaship.service.model.UserModel;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
//处理ajax中跨域请求的问题  origins="*" 表示所有的域名都可以访问
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UserController extends BaseController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    HttpServletRequest httpServletRequest; //单例的 内部使用ThreadLocal是线程安全的

    /*
    用户的登入
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType userLogin(@RequestParam("telphone") String telphone, @RequestParam("password") String password) throws BusinessException {
        UserModel userModel = null;

        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }

        try {
            userModel = userService.validateLogin(telphone, enCodeByMD5(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将登录凭证加入到合法用户的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("USER_LOG", userModel);
        return CommonReturnType.create(null);
    }

    /*
    用户的注册接口
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/json;charset=utf-8"})
    @ResponseBody
    public CommonReturnType register(@RequestBody UserModel userModel) throws BusinessException {
        System.out.println("=============");
        //判断otp和tel是否符合
        String sessionOpt = (String) httpServletRequest.getSession().getAttribute(userModel.getTelphone());

        //前端传来的otpCode
        String otpCode = userModel.getOtpCode();

        if (!sessionOpt.equals(otpCode)) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }

        //前端的pwd
        String pwd = userModel.getPassword();
        try {
            userModel.setEncrptPassword(enCodeByMD5(pwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userModel.setRegisterMode("byphone");
        System.out.println("=====" + userModel + "=====");
        userService.registers(userModel);
        CommonReturnType commonReturnType = CommonReturnType.create(null);
        return commonReturnType;
    }

    //对密码进行md5的加密
    public String enCodeByMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newStr = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));
        return newStr;
    }

    //用户短信验证码
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        //生成opt验证码
        Random random = new Random();
        //最大的取值
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);

        //将手机号和opt进行绑定
        httpServletRequest.getSession().setAttribute(telphone, optCode);
        System.out.println("telphone" + telphone + ":optCode" + optCode);
        return CommonReturnType.create(null);
    }


    @ResponseBody
    @RequestMapping("/getUser/{id}")
    public CommonReturnType getUserById(@PathVariable("id") Integer id) throws Exception {
        /*
        一般用户的信息不能全部显示给前端页面，所以在建义ModelVo来显示要传给前端的数据
         */
        UserModel userModel = userService.getUserById(id);
        UserVo userVo = convertFromUserModel(userModel);

        if (userModel == null) {
            //抛出一个自定义的异常
            throw new BusinessException(EmBussinessError.USER_NOT_EXIST);
        }
        return CommonReturnType.create(userVo);
    }

    public UserVo convertFromUserModel(UserModel userModel) {
        UserVo userVo = new UserVo();
        if (userModel == null) {
            return null;
        } else {
            BeanUtils.copyProperties(userModel, userVo);
        }
        return userVo;
    }
}
