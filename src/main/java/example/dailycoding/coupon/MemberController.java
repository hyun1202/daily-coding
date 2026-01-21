package example.dailycoding.coupon;

import example.dailycoding.coupon.dto.MemberDto;
import example.dailycoding.coupon.dto.MemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDto> create(@RequestBody @Valid MemberRequest memberRequest) {
        MemberDto memberDto = memberService.create(memberRequest);

        return ResponseEntity.ok(memberDto);
    }
}
