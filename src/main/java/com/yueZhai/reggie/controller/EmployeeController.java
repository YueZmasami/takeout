/*package com.yueZhai.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.entity.Employee;
import com.yueZhai.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R <Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1 将提交的密码进行加密处理
        String password = employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        //2 根据用户名来查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
         Employee emp = employeeService.getOne(queryWrapper);
//3 如果没有查到直接返回查询失败

if(emp==null){
        return R.error("登陆失败");
    }
// 4 如果查询到结果，进行密码比对
        if(!emp.getPassword().equals(password)){
            return R.error("登陆失败");
        }
// 5 查询员工状态，如果禁用
        if(emp.getStatus()!=1){
            return R.error("账号已禁用");

        }
        // 将员工id放进session中并返回
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

}*/
package com.yueZhai.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.entity.Employee;
import com.yueZhai.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */


    @PostMapping("/logout")
public R<String> logout(HttpServletRequest request){
    request.getSession().removeAttribute("employee");
return R.success("退出成功");
}
    /**
     * 新增员工
     * @param request
     * @return
     */
@PostMapping
public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        //设置初始密码，加密
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
   //  默认时间和创造用户更新用户
   // employee.setCreateTime(LocalDateTime.now());
  //  employee.setUpdateTime(LocalDateTime.now());
  // Long empId= (Long)request.getSession().getAttribute("employee");
   // employee.setCreateUser(empId);
  //  employee.setUpdateUser(empId);
employeeService.save(employee);
return R.success("新增员工成功");
}
/**
 * 分页查询
 */
@GetMapping("/page")
public R<Page> page(int page, int pageSize, String name ){
// 构造分页构造器
    log.info("page={}, pageSize={}, name={}", page, pageSize, name);
    Page pageInfo= new Page(page, pageSize);

    // 条件构造器
    LambdaQueryWrapper <Employee>queryWrapper=new LambdaQueryWrapper();
    queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
// 排序条件
    queryWrapper.orderByDesc(Employee::getUpdateTime);
    // 执行查询
    employeeService.page(pageInfo, queryWrapper);
    return R.success(pageInfo);

}
/**
 * 员工的状态启用与禁用
 * 1。 更新时间和用户
 * 2。 直接调用mybatis 中updatebyID 即可
 * 3。加注解putMapping
 * 4. 因为只有code==1，所以只返回String即可
 */
@PutMapping
public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
  // employee.setUpdateTime(LocalDateTime.now());
  // Long empId=(Long)request.getSession().getAttribute("employee");
  // employee.setUpdateUser(empId);
employeeService.updateById(employee);
return R.success("修改成功");
}
/**
 *根据id查询员工信息
 */
@GetMapping("/{id}")
public R<Employee> getById(@PathVariable Long id){
    Employee employee = employeeService.getById(id);
    if(employee!=null){
        return R.success(employee);
    }
   else {
       return R.error("没有查询到信息"); 
    }



}


}
