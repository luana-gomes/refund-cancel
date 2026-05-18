package com.payments.services.api.services;
import com.payments.services.api.config.RabbitConfig;
import com.payments.services.api.dto.RefundRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefundProducer {

    private final RabbitTemplate rabbitTemplate;

    public RefundProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRefund(RefundRequest request) {

        rabbitTemplate.convertAndSend(
                RabbitConfig.QUEUE,
                request
        );
    }
}