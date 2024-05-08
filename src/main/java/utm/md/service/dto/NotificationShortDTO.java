package utm.md.service.dto;

public class NotificationShortDTO implements NotificationDTO {

    private String receiver;
    private String content;

    public NotificationShortDTO(String receiver, String content) {
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
