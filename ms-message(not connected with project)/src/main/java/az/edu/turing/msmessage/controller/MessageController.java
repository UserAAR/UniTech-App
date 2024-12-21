package az.edu.turing.msmessage.controller;

import az.edu.turing.msmessage.model.dto.request.MessageRequest;
import az.edu.turing.msmessage.model.dto.request.OtpRequest;
import az.edu.turing.msmessage.model.dto.response.OtpResponse;
import az.edu.turing.msmessage.service.MessageService;
import az.edu.turing.msmessage.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    private final MessageService messageService;
    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.sendMessage(messageRequest);
        return ResponseEntity.ok("Message sent");
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestBody MessageRequest messageRequest) {
        messageService.sendOTP(messageRequest);
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponse> verifyOTP(@RequestBody OtpRequest otpRequest) {
        boolean isValid = messageService.validateOTP(otpRequest.getEmail(), otpRequest.getOtp());
        OtpResponse response = new OtpResponse();
        response.setValid(isValid);
        return ResponseEntity.ok(response);
    }
}
