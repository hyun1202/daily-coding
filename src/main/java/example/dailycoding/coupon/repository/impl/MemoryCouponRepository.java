package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.repository.CouponRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class MemoryCouponRepository implements CouponRepository {
    private final Map<String, Coupon> couponMap = new HashMap<>();

    @Override
    public Optional<Coupon> getCoupon(String uuid) {
        return Optional.ofNullable(couponMap.get(uuid));
    }

    @Override
    public Coupon addCoupon(String uuid, Coupon coupon) {
        return couponMap.put(uuid, coupon);
    }
}
