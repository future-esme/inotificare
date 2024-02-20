package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;
import utm.md.domain.enumeration.Channel;
import utm.md.domain.enumeration.MessageStatus;
import utm.md.domain.enumeration.Priority;
import utm.md.domain.enumeration.RecipientType;

/**
 * Criteria class for the {@link utm.md.domain.RequestData} entity. This class is used
 * in {@link utm.md.web.rest.RequestDataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /request-data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestDataCriteria implements Serializable, Criteria {

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
     * Class for filtering RecipientType
     */
    public static class RecipientTypeFilter extends Filter<RecipientType> {

        public RecipientTypeFilter() {}

        public RecipientTypeFilter(RecipientTypeFilter filter) {
            super(filter);
        }

        @Override
        public RecipientTypeFilter copy() {
            return new RecipientTypeFilter(this);
        }
    }

    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {

        public PriorityFilter() {}

        public PriorityFilter(PriorityFilter filter) {
            super(filter);
        }

        @Override
        public PriorityFilter copy() {
            return new PriorityFilter(this);
        }
    }

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

    private ChannelFilter channel;

    private StringFilter recipients;

    private RecipientTypeFilter recipientType;

    private PriorityFilter priority;

    private StringFilter content;

    private MessageStatusFilter messageStatus;

    private InstantFilter createdTime;

    private UUIDFilter templateIdId;

    private UUIDFilter notificationId;

    private Boolean distinct;

    public RequestDataCriteria() {}

    public RequestDataCriteria(RequestDataCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.channel = other.channel == null ? null : other.channel.copy();
        this.recipients = other.recipients == null ? null : other.recipients.copy();
        this.recipientType = other.recipientType == null ? null : other.recipientType.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.messageStatus = other.messageStatus == null ? null : other.messageStatus.copy();
        this.createdTime = other.createdTime == null ? null : other.createdTime.copy();
        this.templateIdId = other.templateIdId == null ? null : other.templateIdId.copy();
        this.notificationId = other.notificationId == null ? null : other.notificationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RequestDataCriteria copy() {
        return new RequestDataCriteria(this);
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

    public StringFilter getRecipients() {
        return recipients;
    }

    public StringFilter recipients() {
        if (recipients == null) {
            recipients = new StringFilter();
        }
        return recipients;
    }

    public void setRecipients(StringFilter recipients) {
        this.recipients = recipients;
    }

    public RecipientTypeFilter getRecipientType() {
        return recipientType;
    }

    public RecipientTypeFilter recipientType() {
        if (recipientType == null) {
            recipientType = new RecipientTypeFilter();
        }
        return recipientType;
    }

    public void setRecipientType(RecipientTypeFilter recipientType) {
        this.recipientType = recipientType;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public PriorityFilter priority() {
        if (priority == null) {
            priority = new PriorityFilter();
        }
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
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

    public MessageStatusFilter getMessageStatus() {
        return messageStatus;
    }

    public MessageStatusFilter messageStatus() {
        if (messageStatus == null) {
            messageStatus = new MessageStatusFilter();
        }
        return messageStatus;
    }

    public void setMessageStatus(MessageStatusFilter messageStatus) {
        this.messageStatus = messageStatus;
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

    public UUIDFilter getTemplateIdId() {
        return templateIdId;
    }

    public UUIDFilter templateIdId() {
        if (templateIdId == null) {
            templateIdId = new UUIDFilter();
        }
        return templateIdId;
    }

    public void setTemplateIdId(UUIDFilter templateIdId) {
        this.templateIdId = templateIdId;
    }

    public UUIDFilter getNotificationId() {
        return notificationId;
    }

    public UUIDFilter notificationId() {
        if (notificationId == null) {
            notificationId = new UUIDFilter();
        }
        return notificationId;
    }

    public void setNotificationId(UUIDFilter notificationId) {
        this.notificationId = notificationId;
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
        final RequestDataCriteria that = (RequestDataCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(channel, that.channel) &&
            Objects.equals(recipients, that.recipients) &&
            Objects.equals(recipientType, that.recipientType) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(content, that.content) &&
            Objects.equals(messageStatus, that.messageStatus) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(templateIdId, that.templateIdId) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            channel,
            recipients,
            recipientType,
            priority,
            content,
            messageStatus,
            createdTime,
            templateIdId,
            notificationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDataCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (channel != null ? "channel=" + channel + ", " : "") +
            (recipients != null ? "recipients=" + recipients + ", " : "") +
            (recipientType != null ? "recipientType=" + recipientType + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            (messageStatus != null ? "messageStatus=" + messageStatus + ", " : "") +
            (createdTime != null ? "createdTime=" + createdTime + ", " : "") +
            (templateIdId != null ? "templateIdId=" + templateIdId + ", " : "") +
            (notificationId != null ? "notificationId=" + notificationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
