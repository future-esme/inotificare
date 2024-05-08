package utm.md.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import utm.md.domain.enumeration.MessageStatus;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus status;

    @JsonIgnoreProperties(value = { "notifySettings" }, allowSetters = true)
    @OneToOne
    @JoinColumn(name = "recipient_id", insertable = false, updatable = false)
    private User recipient;

    @Column(name = "recipient_id")
    private UUID recipientId;

    @JsonIgnoreProperties(value = { "templateId", "notification" }, allowSetters = true)
    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestData requestId;

    @OneToOne
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelUserCredentials channel;

    @Column(name = "channel_id")
    private UUID channelId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Notification id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Notification content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getStatus() {
        return this.status;
    }

    public Notification status(MessageStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public void setRecipient(User userInternal) {
        this.recipient = userInternal;
    }

    public Notification recipient(User userInternal) {
        this.setRecipient(userInternal);
        return this;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(UUID recipientId) {
        this.recipientId = recipientId;
    }

    public Notification recipientId(UUID recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public Notification channelId(UUID channelId) {
        this.channelId = channelId;
        return this;
    }

    public RequestData getRequestId() {
        return this.requestId;
    }

    public void setRequestId(RequestData requestData) {
        this.requestId = requestData;
    }

    public Notification requestId(RequestData requestData) {
        this.setRequestId(requestData);
        return this;
    }

    public ChannelUserCredentials getChannel() {
        return this.channel;
    }

    public void setChannel(ChannelUserCredentials channelUserCredentials) {
        this.channel = channelUserCredentials;
    }

    public Notification channel(ChannelUserCredentials channelUserCredentials) {
        this.setChannel(channelUserCredentials);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return getId() != null && getId().equals(((Notification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
