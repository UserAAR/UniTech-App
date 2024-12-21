package az.edu.turing.mstransfer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth", url = "${feign.client.config.ms-auth-client.url}")
public interface MsAuthClient {

    @PostMapping("/extract-token")
    String extractToken(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/user-id")
    Long getUserId(@RequestParam String accessToken);

    @PostMapping("/validate-token")
    void validateAccessToken(@RequestParam String accessToken);
}
