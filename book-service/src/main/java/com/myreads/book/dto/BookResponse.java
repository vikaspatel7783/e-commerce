package com.myreads.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private List<Book> books;
}
