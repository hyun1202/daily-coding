package example.dailycoding.coupon.domain;

import lombok.Getter;

@Getter
public enum DiscountType {
    PERCENTAGE("percentage"),
    AMOUNT("amount"),
    ;

    private final String value;

    DiscountType(String value) {
        this.value = value;
    }
}