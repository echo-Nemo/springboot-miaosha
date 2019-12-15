package com.echo.miaoshaship.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PromoModel {
    /*
    秒杀业务模型
     */

    //秒杀的状态 1还没开始 2进行中 3以结束
    private Integer status;

    private Integer id;

    //秒杀活动的民称
    private String promoName;
    //秒杀开始的时间
    private DateTime startDate;
    //结束时间
    private DateTime endDate;

    //秒杀活动的商品ID
    private Integer itemId;
    //秒杀时刻的商品价格
    private BigDecimal promoItemPrice;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }
}
