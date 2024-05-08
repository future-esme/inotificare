package utm.md.web.rest.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause;

public class NotFoundException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private static final String errorKey = "notFound";

    public NotFoundException(String defaultMessage, String entityName) {
        super(
            HttpStatus.NOT_FOUND,
            ProblemDetailWithCause.ProblemDetailWithCauseBuilder
                .instance()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withTitle(defaultMessage)
                .withProperty("message", "error." + errorKey)
                .withProperty("params", entityName)
                .build(),
            null
        );
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
