package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.dto.MemberCouponDto;
import example.dailycoding.coupon.dto.MemberCouponRequest;
import example.dailycoding.coupon.exception.DuplicateCouponException;
import example.dailycoding.coupon.exception.InvalidCouponException;
import example.dailycoding.coupon.repository.CouponRepository;
import example.dailycoding.coupon.repository.MemberCouponRepository;
import example.dailycoding.coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponDto addCoupon(MemberCouponRequest request) {
        String memberId = request.memberId();
        String couponId = request.couponId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("not found member, id: " + memberId));

        return addCoupon(member, couponId);
    }

    protected MemberCouponDto addCoupon(Member member, String couponId) {
        // 존재하는 쿠폰 ID 확인
        Coupon coupon = couponRepository.getCoupon(couponId)
                .orElseThrow(() -> new NoSuchElementException("not found coupon, id: " + couponId));

        LocalDateTime now = LocalDateTime.now();

        if (!coupon.isValid(now)) {
            throw new InvalidCouponException("invalid coupon, id: " + couponId);
        }

        MemberCoupon memberCoupon = memberCouponRepository.findById(member.getId())
                .orElseGet(() -> MemberCoupon.builder()
                        .member(member)
                        .build());

        if (!memberCoupon.addCoupon(coupon)) {
            throw new DuplicateCouponException("duplicate coupon, id: " + couponId);
        }

        MemberCoupon savedMemberCoupon = memberCouponRepository.save(memberCoupon);

        return MemberCouponDto.of(savedMemberCoupon);
    }
}
