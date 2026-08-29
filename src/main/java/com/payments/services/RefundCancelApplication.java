package com.payments.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
/*
 * Esta classe é responsável por:
 * - iniciar o Spring
 * - subir o contexto da aplicação
 * - criar os beans
 * - iniciar o servidor embutido do Tomcat
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
 * Esta anotação sinaliza para o Spring que esta é a classe principal da aplicação.
 * Ela habilita a configuração automática e o component scan.
 */
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RefundCancelApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefundCancelApplication.class, args);
    }

    @Bean
    CommandLineRunner test(
            @Value("${spring.datasource.url:NAO_ENCONTROU}") String url) {

        return args -> {
            System.out.println("=================================");
            System.out.println("URL BANCO = " + url);
            System.out.println("=================================");
        };
    }
}

/*
 * public: acessível de qualquer lugar
 * static: pode ser executado sem instanciar a classe
 * void: não retorna valor
 * main: ponto de entrada reconhecido pela JVM
 *
 * Quando executamos:
 * mvn spring-boot:run
 * ou iniciamos pelo Eclipse,
 * o Spring sobe as dependências, cria os beans e inicializa a aplicação.
 */
 