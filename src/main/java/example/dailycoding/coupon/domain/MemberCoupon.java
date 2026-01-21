package example.dailycoding.coupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class MemberCoupon {
    private Member member;
    private List<Coupon> coupons;

    @Builder
    public MemberCoupon(Member member, List<Coupon> coupons) {
        this.member = member;
        this.coupons = coupons;
    }
}
