package utm.md.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link utm.md.domain.Template} entity. This class is used
 * in {@link utm.md.web.rest.TemplateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter title;

    private StringFilter bodyRo;

    private StringFilter bodyRu;

    private StringFilter bodyEn;

    private StringFilter bodyShortRo;

    private StringFilter bodyShortRu;

    private StringFilter bodyShortEn;

    private StringFilter subjectRo;

    private StringFilter subjectRu;

    private StringFilter subjectEn;

    private UUIDFilter requestDataId;

    private Boolean distinct;

    public TemplateCriteria() {}

    public TemplateCriteria(TemplateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.bodyRo = other.bodyRo == null ? null : other.bodyRo.copy();
        this.bodyRu = other.bodyRu == null ? null : other.bodyRu.copy();
        this.bodyEn = other.bodyEn == null ? null : other.bodyEn.copy();
        this.bodyShortRo = other.bodyShortRo == null ? null : other.bodyShortRo.copy();
        this.bodyShortRu = other.bodyShortRu == null ? null : other.bodyShortRu.copy();
        this.bodyShortEn = other.bodyShortEn == null ? null : other.bodyShortEn.copy();
        this.subjectRo = other.subjectRo == null ? null : other.subjectRo.copy();
        this.subjectRu = other.subjectRu == null ? null : other.subjectRu.copy();
        this.subjectEn = other.subjectEn == null ? null : other.subjectEn.copy();
        this.requestDataId = other.requestDataId == null ? null : other.requestDataId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TemplateCriteria copy() {
        return new TemplateCriteria(this);
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

    public StringFilter getBodyRo() {
        return bodyRo;
    }

    public StringFilter bodyRo() {
        if (bodyRo == null) {
            bodyRo = new StringFilter();
        }
        return bodyRo;
    }

    public void setBodyRo(StringFilter bodyRo) {
        this.bodyRo = bodyRo;
    }

    public StringFilter getBodyRu() {
        return bodyRu;
    }

    public StringFilter bodyRu() {
        if (bodyRu == null) {
            bodyRu = new StringFilter();
        }
        return bodyRu;
    }

    public void setBodyRu(StringFilter bodyRu) {
        this.bodyRu = bodyRu;
    }

    public StringFilter getBodyEn() {
        return bodyEn;
    }

    public StringFilter bodyEn() {
        if (bodyEn == null) {
            bodyEn = new StringFilter();
        }
        return bodyEn;
    }

    public void setBodyEn(StringFilter bodyEn) {
        this.bodyEn = bodyEn;
    }

    public StringFilter getBodyShortRo() {
        return bodyShortRo;
    }

    public StringFilter bodyShortRo() {
        if (bodyShortRo == null) {
            bodyShortRo = new StringFilter();
        }
        return bodyShortRo;
    }

    public void setBodyShortRo(StringFilter bodyShortRo) {
        this.bodyShortRo = bodyShortRo;
    }

    public StringFilter getBodyShortRu() {
        return bodyShortRu;
    }

    public StringFilter bodyShortRu() {
        if (bodyShortRu == null) {
            bodyShortRu = new StringFilter();
        }
        return bodyShortRu;
    }

    public void setBodyShortRu(StringFilter bodyShortRu) {
        this.bodyShortRu = bodyShortRu;
    }

    public StringFilter getBodyShortEn() {
        return bodyShortEn;
    }

    public StringFilter bodyShortEn() {
        if (bodyShortEn == null) {
            bodyShortEn = new StringFilter();
        }
        return bodyShortEn;
    }

    public void setBodyShortEn(StringFilter bodyShortEn) {
        this.bodyShortEn = bodyShortEn;
    }

    public StringFilter getSubjectRo() {
        return subjectRo;
    }

    public StringFilter subjectRo() {
        if (subjectRo == null) {
            subjectRo = new StringFilter();
        }
        return subjectRo;
    }

    public void setSubjectRo(StringFilter subjectRo) {
        this.subjectRo = subjectRo;
    }

    public StringFilter getSubjectRu() {
        return subjectRu;
    }

    public StringFilter subjectRu() {
        if (subjectRu == null) {
            subjectRu = new StringFilter();
        }
        return subjectRu;
    }

    public void setSubjectRu(StringFilter subjectRu) {
        this.subjectRu = subjectRu;
    }

    public StringFilter getSubjectEn() {
        return subjectEn;
    }

    public StringFilter subjectEn() {
        if (subjectEn == null) {
            subjectEn = new StringFilter();
        }
        return subjectEn;
    }

    public void setSubjectEn(StringFilter subjectEn) {
        this.subjectEn = subjectEn;
    }

    public UUIDFilter getRequestDataId() {
        return requestDataId;
    }

    public UUIDFilter requestDataId() {
        if (requestDataId == null) {
            requestDataId = new UUIDFilter();
        }
        return requestDataId;
    }

    public void setRequestDataId(UUIDFilter requestDataId) {
        this.requestDataId = requestDataId;
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
        final TemplateCriteria that = (TemplateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(bodyRo, that.bodyRo) &&
            Objects.equals(bodyRu, that.bodyRu) &&
            Objects.equals(bodyEn, that.bodyEn) &&
            Objects.equals(bodyShortRo, that.bodyShortRo) &&
            Objects.equals(bodyShortRu, that.bodyShortRu) &&
            Objects.equals(bodyShortEn, that.bodyShortEn) &&
            Objects.equals(subjectRo, that.subjectRo) &&
            Objects.equals(subjectRu, that.subjectRu) &&
            Objects.equals(subjectEn, that.subjectEn) &&
            Objects.equals(requestDataId, that.requestDataId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            bodyRo,
            bodyRu,
            bodyEn,
            bodyShortRo,
            bodyShortRu,
            bodyShortEn,
            subjectRo,
            subjectRu,
            subjectEn,
            requestDataId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (bodyRo != null ? "bodyRo=" + bodyRo + ", " : "") +
            (bodyRu != null ? "bodyRu=" + bodyRu + ", " : "") +
            (bodyEn != null ? "bodyEn=" + bodyEn + ", " : "") +
            (bodyShortRo != null ? "bodyShortRo=" + bodyShortRo + ", " : "") +
            (bodyShortRu != null ? "bodyShortRu=" + bodyShortRu + ", " : "") +
            (bodyShortEn != null ? "bodyShortEn=" + bodyShortEn + ", " : "") +
            (subjectRo != null ? "subjectRo=" + subjectRo + ", " : "") +
            (subjectRu != null ? "subjectRu=" + subjectRu + ", " : "") +
            (subjectEn != null ? "subjectEn=" + subjectEn + ", " : "") +
            (requestDataId != null ? "requestDataId=" + requestDataId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
