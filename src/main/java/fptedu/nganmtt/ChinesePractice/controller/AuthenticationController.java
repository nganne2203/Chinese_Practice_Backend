package fptedu.nganmtt.ChinesePractice.controller;

import com.nimbusds.jose.JOSEException;
import fptedu.nganmtt.ChinesePractice.dto.request.*;
import fptedu.nganmtt.ChinesePractice.dto.response.AuthenticationResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.IntrospectResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.RefreshTokenResponse;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "User login", description = "Authenticate user and return access and refresh tokens", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized exception", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/login")
    ApiResult<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResult.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @Operation(summary = "User registration", description = "Register a new user with the provided information", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Username is already exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Email is already exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/register")
    ApiResult<UserResponse> register(@Valid @RequestBody UserCreationRequest userCreationRequest){
        var result = authenticationService.register(userCreationRequest);
        return ApiResult.<UserResponse>builder()
                .result(result)
                .build();
    }

    @Operation(summary = "Introspect token", description = "Check the validity of an access token", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token introspected successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = IntrospectResponse.class))
            })
    })
    @PostMapping("/introspect")
    ApiResult<IntrospectResponse> introspect(@Valid @RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var result = authenticationService.introspect(introspectRequest);
        return ApiResult.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @Operation(summary = "User logout", description = "Invalidate the provided access and refresh tokens", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged out successfully", content = @Content)
    })
    @PostMapping("/logout")
    ApiResult<Void> logout(@Valid @RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ApiResult.<Void>builder()
                .build();

    }

    @Operation(summary = "Refresh token", description = "Generate new access and refresh tokens using a valid refresh token", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens refreshed successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token", content = @Content),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/refresh")
    ApiResult<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshRequest);
        return ApiResult.<RefreshTokenResponse>builder()
                .result(result)
                .build();
    }
}
