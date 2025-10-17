package fptedu.nganmtt.ChinesePractice.controller;

import com.nimbusds.jose.JOSEException;
import fptedu.nganmtt.ChinesePractice.dto.request.*;
import fptedu.nganmtt.ChinesePractice.dto.response.AuthenticationResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.IntrospectResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.RefreshTokenResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    AuthenticationService  authenticationService;

    @PostMapping("/login")
    ApiResult<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResult.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/register")
    ApiResult<UserResponse> register(@Valid @RequestBody UserCreationRequest userCreationRequest){
        var result = authenticationService.register(userCreationRequest);
        return ApiResult.<UserResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResult<IntrospectResponse> introspect(@Valid @RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectRequest);
        return ApiResult.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResult<Void> logout(@Valid @RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ApiResult.<Void>builder()
                .build();

    }

    @PostMapping("/refresh")
    ApiResult<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshRequest);
        return ApiResult.<RefreshTokenResponse>builder()
                .result(result)
                .build();
    }
}
