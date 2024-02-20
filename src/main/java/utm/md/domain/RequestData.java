package utm.md.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.MessageStatus;
import utm.md.domain.enumeration.Priority;
import utm.md.domain.enumeration.RecipientType;

/**
 * A RequestData.
 */
@Entity
@Table(name = "request_data")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false)
    private Channel channel;

    @Column(name = "recipients")
    private String recipients;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type")
    private RecipientType recipientType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "content")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "message_status", nullable = false)
    private MessageStatus messageStatus;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @JsonIgnoreProperties(value = { "requestData" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template templateId;

    @JsonIgnoreProperties(value = { "recipient", "requestId", "channel" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "requestId")
    private Notification notification;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public RequestData id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public RequestData channel(Channel channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getRecipients() {
        return this.recipients;
    }

    public RequestData recipients(String recipients) {
        this.setRecipients(recipients);
        return this;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public RecipientType getRecipientType() {
        return this.recipientType;
    }

    public RequestData recipientType(RecipientType recipientType) {
        this.setRecipientType(recipientType);
        return this;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public RequestData priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getContent() {
        return this.content;
    }

    public RequestData content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getMessageStatus() {
        return this.messageStatus;
    }

    public RequestData messageStatus(MessageStatus messageStatus) {
        this.setMessageStatus(messageStatus);
        return this;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public RequestData createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Template getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Template template) {
        this.templateId = template;
    }

    public RequestData templateId(Template template) {
        this.setTemplateId(template);
        return this;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public void setNotification(Notification notification) {
        if (this.notification != null) {
            this.notification.setRequestId(null);
        }
        if (notification != null) {
            notification.setRequestId(this);
        }
        this.notification = notification;
    }

    public RequestData notification(Notification notification) {
        this.setNotification(notification);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestData)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestData) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestData{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", recipients='" + getRecipients() + "'" +
            ", recipientType='" + getRecipientType() + "'" +
            ", priority='" + getPriority() + "'" +
            ", content='" + getContent() + "'" +
            ", messageStatus='" + getMessageStatus() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
