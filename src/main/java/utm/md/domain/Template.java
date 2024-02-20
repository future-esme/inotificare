package utm.md.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body_ro")
    private String bodyRo;

    @Column(name = "body_ru")
    private String bodyRu;

    @Column(name = "body_en")
    private String bodyEn;

    @Column(name = "body_short_ro")
    private String bodyShortRo;

    @Column(name = "body_short_ru")
    private String bodyShortRu;

    @Column(name = "body_short_en")
    private String bodyShortEn;

    @Column(name = "subject_ro")
    private String subjectRo;

    @Column(name = "subject_ru")
    private String subjectRu;

    @Column(name = "subject_en")
    private String subjectEn;

    @JsonIgnoreProperties(value = { "templateId", "notification" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "templateId")
    private RequestData requestData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Template id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Template title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyRo() {
        return this.bodyRo;
    }

    public Template bodyRo(String bodyRo) {
        this.setBodyRo(bodyRo);
        return this;
    }

    public void setBodyRo(String bodyRo) {
        this.bodyRo = bodyRo;
    }

    public String getBodyRu() {
        return this.bodyRu;
    }

    public Template bodyRu(String bodyRu) {
        this.setBodyRu(bodyRu);
        return this;
    }

    public void setBodyRu(String bodyRu) {
        this.bodyRu = bodyRu;
    }

    public String getBodyEn() {
        return this.bodyEn;
    }

    public Template bodyEn(String bodyEn) {
        this.setBodyEn(bodyEn);
        return this;
    }

    public void setBodyEn(String bodyEn) {
        this.bodyEn = bodyEn;
    }

    public String getBodyShortRo() {
        return this.bodyShortRo;
    }

    public Template bodyShortRo(String bodyShortRo) {
        this.setBodyShortRo(bodyShortRo);
        return this;
    }

    public void setBodyShortRo(String bodyShortRo) {
        this.bodyShortRo = bodyShortRo;
    }

    public String getBodyShortRu() {
        return this.bodyShortRu;
    }

    public Template bodyShortRu(String bodyShortRu) {
        this.setBodyShortRu(bodyShortRu);
        return this;
    }

    public void setBodyShortRu(String bodyShortRu) {
        this.bodyShortRu = bodyShortRu;
    }

    public String getBodyShortEn() {
        return this.bodyShortEn;
    }

    public Template bodyShortEn(String bodyShortEn) {
        this.setBodyShortEn(bodyShortEn);
        return this;
    }

    public void setBodyShortEn(String bodyShortEn) {
        this.bodyShortEn = bodyShortEn;
    }

    public String getSubjectRo() {
        return this.subjectRo;
    }

    public Template subjectRo(String subjectRo) {
        this.setSubjectRo(subjectRo);
        return this;
    }

    public void setSubjectRo(String subjectRo) {
        this.subjectRo = subjectRo;
    }

    public String getSubjectRu() {
        return this.subjectRu;
    }

    public Template subjectRu(String subjectRu) {
        this.setSubjectRu(subjectRu);
        return this;
    }

    public void setSubjectRu(String subjectRu) {
        this.subjectRu = subjectRu;
    }

    public String getSubjectEn() {
        return this.subjectEn;
    }

    public Template subjectEn(String subjectEn) {
        this.setSubjectEn(subjectEn);
        return this;
    }

    public void setSubjectEn(String subjectEn) {
        this.subjectEn = subjectEn;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        if (this.requestData != null) {
            this.requestData.setTemplateId(null);
        }
        if (requestData != null) {
            requestData.setTemplateId(this);
        }
        this.requestData = requestData;
    }

    public Template requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Template)) {
            return false;
        }
        return getId() != null && getId().equals(((Template) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", bodyRo='" + getBodyRo() + "'" +
            ", bodyRu='" + getBodyRu() + "'" +
            ", bodyEn='" + getBodyEn() + "'" +
            ", bodyShortRo='" + getBodyShortRo() + "'" +
            ", bodyShortRu='" + getBodyShortRu() + "'" +
            ", bodyShortEn='" + getBodyShortEn() + "'" +
            ", subjectRo='" + getSubjectRo() + "'" +
            ", subjectRu='" + getSubjectRu() + "'" +
            ", subjectEn='" + getSubjectEn() + "'" +
            "}";
    }
}
