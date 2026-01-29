package example.dailycoding.coupon.domain;

import example.dailycoding.coupon.exception.AlreadyUsedCouponException;
import example.dailycoding.coupon.fixture.CouponFixture;
import example.dailycoding.coupon.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberCouponTest {

    @Test
    @DisplayName("쿠폰을 생성하면 미사용 상태로 생성된다")
    void createCoupon() {
        // given
        Coupon coupon = CouponFixture.get();
        Member member = MemberFixture.get();

        // when
        MemberCoupon memberCoupon = MemberCoupon.of(member, coupon);

        // then
        assertThat(memberCoupon.getStatus()).isEqualTo("미사용");
    }

    @Test
    @DisplayName("해당 쿠폰을 가지고 있으면 true를 반환한다")
    void hasCoupon() {
        // given
        Coupon coupon = CouponFixture.get();
        Member member = MemberFixture.get();

        String id = coupon.getId();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();

        // when
        boolean result = memberCoupon.hasCoupon(id);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해당 쿠폰을 가지고 있지 않으면 false를 반환한다")
    void hasCoupon_false() {
        // given
        Coupon coupon = CouponFixture.get();
        Member member = MemberFixture.get();

        String id = "a";

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();

        // when
        boolean result = memberCoupon.hasCoupon(id);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("쿠폰을 사용할 수 있다")
    void useCoupon() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();

        // when
        memberCoupon.useCoupon();

        // then
        assertThat(memberCoupon.getStatus()).isEqualTo("사용 완료");
    }

    @Test
    @DisplayName("이미 사용한 쿠폰은 다시 사용할 수 없다")
    void useCoupon_fail() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();

        // when & then
        assertThatThrownBy(() -> {
            memberCoupon.useCoupon();
            memberCoupon.useCoupon();
        }).isInstanceOf(AlreadyUsedCouponException.class);
    }
}