package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.UUIDFilter;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.NotifyChannelStatusEnum;

/**
 * Criteria class for the {@link utm.md.domain.NotifySettings} entity. This class is used
 * in {@link utm.md.web.rest.NotifySettingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notify-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotifySettingsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Channel
     */
    public static class ChannelFilter extends Filter<Channel> {

        public ChannelFilter() {}

        public ChannelFilter(ChannelFilter filter) {
            super(filter);
        }

        @Override
        public ChannelFilter copy() {
            return new ChannelFilter(this);
        }
    }

    /**
     * Class for filtering NotifyChannelStatusEnum
     */
    public static class NotifyChannelStatusEnumFilter extends Filter<NotifyChannelStatusEnum> {

        public NotifyChannelStatusEnumFilter() {}

        public NotifyChannelStatusEnumFilter(NotifyChannelStatusEnumFilter filter) {
            super(filter);
        }

        @Override
        public NotifyChannelStatusEnumFilter copy() {
            return new NotifyChannelStatusEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private ChannelFilter channel;

    private NotifyChannelStatusEnumFilter status;

    private UUIDFilter credentialsId;

    private UUIDFilter userInternalId;

    private Boolean distinct;

    public NotifySettingsCriteria() {}

    public NotifySettingsCriteria(NotifySettingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.channel = other.channel == null ? null : other.channel.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.credentialsId = other.credentialsId == null ? null : other.credentialsId.copy();
        this.userInternalId = other.userInternalId == null ? null : other.userInternalId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotifySettingsCriteria copy() {
        return new NotifySettingsCriteria(this);
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

    public ChannelFilter getChannel() {
        return channel;
    }

    public ChannelFilter channel() {
        if (channel == null) {
            channel = new ChannelFilter();
        }
        return channel;
    }

    public void setChannel(ChannelFilter channel) {
        this.channel = channel;
    }

    public NotifyChannelStatusEnumFilter getStatus() {
        return status;
    }

    public NotifyChannelStatusEnumFilter status() {
        if (status == null) {
            status = new NotifyChannelStatusEnumFilter();
        }
        return status;
    }

    public void setStatus(NotifyChannelStatusEnumFilter status) {
        this.status = status;
    }

    public UUIDFilter getCredentialsId() {
        return credentialsId;
    }

    public UUIDFilter credentialsId() {
        if (credentialsId == null) {
            credentialsId = new UUIDFilter();
        }
        return credentialsId;
    }

    public void setCredentialsId(UUIDFilter credentialsId) {
        this.credentialsId = credentialsId;
    }

    public UUIDFilter getUserInternalId() {
        return userInternalId;
    }

    public UUIDFilter userInternalId() {
        if (userInternalId == null) {
            userInternalId = new UUIDFilter();
        }
        return userInternalId;
    }

    public void setUserInternalId(UUIDFilter userInternalId) {
        this.userInternalId = userInternalId;
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
        final NotifySettingsCriteria that = (NotifySettingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(channel, that.channel) &&
            Objects.equals(status, that.status) &&
            Objects.equals(credentialsId, that.credentialsId) &&
            Objects.equals(userInternalId, that.userInternalId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channel, status, credentialsId, userInternalId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotifySettingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (channel != null ? "channel=" + channel + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (credentialsId != null ? "credentialsId=" + credentialsId + ", " : "") +
            (userInternalId != null ? "userInternalId=" + userInternalId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
