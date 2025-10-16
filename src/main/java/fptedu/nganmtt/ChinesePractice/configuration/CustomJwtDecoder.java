package fptedu.nganmtt.ChinesePractice.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import fptedu.nganmtt.ChinesePractice.repository.InvalidatedTokenRepository;
import fptedu.nganmtt.ChinesePractice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signer.key}")
    private String signerKey;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    private NimbusJwtDecoder jwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            SignedJWT signedJWT = jwtService.verifySignedJwt(token);
            var claims = signedJWT.getJWTClaimsSet();
            
            var type = claims.getStringClaim("type");
            if (!"access".equals(type)) throw new JwtException("Invalid token type");
            
            // Check if token is not expired
            if (!claims.getExpirationTime().after(new Date())) {
                throw new JwtException("Token expired");
            }
            
            // Check if token has been invalidated (logout)
            if (invalidatedTokenRepository.existsById(claims.getJWTID())) {
                throw new JwtException("Token has been invalidated");
            }
        } catch (JOSEException | ParseException e ) {
            throw new JwtException("Invalid JWT token: " + e.getMessage(), e);
        }

        if (Objects.isNull(jwtDecoder)) {
            SecretKeySpec secretKey = new SecretKeySpec(signerKey.getBytes(), "HmacSHA256");
            jwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKey)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return jwtDecoder.decode(token);
    }
}
