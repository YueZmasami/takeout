package com.yueZhai.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.dto.DishDto;
import com.yueZhai.reggie.dto.SetmealDto;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.entity.Dish;
import com.yueZhai.reggie.entity.DishFlavor;
import com.yueZhai.reggie.service.CategoryService;
import com.yueZhai.reggie.service.DishFlavorService;
import com.yueZhai.reggie.service.DishService;
import com.yueZhai.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("接收到的数据为：{}", dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, queryWrapper);
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);


        return R.success(dishDtoPage);

    }
    // 菜品修改--1 根据id查询数据返回给前端以便进行数据回显
    @GetMapping("/{id}")
    public R<DishDto> getByIdWithFlavor(@PathVariable Long id){
        DishDto  dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);

    }
    // 菜品修改--2 更新菜品

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info("接收到的数据为：{}", dishDto);
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }
// 添加套餐1 ，路径为dish/list。。。。所以来dishController中写get方法显示菜品数据
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        //条件查询
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        queryWrapper.eq(Dish::getStatus , 1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish>  dishes  = dishService.list(queryWrapper);
//        return R.success(dishes);
//
//
//    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //条件查询
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus , 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        //item就是list中的每一条数据，相当于遍历了
        List<DishDto> dishDtoList = list.stream().map((item) -> {
            //创建一个dishDto对象
            DishDto dishDto = new DishDto();
            //将item的属性全都copy到dishDto里
            BeanUtils.copyProperties(item, dishDto);
            //由于dish表中没有categoryName属性，只存了categoryId
            Long categoryId = item.getCategoryId();
            //所以我们要根据categoryId查询对应的category
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //然后取出categoryName，赋值给dishDto
                dishDto.setCategoryName(category.getName());
            }
            //然后获取一下菜品id，根据菜品id去dishFlavor表中查询对应的口味，并赋值给dishDto
            Long itemId = item.getId();
            //条件构造器
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //条件就是菜品id
            lambdaQueryWrapper.eq(itemId != null, DishFlavor::getDishId, itemId);
            //根据菜品id，查询到菜品口味
            List<DishFlavor> flavors = dishFlavorService.list(lambdaQueryWrapper);
            //赋给dishDto的对应属性
            dishDto.setFlavors(flavors);
            //并将dishDto作为结果返回
            return dishDto;
            //将所有返回结果收集起来，封装成List
        }).collect(Collectors.toList());

        return R.success(dishDtoList);

    }


}
