package example.dailycoding.coupon.domain;

import example.dailycoding.coupon.exception.DuplicateCouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberCouponTest {

    @Test
    @DisplayName("계정에 쿠폰을 등록한다")
    void addCoupon() {
        // given
        Member member = Member.builder()
                .name("member1")
                .id("id1")
                .build();

        Coupon coupon = Coupon.builder()
                .uuid("uuid1")
                .name("coupon1")
                .discount(BigDecimal.valueOf(10))
                .discountType(DiscountType.PERCENTAGE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7))
                .build();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .build();

        // when
        memberCoupon.addCoupon(coupon);

        // then
        assertThat(memberCoupon.getCoupons())
                .hasSize(1)
                .extracting(Coupon::getId)
                .containsExactly("uuid1");
    }

    @Test
    @DisplayName("계정에 이미 등록된 쿠폰은 등록할 수 없다")
    void addCoupon_fail() {
        // given
        Member member = Member.builder()
                .name("member1")
                .id("id1")
                .build();

        Coupon coupon = Coupon.builder()
                .uuid("uuid1")
                .name("coupon1")
                .discount(BigDecimal.valueOf(10))
                .discountType(DiscountType.PERCENTAGE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7))
                .build();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupons(List.of(coupon))
                .build();

        // when & then
        assertThatThrownBy(() -> {
            memberCoupon.addCoupon(coupon);
        }).isInstanceOf(DuplicateCouponException.class);
    }
}