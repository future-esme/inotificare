package utm.md.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;

/**
 * A NotifySettings.
 */
@Entity
@Table(name = "notify_settings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotifySettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotifyChannelStatusEnum status;

    @OneToOne
    @JoinColumn
    private ChannelUserCredentials credentials;

    @ManyToOne
    @JsonIgnoreProperties(value = { "notifySettings" }, allowSetters = true)
    private User userInternal;

    @OneToOne(mappedBy = "notifySettings")
    private ChannelsToken channelsToken;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public NotifySettings id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public NotifySettings channel(Channel channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public NotifyChannelStatusEnum getStatus() {
        return this.status;
    }

    public NotifySettings status(NotifyChannelStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(NotifyChannelStatusEnum status) {
        this.status = status;
    }

    public ChannelUserCredentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(ChannelUserCredentials channelUserCredentials) {
        this.credentials = channelUserCredentials;
    }

    public NotifySettings credentials(ChannelUserCredentials channelUserCredentials) {
        this.setCredentials(channelUserCredentials);
        return this;
    }

    public User getUserInternal() {
        return this.userInternal;
    }

    public void setUserInternal(User userInternal) {
        this.userInternal = userInternal;
    }

    public NotifySettings userInternal(User userInternal) {
        this.setUserInternal(userInternal);
        return this;
    }

    public ChannelsToken getChannelsToken() {
        return channelsToken;
    }

    public void setChannelsToken(ChannelsToken channelsToken) {
        this.channelsToken = channelsToken;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotifySettings)) {
            return false;
        }
        return getId() != null && getId().equals(((NotifySettings) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotifySettings{" +
            "id=" + getId() +
            ", channel='" + getChannel() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
