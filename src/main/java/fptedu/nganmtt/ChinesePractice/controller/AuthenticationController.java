package fptedu.nganmtt.ChinesePractice.controller;

import com.nimbusds.jose.JOSEException;
import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.AuthenticationRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.IntrospectRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserCreationRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.AuthenticationResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.IntrospectResponse;
import fptedu.nganmtt.ChinesePractice.service.AuthenticationService;
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
    ApiResult<AuthenticationResponse> register(@RequestBody UserCreationRequest userCreationRequest){
        var result = authenticationService.register(userCreationRequest);
        return ApiResult.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResult<IntrospectResponse> login(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectRequest);
        return ApiResult.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
