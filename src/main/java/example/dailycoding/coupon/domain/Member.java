package example.dailycoding.coupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class Member {
    private String id;
    private String name;
    private LocalDateTime createdAt;

    @Builder(toBuilder = true)
    public Member(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
}
