package com.yueZhai.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
