package com.yueZhai.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueZhai.reggie.entity.Orders;


public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}