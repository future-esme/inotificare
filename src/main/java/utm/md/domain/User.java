package utm.md.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import utm.md.config.Constants;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
public class User extends AbstractAuditingEntity<UUID> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private UUID id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInternal")
    @JsonIgnoreProperties(value = { "credentials", "userInternal" }, allowSetters = true)
    private Set<NotifySettings> notifySettings = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<NotifySettings> getNotifySettings() {
        return this.notifySettings;
    }

    public void setNotifySettings(Set<NotifySettings> notifySettings) {
        if (this.notifySettings != null) {
            this.notifySettings.forEach(i -> i.setUserInternal(null));
        }
        if (notifySettings != null) {
            notifySettings.forEach(i -> i.setUserInternal(this));
        }
        this.notifySettings = notifySettings;
    }

    public User notifySettings(Set<NotifySettings> notifySettings) {
        this.setNotifySettings(notifySettings);
        return this;
    }

    public User addNotifySettings(NotifySettings notifySettings) {
        this.notifySettings.add(notifySettings);
        notifySettings.setUserInternal(this);
        return this;
    }

    public User removeNotifySettings(NotifySettings notifySettings) {
        this.notifySettings.remove(notifySettings);
        notifySettings.setUserInternal(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            "}";
    }
}
