package org.product.domain.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductPolicy {

    public static final int MIN_STOCK = 0;
    public static final int MAX_STOCK = 1_000_000;
    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 2_000_000_000;

    public static final String PRODUCT_NAME_NULL_ERROR_MESSAGE = "상품명은 필수입니다.";
    public static final String STOCK_RANGE_ERROR_MESSAGE = "재고는 " + MIN_STOCK + "원 이상, " + MAX_STOCK + "원 이상이어야 합니다.";
    public static final String PRICE_RANGE_ERROR_MESSAGE = "상품 가격은 " + MIN_PRICE + "원 이상, " + MAX_PRICE + "원 이상이어야 합니다.";

}
