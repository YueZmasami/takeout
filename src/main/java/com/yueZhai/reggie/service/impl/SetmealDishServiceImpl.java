package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.entity.Setmeal;
import com.yueZhai.reggie.entity.SetmealDish;
import com.yueZhai.reggie.mapper.SetmealDishMapper;
import com.yueZhai.reggie.mapper.SetmealMapper;
import com.yueZhai.reggie.service.SetmealDishService;
import com.yueZhai.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
