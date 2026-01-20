package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.DiscountType;
import example.dailycoding.coupon.dto.CouponDto;
import example.dailycoding.coupon.dto.CouponRequest;
import example.dailycoding.coupon.repository.CouponRepository;
import example.dailycoding.coupon.repository.impl.MemoryCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CouponServiceTest {

    CouponRepository repository;
    CouponService service;

    @BeforeEach
    void setUp() {
        repository = new MemoryCouponRepository();
        service = new CouponService(repository);
    }

    @Test
    @DisplayName("쿠폰을 생성할 수 있다")
    void testCreateCoupon() {
        // given
        String name = "coupon1";
        DiscountType discountType = DiscountType.PERCENTAGE;
        BigDecimal discount = BigDecimal.valueOf(10);
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 2, 11);
        LocalDateTime endDate = startDate.plusDays(10);

        CouponRequest request = new CouponRequest(
                name,
                discountType,
                discount,
                startDate,
                endDate
        );

        // when
        CouponDto coupon = service.createCoupon(request);

        // then
        assertThat(coupon)
                .extracting(CouponDto::getName, CouponDto::getDiscountType, CouponDto::getDiscount, CouponDto::getStartDate, CouponDto::getEndDate)
                .containsExactly(name, discountType, discount, startDate, endDate);
        assertThat(coupon.getId()).isNotNull();
    }

    @Test
    @DisplayName("쿠폰을 ID로 조회할 수 있다")
    void testGetCoupon() {
        // given
        String name = "coupon1";
        DiscountType discountType = DiscountType.PERCENTAGE;
        BigDecimal discount = BigDecimal.valueOf(10);
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 2, 11);
        LocalDateTime endDate = startDate.plusDays(10);

        CouponRequest request = new CouponRequest(
                name,
                discountType,
                discount,
                startDate,
                endDate
        );

        CouponDto createdCoupon = service.createCoupon(request);

        String id = createdCoupon.getId();

        // when
        CouponDto coupon = service.getCoupon(id);

        // then
        assertThat(coupon)
                .extracting(CouponDto::getId, CouponDto::getName, CouponDto::getDiscountType, CouponDto::getDiscount, CouponDto::getStartDate, CouponDto::getEndDate)
                .containsExactly(id, name, discountType, discount, startDate, endDate);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 조회시 예외를 던진다")
    void testGetCouponNotFound() {
        // when & then
        assertThatThrownBy(() -> {
            service.getCoupon("111");
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("모든 쿠폰을 조회할 수 있다")
    void testGetCoupons() {
        // given
        String name = "coupon1";
        DiscountType discountType = DiscountType.PERCENTAGE;
        BigDecimal discount = BigDecimal.valueOf(10);
        LocalDateTime startDate = LocalDateTime.of(2026, 1, 18, 2, 11);
        LocalDateTime endDate = startDate.plusDays(10);

        CouponRequest request = new CouponRequest(
                name,
                discountType,
                discount,
                startDate,
                endDate
        );

        String name2 = "coupon2";
        DiscountType discountType2 = DiscountType.PERCENTAGE;
        BigDecimal discount2 = BigDecimal.valueOf(100);
        LocalDateTime startDate2 = LocalDateTime.of(2026, 1, 28, 2, 11);
        LocalDateTime endDate2 = startDate2.plusDays(5);

        CouponRequest request2 = new CouponRequest(
                name2,
                discountType2,
                discount2,
                startDate2,
                endDate2
        );

        service.createCoupon(request);
        service.createCoupon(request2);

        // when
        List<CouponDto> coupons = service.getCoupons();

        // then
        assertThat(coupons)
                .hasSize(2)
                .extracting(CouponDto::getName, CouponDto::getDiscountType, CouponDto::getDiscount, CouponDto::getStartDate, CouponDto::getEndDate)
                .containsExactlyInAnyOrder(
                        tuple(name, discountType, discount, startDate, endDate),
                        tuple(name2, discountType2, discount2, startDate2, endDate2)
                );
    }
}