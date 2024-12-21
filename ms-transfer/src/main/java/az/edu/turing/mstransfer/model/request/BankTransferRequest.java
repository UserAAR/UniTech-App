package az.edu.turing.mstransfer.model.request;

import az.edu.turing.mstransfer.model.annotations.ValidIban;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record BankTransferRequest(
        @ValidIban
        String toIban,

        @Positive(message = "Transfer amount must be greater than 0")
        BigDecimal amount) {
}
