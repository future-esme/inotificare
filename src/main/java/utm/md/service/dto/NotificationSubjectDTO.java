package utm.md.service.dto;

public class NotificationSubjectDTO extends NotificationShortDTO {

    private String subject;

    public NotificationSubjectDTO(String receiver, String content, String subject) {
        super(receiver, content);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "NotificationSubjectDTO{" + "subject='" + subject + '\'' + '}';
    }
}
