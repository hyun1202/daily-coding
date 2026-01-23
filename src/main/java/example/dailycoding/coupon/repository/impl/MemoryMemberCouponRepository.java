package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.MemberCoupon;
import example.dailycoding.coupon.repository.MemberCouponRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryMemberCouponRepository implements MemberCouponRepository {
    private final Map<String, MemberCoupon> memberCouponMap = new ConcurrentHashMap<>();

    @Override
    public MemberCoupon save(MemberCoupon memberCoupon) {
        String memberId = memberCoupon.getMember().getId();
        memberCouponMap.put(memberId, memberCoupon);
        return memberCoupon;
    }

    @Override
    public Optional<MemberCoupon> findById(String memberId) {
        MemberCoupon memberCoupon = memberCouponMap.get(memberId);
        return Optional.ofNullable(memberCoupon);
    }
}
