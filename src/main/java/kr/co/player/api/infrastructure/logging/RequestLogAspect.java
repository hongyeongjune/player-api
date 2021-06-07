package kr.co.player.api.infrastructure.logging;

import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.infrastructure.interceptor.UserThreadLocal;
import kr.co.player.api.infrastructure.persistence.entity.UserEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
public class RequestLogAspect {

    Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    @Pointcut("execution(* kr.co.player.api.interfaces.controller.*.*(..))")
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object printRequestLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().toShortString();
        Object result = proceedingJoinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        ResponseFormat response = (ResponseFormat) result;
        try {
            UserEntity userEntity = UserThreadLocal.get();
            LogSchema logSchema;
            if(userEntity == null) {
                logSchema = new LogSchema(LogType.RESPONSE, request, response, methodName);
            }
            else {
                logSchema = new LogSchema(userEntity, LogType.RESPONSE, request, response, methodName);
            }

            logger.info("{}", logSchema.toString());

        } catch (Exception e) {
            logger.error("LoggerAspect error", e);
        }

        return result;
    }
}
