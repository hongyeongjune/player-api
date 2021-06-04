package kr.co.player.api.infrastructure.error.handler;

import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.infrastructure.error.exception.jwt.JwtTokenExpiredException;
import kr.co.player.api.infrastructure.error.exception.jwt.JwtTokenInvalidException;
import kr.co.player.api.infrastructure.logging.LogSchema;
import kr.co.player.api.infrastructure.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestControllerAdvice
public class FilterExceptionHandler {

    Logger logger = LoggerFactory.getLogger(FilterExceptionHandler.class);

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity handleJwtTokenExpiredException(JwtTokenExpiredException e) {
        ResponseFormat responseFormat = ResponseFormat.expire();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        LogSchema logSchema = new LogSchema(LogType.EXCEPTION, request, responseFormat, "token expired");
        logger.warn("{}", logSchema);

        return new ResponseEntity(responseFormat, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity handleJwtTokenInvalidException(JwtTokenInvalidException e) {
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        LogSchema logSchema = new LogSchema(LogType.EXCEPTION, request, responseFormat, "jwt invalid");
        logger.error("{}", logSchema);

        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }

}
