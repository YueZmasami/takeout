package com.yueZhai.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.yueZhai.reggie.common.BaseContext;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.entity.ShoppingCart;
import com.yueZhai.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
@PostMapping("/add")
public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
    log.info("购物车添加信息：{}",shoppingCart);
  // 获取当前用户id，添加到参数中
    Long currentId = BaseContext.getCurrentId();
    shoppingCart.setUserId(currentId);
    //获取菜品id
    Long dishId = shoppingCart.getDishId();
    LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
//判断添加的是菜品还是套餐
    if(dishId!=null){
queryWrapper.eq(ShoppingCart::getDishId, dishId);
    }else {
    queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());}
    ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
    if(cartServiceOne!=null){
        //说明当前菜品或者套餐已经存在
        Integer number = cartServiceOne.getNumber();
        cartServiceOne.setNumber(number+1);
        shoppingCartService.updateById(cartServiceOne);

    }else {
        shoppingCart.setCreateTime(LocalDateTime.now());
        //如果不存在，则添加到购物车，数量默认为1
        shoppingCartService.save(shoppingCart);
        //这里是为了统一结果，最后都返回cartServiceOne会比较方便
        cartServiceOne = shoppingCart;

    }
    return R.success(cartServiceOne);
}
    //查看购物车的信息
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);
        return R.success(shoppingCartList);

    }
    //清空购物车的信息
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseContext.getCurrentId()!=null, ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("成功清空购物车");
    }






}