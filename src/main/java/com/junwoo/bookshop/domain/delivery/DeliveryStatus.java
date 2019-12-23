package com.junwoo.bookshop.domain.delivery;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    READY("배송준비"),
    COMP("배송완료");

    private final String title;
}
