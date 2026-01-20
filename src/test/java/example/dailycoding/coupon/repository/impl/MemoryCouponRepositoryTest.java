package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.DiscountType;
import example.dailycoding.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MemoryCouponRepositoryTest {
    CouponRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryCouponRepository();
    }

    @Test
    @DisplayName("생성된 모든 쿠폰을 가져온다")
    void getAllCoupons() {
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

        String uuid2 = UUID.randomUUID().toString();
        String name2 = "coupon2";
        BigDecimal discount2 = BigDecimal.valueOf(100);
        LocalDateTime startDate2 = LocalDateTime.of(2026, 1, 16, 2, 11);
        LocalDateTime endDate2 = startDate2.plusDays(20);

        Coupon coupon2 = Coupon.builder()
                .uuid(uuid2)
                .name(name2)
                .discount(discount2)
                .startDate(startDate2)
                .endDate(endDate2)
                .build();

        repository.addCoupon(coupon);
        repository.addCoupon(coupon2);

        // when
        List<Coupon> allCoupons = repository.getAllCoupons();

        // then
        assertThat(allCoupons)
                .hasSize(2)
                .extracting(Coupon::getId, Coupon::getName, Coupon::getDiscount, Coupon::getStartDate, Coupon::getEndDate)
                .containsExactlyInAnyOrder(
                        tuple(uuid, name, discount, startDate, endDate),
                        tuple(uuid2, name2, discount2, startDate2, endDate2)
                );
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