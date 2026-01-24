package example.dailycoding.coupon.repository;

import example.dailycoding.coupon.domain.MemberCoupon;

import java.util.List;
import java.util.Optional;

public interface MemberCouponRepository {
    MemberCoupon save(MemberCoupon memberCoupon);
    Optional<MemberCoupon> findById(String memberId);
}
