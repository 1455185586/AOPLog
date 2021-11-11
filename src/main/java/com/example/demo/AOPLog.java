package com.example.demo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
@Aspect//该注解让其成为切面类
@Component//把切面类放入IOC容器里面


public class AOPLog {

    private Logger logger =LoggerFactory.getLogger(this.getClass());
    ThreadLocal<Long> startTime=new ThreadLocal<>();//线程的局部变量用来解决多线程中相同变量的访问冲突问题
    //定义切点
    @Pointcut("execution(public * com.example..*.*(..))")//定义切点
    public void aopWebLog(){

    }
    @Before("aopWebLog()")
    public  void  doBedore(JoinPoint joinPoint) throws  Throwable{
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//接收到请求，记录请求内容
        HttpServletRequest request=attributes.getRequest();
        logger.info("URL:"+request.getRequestURL().toString());
        logger.info("HTTP方法:"+request.getMethod());
        logger.info(("IP地址:"+request.getRemoteAddr()));
        logger.info("类的方法:"+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("参数:"+request.getQueryString());//记录请求的内容

    }
    @AfterReturning(pointcut = "aopWebLog()",returning = "retObject")
    public void doAfterReturning(Object retObject) throws Throwable{
        //处理完请求，返回内容
        logger.info("应答值:"+retObject);
        logger.info("费时:"+(System.currentTimeMillis()-startTime.get()));

    }



    //方法抛出异常退出时执行的通知

    @AfterThrowing(pointcut = "aopWebLog()",throwing="ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint,Exception ex){
        logger.error("执行"+"异常",ex);
    }

}
