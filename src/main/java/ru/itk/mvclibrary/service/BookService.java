package ru.itk.mvclibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.mvclibrary.dto.BookDto;
import ru.itk.mvclibrary.exception.AuthorErrorCodeEnum;
import ru.itk.mvclibrary.exception.AuthorException;
import ru.itk.mvclibrary.exception.BookErrorCodeEnum;
import ru.itk.mvclibrary.exception.BookException;
import ru.itk.mvclibrary.mapper.BookMapper;
import ru.itk.mvclibrary.model.Author;
import ru.itk.mvclibrary.model.Book;
import ru.itk.mvclibrary.repository.AuthorRepository;
import ru.itk.mvclibrary.repository.BookRepository;
import ru.itk.mvclibrary.request.CreateBookRequest;
import ru.itk.mvclibrary.request.UpdateBookRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional(rollbackFor = Exception.class)
    public BookDto createBook(CreateBookRequest request) throws AuthorException {

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AuthorException(AuthorErrorCodeEnum.NOT_FOUND_AUTHOR_BY_ID));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(author);

        book = bookRepository.saveAndFlush(book);

        return bookMapper.mapToBookDto(book);

    }
    
    public Page<BookDto> getAllBooks(Pageable pageable) {

        if(!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }

        Page<Book> books = bookRepository.findAll(pageable);
        
        return books.map(bookMapper::mapToBookDto);

    }


    public BookDto getBook(UUID id) throws BookException {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookException(BookErrorCodeEnum.NOT_FOUND_BOOK_BY_ID));

        return bookMapper.mapToBookDto(book);
        
    }

    @Transactional(rollbackFor = Exception.class)
    public BookDto updateBook(UUID id, UpdateBookRequest request) throws BookException{

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookException(BookErrorCodeEnum.NOT_FOUND_BOOK_BY_ID));

        book = bookMapper.mapToBook(request);

        book = bookRepository.saveAndFlush(book);

        return bookMapper.mapToBookDto(book);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBook(UUID id) throws BookException {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookException(BookErrorCodeEnum.NOT_FOUND_BOOK_BY_ID));

        bookRepository.deleteById(book.getId());

    }

}
