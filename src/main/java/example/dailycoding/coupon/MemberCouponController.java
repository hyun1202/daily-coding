package example.dailycoding.coupon;

import example.dailycoding.coupon.dto.MemberCouponDto;
import example.dailycoding.coupon.dto.MemberCouponRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/coupons")
public class MemberCouponController {
    private final MemberCouponService memberCouponService;

    @PostMapping
    public ResponseEntity<MemberCouponDto> addCoupon(@RequestBody @Valid MemberCouponRequest request) {
        MemberCouponDto memberCouponDto = memberCouponService.addCoupon(request);

        return ResponseEntity.ok(memberCouponDto);
    }
}
