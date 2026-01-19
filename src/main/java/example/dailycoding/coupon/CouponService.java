package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.dto.CouponDto;
import example.dailycoding.coupon.dto.CouponRequest;
import example.dailycoding.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponDto createCoupon(CouponRequest request) {
        String uuid = UUID.randomUUID().toString();

        Coupon coupon = Coupon.builder()
                .uuid(uuid)
                .name(request.name())
                .discountType(request.discountType())
                .discount(request.discount())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();

        Coupon result = couponRepository.addCoupon(coupon);

        return CouponDto.of(result);
    }

    public CouponDto getCoupon(String couponId) {
        Coupon coupon = couponRepository.getCoupon(couponId)
                .orElseThrow(() -> new IllegalArgumentException("not found coupon, invalid couponId"));

        return CouponDto.of(coupon);
    }
}
