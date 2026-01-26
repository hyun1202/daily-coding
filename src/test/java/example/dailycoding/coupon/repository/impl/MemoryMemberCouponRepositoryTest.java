package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.domain.MemberCoupons;
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

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();

        // when
        MemberCoupon savedMemberCoupon = repository.save(memberCoupon);

        // then
        assertThat(savedMemberCoupon.getMember().getId()).isEqualTo(member.getId());
        assertThat(savedMemberCoupon.getCoupon())
                .extracting(Coupon::getId)
                .isEqualTo(coupon.getId());
    }

    @Test
    @DisplayName("회원에게 등록된 쿠폰 조회")
    void findById() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .build();

        repository.save(memberCoupon);

        // when
        MemberCoupons result = repository.findById(member.getId());

        // then
        assertThat(result.getMemberCoupons())
                .hasSize(1)
                .extracting(MemberCoupon::getCoupon)
                .extracting(Coupon::getId)
                .containsExactly(coupon.getId());
    }
}