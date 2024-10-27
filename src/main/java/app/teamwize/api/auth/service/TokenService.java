package app.teamwize.api.auth.service;


import app.teamwize.api.auth.model.ActivationToken;
import app.teamwize.api.auth.model.JwtTokenType;
import app.teamwize.api.auth.model.ResetPasswordToken;
import app.teamwize.api.user.domain.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;



@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${app.auth.jwt.secret}")
    private String secret;
    @Value("${app.auth.jwt.access-token.expiration-in-ms}")
    private long accessTokenExpirationInMs;
    @Value("${app.auth.jwt.access-token.name}")
    private String accessTokenCookieName;
    @Value("${app.auth.jwt.refresh-token.expiration-in-ms}")
    private long refreshTokenExpirationInMs;
    @Value("${app.auth.jwt.refresh-token.name}")
    private String refreshTokenCookieName;
    @Value("${app.auth.jwt.invitation-token.secret}")
    private String activationTokenSecret;
    @Value("${app.auth.jwt.invitation-token.expiration-in-ms}")
    private long activationTokenExpirationInMs;
    @Value("${app.auth.jwt.reset-password-token.secret}")
    private String resetPasswordTokenSecret;
    @Value("${app.auth.jwt.reset-password-token.expiration-in-ms}")
    private long resetPasswordTokenExpirationInMs;

    private final ObjectMapper objectMapper;

    private Algorithm key;
    private Algorithm activationTokenSecretKey;
    private Algorithm resetPasswordTokenSecretKey;


    @PostConstruct
    public void init() {
        this.key = Algorithm.HMAC256(secret);
        this.activationTokenSecretKey = Algorithm.HMAC256(activationTokenSecret);
        this.resetPasswordTokenSecretKey = Algorithm.HMAC256(resetPasswordTokenSecret);

    }

    public String generateToken(String subject, Algorithm key, LocalDateTime expiresAt, Map<String, Object> claims) {
        var now = new Date();
        var expiresAtInstant = expiresAt.toInstant(ZoneOffset.UTC);
        return JWT.create()
                .withPayload(claims)
                .withSubject(subject)
                .withIssuedAt(now)
                .withIssuer("Teamwize-Auth")
                .withExpiresAt(expiresAtInstant)
                .sign(key);
    }

    public String getSubjectFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public JwtTokenType getTypeFromToken(String token) {
        return JwtTokenType.valueOf(JWT.decode(token).getClaim("type").asString());
    }

    public String getJwtFromRequest(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }

    public boolean validateToken(String token) {
        try {
            JWT.decode(token);
            return true;
        } catch (JWTDecodeException e) {
            log.info("input jwt is not valid errorMessage:[{}]", e.getMessage());
        }
        return false;
    }

    public String generateAccessToken(String subject, Long organizationId, Long userId, UserRole role, LocalDateTime expiresAt) {
        Map<String, Object> claims = Map.of(
                "type", JwtTokenType.ACCESS_TOKEN.toString(),
                "organizationId", organizationId,
                "userId", userId,
                "role", role.toString()
        );
        return generateToken(subject, key, expiresAt, claims);
    }

    public String generateRefreshToken(String subject, LocalDateTime expiresAt) {
        return generateToken(subject, key, expiresAt, Map.of("type", JwtTokenType.REFRESH_TOKEN.toString()));
    }

    public String generateApiKey(Long organizationId, Long userId, LocalDateTime expiresAt) {
        return generateToken(userId.toString(), key, expiresAt, Map.of("type", JwtTokenType.API_KEY_TOKEN.toString(), "organizationId", organizationId));
    }


    public String generateActivationToken(ActivationToken tokenObject) {
        Map<String, Object> tokenClaims = objectMapper.convertValue(tokenObject, new TypeReference<>() {
        });
        return generateToken(String.valueOf(tokenObject.getUserId()), activationTokenSecretKey,
                LocalDateTime.now().plusSeconds(activationTokenExpirationInMs / 1000), tokenClaims
        );
    }

    public ActivationToken extractActivationTokenObject(String token) {
        var jwt = JWT.decode(token);
        return objectMapper.convertValue(jwt, ActivationToken.class);
    }


    public String generateResetPasswordToken(ResetPasswordToken tokenObject) {
        Map<String, Object> tokenClaims = objectMapper.convertValue(tokenObject, new TypeReference<>() {
        });
        return generateToken(String.valueOf(tokenObject.getUserId()), resetPasswordTokenSecretKey,
                LocalDateTime.now().plusSeconds(resetPasswordTokenExpirationInMs / 1000), tokenClaims);
    }

    public ResetPasswordToken extractResetPasswordTokenObject(String token) {
        var jwt = JWT.decode(token);
        return objectMapper.convertValue(jwt, ResetPasswordToken.class);
    }

}





