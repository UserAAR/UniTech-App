package az.edu.turing.mstransfer.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Error {
    ERR_01("ERR_01", "Account does not exist!"),
    ERR_02("ERR_02", "Exchange rate not available for the currency: "),
    ERR_03("ERR_03", "Exchange rate not available for one of the currencies."),
    ERR_04("ERR_04", "Account is not active!"),
    ERR_05("ERR_05", "Bank Transfer to the same account is not allowed"),
    ERR_06("ERR_06", "Balance is not enough!"),
    ERR_07("ERR_07", "Top-up transfer should be between accounts of same user"),
    ERR_08("ERR_08", "Account password for this user exists"),
    ERR_09("ERR_09", "Invalid or expired token!");

    private final String errorCode;
    private final String errorDescription;
}
