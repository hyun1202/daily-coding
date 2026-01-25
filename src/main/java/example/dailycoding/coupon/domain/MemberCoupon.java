package example.dailycoding.coupon.domain;

import example.dailycoding.coupon.exception.DuplicateCouponException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@ToString
@Getter
public class MemberCoupon {
    private final Member member;
    private List<Coupon> coupons;

    @Builder
    public MemberCoupon(Member member, List<Coupon> coupons) {
        this.member = Objects.requireNonNull(member, "member cannot be null");
        this.coupons = coupons != null ? new ArrayList<>(coupons) : new ArrayList<>();
    }

    public void addCoupon(Coupon newCoupon) {
        Objects.requireNonNull(newCoupon, "coupon cannot be null");

        if (isDuplicated(newCoupon)) {
            throw new DuplicateCouponException("duplicate coupon, id: " + newCoupon.getId());
        }

        coupons.add(newCoupon);
    }

    public void deleteCoupon(Coupon coupon) {
        Objects.requireNonNull(coupon, "coupon cannot be null");

        if (!coupons.removeIf(c -> c.getId().equals(coupon.getId()))) {
            throw new NoSuchElementException("this coupon not found in member's, id: " + coupon.getId());
        }
    }

    private boolean isDuplicated(Coupon newCoupon) {
        return coupons.stream()
                .anyMatch(coupon -> Objects.equals(coupon.getId(), newCoupon.getId()));
    }
}
