package ru.itk.mvclibrary.mapper;

import org.mapstruct.Mapper;
import ru.itk.mvclibrary.dto.BookDto;
import ru.itk.mvclibrary.model.Book;
import ru.itk.mvclibrary.request.UpdateBookRequest;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto mapToBookDto(Book book);

    Book mapToBook(UpdateBookRequest request);

}
