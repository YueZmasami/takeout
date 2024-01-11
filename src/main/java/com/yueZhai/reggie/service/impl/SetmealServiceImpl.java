package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.common.CustomerException;
import com.yueZhai.reggie.dto.SetmealDto;
import com.yueZhai.reggie.entity.DishFlavor;
import com.yueZhai.reggie.entity.Setmeal;
import com.yueZhai.reggie.entity.SetmealDish;
import com.yueZhai.reggie.mapper.SetmealMapper;
import com.yueZhai.reggie.service.SetmealDishService;
import com.yueZhai.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl  extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存在setmeal表中
        this.save(setmealDto);

        // 保存在setmealdish表格中

        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper<>();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
       setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

setmealDishService.saveBatch(setmealDishes);



    }

    @Override
    public void deleteWithDish(List<Long> ids) {
//判断是否可以删除
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId, ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(setmealLambdaQueryWrapper);
        //不能删除抛异常
        if(count>0){
            throw new CustomerException("套餐正在售卖中，请先停售再进行删除");
        }
        //先删除setmeal这张表格的
        this.removeByIds(ids);
        //删除setmealDish这张表格的,根据表格中的setmeal id
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);






    }
}
