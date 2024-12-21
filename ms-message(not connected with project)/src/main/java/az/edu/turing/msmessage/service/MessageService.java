package az.edu.turing.msmessage.service;

import az.edu.turing.msmessage.config.MessageProperties;
import az.edu.turing.msmessage.model.dto.request.MessageRequest;
import az.edu.turing.msmessage.model.enums.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MailService mailService;
    private final OtpService otpService;
    private final MessageProperties messageProperties;

    public void sendMessage(MessageRequest messageRequest) {
        MessageProperties.MessageTemplate template = getMessageTemplateByType(messageRequest.getMessageType());
        mailService.sendEmail(
                messageRequest.getEmail(),
                template.getSubject(),
                template.getBody(),
                messageProperties.getEmail().getSender()
        );
    }

    public void sendOTP(MessageRequest messageRequest) {
        String otp = otpService.generateOTP(messageRequest.getEmail(), messageProperties.getOtp().getExpiration());
        MessageProperties.MessageTemplate template = getMessageTemplateByType(messageRequest.getMessageType());
        String bodyWithOtp = template.getBody().replace("{otp}", otp);

        mailService.sendEmail(
                messageRequest.getEmail(),
                template.getSubject(),
                bodyWithOtp,
                messageProperties.getEmail().getSender()
        );
    }

    public boolean validateOTP(String email, String otp) {
        return otpService.validateOTP(email, otp);
    }

    private MessageProperties.MessageTemplate getMessageTemplateByType(MessageType messageType) {
        String typeKey = messageType.name().toLowerCase();
        MessageProperties.MessageTemplate template = messageProperties.getMessages().get(typeKey);
        if (template == null) {
            throw new IllegalArgumentException("Unknown message type: " + messageType);
        }
        return template;
    }
}
