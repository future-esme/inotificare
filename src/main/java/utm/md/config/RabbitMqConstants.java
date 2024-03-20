package utm.md.config;

public class RabbitMqConstants {

    private RabbitMqConstants() {}

    public static final String TELEGRAM_QUEUE = "telegram";
    public static final String VIBER_QUEUE = "viber";
    public static final String MAIL_QUEUE = "mail";

    public static final String TELEGRAM_EXCHANGE = "telegramExchange";
    public static final String VIBER_EXCHANGE = "viberExchange";
    public static final String MAIL_EXCHANGE = "mailExchange";

    public static final String TELEGRAM_ROUTING_KEY = "telegramRoutingKey";
    public static final String VIBER_ROUTING_KEY = "viberRoutingKey";
    public static final String MAIL_ROUTING_KEY = "mailRoutingKey";
}
