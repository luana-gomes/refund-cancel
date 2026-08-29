package com.payments.services.api.config;

import org.springframework.amqp.core.Queue;
/* todo import em java é uma classe 
 * este import é uma classe em java com o foco de declarar uma fila.*/
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
/* todo import em java é uma classe 
 * este import é uma classe em java com o foco de fazer a conexão com o Rabbit mq.*/
import org.springframework.amqp.rabbit.core.RabbitTemplate;
/* todo import em java é uma classe 
 * este import é uma classe em java com o importar o template utilziado para enviar mensagem.*/
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
/* todo import em java é uma classe 
 * este import é uma classe em java com o foco de importar containers utilizado pelo metodo @RabbitListener.*/
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
/* conversor de java para jason e vise versa.*/
import org.springframework.context.annotation.Bean;
/* importa a anotação bean.*/
import org.springframework.context.annotation.Configuration;
/* importa a anotação configuration.*/
import java.util.Map;
/* importa a biblioteca padrao java utilziada normalmente para armazenar chave-valor, em configuracoes rabbit mq*/

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "refund.quorum.queue";

    @Bean
    public Queue refundQueue() {

        return new Queue(
                QUEUE,
                true,
                false,
                false,
                Map.of(
                        "x-queue-type", "quorum"
                )
        );
    }
/*as informações acima, nada mais é do que a configuração da fila no rabbit
 * os parametros acima significa 
 * Parâmetro		Valor					Significado
	name		refund.quorum.queue			Nome da fila
	durable			true					Sobrevive ao restart do RabbitMQ
	xclusive		false					Pode ser usada por várias conexões
	autoDelete		false					Não é apagada automaticamente
	x-queue-type	quorum					Fila replicada e resiliente */
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }
/*A linha cima é so um conversor, tudo que receber em java vira json e vice versa*/
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory
    ) {

        RabbitTemplate template =
                new RabbitTemplate(connectionFactory);

        template.setMessageConverter(
                messageConverter()
        );

        return template;
    }
/*as linhas acima trata as configurações de conexões do rabbit, injetando automaticamente, tudo que estiver configurado no arquivo.yml 
 * é visto como injecao dessas configurações.  */
    @Bean
    public SimpleRabbitListenerContainerFactory
    rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory
    ) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(
                connectionFactory
        );

        factory.setMessageConverter(
                messageConverter()
        );

        return factory;
    }

/*nas linhas acima é onde configuramos quem recebe essas mensagens, quando vc utiliza esse tipo de configuracao padrao 
 * o spring irá buscar onde eu tenho @RabbitListener  */
}
