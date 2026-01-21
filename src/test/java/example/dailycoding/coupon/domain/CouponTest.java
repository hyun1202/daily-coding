package example.dailycoding.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CouponTest {

    @Test
    @DisplayName("쿠폰을 생성한다. 생성된 쿠폰은 고유한 ID를 가진다.")
    void createCoupon() {
        // given & when
        Coupon coupon = getCoupon();

        // then
        assertThat(coupon)
                .extracting(Coupon::getId, Coupon::getName, Coupon::getDiscountType)
                .containsExactly(coupon.getId(), coupon.getName(), coupon.getDiscountType());
    }

    @Test
    @DisplayName("쿠폰이 유효한 상태이면 성공한다")
    void isValid_success() {
        // given
        Coupon coupon = getCoupon();
        LocalDateTime currentDate = LocalDateTime.of(2026, 1, 19, 3, 51);
        // 시작날짜, 만료날짜 포함
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 3, 51);
        LocalDateTime endDate = LocalDateTime.of(2026, 2, 18, 3, 51);

        // when
        boolean result = coupon.isValid(currentDate);
        boolean result2 = coupon.isValid(startDate);
        boolean result3 = coupon.isValid(endDate);

        // then
        assertThat(result).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @Test
    @DisplayName("쿠폰이 유효기간을 넘기거나 시작기간이 아니면 실패한다")
    void isValid_fail() {
        // given
        Coupon coupon = getCoupon();
        LocalDateTime beforeStartDate = LocalDateTime.of(2026, 1, 1, 3, 51);
        LocalDateTime afterEndDate = LocalDateTime.of(2026, 3, 19, 3, 51);

        // when
        boolean result = coupon.isValid(beforeStartDate);
        boolean result2 = coupon.isValid(afterEndDate);

        // then
        assertThat(result).isFalse();
        assertThat(result2).isFalse();
    }

    Coupon getCoupon() {
        String uuid = UUID.randomUUID().toString();
        String name = "쿠폰 1";
        DiscountType discountType = DiscountType.AMOUNT;
        BigDecimal discount = BigDecimal.valueOf(0);
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 3, 51);
        LocalDateTime endDate = LocalDateTime.of(2026, 2, 18, 3, 51);

        Coupon coupon = new Coupon(
                uuid, name, discountType,
                discount, startDate, endDate
        );

        return coupon;
    }
}