package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;
import utm.md.domain.enumeration.ViberSubscriptionStatus;

/**
 * Criteria class for the {@link utm.md.domain.ViberSubscriptions} entity. This class is used
 * in {@link utm.md.web.rest.ViberSubscriptionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /viber-subscriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViberSubscriptionsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ViberSubscriptionStatus
     */
    public static class ViberSubscriptionStatusFilter extends Filter<ViberSubscriptionStatus> {

        public ViberSubscriptionStatusFilter() {}

        public ViberSubscriptionStatusFilter(ViberSubscriptionStatusFilter filter) {
            super(filter);
        }

        @Override
        public ViberSubscriptionStatusFilter copy() {
            return new ViberSubscriptionStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter userId;

    private StringFilter userName;

    private ViberSubscriptionStatusFilter status;

    private InstantFilter createdTime;

    private InstantFilter lastUpdatedTime;

    private Boolean distinct;

    public ViberSubscriptionsCriteria() {}

    public ViberSubscriptionsCriteria(ViberSubscriptionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdTime = other.createdTime == null ? null : other.createdTime.copy();
        this.lastUpdatedTime = other.lastUpdatedTime == null ? null : other.lastUpdatedTime.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ViberSubscriptionsCriteria copy() {
        return new ViberSubscriptionsCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public UUIDFilter id() {
        if (id == null) {
            id = new UUIDFilter();
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public StringFilter userId() {
        if (userId == null) {
            userId = new StringFilter();
        }
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public StringFilter userName() {
        if (userName == null) {
            userName = new StringFilter();
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public ViberSubscriptionStatusFilter getStatus() {
        return status;
    }

    public ViberSubscriptionStatusFilter status() {
        if (status == null) {
            status = new ViberSubscriptionStatusFilter();
        }
        return status;
    }

    public void setStatus(ViberSubscriptionStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getCreatedTime() {
        return createdTime;
    }

    public InstantFilter createdTime() {
        if (createdTime == null) {
            createdTime = new InstantFilter();
        }
        return createdTime;
    }

    public void setCreatedTime(InstantFilter createdTime) {
        this.createdTime = createdTime;
    }

    public InstantFilter getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public InstantFilter lastUpdatedTime() {
        if (lastUpdatedTime == null) {
            lastUpdatedTime = new InstantFilter();
        }
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(InstantFilter lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ViberSubscriptionsCriteria that = (ViberSubscriptionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(lastUpdatedTime, that.lastUpdatedTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, userName, status, createdTime, lastUpdatedTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViberSubscriptionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (userName != null ? "userName=" + userName + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
            (lastUpdatedTime != null ? "lastUpdatedTime=" + lastUpdatedTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
