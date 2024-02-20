package utm.md.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import utm.md.domain.enumeration.Channel;

/**
 * A ChannelUserCredentials.
 */
@Entity
@Table(name = "channel_user_credentials")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelUserCredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "chat_id")
    private String chatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private Channel channel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ChannelUserCredentials id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getChatId() {
        return this.chatId;
    }

    public ChannelUserCredentials chatId(String chatId) {
        this.setChatId(chatId);
        return this;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public ChannelUserCredentials channel(Channel channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelUserCredentials)) {
            return false;
        }
        return getId() != null && getId().equals(((ChannelUserCredentials) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelUserCredentials{" +
            "id=" + getId() +
            ", chatId='" + getChatId() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
