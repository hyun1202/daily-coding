package example.dailycoding.coupon.repository.impl;

import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryMemberRepository implements MemberRepository {
    private final Map<String, Member> memberMap = new ConcurrentHashMap<>();

    @Override
    public Member save(Member member) {
        memberMap.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(String memberId) {
        Member member = memberMap.get(memberId);
        return Optional.ofNullable(member);
    }
}
