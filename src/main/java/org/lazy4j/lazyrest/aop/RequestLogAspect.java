package org.lazy4j.lazyrest.aop;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求日志记录
 * <p/>
 * Created by Melon on 17/2/15.
 */
@Aspect
@Component
public class RequestLogAspect {

    public static final Logger logger = Logger.getLogger(RequestLogAspect.class);


    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void doAfterReturning() {

    }

    /**
     * @param joinPoint
     */
    @AfterReturning(value = "doAfterReturning()")
    public void doAfterReturning(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("className", joinPoint.getTarget().getClass().getName());
        map.put("methodName", method.getName());
        map.put("args", JSONUtils.toJSONString(joinPoint.getArgs().toString()));
        logger.error(JSONUtils.toJSONString(map));
    }
}
