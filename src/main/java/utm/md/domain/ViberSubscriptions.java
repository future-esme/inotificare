package utm.md.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import utm.md.domain.enumeration.ViberSubscriptionStatus;

/**
 * A ViberSubscriptions.
 */
@Entity
@Table(name = "viber_subscriptions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViberSubscriptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ViberSubscriptionStatus status;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "last_updated_time")
    private Instant lastUpdatedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ViberSubscriptions id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public ViberSubscriptions userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public ViberSubscriptions userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ViberSubscriptionStatus getStatus() {
        return this.status;
    }

    public ViberSubscriptions status(ViberSubscriptionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ViberSubscriptionStatus status) {
        this.status = status;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ViberSubscriptions createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }

    public ViberSubscriptions lastUpdatedTime(Instant lastUpdatedTime) {
        this.setLastUpdatedTime(lastUpdatedTime);
        return this;
    }

    public void setLastUpdatedTime(Instant lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViberSubscriptions)) {
            return false;
        }
        return getId() != null && getId().equals(((ViberSubscriptions) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViberSubscriptions{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", lastUpdatedTime='" + getLastUpdatedTime() + "'" +
            "}";
    }
}
