package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.Coupon;
import example.dailycoding.coupon.domain.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CouponDto {
    private String id;
    private String name;
    private DiscountType discountType;
    private BigDecimal discount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static CouponDto of(Coupon coupon) {
        return new CouponDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountType(),
                coupon.getDiscount(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }
}
