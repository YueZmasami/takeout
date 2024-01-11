package com.yueZhai.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用于设置和获取用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public  static void setCurrentId(Long id ){
        threadLocal.set(id);
    }
    public static Long  getCurrentId( ){
       return threadLocal.get();
    }
}
