package fptedu.nganmtt.ChinesePractice.service;

import com.nimbusds.jose.*;
import fptedu.nganmtt.ChinesePractice.dto.request.*;
import fptedu.nganmtt.ChinesePractice.dto.response.AuthenticationResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.IntrospectResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.RefreshTokenResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.UserMapper;
import fptedu.nganmtt.ChinesePractice.model.InvalidatedToken;
import fptedu.nganmtt.ChinesePractice.model.User;
import fptedu.nganmtt.ChinesePractice.repository.InvalidatedTokenRepository;
import fptedu.nganmtt.ChinesePractice.repository.RoleRepository;
import fptedu.nganmtt.ChinesePractice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    UserMapper userMapper;
    InvalidatedTokenRepository invalidatedTokenRepository;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            var user = userRepository.findByUserName(authenticationRequest.getUserName())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

            if (!authenticated) {
                throw new AppException(ErrorCode.UNAUTHORIZED_EXCEPTION);
            }

            var accessToken = jwtService.generateAccessToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(userMapper.toUserResponse(user))
                    .build();
        } catch (Exception e) {
            log.info("In method authenticate", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        var token = introspectRequest.getAccessToken();

        try {
            var signedJWT = jwtService.verifySignedJwt(token);
            var claims = signedJWT.getJWTClaimsSet();

            if (!claims.getExpirationTime().after(new Date())) {
                return IntrospectResponse.builder().valid(false).build();
            }

            if (invalidatedTokenRepository.existsById(claims.getJWTID())) {
                return IntrospectResponse.builder().valid(false).build();
            }

            return IntrospectResponse.builder().valid(true).build();

        } catch (Exception e) {
            return IntrospectResponse.builder().valid(false).build();
        }
    }

    public UserResponse register(UserCreationRequest request) {
        try {
            if (userRepository.findByUserName(request.getUserName()).isPresent()) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }

            User user = userMapper.toUser(request);

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Set.of(
                    roleRepository.findById("USER")
                            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND))
            ));
            userRepository.save(user);

            return userMapper.toUserResponse(user);
        } catch (Exception e) {
            log.info("In method register", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            if (request.getAccessToken() != null && !request.getAccessToken().isEmpty()) {
                invalidateToken(request.getAccessToken());
            }

            if (request.getRefreshToken() != null && !request.getRefreshToken().isEmpty()) {
                invalidateToken(request.getRefreshToken());
            }

            log.info("Logout request received");
        } catch (AppException e) {
            log.info("Token already invalidated or invalid");
            throw e;
        }
    }

    private void invalidateToken(String token) throws ParseException, JOSEException {
        var signedJWT = jwtService.verifySignedJwt(token);
        var claims = signedJWT.getJWTClaimsSet();

        String jwt = claims.getJWTID();
        Date expirationTime = claims.getExpirationTime();

        if (invalidatedTokenRepository.existsById(jwt)) {
            log.info("Token {} already invalidated or invalid", jwt);
            return;
        }

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jwt)
                .expiryDate(expirationTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        log.info("Token {} already invalidated", jwt);
    }

    public RefreshTokenResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        try {
            var signedJWT = jwtService.verifySignedJwt(request.getRefreshToken());

            var claims = signedJWT.getJWTClaimsSet();

            var type = claims.getStringClaim("type");
            if (!"refresh".equals(type)) throw new AppException(ErrorCode.UNAUTHENTICATED);

            var jwt = claims.getJWTID();
            var expirationTime = claims.getExpirationTime();

            if (invalidatedTokenRepository.existsById(jwt))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            if (!expirationTime.after(new Date()))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwt)
                    .expiryDate(expirationTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);

            var userName = claims.getSubject();
            var user = userRepository.findByUserName(userName)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            var accessToken = jwtService.generateAccessToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            return RefreshTokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .authenticated(true)
                    .build();
        } catch (Exception e) {
            log.info("In method refreshToken", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
