package utm.md.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatesTelegramDTO(@JsonProperty("update_id") Integer updateId, MessageTelegramDTO message) {}
