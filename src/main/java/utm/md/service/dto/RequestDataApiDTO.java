package utm.md.service.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import utm.md.domain.RequestData;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.MessageStatus;
import utm.md.domain.enumeration.Priority;
import utm.md.domain.enumeration.RecipientType;

public class RequestDataApiDTO {

    private UUID uuid;
    private Channel channel;
    private List<String> recipients;
    private RecipientType recipientType;
    private Priority priority;
    private String content;
    private MessageStatus messageStatus;

    public RequestDataApiDTO() {}

    public RequestDataApiDTO(RequestData requestData) {
        this.uuid = requestData.getId();
        this.channel = requestData.getChannel();
        this.recipients = toList(requestData.getRecipients());
        this.recipientType = requestData.getRecipientType();
        this.priority = requestData.getPriority();
        this.content = requestData.getContent();
        this.messageStatus = requestData.getMessageStatus();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    private List<String> toList(String recipientsString) {
        recipientsString = recipientsString.replace("[", "").replace("]", "");
        return Arrays.asList(recipientsString.split(","));
    }
}
