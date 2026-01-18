package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponRequest(
        @NotBlank
        String name,
        DiscountType discountType,
        @NotNull(message = "discount is required")
        @PositiveOrZero(message = "discount must be greater than or equal to 0")
        BigDecimal discount,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate
) {
}
