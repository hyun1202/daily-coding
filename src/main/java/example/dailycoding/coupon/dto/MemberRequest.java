package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.Member;
import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank
        String name
) {
    public Member toMember() {
        return Member.builder()
                .name(name)
                .build();
    }
}
