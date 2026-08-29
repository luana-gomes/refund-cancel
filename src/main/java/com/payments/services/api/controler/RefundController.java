package com.payments.services.api.controler;
import com.payments.services.api.dto.RefundRequest;
/*Importa o dto de refund request */
import com.payments.services.api.services.RefundProducer;
/*Importa o service refund producer */
import org.springframework.http.ResponseEntity;
/*import dos controles de resposta de http*/
import org.springframework.web.bind.annotation.*;
/*import das anotações e classes do principal pacote de criação de API REST do spring boot */

@RestController
/*define a classe que será o controler rest */
@RequestMapping("/refund")
/*define a rota base */
public class RefundController {

    private final RefundProducer producer;

    public RefundController(RefundProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    /*define metodo que será o post 
     * toda vez que a alguem fizer um post para a rota associada, esse metodo que será executado no caso a rota seraá http://localhost:8080/refund*/
    public ResponseEntity<String> refund(
            @RequestBody RefundRequest request
    ) {

        producer.sendRefund(request);
        
        return ResponseEntity.ok(
                "Pedido de estorno enviado para processamento"
        /*siginifica que quando o status http 200 o corpo será um string*/
        );
    }
}