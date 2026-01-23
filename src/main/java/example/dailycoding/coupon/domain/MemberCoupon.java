package example.dailycoding.coupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
public class MemberCoupon {
    private final Member member;
    private List<Coupon> coupons;

    @Builder
    public MemberCoupon(Member member, List<Coupon> coupons) {
        this.member = member;
        this.coupons = coupons;
    }

    public boolean addCoupon(Coupon newCoupon) {
        if (coupons == null) {
            coupons = new ArrayList<>();
        }

        if (isDuplicated(newCoupon)) {
            return false;
        }

        coupons.add(newCoupon);

        return true;
    }

    private boolean isDuplicated(Coupon newCoupon) {
        return coupons.stream()
                .anyMatch(coupon -> Objects.equals(coupon.getId(), newCoupon.getId()));
    }
}
