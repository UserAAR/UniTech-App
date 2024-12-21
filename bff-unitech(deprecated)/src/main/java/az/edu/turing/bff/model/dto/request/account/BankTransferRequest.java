package az.edu.turing.bff.model.dto.request.account;

import az.edu.turing.bff.model.annotations.ValidIban;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BankTransferRequest(@ValidIban String toIban,
                                  @NotNull @Positive BigDecimal amount) {
}
