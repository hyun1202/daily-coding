package example.dailycoding.coupon.exception;

public class AlreadyUsedCouponException extends RuntimeException {
    public AlreadyUsedCouponException(String message) {
        super(message);
    }
}
