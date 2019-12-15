package com.echo.miaoshaship.controller;

import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.response.CommonReturnType;
import com.echo.miaoshaship.service.OrderService;
import com.echo.miaoshaship.service.model.OrderModel;
import com.echo.miaoshaship.service.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController extends BaseController {
    @Autowired
    OrderService orderService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createrOrder(@RequestParam("itemId") Integer itemId,
                                         @RequestParam("amount") Integer amount, @RequestParam(name = "promoId", required = false) Integer promoId) throws BusinessException {

        //判断该用户是否存在
        Boolean is_login = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");

        if (is_login == null || !is_login.booleanValue()) {
            throw new BusinessException(EmBussinessError.USER_NOT_LOGIN);
        }

        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("USER_LOG");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);
        return CommonReturnType.create(null);
    }
}
