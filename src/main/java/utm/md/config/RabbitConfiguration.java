package utm.md.config;

import static utm.md.config.RabbitMqConstants.*;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE);
    }

    @Bean
    DirectExchange mailExchange() {
        return new DirectExchange(MAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY);
    }

    @Bean
    public Queue telegramQueue() {
        return new Queue(TELEGRAM_QUEUE);
    }

    @Bean
    DirectExchange telegramExchange() {
        return new DirectExchange(TELEGRAM_EXCHANGE, true, false);
    }

    @Bean
    public Binding telegramBinding() {
        return BindingBuilder.bind(telegramQueue()).to(telegramExchange()).with(TELEGRAM_ROUTING_KEY);
    }

    @Bean
    public Queue viberQueue() {
        return new Queue(VIBER_QUEUE);
    }

    @Bean
    DirectExchange viberExchange() {
        return new DirectExchange(VIBER_EXCHANGE, true, false);
    }

    @Bean
    public Binding viberBinding() {
        return BindingBuilder.bind(viberQueue()).to(viberExchange()).with(VIBER_ROUTING_KEY);
    }
}
