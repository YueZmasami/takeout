package com.yueZhai.reggie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yueZhai.reggie.entity.OrderDetail;
import com.yueZhai.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}