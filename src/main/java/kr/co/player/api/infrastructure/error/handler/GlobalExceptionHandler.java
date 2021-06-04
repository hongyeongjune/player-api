package kr.co.player.api.infrastructure.error.handler;

import kr.co.player.api.domain.shared.ResponseFormat;
import kr.co.player.api.infrastructure.error.exception.BusinessLogicException;
import kr.co.player.api.infrastructure.error.exception.UserDefineException;
import kr.co.player.api.infrastructure.error.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessLogicException.class, RuntimeException.class})
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }

    @ExceptionHandler(UserDefineException.class)
    public ResponseEntity handleUserDefineException(UserDefineException e) {
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        ResponseFormat responseFormat = ResponseFormat.fail(e.getMessage());
        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }

    /*
     * @Valid 어노테이션 에러 검증시 발생하는 예외를 처리해주는 메서드
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        ResponseFormat responseFormat = ResponseFormat.fail(errorMessage);

        return new ResponseEntity(responseFormat, HttpStatus.OK);
    }
}
