package com.yueZhai.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yueZhai.reggie.entity.Dish;
import com.yueZhai.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
