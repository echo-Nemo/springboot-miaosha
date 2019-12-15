package com.echo.miaoshaship.service;

import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    //新增商品
    public ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品的显示
    public List<ItemModel> listItem();

    //商品的详情浏览
    public ItemModel getItemById(Integer id);

    //下单减库存
    boolean decreaseByStock(Integer itemId, Integer amount);

    //下单成功商品的销量减少
    void increaseSales(Integer itemId, Integer amount);

}
