package com.echo.miaoshaship.service.impl;

import com.echo.miaoshaship.dao.OrderDOMapper;
import com.echo.miaoshaship.dao.SequenceDOMapper;
import com.echo.miaoshaship.dataobject.OrderDO;
import com.echo.miaoshaship.dataobject.SequenceDO;
import com.echo.miaoshaship.dataobject.UserDO;
import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.service.ItemService;
import com.echo.miaoshaship.service.OrderService;
import com.echo.miaoshaship.service.UserService;
import com.echo.miaoshaship.service.model.ItemModel;
import com.echo.miaoshaship.service.model.OrderModel;
import com.echo.miaoshaship.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //进行校验
        UserModel userModel = userService.getUserById(userId);

        if (userModel == null) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }

        ItemModel itemModel = itemService.getItemById(itemId);

        if (itemModel == null) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, "订单不存在");
        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBussinessError.STOCK_OF_ENOUGH);
        }

        //落单减库存
        boolean flage = itemService.decreaseByStock(itemId, amount);

        //订单入库
        if (!flage) {
            throw new BusinessException(EmBussinessError.STOCK_OF_ENOUGH);
        }


        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setAmout(amount);
        orderModel.setItemId(itemId);

//        //秒杀商品的promoId的判断
        if (promoId != null) {
            //前端传来的的promoId和 itemModel中的promoId是否一致
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
            } else if (itemModel.getPromoModel().getStatus() != 2) {  //活动还没有开始
                throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, "活动还没有开始");
            } else {
                //将商品的价格改成秒杀的价格
                orderModel.setPromoId(promoId);
                orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
                orderModel.setOrderPrice(orderModel.getItemPrice().multiply(BigDecimal.valueOf(amount)));
            }
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
            //订单的的总价
            orderModel.setOrderPrice(orderModel.getItemPrice().multiply(BigDecimal.valueOf(amount)));
        }

        //订单的流水号
        orderModel.setId(generatorOrderNo());

        //下单后销量增加
        itemService.increaseSales(itemId, amount);
        OrderDO orderDO = convertFromOrderModel(orderModel);

        //将订单的数据写到数据库中
        orderDOMapper.insertSelective(orderDO);
//        int i = 10 / 0;
        return orderModel;
    }

    //订单流水号的生成
    @Transactional(propagation = Propagation.REQUIRES_NEW) //即使事务失败,将事务进行回滚，订单号还是要生成新的订单号
    public String generatorOrderNo() {
//        System.out.println("===========hhhhha" + "==========" + Math.random() * 1000);

        StringBuilder builder = new StringBuilder();
        int sequence = 0;
        //前8位为时间，年月日
        //jdk8的LocalDateTime
        LocalDateTime time = LocalDateTime.now();

        //2019-12-10 将"-"用""代替
        String nowDate = time.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        builder.append(nowDate);

        //中间6位为自增序列
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        //拿到序列号
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());

        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        String sequenceStr = String.valueOf(sequence);
        //凑足6位 不够的用0填充
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            builder.append("0");
        }
        builder.append(sequenceStr);

        //后面2位为分库分表位暂时写死
        builder.append("00");
        return builder.toString();
    }

    public OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }
}
