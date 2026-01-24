package example.dailycoding.coupon.fixture;

import example.dailycoding.coupon.domain.Member;

import java.time.LocalDateTime;

public class MemberFixture {

    public static Member get() {
        return Member.builder()
                .id("member1")
                .name("member1_name")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
