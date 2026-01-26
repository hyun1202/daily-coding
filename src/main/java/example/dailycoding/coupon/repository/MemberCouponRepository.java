package example.dailycoding.coupon.repository;

import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.domain.MemberCoupons;

import java.util.List;

public interface MemberCouponRepository {
    MemberCoupon save(MemberCoupon memberCoupon);

    void delete(String memberId, List<MemberCoupon> memberCoupons);

    MemberCoupons findById(String memberId);
}
