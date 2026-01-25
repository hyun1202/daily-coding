package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.dto.LoginMember;
import example.dailycoding.coupon.dto.MemberCouponDto;
import example.dailycoding.coupon.dto.MemberCouponRequest;
import example.dailycoding.coupon.exception.InvalidCouponException;
import example.dailycoding.coupon.repository.CouponRepository;
import example.dailycoding.coupon.repository.MemberCouponRepository;
import example.dailycoding.coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponDto getMemberCoupons(LoginMember loginMember) {
        Member member = findMember(loginMember.memberId());

        return getMemberCoupons(member);
    }

    protected MemberCouponDto getMemberCoupons(Member member) {
        MemberCoupon memberCoupon = memberCouponRepository.findById(member.getId())
                .orElseGet(() -> new MemberCoupon(member, new ArrayList<>()));

        return MemberCouponDto.of(memberCoupon);
    }

    public MemberCouponDto addCoupon(MemberCouponRequest request) {
        Member member = findMember(request.memberId());
        return addCoupon(member, request.couponId());
    }

    protected MemberCouponDto addCoupon(Member member, String couponId) {
        // 존재하는 쿠폰 ID 확인
        Coupon coupon = getCoupon(couponId);

        // 쿠폰 중복 여부 확인
        validateCoupon(coupon);

        // 유저 쿠폰 조회
        MemberCoupon memberCoupon = findOrCreateMemberCoupon(member);

        // 쿠폰 중복 여부 조회
        addCouponToMemberCoupon(memberCoupon, coupon);

        // 쿠폰 등록
        MemberCoupon savedMemberCoupon = memberCouponRepository.save(memberCoupon);

        return MemberCouponDto.of(savedMemberCoupon);
    }

    public void deleteCoupon(LoginMember loginMember, String couponId) {
        Member member = findMember(loginMember.memberId());

        deleteCoupon(member, couponId);
    }

    protected void deleteCoupon(Member member, String couponId) {
        Coupon coupon = getCoupon(couponId);

        MemberCoupon memberCoupon = memberCouponRepository.findById(member.getId())
                .orElseThrow(() -> new NoSuchElementException("this coupon not found in member's, id: " + couponId));

        memberCoupon.deleteCoupon(coupon);
    }

    private Member findMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("not found member, id: " + memberId));
    }

    private Coupon getCoupon(String couponId) {
        return couponRepository.getCoupon(couponId)
                .orElseThrow(() -> new NoSuchElementException("not found coupon, id: " + couponId));
    }

    private void validateCoupon(Coupon coupon) {
        if (!coupon.isValid(LocalDateTime.now())) {
            throw new InvalidCouponException("invalid coupon, id: " + coupon.getId());
        }
    }

    private MemberCoupon findOrCreateMemberCoupon(Member member) {
        return memberCouponRepository.findById(member.getId())
                .orElseGet(() -> MemberCoupon.builder()
                        .member(member)
                        .build());
    }

    private void addCouponToMemberCoupon(MemberCoupon memberCoupon, Coupon coupon) {
        memberCoupon.addCoupon(coupon);
    }
}
