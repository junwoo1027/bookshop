package com.junwoo.bookshop.domain.order;

import com.junwoo.bookshop.domain.delivery.Delivery;
import com.junwoo.bookshop.domain.delivery.DeliveryStatus;
import com.junwoo.bookshop.domain.member.Member;
import com.junwoo.bookshop.domain.orderBook.OrderBook;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    @Builder
    public Order(Member member, Delivery delivery, OrderStatus status, LocalDateTime orderDate) {
        this.member = member;
        this.delivery = delivery;
        this.status = status;
        this.orderDate = orderDate;
    }

    public void createStatus(OrderStatus status) {
        this.status = status;
    }

    public void createOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderBook orderBook) {
        orderBooks.add(orderBook);
        orderBook.orderUpdate(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.orderUpdate(this);
    }

    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderBook... orderBooks) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderBook orderBook : orderBooks) {
            order.addOrderItem(orderBook);
        }
        order.createStatus(OrderStatus.CANCEL);
        order.createOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직
    /**
     * 주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소를 못합니다.");
        }

        this.createStatus(OrderStatus.CANCEL);
        for (OrderBook orderBook : orderBooks) {
            orderBook.cancel();
        }
    }

    //조회 로직
    /**
     * 전체 주문 가격 조회
    */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderBook orderBook : orderBooks) {
            totalPrice += orderBook.getTotalPrice();
        }
        return totalPrice;
    }
}
