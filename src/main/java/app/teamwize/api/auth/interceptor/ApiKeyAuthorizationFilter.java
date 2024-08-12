//package app.workive.api.auth.interceptor;
//
//
//import app.workive.api.auth.domain.AuthUserDetails;
//import app.workive.api.base.config.SecurityConfig;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ApiKeyAuthorizationFilter extends OncePerRequestFilter {
//    private final ApiKeyService apiKeyService;
//
//    @Value("${app.auth.api-key.header-name}")
//    private String apiKeyHeaderName;
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return SecurityConfig.shouldBeIgnored(request.getServletPath()) || !isApiKeyHeaderExists(request);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            var apiKeyValue = getApiKeyHeader(request);
//            if (!StringUtils.hasText(apiKeyValue)) {
//                return;
//            }
//            var apiKey = apiKeyService.getApiKey(apiKeyValue);
//            var userDetails = AuthUserDetails.build(apiKeyValue, apiKey.getOrganization().getId(), defaultSite.getId(), UserRole.API);
//            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private boolean isApiKeyHeaderExists(HttpServletRequest request) {
//        return StringUtils.hasText(request.getHeader(apiKeyHeaderName));
//    }
//
//    private String getApiKeyHeader(HttpServletRequest request) {
//        return request.getHeader(apiKeyHeaderName);
//    }
//
//}