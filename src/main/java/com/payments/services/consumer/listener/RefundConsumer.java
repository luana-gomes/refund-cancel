package com.payments.services.consumer.listener;

import com.payments.services.api.dto.RefundRequest;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RefundConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(RefundConsumer.class);

    private static final String SERVICE_NAME = "RefundConsumer";

    @RabbitListener(queues = "refund.quorum.queue")
    public void consume(RefundRequest request,
                        Message message) {

        var headers = message.getMessageProperties().getHeaders();

        String traceId = String.valueOf(
                headers.getOrDefault("traceId", "no-trace")
        );

        String spanId = String.valueOf(
                headers.getOrDefault("spanId", "no-span")
        );

        log.info("""
                service-name={}
                transactionId={}
                traceId={}
                spanId={}
                cardNumber={}
                amount={}
                reason={}
                """,
                SERVICE_NAME,
                request.getTransactionId(),
                traceId,
                spanId,
                request.getCardNumber(),
                request.getAmount(),
                request.getReason()
        );

        // aqui entra sua regra de negócio
    }
}