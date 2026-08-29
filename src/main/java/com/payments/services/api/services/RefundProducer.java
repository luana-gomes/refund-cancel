package com.payments.services.api.services;

import com.payments.services.api.config.RabbitConfig;
import com.payments.services.api.dto.RefundRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;

import io.opentelemetry.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefundProducer {

    private static final Logger log =
            LoggerFactory.getLogger(RefundProducer.class);

    private static final String SERVICE_NAME = "RefundProducer";

    private final RabbitTemplate rabbitTemplate;
    private final JdbcTemplate jdbcTemplate;

    public RefundProducer(RabbitTemplate rabbitTemplate,
                          JdbcTemplate jdbcTemplate) {

        this.rabbitTemplate = rabbitTemplate;
        this.jdbcTemplate = jdbcTemplate;

        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("[{}][PRODUCER] Mensagem confirmada pelo RabbitMQ", SERVICE_NAME);
            } else {
                log.error("[{}][PRODUCER] Falha ao enviar mensagem: {}", SERVICE_NAME, cause);
            }
        });
    }

    public void sendRefund(RefundRequest request) {

        Span currentSpan = Span.current();
        SpanContext ctx = currentSpan.getSpanContext();

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
                ctx.getTraceId(),
                ctx.getSpanId(),
                request.getCardNumber(),
                request.getAmount(),
                request.getReason()
        );

        jdbcTemplate.update("""
                INSERT INTO refunds
                (transaction_id, card_number, amount, reason)
                VALUES (?, ?, ?, ?)
                """,
                request.getTransactionId(),
                request.getCardNumber(),
                request.getAmount(),
                request.getReason()
        );

        log.info("[{}][DATABASE] Registro inserido no PostgreSQL", SERVICE_NAME);

        // 🔥 CRIA HEADERS COM TRACE CONTEXT
        Map<String, Object> headers = new HashMap<>();

        headers.put("traceId", ctx.getTraceId());
        headers.put("spanId", ctx.getSpanId());

        rabbitTemplate.convertAndSend(
                RabbitConfig.QUEUE,
                request,
                message -> {
                    message.getMessageProperties().setHeaders(headers);
                    message.getMessageProperties()
                            .setCorrelationId(request.getTransactionId());
                    return message;
                }
        );

        log.info("[{}][PRODUCER] Mensagem enviada para fila={}", SERVICE_NAME, RabbitConfig.QUEUE);
    }
}