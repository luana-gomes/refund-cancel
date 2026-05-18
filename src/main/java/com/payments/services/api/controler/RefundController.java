package com.payments.services.api.controler;
import com.payments.services.api.dto.RefundRequest;
import com.payments.services.api.services.RefundProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refund")
public class RefundController {

    private final RefundProducer producer;

    public RefundController(RefundProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> refund(
            @RequestBody RefundRequest request
    ) {

        producer.sendRefund(request);

        return ResponseEntity.ok(
                "Pedido de estorno enviado para processamento"
        );
    }
}