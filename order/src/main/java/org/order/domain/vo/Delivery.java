package org.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String shopperName;

    private String message;

    public Delivery(String address, String detailAddress, String shopperName, String message) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.shopperName = shopperName;
        this.message = message;
    }

}
