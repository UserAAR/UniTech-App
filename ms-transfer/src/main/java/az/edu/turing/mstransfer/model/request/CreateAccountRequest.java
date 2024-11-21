package az.edu.turing.mstransfer.model.request;

import az.edu.turing.mstransfer.model.enums.Bank;
import az.edu.turing.mstransfer.model.enums.Currency;
import az.edu.turing.mstransfer.model.annotations.ValidBankAccountPassword;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(
        @NotNull
        Bank bank,

        @ValidBankAccountPassword
        String password,

        @NotNull
        Currency currency) {
}
