package com.glauberperez.fintrackapi.controller;

import com.glauberperez.fintrackapi.model.Transaction;
import com.glauberperez.fintrackapi.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    public record TransactionRequest(String fromAccount, String toAccount, BigDecimal amount) {}

    @PostMapping
    public ResponseEntity<Transaction> transfer(@RequestBody TransactionRequest request) {
        Transaction result = service.processTransfer(
                request.fromAccount(),
                request.toAccount(),
                request.amount()
        );

        if ("FAILED".equals(result.getStatus())) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}