package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.repository.CouponRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryCouponRepository implements CouponRepository {
    private final Map<String, Coupon> couponMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Coupon> getCoupon(String uuid) {
        return Optional.ofNullable(couponMap.get(uuid));
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        couponMap.put(coupon.getId(), coupon);
        return coupon;
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponMap.values()
                .stream()
                .toList();
    }
}
