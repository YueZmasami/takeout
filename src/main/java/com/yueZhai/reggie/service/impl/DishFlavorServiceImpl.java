package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.dto.DishDto;
import com.yueZhai.reggie.entity.Dish;
import com.yueZhai.reggie.entity.DishFlavor;
import com.yueZhai.reggie.mapper.DishFlavorMapper;
import com.yueZhai.reggie.mapper.DishMapper;
import com.yueZhai.reggie.service.DishFlavorService;
import com.yueZhai.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}
