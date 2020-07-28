package com.gala.blockchain.common.utils;

import com.gala.blockchain.entity.SysLog;
import com.gala.blockchain.service.ISysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @Desc
 * @Author YBW
 * @Date 2020.6.7
 */

@Aspect
@Component
public class LogAsPect {

    @Autowired
    ISysLogService sysLogService;

    //表示匹配带有自定义注解的方法
    @Pointcut("@annotation(com.gala.blockchain.common.utils.Log)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result =null;
        long beginTime = System.currentTimeMillis();

        try {
//            log.info("我在目标方法之前执行！");
            result = point.proceed();
            long endTime = System.currentTimeMillis();
            insertLog(point,endTime-beginTime);
        } catch (Throwable e) {
            // TODO Auto-generated catch block
        }
        return result;
    }

    private void insertLog(ProceedingJoinPoint point ,long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();

        Log userAction = method.getAnnotation(Log.class);
        if (userAction != null) {
            // 注解上的描述
            sysLog.setDoing(userAction.value());
        }

        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        // 请求的方法参数值
        String args = Arrays.toString(point.getArgs());

        //从session中获取当前登陆人id
//      Long useride = (Long)SecurityUtils.getSubject().getSession().getAttribute("userid");

        Integer userid = 1;//应该从session中获取当前登录人的id，这里简单模拟下

        sysLog.setAuthorName("管理员");

        sysLog.setAuthorId(userid);

        sysLog.setCreateTime(new Date());

//        log.info("当前登陆人：{},类名:{},方法名:{},参数：{},执行时间：{}",userid, className, methodName, args, time);

        sysLogService.save(sysLog);
    }
}
