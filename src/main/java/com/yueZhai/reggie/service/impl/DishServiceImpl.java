package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.dto.DishDto;
import com.yueZhai.reggie.entity.Dish;
import com.yueZhai.reggie.entity.DishFlavor;
import com.yueZhai.reggie.mapper.DishMapper;
import com.yueZhai.reggie.service.DishFlavorService;
import com.yueZhai.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 先存进去信息, 这里的this指代dishService
        this.save(dishDto);
        //获取id
        Long dishId = dishDto.getId();
        //将获取到的dishId赋值给dishFlavor的dishId属性
        List<DishFlavor> flavors = dishDto.getFlavors();
      for(DishFlavor dishFlavor: flavors){
          dishFlavor.setDishId(dishId);
      }

//将菜品口味数据保存到dish-flavor中
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
       //1 根据id查出dish对象
        Dish dish = this.getById(id);
        //2 创建dto对象
        DishDto dishDto=new DishDto();
        //3 copy
        BeanUtils.copyProperties(dish, dishDto);
        //4 对dishflavor进行查寻
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor>  flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;

    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        // 1 dish表直接更新
        this.updateById(dishDto);
        // 2 清理flavor表格
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //3 保存新的数据，用法同save

        List<DishFlavor> flavors = dishDto.getFlavors();
        for(DishFlavor dishFlavor: flavors){
            dishFlavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(flavors);

    }
}
