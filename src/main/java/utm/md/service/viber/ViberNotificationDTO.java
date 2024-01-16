package utm.md.service.viber;

public record ViberNotificationDTO(String receiver, ViberMessageTypeEnum type, ViberSenderDTO sender) {}
