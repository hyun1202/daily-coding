package example.dailycoding.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CouponTest {

    @Test
    @DisplayName("쿠폰을 생성한다. 생성된 쿠폰은 고유한 ID를 가진다.")
    void createCoupon() {
        String uuid = UUID.randomUUID().toString();
        String name = "쿠폰 1";
        DiscountType discountType = DiscountType.AMOUNT;
        double discount = 0;
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 3, 51);
        LocalDateTime endDate = LocalDateTime.of(2026, 2, 18, 3, 51);

        Coupon coupon = new Coupon(
                uuid, name, discountType,
                discount, startDate, endDate
        );

        assertThat(coupon)
                .extracting(Coupon::getId, Coupon::getName, Coupon::getDiscountType)
                .containsExactly(uuid, name, discountType);
    }

}