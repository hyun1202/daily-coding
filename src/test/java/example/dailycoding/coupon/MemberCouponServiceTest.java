package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.dto.CouponDto;
import example.dailycoding.coupon.dto.MemberCouponDto;
import example.dailycoding.coupon.dto.MemberCouponRequest;
import example.dailycoding.coupon.exception.DuplicateCouponException;
import example.dailycoding.coupon.fixture.CouponFixture;
import example.dailycoding.coupon.fixture.MemberFixture;
import example.dailycoding.coupon.repository.CouponRepository;
import example.dailycoding.coupon.repository.MemberRepository;
import example.dailycoding.coupon.repository.impl.MemoryCouponRepository;
import example.dailycoding.coupon.repository.impl.MemoryMemberCouponRepository;
import example.dailycoding.coupon.repository.impl.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberCouponServiceTest {

    MemberCouponService service;
    MemberRepository memberRepository;
    CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        couponRepository = new MemoryCouponRepository();
        var memberCouponRepository = new MemoryMemberCouponRepository();

        service = new MemberCouponService(memberRepository, couponRepository, memberCouponRepository);
    }

    @Test
    @DisplayName("회원은 발급된 쿠폰을 추가할 수 있다")
    void addCoupon() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();

        memberRepository.save(member);
        couponRepository.addCoupon(coupon);

        var request = new MemberCouponRequest(
                member.getId(),
                coupon.getId()
        );

        // when
        MemberCouponDto result = service.addCoupon(request);

        // then
        assertThat(result.userName()).isEqualTo(member.getName());
        assertThat(result.coupons())
                .hasSize(1)
                .extracting(CouponDto::getId)
                .containsExactly(coupon.getId());
    }

    @Test
    @DisplayName("회원은 이미 등록된 쿠폰을 다시 등록할 수 없다")
    void addCoupon_fail() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();

        memberRepository.save(member);
        couponRepository.addCoupon(coupon);

        var request = new MemberCouponRequest(
                member.getId(),
                coupon.getId()
        );

        // when
        assertThatThrownBy(() -> {
            service.addCoupon(request);
            service.addCoupon(request); // 두번 등록
        }).isInstanceOf(DuplicateCouponException.class);
    }
}