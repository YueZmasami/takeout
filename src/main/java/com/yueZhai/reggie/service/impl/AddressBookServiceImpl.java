package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.entity.AddressBook;
import com.yueZhai.reggie.entity.Category;
import com.yueZhai.reggie.mapper.AddressBookMapper;
import com.yueZhai.reggie.mapper.CategoryMapper;
import com.yueZhai.reggie.service.AddressBookService;
import com.yueZhai.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
