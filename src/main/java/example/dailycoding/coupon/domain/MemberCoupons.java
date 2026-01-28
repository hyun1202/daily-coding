package example.dailycoding.coupon.domain;

import example.dailycoding.coupon.exception.DuplicateCouponException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MemberCoupons {
    private final List<MemberCoupon> memberCoupons;

    @Builder
    public MemberCoupons(List<MemberCoupon> memberCoupons) {
        this.memberCoupons = memberCoupons != null ? memberCoupons : new ArrayList<>();
    }

    public List<MemberCoupon> deleteCoupon(Coupon deletedCoupon) {
        ArrayList<MemberCoupon> coupons = new ArrayList<>(memberCoupons);

        Objects.requireNonNull(deletedCoupon, "coupon cannot be null");

        if (!coupons.removeIf(c -> c.hasCoupon(deletedCoupon.getId()))) {
            throw new NoSuchElementException("this coupon not found in member's, id: " + deletedCoupon.getId());
        }

        return coupons;
    }

    public List<MemberCoupon> getMemberCoupons() {
        return new ArrayList<>(memberCoupons);
    }

    public boolean isDuplicated(Coupon newCoupon) {
        return memberCoupons.stream()
                .anyMatch(c -> c.hasCoupon(newCoupon.getId()));
    }

}
