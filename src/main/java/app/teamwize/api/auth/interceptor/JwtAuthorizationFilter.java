package app.teamwize.api.auth.interceptor;


import app.teamwize.api.auth.service.AuthenticationService;
import app.teamwize.api.base.config.SecurityConfig;
import app.teamwize.api.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AuthenticationService detailsService;
    private final TokenService tokenService;

    private String jwtHeaderName = "Authorization";

    private String apiKeyHeaderName = "API-Key";


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return SecurityConfig.shouldBeIgnored(request.getServletPath()) || apiKeyHeaderExist(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var jwt = getJwtToken(request);
            if (!StringUtils.hasText(jwt) || !tokenService.validateToken(jwt)) {
                return;
            }
            var subject = tokenService.getSubjectFromToken(jwt);
            var userDetails = detailsService.loadUserByUsername(subject);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private boolean apiKeyHeaderExist(HttpServletRequest request) {
        return StringUtils.hasText(request.getHeader(apiKeyHeaderName));
    }

    private String getJwtToken(HttpServletRequest request) {
        return tokenService.getJwtFromRequest(request.getHeader(jwtHeaderName));
    }

}