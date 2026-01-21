package example.dailycoding.coupon.dto;

import example.dailycoding.coupon.domain.Member;

import java.time.LocalDateTime;

public record MemberDto(
        String id,
        String name,
        LocalDateTime createdAt
) {
    public static MemberDto of(Member savedMember) {
        return new MemberDto(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getCreatedAt()
        );
    }
}
