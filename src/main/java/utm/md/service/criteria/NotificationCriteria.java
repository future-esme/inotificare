package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;
import utm.md.domain.enumeration.MessageStatus;

/**
 * Criteria class for the {@link utm.md.domain.Notification} entity. This class is used
 * in {@link utm.md.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MessageStatus
     */
    public static class MessageStatusFilter extends Filter<MessageStatus> {

        public MessageStatusFilter() {}

        public MessageStatusFilter(MessageStatusFilter filter) {
            super(filter);
        }

        @Override
        public MessageStatusFilter copy() {
            return new MessageStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter content;

    private MessageStatusFilter status;

    private UUIDFilter recipientId;

    private UUIDFilter requestIdId;

    private UUIDFilter channelId;

    private Boolean distinct;

    public NotificationCriteria() {}

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.recipientId = other.recipientId == null ? null : other.recipientId.copy();
        this.requestIdId = other.requestIdId == null ? null : other.requestIdId.copy();
        this.channelId = other.channelId == null ? null : other.channelId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
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

    public StringFilter getContent() {
        return content;
    }

    public StringFilter content() {
        if (content == null) {
            content = new StringFilter();
        }
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public MessageStatusFilter getStatus() {
        return status;
    }

    public MessageStatusFilter status() {
        if (status == null) {
            status = new MessageStatusFilter();
        }
        return status;
    }

    public void setStatus(MessageStatusFilter status) {
        this.status = status;
    }

    public UUIDFilter getRecipientId() {
        return recipientId;
    }

    public UUIDFilter recipientId() {
        if (recipientId == null) {
            recipientId = new UUIDFilter();
        }
        return recipientId;
    }

    public void setRecipientId(UUIDFilter recipientId) {
        this.recipientId = recipientId;
    }

    public UUIDFilter getRequestIdId() {
        return requestIdId;
    }

    public UUIDFilter requestIdId() {
        if (requestIdId == null) {
            requestIdId = new UUIDFilter();
        }
        return requestIdId;
    }

    public void setRequestIdId(UUIDFilter requestIdId) {
        this.requestIdId = requestIdId;
    }

    public UUIDFilter getChannelId() {
        return channelId;
    }

    public UUIDFilter channelId() {
        if (channelId == null) {
            channelId = new UUIDFilter();
        }
        return channelId;
    }

    public void setChannelId(UUIDFilter channelId) {
        this.channelId = channelId;
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
        final NotificationCriteria that = (NotificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(content, that.content) &&
            Objects.equals(status, that.status) &&
            Objects.equals(recipientId, that.recipientId) &&
            Objects.equals(requestIdId, that.requestIdId) &&
            Objects.equals(channelId, that.channelId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, status, recipientId, requestIdId, channelId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (recipientId != null ? "recipientId=" + recipientId + ", " : "") +
            (requestIdId != null ? "requestIdId=" + requestIdId + ", " : "") +
            (channelId != null ? "channelId=" + channelId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
