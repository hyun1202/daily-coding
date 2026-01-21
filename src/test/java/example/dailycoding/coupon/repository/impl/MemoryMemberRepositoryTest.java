package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.dto.MemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryMemberRepository();
    }

    @Test
    @DisplayName("멤버를 저장한다.")
    void save() {
        String id = "id1";
        String name = "name1";
        LocalDateTime now = LocalDateTime.now();

        Member member = Member.builder()
                .id(id)
                .name(name)
                .createdAt(now)
                .build();

        // when
        Member result = repository.save(member);

        // then
        assertThat(result)
                .extracting(Member::getId, Member::getName, Member::getCreatedAt)
                .containsExactly(id, name, now);
    }
}