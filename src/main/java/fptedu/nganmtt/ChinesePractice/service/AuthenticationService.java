package fptedu.nganmtt.ChinesePractice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.SignedJWT;
import fptedu.nganmtt.ChinesePractice.configuration.JwtProperties;
import fptedu.nganmtt.ChinesePractice.dto.request.*;
import fptedu.nganmtt.ChinesePractice.dto.response.AuthenticationResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.IntrospectResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.UserMapper;
import fptedu.nganmtt.ChinesePractice.model.InvalidatedToken;
import fptedu.nganmtt.ChinesePractice.model.User;
import fptedu.nganmtt.ChinesePractice.repository.InvalidatedTokenRepository;
import fptedu.nganmtt.ChinesePractice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    UserMapper userMapper;
    InvalidatedTokenRepository invalidatedTokenRepository;
    JwtProperties jwtProperties;
    JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUserName(authenticationRequest.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

    var accessToken = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return AuthenticationResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .authenticated(true)
        .build();

    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        boolean isValid = true;
        try {
            var signedJWT = jwtService.verifySignedJwt(token);
            var claims = signedJWT.getJWTClaimsSet();
            
            if (!claims.getExpirationTime().after(new Date())) {
                isValid = false;
            }
            
            if (invalidatedTokenRepository.existsById(claims.getJWTID())) {
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse register(UserCreationRequest request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

    var accessToken = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return AuthenticationResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .authenticated(true)
        .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signedJWT = jwtService.verifySignedJwt(request.getToken());
            var claims = signedJWT.getJWTClaimsSet();

            String jwt = claims.getJWTID();
            Date expirationTime = claims.getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwt)
                    .expiryDate(expirationTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already invalidated or invalid");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
    var signedJWT = jwtService.verifySignedJwt(request.getToken());

    var claims = signedJWT.getJWTClaimsSet();

    var type = claims.getStringClaim("type");
    if (!"refresh".equals(type)) throw new AppException(ErrorCode.UNAUTHENTICATED);

    var jwt = claims.getJWTID();
    var expirationTime = claims.getExpirationTime();

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

    return AuthenticationResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .authenticated(true)
        .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        SignedJWT signedJWT = jwtService.verifySignedJwt(token);

        var claims = signedJWT.getJWTClaimsSet();

        if (invalidatedTokenRepository.existsById(claims.getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var now = new Date();
        if (!claims.getExpirationTime().after(now)) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (isRefresh) {
            var type = claims.getStringClaim("type");
            if (!"refresh".equals(type)) throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
