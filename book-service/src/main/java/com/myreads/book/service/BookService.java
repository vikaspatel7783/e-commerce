package com.myreads.book.service;

import com.myreads.book.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book save(Book book);
}
