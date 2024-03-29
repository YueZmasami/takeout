package com.yueZhai.reggie.controller;

import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.entity.Orders;
import com.yueZhai.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
@PostMapping("/submit")
public R<String> submit(@RequestBody Orders orders){
    log.info("orders:{}", orders);
    orderService.submit(orders);
    return R.success("用户下单成功");
}




}
