package roomescape.config;

import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import roomescape.domain.Member;
import roomescape.service.MemberService;
import roomescape.util.CookieUtil;
import roomescape.util.JwtProvider;

@Component
public class CheckRoleInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    CheckRoleInterceptor(JwtProvider jwtProvider, MemberService memberService) {
        this.jwtProvider = jwtProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new TokenValidationFailureException("토큰이 존재하지 않습니다.");
        }
        Optional<String> token = CookieUtil.extractToken(cookies);

        token.ifPresentOrElse(
                this::validateAdmin,
                () -> {
                    throw new TokenValidationFailureException("토큰이 존재하지 않습니다.");
                }
        );

        return true;
    }

    private void validateAdmin(String token) {
        String subject = jwtProvider.getSubject(token);
        long memberId = Long.parseLong(subject);
        Member member = memberService.findValidatedSiteUserById(memberId);
        if (!member.getRole().isAdmin()) {
            throw new ForbiddenAccessException();
        }
    }
}
