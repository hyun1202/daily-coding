package example.dailycoding.coupon;

import example.dailycoding.coupon.dto.MemberDto;
import example.dailycoding.coupon.dto.MemberRequest;
import example.dailycoding.coupon.repository.MemberRepository;
import example.dailycoding.coupon.repository.impl.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberRepository repository;
    MemberService service;

    @BeforeEach
    void setUp() {
        repository = new MemoryMemberRepository();
        service = new MemberService(repository);
    }

    @Test
    @DisplayName("회원을 생성한다")
    void create() {
        // given
        String name = "test1";
        MemberRequest request = new MemberRequest(name);

        // when
        MemberDto result = service.create(request);

        // then
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.createdAt()).isNotNull();

    }
}