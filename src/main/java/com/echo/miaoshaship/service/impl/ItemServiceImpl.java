package com.echo.miaoshaship.service.impl;

import com.echo.miaoshaship.dao.*;
import com.echo.miaoshaship.dao.ItemStockDOMapper;
import com.echo.miaoshaship.dataobject.ItemDO;
import com.echo.miaoshaship.dataobject.ItemStockDO;
import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.service.ItemService;
import com.echo.miaoshaship.service.PromoService;
import com.echo.miaoshaship.service.model.ItemModel;
import com.echo.miaoshaship.service.model.PromoModel;
import com.echo.miaoshaship.validator.ValidationResult;
import com.echo.miaoshaship.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDOMapper itemDOMapper;

    @Autowired
    ItemStockDOMapper itemStockDOMapper;

    @Autowired
    ValidatorImpl validatorImpl;

    @Autowired
    PromoService promoService;

    //下订单减库存
    @Override
    @Transactional
    public boolean decreaseByStock(Integer itemId, Integer amount) {
        int result = itemStockDOMapper.decreaseStock(itemId, amount);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    //销量增加   在下单操作手有多个步骤
    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseBySales(itemId, amount);
    }

    //新增数据
    @Override
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {

        ValidationResult validationResult = validatorImpl.validate(itemModel);

        if (validationResult.isHasErroes()) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }

        //数据写到数据库中
        ItemDO itemDO = convertItemDOFromItemModel(itemModel);
        itemDOMapper.insertSelective(itemDO);

        //这里一定要把item中的id设置进来,不让item_stock中的item_id不能关联
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = convertItemStockDoFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        //返回创建完成后数据库里的对象
        return this.getItemById(itemDO.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemList = itemDOMapper.selectItemList();
        //stream中的map()实现类型的转化
        List<ItemModel> modelList = itemList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = convetItemModeFromItemStockDO(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        System.out.println("====" + modelList + "====");
        return modelList;
    }


    //根据item_id获取itemstock对象，在转化为itemodel
    @Override
    public ItemModel getItemById(Integer id) {

        ItemModel itemModel = new ItemModel();
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);

        //先转化未itemModel 再判断当前商品的是否在参加活动
        itemModel = convetItemModeFromItemStockDO(itemDO, itemStockDO);

        //判断当前商品是否在参见秒杀活动
        PromoModel promoModel = promoService.getPromoModelByItemId(itemDO.getId());


        //秒杀的活动还没有结束
        if (promoModel != null && promoModel.getStatus().intValue() != 3) {
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }


    //将ItemModel转化为ItemDO
    public ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        return itemDO;
    }

    //将ItemModel转化为ItemStockDO
    public ItemStockDO convertItemStockDoFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setStock(itemModel.getStock());
        itemStockDO.setItemId(itemModel.getId());

        return itemStockDO;
    }

    //将ItemDO和ItemStaockDO转化为ItemModel
    public ItemModel convetItemModeFromItemStockDO(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();

        BeanUtils.copyProperties(itemDO, itemModel);

        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }

}
