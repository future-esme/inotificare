package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link utm.md.domain.Department} entity. This class is used
 * in {@link utm.md.web.rest.DepartmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter title;

    private UUIDFilter departmentAdminId;

    private UUIDFilter membersId;

    private Boolean distinct;

    public DepartmentCriteria() {}

    public DepartmentCriteria(DepartmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.departmentAdminId = other.departmentAdminId == null ? null : other.departmentAdminId.copy();
        this.membersId = other.membersId == null ? null : other.membersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DepartmentCriteria copy() {
        return new DepartmentCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public UUIDFilter getDepartmentAdminId() {
        return departmentAdminId;
    }

    public UUIDFilter departmentAdminId() {
        if (departmentAdminId == null) {
            departmentAdminId = new UUIDFilter();
        }
        return departmentAdminId;
    }

    public void setDepartmentAdminId(UUIDFilter departmentAdminId) {
        this.departmentAdminId = departmentAdminId;
    }

    public UUIDFilter getMembersId() {
        return membersId;
    }

    public UUIDFilter membersId() {
        if (membersId == null) {
            membersId = new UUIDFilter();
        }
        return membersId;
    }

    public void setMembersId(UUIDFilter membersId) {
        this.membersId = membersId;
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
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(departmentAdminId, that.departmentAdminId) &&
            Objects.equals(membersId, that.membersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, departmentAdminId, membersId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (departmentAdminId != null ? "departmentAdminId=" + departmentAdminId + ", " : "") +
            (membersId != null ? "membersId=" + membersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
