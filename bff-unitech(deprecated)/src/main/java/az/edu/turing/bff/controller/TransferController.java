package az.edu.turing.bff.controller;

import az.edu.turing.bff.model.dto.request.account.BankTransferRequest;
import az.edu.turing.bff.model.dto.request.account.TopUpRequest;
import az.edu.turing.bff.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bff/v1/accounts")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> makeBankTransfer(@RequestHeader("Authorization") String auth,
                                                 @PathVariable("id") Long accountId,
                                                 @RequestBody BankTransferRequest bankTransferRequest) {
        transferService.makeBankTransfer(auth, accountId, bankTransferRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/topup")
    public ResponseEntity<Void> topUp(@RequestHeader("Authorization") String auth,
                                      @PathVariable("id") Long accountId,
                                      @RequestBody TopUpRequest topUpRequest) {
        transferService.topUp(auth, accountId, topUpRequest);
        return ResponseEntity.accepted().build();
    }
}
