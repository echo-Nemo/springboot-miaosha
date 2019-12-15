package com.echo.miaoshaship.controller;

import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
处理Controller层的异常
 */
@Controller
public class BaseController {
    //定义ajax请求中的contentType
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    /*
   定义一个exceptionhandler解决没有被controller层吸收的异常
    */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK) //状态码为200
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        Map<String, Object> responseData = new HashMap<>();

        if (ex instanceof BusinessException) {
            //ex抛出的信息太多，建议把Exception转化为自定义的BusinessException
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getMsg());
        } else {
            responseData.put("errCode", EmBussinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg", EmBussinessError.UNKNOW_ERROR.getMsg());
        }
        //！！！！
        return CommonReturnType.create(responseData,"fail");
    }
}
