package az.edu.turing.bff.model.dto.response.account;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ExchangeRateResponse(
        String currencyCode,

        BigDecimal rate) {
}
