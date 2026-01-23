package example.dailycoding.coupon.repository;

import example.dailycoding.coupon.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(String memberId);
}
