package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.DiscountType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CouponRequest(
        @NotNull
        String name,
        DiscountType discountType,
        @NotNull
        double discount,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate
) {
}
