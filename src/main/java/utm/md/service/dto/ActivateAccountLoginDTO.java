package utm.md.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class ActivateAccountLoginDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @NotNull
    private String activationKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    @Override
    public String toString() {
        return (
            "ActivateAccountLoginDTO{" +
            "username='" +
            username +
            '\'' +
            ", password='" +
            password +
            '\'' +
            ", activationKey='" +
            activationKey +
            '\'' +
            '}'
        );
    }
}
