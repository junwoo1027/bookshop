package com.junwoo.bookshop.domain.orderBook;

import com.junwoo.bookshop.domain.book.Book;
import com.junwoo.bookshop.domain.order.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    public void createBook(Book book) {
        this.book = book;
    }

    public void createOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void createCount(int count) {
        this.count = count;
    }

    //생성 메서드
    public static OrderBook createOrderItem(Book book, int orderPrice, int count) {
        OrderBook orderBook = new OrderBook();
        orderBook.createBook(book);
        orderBook.createOrderPrice(orderPrice);
        orderBook.createCount(count);

        book.removeStock(count);
        return orderBook;
    }

    /**
     * Order에서 연관관계 메소드 때문에 생성
     */
    public void orderUpdate(Order order) {
        this.order = order;
    }

    //비즈니스 로직
    public void cancel() {
        getBook().addStock(count);
    }

    //조회 로직
    /**
     * 주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * count;
    }
}
