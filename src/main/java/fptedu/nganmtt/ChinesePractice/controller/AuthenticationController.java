package fptedu.nganmtt.ChinesePractice.controller;

import com.nimbusds.jose.JOSEException;
import fptedu.nganmtt.ChinesePractice.dto.request.ApiResponse;
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
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    AuthenticationService  authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/register")
    ApiResponse<AuthenticationResponse> register(@RequestBody UserCreationRequest userCreationRequest){
        var result = authenticationService.register(userCreationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
