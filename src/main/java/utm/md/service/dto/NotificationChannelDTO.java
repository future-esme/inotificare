package utm.md.service.dto;

import utm.md.domain.enumeration.Channel;

public class NotificationChannelDTO {

    private String receiver;
    private String message;
    private Channel channel;
    private String subject;

    public NotificationChannelDTO(String receiver, String message, Channel channel) {
        this.receiver = receiver;
        this.message = message;
        this.channel = channel;
    }

    public NotificationChannelDTO(String receiver, String message, Channel channel, String subject) {
        this(receiver, message, channel);
        this.subject = subject;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
