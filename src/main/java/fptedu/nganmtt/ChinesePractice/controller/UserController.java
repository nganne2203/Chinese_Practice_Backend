package fptedu.nganmtt.ChinesePractice.controller;

import fptedu.nganmtt.ChinesePractice.dto.request.ApiResponse;
import fptedu.nganmtt.ChinesePractice.dto.request.UserCreationRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UserUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UserResponse;
import fptedu.nganmtt.ChinesePractice.model.User;
import fptedu.nganmtt.ChinesePractice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest newUser) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(newUser));
        return response;
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return "success";
    }

}
