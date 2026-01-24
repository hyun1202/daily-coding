package example.dailycoding.coupon.fixture;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponFixture {

    public static Coupon get() {
        return Coupon.builder()
                .uuid("uuid")
                .name("쿠폰 1")
                .discountType(DiscountType.AMOUNT)
                .discount(BigDecimal.valueOf(10))
                .startDate(LocalDateTime.of(2026, 1, 18, 3, 51))
                .endDate(LocalDateTime.of(2026, 2, 18, 3, 51))
                .build();
    }

    public static Coupon getCoupon2() {
        return Coupon.builder()
                .uuid("uuid2")
                .name("쿠폰 2")
                .discountType(DiscountType.PERCENTAGE)
                .discount(BigDecimal.valueOf(20))
                .startDate(LocalDateTime.of(2026, 1, 18, 3, 51))
                .endDate(LocalDateTime.of(2026, 2, 18, 3, 51))
                .build();
    }
}
