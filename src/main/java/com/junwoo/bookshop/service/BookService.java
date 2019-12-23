package com.junwoo.bookshop.service;

import com.junwoo.bookshop.domain.book.Book;
import com.junwoo.bookshop.domain.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void saveItem(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findItems() {
        return bookRepository.findAll();
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 도서가 없습니다. bookId=" + bookId));
    }
}
