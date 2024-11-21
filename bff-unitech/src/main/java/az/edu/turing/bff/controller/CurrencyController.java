package az.edu.turing.bff.controller;

import az.edu.turing.bff.model.dto.response.account.ExchangeRateResponse;
import az.edu.turing.bff.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rates")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<ExchangeRateResponse>> getRates() {
        List<ExchangeRateResponse> rates = currencyService.getExchangeRates();
        return ResponseEntity.ok(rates);
    }
}