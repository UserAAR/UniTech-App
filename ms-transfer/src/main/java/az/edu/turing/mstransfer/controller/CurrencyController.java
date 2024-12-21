package az.edu.turing.mstransfer.controller;

import az.edu.turing.mstransfer.model.response.ExchangeRateResponse;
import az.edu.turing.mstransfer.service.CurrencyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rates")
@Tag(name = "Currency Controller API", description = "currency controller")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<ExchangeRateResponse>> getRates() {
        List<ExchangeRateResponse> exchangeRates =
                currencyService.getExchangeRates();
        return ResponseEntity.ok(exchangeRates);
    }
}
