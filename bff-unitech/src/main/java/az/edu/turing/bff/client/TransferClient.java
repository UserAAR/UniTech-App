package az.edu.turing.bff.client;

import az.edu.turing.bff.config.FeignConfig;
import az.edu.turing.bff.model.dto.request.account.BankTransferRequest;
import az.edu.turing.bff.model.dto.request.account.TopUpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "transfer-client", url = "${account/transfer-service.url}", configuration = FeignConfig.class)
public interface TransferClient {

    @PostMapping("/{id}/transfer")
    void makeBankTransfer(@RequestHeader("Authorization") String auth,
                          @PathVariable("id") Long accountId,
                          @RequestBody BankTransferRequest bankTransferRequest);

    @PostMapping("/{id}/topup")
    void topUp(@RequestHeader("Authorization") String auth,
               @PathVariable("id") Long accountId,
               @RequestBody TopUpRequest topUpRequest);
}
