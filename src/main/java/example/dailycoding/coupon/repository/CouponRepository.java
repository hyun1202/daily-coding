package example.dailycoding.coupon.repository;

import example.dailycoding.coupon.domain.Coupon;

import java.util.Optional;

public interface CouponRepository {
    Optional<Coupon> getCoupon(String uuid);
    Coupon addCoupon(String uuid, Coupon coupon);
}
