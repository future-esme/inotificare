package utm.md.service.viber;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViberCallbackDTO(
    ViberEventTypeEnum event,
    Long timestamp,
    ViberUserSubscribedDTO user,
    @JsonProperty("message_token") Long messageToken,
    ViberUserSubscribedDTO sender,
    ViberReceiveMessageDTO message
) {}
