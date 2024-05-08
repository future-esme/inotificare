package utm.md.service.dto;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.UUID;
import utm.md.domain.enumeration.Priority;
import utm.md.domain.enumeration.RecipientType;

public class RequestDataDTO {

    private List<String> recipients;
    private RecipientType recipientType;
    private Priority priority;
    private String content;
    private UUID templateId;
    private String emailSubject;

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

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Override
    public String toString() {
        return (
            "RequestDataDTO{" +
            ", recipients number : " +
            ((nonNull(recipients)) ? recipients.size() : 0) +
            ", recipientType=" +
            recipientType +
            ", priority=" +
            priority +
            ", content='" +
            content +
            '\'' +
            '}'
        );
    }
}
