package utm.md.service.viber;

public record ViberReceiveMessageDTO(ViberMessageTypeEnum type, String text, String media) {}
