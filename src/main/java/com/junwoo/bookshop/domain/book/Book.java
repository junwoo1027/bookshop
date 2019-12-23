package com.junwoo.bookshop.domain.book;

import com.junwoo.bookshop.exception.NotEnoughStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String publisher;
    private String content;
    private String isbn;

    @Builder
    public Book(Long id, String name, int price, int stockQuantity, String author, String publisher, String content, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.publisher = publisher;
        this.content = content;
        this.isbn = isbn;
    }

    //비즈니스 로직//
    /**
     * 재고 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int quantity) {
        int resetStock = this.stockQuantity - quantity;
        if (resetStock < 0) {
            throw new NotEnoughStockException("재고수량이 0보다 작습니다.");
        }
        this.stockQuantity = resetStock;
    }
}
