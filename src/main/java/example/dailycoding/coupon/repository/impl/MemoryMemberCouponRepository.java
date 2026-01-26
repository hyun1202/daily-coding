package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.domain.MemberCoupons;
import example.dailycoding.coupon.repository.MemberCouponRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryMemberCouponRepository implements MemberCouponRepository {
    private final Map<String, MemberCoupon> memberCouponMap1 = new ConcurrentHashMap<>();
    private final Map<String, List<MemberCoupon>> memberCouponMap = new ConcurrentHashMap<>();

    @Override
    public MemberCoupon save(MemberCoupon memberCoupon) {
        memberCouponMap.computeIfAbsent(memberCoupon.getMember().getId(), k -> new ArrayList<>())
                        .add(memberCoupon);
        return memberCoupon;
    }

    @Override
    public void delete(String memberId, List<MemberCoupon> memberCoupons) {
        memberCouponMap.put(memberId, memberCoupons);
    }

    @Override
    public MemberCoupons findById(String memberId) {
        List<MemberCoupon> memberCoupons = memberCouponMap.get(memberId);

        return new MemberCoupons(memberCoupons);
    }
}
