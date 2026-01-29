package example.dailycoding.coupon.domain;

import example.dailycoding.coupon.exception.AlreadyUsedCouponException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@ToString
@Getter
public class MemberCoupon {
    private final Member member;
    private final Coupon coupon;
    private String status;

    @Builder
    private MemberCoupon(Member member, Coupon coupon, String status) {
        this.member = Objects.requireNonNull(member, "member cannot be null");
        this.coupon = Objects.requireNonNull(coupon, "coupon cannot be null");
        this.status = status;
    }

    public static MemberCoupon of(Member member, Coupon coupon) {
        return new MemberCoupon(member, coupon, "미사용");
    }

    public String getCouponId() {
        return coupon.getId();
    }

    public boolean hasCoupon(String couponId) {
        return Objects.equals(getCouponId(), couponId);
    }

    public void useCoupon() {
        if ("사용 완료".equals(status)) {
            throw new AlreadyUsedCouponException(String.format(
                    "already used coupon, memberId: %s, couponId: %s",
                    member.getId(), coupon.getId()));
        }
        this.status = "사용 완료";
    }
}
