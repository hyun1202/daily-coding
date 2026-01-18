package example.dailycoding.coupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
public class Coupon {
    private final String id;
    private final String name;
    private final DiscountType discountType;
    private final BigDecimal discount;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @Builder
    public Coupon(String uuid, String name, DiscountType discountType, BigDecimal discount, LocalDateTime startDate, LocalDateTime endDate) {
        Assert.notNull(uuid, "uuid is required");
        Assert.notNull(discount, "discount is required");
        Assert.notNull(endDate, "endDate is is required");

        Assert.isTrue(endDate.isAfter(startDate), "endDate must be after startDate");
        Assert.isTrue(discount.compareTo(BigDecimal.ZERO) >= 0, "discount must be greater than or equal to 0");

        this.id = uuid;
        this.name = name;
        this.discountType = discountType == null? DiscountType.AMOUNT : discountType;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
