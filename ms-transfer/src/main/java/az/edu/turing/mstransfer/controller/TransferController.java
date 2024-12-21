package az.edu.turing.mstransfer.controller;


import az.edu.turing.mstransfer.client.MsAuthClient;
import az.edu.turing.mstransfer.model.request.BankTransferRequest;
import az.edu.turing.mstransfer.model.request.TopUpRequest;
import az.edu.turing.mstransfer.service.TransferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Transfer Controller API", description = "transfer controller")
public class TransferController {
    private final TransferService transferService;
    private final MsAuthClient authClient;

    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> makeBankTransfer(@RequestHeader("Authorization") String auth,
                                                 @PathVariable("id") Long accountId,
                                                 @RequestBody @Valid BankTransferRequest bankTransferRequest) {
        String token = authClient.extractToken(auth);
        transferService.makeBankTransfer(token, accountId, bankTransferRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/topup")
    public ResponseEntity<Void> topUp(@RequestHeader("Authorization") String auth,
                                      @PathVariable("id") Long accountId,
                                      @RequestBody @Valid TopUpRequest topUpRequest) {
        String token = authClient.extractToken(auth);
        transferService.topUp(token, accountId, topUpRequest);
        return ResponseEntity.accepted().build();
    }
}

