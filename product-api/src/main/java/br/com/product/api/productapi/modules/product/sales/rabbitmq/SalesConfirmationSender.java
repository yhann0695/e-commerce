package br.com.product.api.productapi.modules.product.sales.rabbitmq;

import br.com.product.api.productapi.modules.product.sales.dto.SalesConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendConfirmationMessage(SalesConfirmationDTO dto) {
        try {
            log.info("sending message: {}", new ObjectMapper().writeValueAsString(dto));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, dto);
            log.info("Message was sent successfully!");
        } catch (Exception exception) {
            log.info("Error while trying to send sales confirmation message: ", exception);
        }
    }
}
