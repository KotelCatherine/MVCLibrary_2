package ru.itk.mvclibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import ru.itk.mvclibrary.dto.BookDto;
import ru.itk.mvclibrary.exception.AuthorException;
import ru.itk.mvclibrary.exception.BookException;
import ru.itk.mvclibrary.mapper.BookMapper;
import ru.itk.mvclibrary.model.Author;
import ru.itk.mvclibrary.model.Book;
import ru.itk.mvclibrary.repository.AuthorRepository;
import ru.itk.mvclibrary.repository.BookRepository;
import ru.itk.mvclibrary.request.CreateBookRequest;
import ru.itk.mvclibrary.request.UpdateBookRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    private UUID authorId;
    private UUID bookId;
    private Author author;
    private Book book;
    private BookDto bookDto;
    private CreateBookRequest createRequest;
    private UpdateBookRequest updateRequest;

    @BeforeEach
    void setUp() {
        authorId = UUID.randomUUID();
        bookId = UUID.randomUUID();

        author = new Author();
        author.setId(authorId);
        author.setFirstName("Лев");
        author.setLastName("Толстой");

        book = new Book();
        book.setId(bookId);
        book.setTitle("Война и мир");
        book.setAuthor(author);

        bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle("Война и мир");

        createRequest = new CreateBookRequest();
        createRequest.setTitle("Война и мир");
        createRequest.setAuthorId(authorId);

        updateRequest = new UpdateBookRequest();
        updateRequest.setTitle("Анна Каренина");
    }

    @Test
    void createBook_whenValidRequest_thenSuccess() throws AuthorException {

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.createBook(createRequest);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());

    }

    @Test
    void createBook_whenAuthorNotFound_thenThrowException() {

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(AuthorException.class, () -> bookService.createBook(createRequest));

    }

    @Test
    void getAllBooks_whenPageableWithSort_thenReturnSorted() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        Page<BookDto> result = bookService.getAllBooks(pageable);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getContent().get(0).getTitle());

    }

    @Test
    void getAllBooks_whenPageableWithoutSort_thenReturnUnsorted() {

        Pageable unsortedPageable = PageRequest.of(0, 10);
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(book), expectedPageable, 1);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);

        Page<BookDto> result = bookService.getAllBooks(unsortedPageable);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getContent().get(0).getTitle());

    }

    @Test
    void getBook_whenBookExists_thenSuccess() throws BookException {

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getBook(bookId);

        assertNotNull(result);
        assertEquals(bookDto.getTitle(), result.getTitle());

    }

    @Test
    void getBook_whenBookNotFound_thenThrowException() {

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookService.getBook(bookId));

    }

    @Test
    void updateBook_whenValidRequest_thenSuccess() throws BookException {

        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Анна Каренина")
                .author(author)
                .build();

        BookDto updatedBookDto = BookDto.builder()
                .id(bookId)
                .title("Анна Каренина")
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.mapToBook(updateRequest)).thenReturn(updatedBook);
        when(bookRepository.saveAndFlush(updatedBook)).thenReturn(updatedBook);
        when(bookMapper.mapToBookDto(updatedBook)).thenReturn(updatedBookDto);

        BookDto result = bookService.updateBook(bookId, updateRequest);

        assertNotNull(result);
        assertEquals(updatedBookDto.getTitle(), result.getTitle());
        assertEquals(updatedBookDto.getAuthor(), result.getAuthor());

    }

    @Test
    void updateBook_whenBookNotFound_thenThrowException() {

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookService.updateBook(bookId, updateRequest));

    }

    @Test
    void deleteBook_whenBookExists_thenSuccess() throws BookException {

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

    }

    @Test
    void deleteBook_whenBookNotFound_thenThrowException() {

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookService.deleteBook(bookId));

    }

}