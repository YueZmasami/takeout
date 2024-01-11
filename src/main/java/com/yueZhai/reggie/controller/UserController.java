package com.yueZhai.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yueZhai.reggie.common.R;
import com.yueZhai.reggie.entity.User;
import com.yueZhai.reggie.service.UserService;
import com.yueZhai.reggie.utils.MailUtils;
import com.yueZhai.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;




    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user , HttpSession session) throws MessagingException {
        String phone=user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //随机生成一个验证码
          String code = ValidateCodeUtils.generateValidateCode(4).toString();


            log.info(code);
//            MailUtils.sendTestMail(phone,code);
            //验证吗存到session ,一会用
            session.setAttribute(phone,code);
            return R.success("验证码发送成功");

        }
        return R.error("验证码发送失败");
    }
// 输入完之后会发送post请求，校验输入的码和生成的码是否一样
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
log.info(map.toString());
        // 获取电话
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

    Object codeInSession= session.getAttribute(phone);



  if(codeInSession!=null&&codeInSession.equals(code)){
            //判断当前用户是否存在
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            //判断依据是从数据库中查询是否有其邮箱
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            //如果不存在，创建一个直接存入数据库
            if(user==null){
                user=new User();
                user.setPhone(phone);
                userService.save(user);
                user.setName("用户" + codeInSession);
            }
            //存到session里面，表示登陆状态
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登陆失败");
    }

}
