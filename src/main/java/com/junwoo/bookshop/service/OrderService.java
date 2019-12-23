package com.junwoo.bookshop.service;

import com.junwoo.bookshop.domain.book.Book;
import com.junwoo.bookshop.domain.book.BookRepository;
import com.junwoo.bookshop.domain.delivery.Delivery;
import com.junwoo.bookshop.domain.member.Member;
import com.junwoo.bookshop.domain.member.MemberRepository;
import com.junwoo.bookshop.domain.order.Order;
import com.junwoo.bookshop.domain.order.OrderRepository;
import com.junwoo.bookshop.domain.orderBook.OrderBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long bookId, int count) {

        //엔티티 조회
        Member member = memberRepository.getOne(memberId);
        Book book = bookRepository.getOne(bookId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.createAddress(member.getAddress());

        //주문상품 생성
        OrderBook orderBook = OrderBook.createOrderItem(book, book.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderBook);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.cancel();
    }

    /**
     * 주문 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }

}
