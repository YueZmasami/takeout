package com.yueZhai.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueZhai.reggie.entity.Category;

public interface CategoryService extends IService<Category> {





    public void remove(long id);

}
