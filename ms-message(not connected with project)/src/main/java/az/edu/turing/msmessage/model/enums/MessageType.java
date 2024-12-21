package az.edu.turing.msmessage.model.enums;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
public enum MessageType {
    REGISTER,
    TRANSFER_MONEY
}
