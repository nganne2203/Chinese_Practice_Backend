package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.*;
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
            @ApiResponse(responseCode = "400", description = "Username is already exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Email is already exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping
    ApiResult<UserResponse> createUser(@RequestBody @Valid UserCreationRequest newUser) {
        return ApiResult.<UserResponse>builder()
                .result(userService.createUser(newUser))
                .build();
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping
    ApiResult<List<UserResponse>> getAllUsers() {
        return ApiResult.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user information by user ID", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/{userId}")
    ApiResult<UserResponse> getUser(@PathVariable String userId) {
        return ApiResult.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @Operation(summary = "Update user profile", description = "Update the profile information of a user", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PutMapping("/{userId}")
    ApiResult<Void> updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateRequest user) {
        userService.updateUser(userId, user);
        return ApiResult.<Void>builder()
                .message("Profile updated successfully")
                .build();
    }

    @Operation(summary = "Update user roles", description = "Update the roles assigned to a user", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Role not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/role-update/{userId}")
    ApiResult<Void> updateUserRole(@PathVariable String userId, @RequestBody UserUpdateRoleRequest request) {
        userService.updateRoleUser(userId, request);
        return ApiResult.<Void>builder()
                .message("User role updated successfully")
                .build();
    }

    @Operation(summary = "Change user password", description = "Change the password of a user", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Old password is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @PostMapping("/change-password/{userId}")
    ApiResult<Void> changePassword(@PathVariable String userId, @RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return ApiResult.<Void>builder()
                .message("Password changed successfully")
                .build();
    }

    @Operation(summary = "Delete user", description = "Delete a user by their ID", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResult.<String>builder().result("User deleted").build().toString();
    }

    @Operation(summary = "Get my user info", description = "Retrieve the information of the currently authenticated user", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Uncategorized exception", content = @Content)
    })
    @GetMapping("/my-info")
    ApiResult<UserResponse> getMyInfo() {
        return ApiResult.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
