package com.echo.miaoshaship.service;

import com.echo.miaoshaship.dataobject.PromoDO;
import com.echo.miaoshaship.service.model.PromoModel;

public interface PromoService {
    //通过itemid查询秒杀的商品

    public PromoModel getPromoModelByItemId(Integer itemId);
}
