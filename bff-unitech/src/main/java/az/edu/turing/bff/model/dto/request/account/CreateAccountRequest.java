package az.edu.turing.bff.model.dto.request.account;

import az.edu.turing.bff.model.annotations.ValidBankAccountPassword;
import az.edu.turing.bff.model.enums.Bank;
import az.edu.turing.bff.model.enums.Currency;
import jakarta.validation.constraints.NotNull;


public record CreateAccountRequest(
        @NotNull
        Bank bank,

        @ValidBankAccountPassword
        String password,

        @NotNull
        Currency currency) {
}
