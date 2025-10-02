package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResult;
import fptedu.nganmtt.ChinesePractice.dto.request.UserCreationRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @Operation(summary = "Create a new user", description = "Create a new user with the provided information", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping
    ApiResult<UserResponse> createUser(@RequestBody @Valid UserCreationRequest newUser) {
        return ApiResult.<UserResponse>builder()
                .result(userService.createUser(newUser))
                .build();
    }

    @GetMapping
    ApiResult<List<UserResponse>> getAllUsers() {
        return ApiResult.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResult<UserResponse> getUser(@PathVariable UUID userId) {
        return ApiResult.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResult<UserResponse> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest user) {
        return ApiResult.<UserResponse>builder()
                .result(userService.updateUser(userId, user))
                .build();
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ApiResult.<String>builder().result("User deleted").build().toString();
    }

    @GetMapping("/my-info")
    ApiResult<UserResponse> getMyInfo() {
        return ApiResult.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
