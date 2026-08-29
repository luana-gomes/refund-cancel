package com.payments.services.api.dto;
/*DTO Data transfer objetc 
 * transporta dados entre camadas da aplicação, sem conter regra de negocio
 * API recebe uma requisição HTTP → converte para um DTO.
 * Service recebe o DTO → processa a lógica.*/
import java.math.BigDecimal;

public class RefundRequest {
/*Este é um DTO que fará um solicitacao de estorno 
 * provavelmente ele será chamado quando alguem fizer um post via http*/
    private String transactionId;
    private String cardNumber;
    private BigDecimal amount;
    private String reason;
/*cada campo acima sao atributos privados que montam o payload a ser recebido pelo http metodo post na rota refund*/
    
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
/*os get e set sao os metodos para inserir valores e pegar valores, pois sao atributos privados*/