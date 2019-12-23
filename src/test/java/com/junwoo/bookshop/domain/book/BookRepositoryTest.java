package com.junwoo.bookshop.domain.book;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책저장_불러오기() throws Exception {
        //given
        bookRepository.save(Book.builder()
        .name("name")
        .price(10000)
        .stockQuantity(100)
        .author("author")
        .publisher("publisher")
        .content("content")
        .isbn("isbn")
        .build());

        //when
        List<Book> bookList = bookRepository.findAll();

        //then
        Book books = bookList.get(0);
        assertThat(books.getName()).isEqualTo("name");
        assertThat(books.getPrice()).isEqualTo(10000);
        assertThat(books.getStockQuantity()).isEqualTo(100);
        assertThat(books.getAuthor()).isEqualTo("author");
        assertThat(books.getPublisher()).isEqualTo("publisher");
        assertThat(books.getContent()).isEqualTo("content");
        assertThat(books.getIsbn()).isEqualTo("isbn");
    }
}