package az.edu.turing.bff.service;

import az.edu.turing.bff.client.UserClient;
import az.edu.turing.bff.model.dto.request.user.UpdateUserRequest;
import az.edu.turing.bff.model.dto.response.RestResponse;
import az.edu.turing.bff.model.dto.response.user.RetrieveUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    public RestResponse<RetrieveUserResponse> getUserById(String token) {
        return userClient.getUserById(token);
    }

    public RestResponse<RetrieveUserResponse> updatePassword(String token, UpdateUserRequest request) {
        return userClient.updateUserPassword(token, request);
    }

    public void deleteUser(String token) {
        userClient.deleteUser(token);
    }
}
