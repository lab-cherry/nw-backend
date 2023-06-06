package lab.cherry.nw.error.handler;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
    }


    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
     */
    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException e) {
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.DUPLICATE.getStatus()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final CustomException e) {
        e.printStackTrace();
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final BusinessException e) {
//        e.printStackTrace();
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
//    }
}
