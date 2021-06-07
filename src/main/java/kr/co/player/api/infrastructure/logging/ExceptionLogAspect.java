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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
public class ExceptionLogAspect {

    Logger logger = LoggerFactory.getLogger(ExceptionLogAspect.class);

    @Pointcut("execution(* kr.co.player.api.infrastructure.error.handler.GlobalExceptionHandler.handleRuntimeException(..))")
    public void runtimeExceptionPointCut(){
    }

    @Pointcut("execution(* kr.co.player.api.infrastructure.error.handler.GlobalExceptionHandler.handleUserDefineException(..))")
    public void userDefineExceptionPointCut(){
    }

    @Pointcut("execution(* kr.co.player.api.infrastructure.error.handler.GlobalExceptionHandler.handleMethodArgumentNotValidException(..))")
    public void methodArgumentExceptionPointCut(){
    }

    @Pointcut("execution(* kr.co.player.api.infrastructure.error.handler.GlobalExceptionHandler.handleException(..))")
    public void exceptionPointCut(){
    }

    @Around(value = "exceptionPointCut()")
    public Object printErrorLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().toShortString();
        Object result = proceedingJoinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        ResponseEntity response = (ResponseEntity) result;

        UserEntity userEntity = UserThreadLocal.get();

        LogSchema logSchema;
        if(userEntity == null){
            logSchema = new LogSchema(LogType.EXCEPTION, request, (ResponseFormat) response.getBody(), methodName);
        }else {
            logSchema = new LogSchema(userEntity, LogType.EXCEPTION, request, (ResponseFormat) response.getBody(), methodName);
        }
        logger.error("{}", logSchema.toString());

        return result;
    }

    @Around(value = "runtimeExceptionPointCut() || userDefineExceptionPointCut() || methodArgumentExceptionPointCut()")
    public Object printWarnLog(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().toShortString();
        Object result = pjp.proceed();
        ResponseEntity response = (ResponseEntity) result;
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        UserEntity userEntity = UserThreadLocal.get();

        try {
            LogSchema logSchema;
            if (userEntity == null) {
                logSchema = new LogSchema(LogType.EXCEPTION, request, (ResponseFormat) response.getBody(), methodName);
            } else {
                logSchema = new LogSchema(userEntity, LogType.EXCEPTION, request, (ResponseFormat) response.getBody(), methodName);
            }
            logger.warn("{}", logSchema.toString());
        }catch (Exception e){
            return result;
        }
        return result;
    }
}
