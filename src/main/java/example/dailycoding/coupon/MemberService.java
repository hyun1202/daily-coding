package example.dailycoding.coupon;

import example.dailycoding.coupon.domain.Member;
import example.dailycoding.coupon.dto.MemberDto;
import example.dailycoding.coupon.dto.MemberRequest;
import example.dailycoding.coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto create(MemberRequest request) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        Member member = request.toMember();
        member = member.toBuilder()
                .id(id)
                .createdAt(now).build();

        Member savedMember = memberRepository.save(member);

        return MemberDto.of(savedMember);
    }
}
