package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.DiscountType;
import example.dailycoding.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryCouponRepositoryTest {
    CouponRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryCouponRepository();
    }

    @Test
    @DisplayName("생성된 쿠폰을 가져온다.")
    void getCoupon() {
        // given
        String uuid = UUID.randomUUID().toString();
        String name = "coupon1";
        BigDecimal discount = BigDecimal.valueOf(10);
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 2, 11);
        LocalDateTime endDate = startDate.plusDays(10);

        Coupon coupon = Coupon.builder()
                .uuid(uuid)
                .name(name)
                .discount(discount)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // when
        repository.addCoupon(coupon);
        Optional<Coupon> result = repository.getCoupon(uuid);

        assertThat(result)
                .isPresent()
                .get()
                .extracting(Coupon::getId, Coupon::getName, Coupon::getDiscount,
                            Coupon::getStartDate, Coupon::getEndDate)
                .containsExactly(uuid, name, discount, startDate, endDate);
    }

    @Test
    @DisplayName("쿠폰을 생성한다.")
    void addCoupon() {
        // given
        String uuid = UUID.randomUUID().toString();
        String name = "coupon1";
        BigDecimal discount = BigDecimal.valueOf(10);
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 2, 11);
        LocalDateTime endDate = startDate.plusDays(10);

        Coupon coupon = Coupon.builder()
                .uuid(uuid)
                .name(name)
                .discount(discount)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // when
        Coupon savedCoupon = repository.addCoupon(coupon);

        // then
        assertThat(savedCoupon).extracting(
                Coupon::getId, Coupon::getName, Coupon::getDiscount,
                Coupon::getDiscountType, Coupon::getStartDate, Coupon::getEndDate)
                .containsExactly(
                        uuid, name, discount,
                        DiscountType.AMOUNT, startDate, endDate
                );
    }

}