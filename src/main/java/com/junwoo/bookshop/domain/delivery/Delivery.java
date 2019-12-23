package com.junwoo.bookshop.domain.delivery;

import com.junwoo.bookshop.domain.Address;
import com.junwoo.bookshop.domain.order.Order;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    /**
     * Order에서 연관관계 메소드 때문에 생성
     */
    public void orderUpdate(Order order) {
        this. order = order;
    }

    public void createAddress(Address address) {
        this.address = address;
    }
}
