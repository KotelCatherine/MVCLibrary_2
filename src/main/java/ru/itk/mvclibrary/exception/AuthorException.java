package ru.itk.mvclibrary.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorException extends Exception {

    private final String description;
    private final String errorCode;
    private final HttpStatus status;

    public AuthorException(AuthorErrorCodeEnum code) {

        super(code.getDescription());
        this.description = code.getDescription();
        this.errorCode = code.getErrorCode();
        this.status = code.getStatus();

    }

}
