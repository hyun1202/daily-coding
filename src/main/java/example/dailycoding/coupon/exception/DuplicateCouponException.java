package example.dailycoding.coupon.exception;

public class DuplicateCouponException extends RuntimeException {
    public DuplicateCouponException(String message) {
        super(message);
    }
}
