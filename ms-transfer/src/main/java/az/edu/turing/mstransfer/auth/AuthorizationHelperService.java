package az.edu.turing.mstransfer.auth;


import az.edu.turing.mstransfer.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import static az.edu.turing.mstransfer.model.enums.Error.ERR_09;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationHelperService {

    private final JwtService jwtService;

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format!");
        }
        log.info(authorizationHeader);
        return authorizationHeader.substring(7);
    }

    public Long getUserId(String accessToken) {
        final String userId = jwtService.extractUserId(accessToken);
        return Long.parseLong(userId);
    }

    public void validateAccessToken(String accessToken) {
        if (jwtService.isTokenExpired(accessToken)) {
            throw new BadRequestException(ERR_09.getErrorCode(), ERR_09.getErrorDescription());
        }
    }
}