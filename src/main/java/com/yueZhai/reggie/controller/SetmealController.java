package com.yueZhai.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.dto.DishDto;
import com.yueZhai.reggie.dto.SetmealDto;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.entity.Dish;
import com.yueZhai.reggie.entity.Setmeal;
import com.yueZhai.reggie.service.CategoryService;
import com.yueZhai.reggie.service.SetmealDishService;
import com.yueZhai.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    //添加套餐2 保存
    @PostMapping
    public R<String> save (@RequestBody SetmealDto setmealDto){
        log.info("套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("套餐添加成功");
    }

//分页查询
@GetMapping("/page")
public R<Page> page(int page, int pageSize, String name) {
    Page<Setmeal> pageInfo = new Page<>(page, pageSize);
    Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);

    LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.like(name != null, Setmeal::getName, name);
    queryWrapper.orderByDesc(Setmeal::getUpdateTime);
    setmealService.page(pageInfo, queryWrapper);
    // 对象拷贝
    BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
    List<Setmeal> records = pageInfo.getRecords();
    List<SetmealDto> list = records.stream().map((item) -> {
        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(item, setmealDto);
        Long categoryId = item.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
        }

        return setmealDto;
    }).collect(Collectors.toList());
   setmealDtoPage.setRecords(list);
    return R.success(setmealDtoPage);
}
//删除套餐
    @DeleteMapping
    public R<String> deleteByIds(@RequestParam List<Long> ids){
        log.info("要删除的套餐id为：{}",ids);
       setmealService.deleteWithDish(ids);
       return R.success("删除成功");

    }
// 用户端展示套餐

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(setmeal.getCategoryId()!=null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, 1);
        //排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmealList = setmealService.list(queryWrapper);
        return R.success(setmealList);
    }


}
