package example.dailycoding.coupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@ToString
@Getter
public class Coupon {
    private final String id;
    private final String name;
    private final DiscountType discountType;
    private final double discount;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @Builder
    public Coupon(String uuid, String name, DiscountType discountType, double discount, LocalDateTime startDate, LocalDateTime endDate) {
        Assert.notNull(uuid, "uuid is required");
        Assert.notNull(discount, "discount is required");
        Assert.notNull(endDate, "endDate is is required");

        this.id = uuid;
        this.name = name;
        this.discountType = discountType == null? DiscountType.AMOUNT : discountType;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
