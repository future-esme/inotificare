package utm.md.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import utm.md.domain.RequestData;

public class SendNotificationResponse {

    private static final String SUCCESS = "Received request to send notifications";
    private static final String INVALID_REQUEST = "Received request to send notifications";
    private String message;
    private UUID requestId;
    private RequestData requestData;
    private int internStatus;

    public SendNotificationResponse() {}

    public SendNotificationResponse(UUID requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    @JsonIgnore
    public void isSuccess() {
        this.setMessage(SUCCESS);
        this.setInternStatus(200);
    }

    @JsonIgnore
    public void isInvalidRequest() {
        this.setMessage(INVALID_REQUEST);
        this.setInternStatus(400);
    }

    @JsonIgnore
    public RequestData getRequestData() {
        return requestData;
    }

    @JsonIgnore
    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    @JsonIgnore
    public int getInternStatus() {
        return internStatus;
    }

    @JsonIgnore
    public void setInternStatus(int internStatus) {
        this.internStatus = internStatus;
    }
}
