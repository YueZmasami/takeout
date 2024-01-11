package com.yueZhai.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
   private CategoryService categoryService;


    /**
     * 新增分类
      */

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category: {}", category);
        categoryService.save(category);
        return R.success("新增菜品成功");

    }

    /**
     * 按页码显示数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        //分页构造器
        Page<Category> pageInfo=new Page<>( page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Category> queryWarpper = new LambdaQueryWrapper<>();
        queryWarpper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWarpper);
        return R.success(pageInfo);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("将被删除的id：{}", id);
         categoryService.remove(id);
         return R.success("删除成功");
    }

@PutMapping
    public R<String> update (@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }
@GetMapping("/list")
public R<List<Category>> list(Category category){
      //条件构造器
    LambdaQueryWrapper <Category> queryWrapper=new LambdaQueryWrapper<>();
    queryWrapper.eq(category.getType()!=null, Category::getType, category.getType());

queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
    List<Category> categoryList = categoryService.list(queryWrapper);
    return R.success(categoryList);



}


}
