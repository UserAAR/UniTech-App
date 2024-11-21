package az.edu.turing.bff.client;

import az.edu.turing.bff.config.FeignConfig;
import az.edu.turing.bff.model.dto.response.account.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "currency-client", url = "${currency-service.url}", configuration = FeignConfig.class)
public interface CurrencyClient {

    @GetMapping("")
    List<ExchangeRateResponse> getExchangeRates();
}