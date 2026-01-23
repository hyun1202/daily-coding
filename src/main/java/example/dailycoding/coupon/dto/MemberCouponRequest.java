package example.dailycoding.coupon.dto;

public record MemberCouponRequest(
        String memberId,
        String couponId
) {
}
