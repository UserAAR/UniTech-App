package az.edu.turing.msmessage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "ms-message")
public class MessageProperties {
    private EmailProperties email;
    private OtpProperties otp;
    private Map<String, MessageTemplate> messages;

    @Data
    public static class EmailProperties {
        private String sender;
    }

    @Data
    public static class OtpProperties {
        private int expiration;
    }

    @Data
    public static class MessageTemplate {
        private String subject;
        private String body;
    }
}
