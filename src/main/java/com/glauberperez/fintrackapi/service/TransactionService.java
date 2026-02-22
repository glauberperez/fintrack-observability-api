package com.glauberperez.fintrackapi.service;

import com.glauberperez.fintrackapi.model.Transaction;
import com.glauberperez.fintrackapi.repository.TransactionRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    // Nossas métricas customizadas
    private final Counter successCounter;
    private final Counter failureCounter;

    public TransactionService(TransactionRepository repository, MeterRegistry meterRegistry) {
        this.repository = repository;

        // Registrando os contadores no Prometheus
        this.successCounter = meterRegistry.counter("fintrack.transactions.success", "type", "transfer");
        this.failureCounter = meterRegistry.counter("fintrack.transactions.failed", "type", "transfer");
    }

    public Transaction processTransfer(String fromAccount, String toAccount, BigDecimal amount) {
        Transaction tx = new Transaction();
        tx.setFromAccount(fromAccount);
        tx.setToAccount(toAccount);
        tx.setAmount(amount);

        // Simulando uma regra de negócio: transferências acima de 10.000 são bloqueadas
        if (amount.compareTo(new BigDecimal("10000")) > 0) {
            tx.setStatus("FAILED");
            failureCounter.increment(); // Prometheus = falha
        } else {
            tx.setStatus("SUCCESS");
            successCounter.increment(); // Prometheus = sucesso

            // Simulando lentidão da database
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return repository.save(tx);
    }
}