package com.yueZhai.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueZhai.reggie.dto.DishDto;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.entity.Dish;

public interface DishService  extends IService<Dish>  {


    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
