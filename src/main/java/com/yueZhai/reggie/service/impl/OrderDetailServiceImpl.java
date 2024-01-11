package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.entity.OrderDetail;
import com.yueZhai.reggie.mapper.OrderDetailMapper;
import com.yueZhai.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
