package example.dailycoding.coupon;

import example.dailycoding.coupon.dto.CouponDto;
import example.dailycoding.coupon.dto.CouponRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponDto> create(@RequestBody @Valid CouponRequest couponRequest) {
        CouponDto coupon = couponService.createCoupon(couponRequest);

        return ResponseEntity.ok(coupon);
    }
}
