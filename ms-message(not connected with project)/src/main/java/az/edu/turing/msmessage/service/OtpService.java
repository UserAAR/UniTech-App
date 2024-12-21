package az.edu.turing.msmessage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final StringRedisTemplate redisTemplate;

    public String generateOTP(String email, int expirationMinutes) {
        String otp = String.valueOf((int) (Math.random() * 1000000));
        redisTemplate.opsForValue().set(email, otp, expirationMinutes, TimeUnit.MINUTES);
        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }
}