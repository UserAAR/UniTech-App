package az.edu.turing.mstransfer.model.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ExchangeRateResponse(
        String currencyCode,

        BigDecimal rate) {
}
