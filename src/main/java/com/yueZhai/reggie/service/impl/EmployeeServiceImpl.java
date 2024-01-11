package com.yueZhai.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueZhai.reggie.entity.Employee;
import com.yueZhai.reggie.mapper.EmployeeMapper;
import com.yueZhai.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
