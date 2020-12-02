package cc.star.aspect;

import cc.star.annotation.UpdateOnly;
import cc.star.annotation.ReadOnly;
import cc.star.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * DAO层AOP 通过切面 及反射获取 执行的方法 所需的数据源 将其注入到Spring的Bean容器
 *
 */
@Component
@Aspect
//确保事务开启之前执行
@Order(-1)
public class SeriveAspect {

    private static final Logger logger = LoggerFactory.getLogger(SeriveAspect.class);

    //定义切入点
    @Pointcut("execution(public * cc.star.service.*.*(..))")
    public void dataSourceSwitch() {
    }



    @Before("dataSourceSwitch()")
    public void beforeMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        ReadOnly readOnlyAnnotation = method.getAnnotation(ReadOnly.class);
        UpdateOnly updateOnlyAnnotation = method.getAnnotation(UpdateOnly.class);
        if (null != readOnlyAnnotation && null !=updateOnlyAnnotation){
            throw new RuntimeException("同一个事务内不可定义多个 数据源");
        }
        if(null !=readOnlyAnnotation){
            //切换读数据源
            DynamicDataSourceContextHolder.setSlaveDataSourceType();
        }
        if (null != updateOnlyAnnotation){
            //切换写数据源
            DynamicDataSourceContextHolder.setMasterDataSourceType();
        }
    }


    @After("dataSourceSwitch()")
    public void after(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceType();
    }



}
