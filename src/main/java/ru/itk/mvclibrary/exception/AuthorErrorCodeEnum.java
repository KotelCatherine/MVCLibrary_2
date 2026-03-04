package ru.itk.mvclibrary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthorErrorCodeEnum {

    INTERNAL_SERVER_ERROR("00", "Ошибка сервиса управления данными", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_OBJECT_PARAMS("01", "Неверные параметры объекта", HttpStatus.BAD_REQUEST),
    NOT_FOUND_AUTHOR_BY_ID("02", "Автор не найден", HttpStatus.NOT_FOUND);

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

}
