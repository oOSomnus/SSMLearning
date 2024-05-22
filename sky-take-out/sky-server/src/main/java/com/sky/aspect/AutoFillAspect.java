package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    //前置通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("autofilling ...");

        //获取操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = autoFill.value();

        //获取被拦截方法参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object arg = args[0];
        //赋值
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //根据当前操作类型进行赋值
        if (value == OperationType.INSERT) {
            try {
                Method setCreateTime = arg.getClass()
                        .getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = arg.getClass()
                        .getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = arg.getClass()
                        .getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = arg.getClass()
                        .getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setCreateTime.invoke(arg, now);
                setCreateUser.invoke(arg, currentId);
                setUpdateTime.invoke(arg, now);
                setUpdateUser.invoke(arg, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if (value == OperationType.UPDATE) {
            try {
                Method setUpdateTime = arg.getClass()
                        .getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = arg.getClass()
                        .getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(arg, now);
                setUpdateUser.invoke(arg, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
