package example.dailycoding.coupon;

import example.dailycoding.coupon.dto.CouponDto;
import example.dailycoding.coupon.dto.CouponRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponDto> create(@RequestBody @Valid CouponRequest couponRequest) {
        CouponDto coupon = couponService.createCoupon(couponRequest);

        return ResponseEntity.created(java.net.URI.create("/api/v1/coupons/" + coupon.getId())).body(coupon);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDto> getCoupon(@PathVariable("couponId") String couponId) {
        try {
            CouponDto coupon = couponService.getCoupon(couponId);
            return ResponseEntity.ok(coupon);
        } catch (IllegalArgumentException e) {
            log.error("getCoupon Exception: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}
