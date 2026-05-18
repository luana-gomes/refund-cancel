package com.payments.services.consumer.listener;
import com.payments.services.api.dto.RefundRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RefundConsumer {

    @RabbitListener(queues = "refund.quorum.queue")
    public void consume(RefundRequest request) {

        System.out.println("Pedido recebido:");

        System.out.println(request.getTransactionId());
        System.out.println(request.getAmount());

        // processa estorno
    }
}