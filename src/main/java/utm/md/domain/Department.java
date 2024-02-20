package utm.md.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User departmentAdmin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_department__members",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "members_id")
    )
    private Set<User> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Department id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Department title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getDepartmentAdmin() {
        return this.departmentAdmin;
    }

    public void setDepartmentAdmin(User userInternal) {
        this.departmentAdmin = userInternal;
    }

    public Department departmentAdmin(User userInternal) {
        this.setDepartmentAdmin(userInternal);
        return this;
    }

    public Set<User> getMembers() {
        return this.members;
    }

    public void setMembers(Set<User> userInternals) {
        this.members = userInternals;
    }

    public Department members(Set<User> userInternals) {
        this.setMembers(userInternals);
        return this;
    }

    public Department addMembers(User userInternal) {
        this.members.add(userInternal);
        return this;
    }

    public Department removeMembers(User userInternal) {
        this.members.remove(userInternal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return getId() != null && getId().equals(((Department) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
