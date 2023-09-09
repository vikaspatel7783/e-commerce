package com.myreads.book.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreads.book.dto.Book;
import com.myreads.book.dto.BookResponse;
import com.myreads.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/myreads/books")
public class BookController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/browse", produces = "application/json")
    public ResponseEntity<BookResponse> getAllBooks() {
        LOGGER.info("Request received to getAllBooks.");
        List<com.myreads.book.entity.Book> allBooksEntity = bookService.getAllBooks();
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBooks(mapEntityToDto(allBooksEntity));
        LOGGER.info("Returning response of getAllBooks");
        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        LOGGER.info("Request received to add category");
        com.myreads.book.entity.Book savedCategory = bookService.save(mapDtoToEntity(book));
        LOGGER.info("Returning response of addBookCategory");
        return ResponseEntity.ok(mapEntityToDto(savedCategory));
    }

    private List<Book> mapEntityToDto(List<com.myreads.book.entity.Book> allBooksEntity) {
        return allBooksEntity.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    private Book mapEntityToDto(com.myreads.book.entity.Book book) {
        return new ObjectMapper().convertValue(book, Book.class);
    }

    private com.myreads.book.entity.Book mapDtoToEntity(Book book) {
        return new ObjectMapper().convertValue(book, com.myreads.book.entity.Book.class);
    }
}
