package ru.itk.mvclibrary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {

        log.error("handleException ->", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                BookErrorCodeEnum.INTERNAL_SERVER_ERROR.getDescription(),
                BookErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorCode(),
                BookErrorCodeEnum.INTERNAL_SERVER_ERROR.getStatus()
        );


        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);

    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<ErrorResponse> handleCommunicationException(BookException exception) {

        log.error("handleCommunicationException -> code={}, ", exception.getErrorCode(), exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getDescription(),  exception.getErrorCode(), exception.getStatus());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);

    }

    @ExceptionHandler(AuthorException.class)
    protected ResponseEntity<ErrorResponse> handleCommunicationException(AuthorException exception) {

        log.error("handleCommunicationException -> code={}, ", exception.getErrorCode(), exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getDescription(), exception.getErrorCode(), exception.getStatus());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        log.error("handleMethodArgumentNotValidException -> ", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                BookErrorCodeEnum.WRONG_OBJECT_PARAMS.getDescription(),
                BookErrorCodeEnum.WRONG_OBJECT_PARAMS.getErrorCode(),
                BookErrorCodeEnum.WRONG_OBJECT_PARAMS.getStatus());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);


    }

}
