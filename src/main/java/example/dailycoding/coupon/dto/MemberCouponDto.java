package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.domain.MemberCoupon;

import java.time.LocalDateTime;
import java.util.List;

public record MemberCouponDto(
        String userName,
        CouponDto coupon
) {
    public static MemberCouponDto of(MemberCoupon savedMemberCoupon) {
        Member member = savedMemberCoupon.getMember();
        Coupon coupon1 = savedMemberCoupon.getCoupon();

        return new MemberCouponDto(
                member.getName(),
                CouponDto.of(coupon1)
        );
    }
}
