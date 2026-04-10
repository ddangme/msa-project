package org.product.domain.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductPolicy {

    public static final int MIN_STOCK = 0;
    public static final int MAX_STOCK = 1_000_000;
    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 2_000_000_000;

}
