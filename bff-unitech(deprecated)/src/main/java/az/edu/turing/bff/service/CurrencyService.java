package az.edu.turing.bff.service;

import az.edu.turing.bff.client.CurrencyClient;
import az.edu.turing.bff.model.dto.response.account.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyClient currencyClient;

    public List<ExchangeRateResponse> getExchangeRates() {
        return currencyClient.getExchangeRates();
    }
}