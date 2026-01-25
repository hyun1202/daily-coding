package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.fixture.CouponFixture;
import example.dailycoding.coupon.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberCouponRepositoryTest {
    MemoryMemberCouponRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryMemberCouponRepository();
    }

    @Test
    @DisplayName("회원 쿠폰 저장")
    void save() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();
        Coupon coupon2 = CouponFixture.getCoupon2();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupons(List.of(coupon, coupon2)).build();

        // when
        MemberCoupon savedMemberCoupon = repository.save(memberCoupon);

        // then
        assertThat(savedMemberCoupon.getMember().getId()).isEqualTo(member.getId());
        assertThat(savedMemberCoupon.getCoupons())
                .hasSize(2)
                .extracting(Coupon::getId)
                .containsExactly(coupon.getId(), coupon2.getId());
    }

    @Test
    @DisplayName("회원에게 등록된 쿠폰 조회")
    void findById() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();
        Coupon coupon2 = CouponFixture.getCoupon2();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupons(List.of(coupon, coupon2)).build();

        repository.save(memberCoupon);

        // when
        MemberCoupon foundMemberCoupon = repository.findById(member.getId()).orElse(null);

        // then
        assertThat(foundMemberCoupon.getMember().getId()).isEqualTo(member.getId());
        assertThat(foundMemberCoupon.getCoupons())
                .hasSize(2)
                .extracting(Coupon::getId)
                .containsExactly(coupon.getId(), coupon2.getId());
    }
}