package example.dailycoding.coupon;

import example.dailycoding.coupon.dto.LoginMember;
import example.dailycoding.coupon.dto.MemberCouponDto;
import example.dailycoding.coupon.dto.MemberCouponRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<MemberCouponDto>> getCoupons(@ModelAttribute LoginMember loginMember) {
        List<MemberCouponDto> memberCoupons = memberCouponService.getMemberCoupons(loginMember);

        return ResponseEntity.ok(memberCoupons);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@ModelAttribute LoginMember loginMember,
                                             @PathVariable("couponId") String couponId) {
        memberCouponService.deleteCoupon(loginMember, couponId);

        return ResponseEntity.noContent().build();
    }
}
