package com.myreads.book;

import com.myreads.book.contoller.BookController;
import com.myreads.book.dto.Book;
import com.myreads.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookServiceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BookService bookService;

	@Test
	void contextLoads() {
	}

	@Test
	void testGetAllBooksCategories() throws Exception {
//		when(bookService.getAllCategories()).thenReturn(getBookCategoryResponse());

		this.mockMvc.perform(get("http://localhost:8080/myreads/books/categories"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("BookName")));
	}

//	private List<Book> getBookCategoryResponse() {
////		Book cat1 = new Book(1, "BookName", "BookDescription");
////		BookCategory cat2 = new BookCategory(2, "BookName-2", "BookDescription-2");
////		BookCategory cat3 = new BookCategory(3, "BookName-3", "BookDescription-3");
//
//		ArrayList<Book> bookCategories = new ArrayList<>();
////		bookCategories.add(cat1);
////		bookCategories.add(cat2);
////		bookCategories.add(cat3);
//
//		return bookCategories;
//	}
}
