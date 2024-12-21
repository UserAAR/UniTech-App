package az.edu.turing.msmessage.model.dto.request;

import az.edu.turing.msmessage.model.enums.MessageType;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @Email
    private String email;

    private MessageType messageType;

}
