package utm.md.service.dto;

public class NotificationDTO {

    private String receiver;
    private String content;

    public NotificationDTO(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" + "receiver='" + receiver + '\'' + '}';
    }
}
