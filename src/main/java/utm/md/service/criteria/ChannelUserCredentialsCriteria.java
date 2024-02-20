package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;
import utm.md.domain.enumeration.Channel;

/**
 * Criteria class for the {@link utm.md.domain.ChannelUserCredentials} entity. This class is used
 * in {@link utm.md.web.rest.ChannelUserCredentialsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /channel-user-credentials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelUserCredentialsCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter chatId;

    private ChannelFilter channel;

    private Boolean distinct;

    public ChannelUserCredentialsCriteria() {}

    public ChannelUserCredentialsCriteria(ChannelUserCredentialsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chatId = other.chatId == null ? null : other.chatId.copy();
        this.channel = other.channel == null ? null : other.channel.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ChannelUserCredentialsCriteria copy() {
        return new ChannelUserCredentialsCriteria(this);
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

    public StringFilter getChatId() {
        return chatId;
    }

    public StringFilter chatId() {
        if (chatId == null) {
            chatId = new StringFilter();
        }
        return chatId;
    }

    public void setChatId(StringFilter chatId) {
        this.chatId = chatId;
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
        final ChannelUserCredentialsCriteria that = (ChannelUserCredentialsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chatId, that.chatId) &&
            Objects.equals(channel, that.channel) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, channel, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelUserCredentialsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chatId != null ? "chatId=" + chatId + ", " : "") +
            (channel != null ? "channel=" + channel + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
