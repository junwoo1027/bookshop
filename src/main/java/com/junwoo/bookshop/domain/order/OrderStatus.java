package com.junwoo.bookshop.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDER("주문완료"),
    CANCEL("주문취소");

    private final String title;

}
