package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.dto.CouponDto;
import example.dailycoding.coupon.dto.LoginMember;
import example.dailycoding.coupon.dto.MemberCouponDto;
import example.dailycoding.coupon.dto.MemberCouponRequest;
import example.dailycoding.coupon.exception.DuplicateCouponException;
import example.dailycoding.coupon.fixture.CouponFixture;
import example.dailycoding.coupon.fixture.MemberFixture;
import example.dailycoding.coupon.repository.CouponRepository;
import example.dailycoding.coupon.repository.MemberCouponRepository;
import example.dailycoding.coupon.repository.MemberRepository;
import example.dailycoding.coupon.repository.impl.MemoryCouponRepository;
import example.dailycoding.coupon.repository.impl.MemoryMemberCouponRepository;
import example.dailycoding.coupon.repository.impl.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberCouponServiceTest {

    MemberCouponService service;
    MemberRepository memberRepository;
    CouponRepository couponRepository;
    MemberCouponRepository memberCouponRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        couponRepository = new MemoryCouponRepository();
        memberCouponRepository = new MemoryMemberCouponRepository();

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

    @Test
    @DisplayName("회원이 등록한 쿠폰 리스트를 가져올 수 있다")
    void getCoupons() {
        // given
        Member member = MemberFixture.get();
        Coupon coupon = CouponFixture.get();
        Coupon coupon2 = CouponFixture.getCoupon2();

        memberRepository.save(member);
        couponRepository.addCoupon(coupon);
        couponRepository.addCoupon(coupon2);

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .member(member)
                .coupons(List.of(coupon, coupon2))
                .build();

        memberCouponRepository.save(memberCoupon);

        LoginMember loginMember = new LoginMember(member.getId());

        // when
        MemberCouponDto result = service.getMemberCoupons(loginMember);

        // then
        assertThat(result.userName()).isEqualTo(member.getName());
        assertThat(result.coupons())
                .hasSize(2)
                .extracting(CouponDto::getId)
                .containsExactly(coupon.getId(), coupon2.getId());
    }

    @Test
    @DisplayName("회원이 등록한 쿠폰이 없으면 빈 리스트를 반환한다")
    void getCoupons_empty() {
        // given
        Member member = MemberFixture.get();
        memberRepository.save(member);

        LoginMember loginMember = new LoginMember(member.getId());

        // when
        MemberCouponDto result = service.getMemberCoupons(loginMember);

        // then
        assertThat(result.userName()).isEqualTo(member.getName());
        assertThat(result.coupons())
                .hasSize(0);
    }
}