package com.echo.miaoshaship.service;

import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.service.model.OrderModel;

public interface OrderService {
    /*
    订单的创建
    传入一个promoId 判断该商品是否在进行秒杀
     */
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
