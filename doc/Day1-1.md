## 📋 요구사항 설명

관리자가 새로운 쿠폰 정보를 시스템에 등록합니다.

---

## ✅ 완료 조건

- [x]  쿠폰 이름, 할인 타입(비율/금액), 할인 값, 유효 기간 시작일, 유효 기간 종료일을 입력받아 쿠폰을 생성한다.
- [x]  생성된 쿠폰은 고유한 ID를 가진다.
- [x]  생성 성공 시, 생성된 쿠폰의 기본 정보를 반환한다.

---

## 💡 구현 힌트

- 데이터베이스 모델 정의 (Coupon ID, Name, Discount Type, Value, Start Date, End Date 등).
- HTTP POST 요청 처리 및 JSON 바디 파싱.
- 간단한 입력값 유효성 검사 (예: 필수 필드 누락 여부).

---

<aside>
🎯 비즈니스 로직 난이도: 1

</aside>

---

## 🚀 향후 확장 가능 기능

- 쿠폰 코드 자동 생성 (UUID 또는 랜덤 문자열).
- 쿠폰 발급 수량 제한 기능 추가.
- 쿠폰 사용 조건 (최소 주문 금액, 특정 상품 등) 추가.

---

# 회고

## **일일 코딩 연습**

- 완료 여부: N
- 이유: 요구사항을 넘어가는 기능 구현으로 인한 시간 소모

## **회고**

요구사항을 넘어서 저장 로직을 구현하려고 하여 요구하는 기능을 전부 구현하지 못했다.

저장 로직을 구현하니 테스트 코드도 작성을 해야했으며, 테스트 케이스를 생각하는 동안 끝났다..

먼저 요구사항 부터 완료한 후 시간이 남으면 그 때 추가 기능을 작성해야겠다..

## 코드 리뷰

### 1. `Map.put(key, value)` 메소드는 해당 키에 대한 이전 값을 반환

- 작성 코드

    ```java
    public Coupon addCoupon(String uuid, Coupon coupon) {
            return couponMap.put(uuid, coupon);
        }
    ```

- 수정 코드

    ```java
    public Coupon addCoupon(String uuid, Coupon coupon) {
        couponMap.put(uuid, coupon);
        return coupon;  // 추가된 쿠폰 반환
    }
    ```


### 2. 부동 소수점 오류 위험 BigDecimal 타입 사용

- 현재 `double` 타입에 `Assert.notNull`을 사용하고 있는데, 원시 타입은 `null`이 될 수 없어 해당 검증이 무의미
- 작성 코드

    ```java
    private final String id;
        private final String name;
        private final DiscountType discountType;
        private final double discount;
    ```

- 수정 코드

    ```java
    private final String id;
        private final String name;
        private final DiscountType discountType;
        private final BigDecimal discount;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
    
    ```


### 3. 비즈니스 규칙에 대한 유효성 검사를 추가

- 쿠폰의 유효 기간 종료일(`endDate`)이 시작일(`startDate`)보다 이전일 수 없다는 규칙을 추가
- 예시

    ```java
    if (startDate != null && endDate.isBefore(startDate)) {
        throw new IllegalArgumentException("endDate must be after startDate");
    }
    ```

- 수정

    ```java
    Assert.isTrue(endDate.isAfter(startDate), "endDate must be after startDate");
    ```


- 부록: 날짜 비교 메서드:

    ```java
    endDate.isAfter(startDate)      // endDate > startDate
    endDate.isBefore(startDate)     // endDate < startDate
    endDate.isEqual(startDate)      // endDate == startDate
    !endDate.isBefore(startDate)    // endDate >= startDate
    ```


### 4. 메서드 파라미터 단순화

- 이전

    ```java
    public interface CouponRepository {
        Optional<Coupon> getCoupon(String uuid);
        Coupon addCoupon(String uuid, Coupon coupon);
    }
    ```

- 수정

    ```java
    public interface CouponRepository {
        Optional<Coupon> getCoupon(String uuid);
        Coupon addCoupon(Coupon coupon);
    }
    ```