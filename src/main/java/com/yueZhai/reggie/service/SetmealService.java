package com.yueZhai.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueZhai.reggie.dto.SetmealDto;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

   void saveWithDish(SetmealDto setmealDto);
   void deleteWithDish( List<Long> ids);

}
