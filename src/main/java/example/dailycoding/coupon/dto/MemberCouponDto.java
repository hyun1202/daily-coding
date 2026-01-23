package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;

import java.time.LocalDateTime;
import java.util.List;

public record MemberCouponDto(
        String userName,
        List<CouponDto> coupons
) {
    public static MemberCouponDto of(MemberCoupon savedMemberCoupon) {
        Member member = savedMemberCoupon.getMember();
        List<Coupon> coupons = savedMemberCoupon.getCoupons();

        return new MemberCouponDto(
                member.getName(),
                coupons.stream()
                        .map(CouponDto::of)
                        .toList()
        );
    }
}
