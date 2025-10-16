package fptedu.nganmtt.ChinesePractice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import fptedu.nganmtt.ChinesePractice.configuration.JwtProperties;
import fptedu.nganmtt.ChinesePractice.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtProperties jwtProperties;

    @Value("${jwt.signer.key}")
    protected String SIGNER_KEY;

    public String generateAccessToken(User user) {
        return generateTokenWithScope(user.getUserName(), "access", jwtProperties.getValidDuration(), buildScope(user));
    }

    public String generateRefreshToken(User user) {
        return generateToken(user.getUserName(), "refresh", jwtProperties.getRefreshableDuration());
    }

    private String generateToken(String subject, String type, long durationSeconds) {
        return generateTokenWithScope(subject, type, durationSeconds, null);
    }

    private String generateTokenWithScope(String subject, String type, long durationSeconds, String scope) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer("nganne")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(durationSeconds, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("type", type);

        if (scope != null && !scope.isEmpty()) {
            claimsBuilder.claim("scope", scope);
        }

        JWTClaimsSet jwtClaimsSet = claimsBuilder.build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Failed to sign JWT", e);
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verifySignedJwt(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        boolean verified = signedJWT.verify(verifier);
        if (!verified) throw new JOSEException("JWT signature verification failed");

        return signedJWT;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions()
                            .forEach(permission ->
                                    stringJoiner.add(permission.getName()));
            });
        }
        return stringJoiner.toString();
    }
}
