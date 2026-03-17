package ru.itk.mvclibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.itk.mvclibrary.dto.BookDto;
import ru.itk.mvclibrary.exception.AuthorException;
import ru.itk.mvclibrary.exception.BookException;
import ru.itk.mvclibrary.request.CreateBookRequest;
import ru.itk.mvclibrary.request.UpdateBookRequest;
import ru.itk.mvclibrary.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequest request) throws AuthorException {
        return service.createBook(request);
    }

    @GetMapping("/pageBooks")
    public Page<BookDto> getAllBooks(@ParameterObject Pageable pageable) {
        return service.getAllBooks(pageable);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable UUID id) throws BookException {
        return service.getBook(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable UUID id, @RequestBody UpdateBookRequest request) throws BookException{
        return service.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) throws BookException{
        service.deleteBook(id);
    }


}
