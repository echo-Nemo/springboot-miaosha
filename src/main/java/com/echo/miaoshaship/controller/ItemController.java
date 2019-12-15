package com.echo.miaoshaship.controller;

import com.echo.miaoshaship.controller.ViewObject.ItemVo;
import com.echo.miaoshaship.error.BusinessException;
import com.echo.miaoshaship.error.EmBussinessError;
import com.echo.miaoshaship.response.CommonReturnType;
import com.echo.miaoshaship.service.ItemService;
import com.echo.miaoshaship.service.model.ItemModel;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ItemController extends BaseController {
    @Autowired
    ItemService itemService;

    //新增商品
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {"application/json;charset=utf-8"})
    @ResponseBody
    public CommonReturnType createItem(@RequestBody ItemModel itemModel) throws BusinessException {

        if (itemModel == null) {
            throw new BusinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }

        ItemModel item = itemService.createItem(itemModel);

        ItemVo itemVo = convertItemVoFromItemModel(item);

        return CommonReturnType.create(itemVo);
    }

    //显示商品列表
    @RequestMapping(value = "/getItemList", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItemList() {
        List<ItemModel> itemModels = itemService.listItem();

        List<ItemVo> itemVoList = itemModels.stream().map(itemModel -> {
            ItemVo itemVo = convertItemVoFromItemModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVoList);
    }

    //根据item中的id显示商品信息
    @RequestMapping(value = "/getItem", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItemById(@RequestParam(name = "id") Integer id) {

        ItemModel itemModel = itemService.getItemById(id);

        ItemVo itemVo = convertItemVoFromItemModel(itemModel);

        return CommonReturnType.create(itemVo);
    }

    //将ItemModel转化为ItemVo
    public ItemVo convertItemVoFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel, itemVo);
        //判断有没有秒杀活动
        if (itemModel.getPromoModel() != null) {
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoId(itemModel.getPromoModel().getId());
        } else {
            //0代表还没有参加秒杀活动
            itemVo.setPromoStatus(0);
        }
        return itemVo;
    }
}
