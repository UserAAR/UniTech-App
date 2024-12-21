package az.edu.turing.bff.controller;

import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.request.user.UpdateUserRequest;
import az.edu.turing.bff.model.dto.response.user.RetrieveUserResponse;
import az.edu.turing.bff.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<RestResponse<RetrieveUserResponse>> getUserById(
            @RequestHeader("Authorization") String authHeader) {

        RestResponse<RetrieveUserResponse> restResponse = userService.getUserById(authHeader);

        return ResponseEntity.ok(restResponse);
    }

    @PutMapping
    public ResponseEntity<RestResponse<RetrieveUserResponse>> updatePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateUserRequest request) {

        RestResponse<RetrieveUserResponse> restResponse = userService.updatePassword(token, request);

        return ResponseEntity.ok(restResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }
}
