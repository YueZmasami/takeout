package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.common.CustomerException;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.entity.Dish;
import com.yueZhai.reggie.entity.Setmeal;
import com.yueZhai.reggie.mapper.CategoryMapper;
import com.yueZhai.reggie.service.CategoryService;
import com.yueZhai.reggie.service.DishService;
import com.yueZhai.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(long id) {
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
     queryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(queryWrapper);
        log.info("dish查询条件，查询到的条目数为：{}",count1);
        if(count1>0){
            throw new CustomerException("当前分类下关联菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        log.info("setmeal查询条件，查询到的条目数为：{}",count2);
        if(count2>0){
            throw new CustomerException("当前分类下关联套餐，不能删除");
        }
     super.removeById(id);

    }
}
