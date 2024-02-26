package utm.md.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import utm.md.domain.enumeration.Channel;

/**
 * A ChannelsToken.
 */
@Entity
@Table(name = "channels_token")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelsToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Size(max = 10)
    @Column(name = "token", length = 10)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private Channel channel;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "expiration_time")
    private Instant expirationTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ChannelsToken id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public ChannelsToken token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public ChannelsToken userId(UUID userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ChannelsToken createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getExpirationTime() {
        return this.expirationTime;
    }

    public ChannelsToken expirationTime(Instant expirationTime) {
        this.setExpirationTime(expirationTime);
        return this;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Channel getChannel() {
        return channel;
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
        if (!(o instanceof ChannelsToken)) {
            return false;
        }
        return getId() != null && getId().equals(((ChannelsToken) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelsToken{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", userId='" + getUserId() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", expirationTime='" + getExpirationTime() + "'" +
            "}";
    }
}
