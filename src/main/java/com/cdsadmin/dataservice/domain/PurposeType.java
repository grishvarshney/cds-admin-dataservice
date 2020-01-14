package com.cdsadmin.dataservice.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PurposeType.
 */
@Entity
@Table(name = "purpose_type")
public class PurposeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purpose_type_code")
    private String purposeTypeCode;

    @Column(name = "purpose_type_description")
    private String purposeTypeDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurposeTypeCode() {
        return purposeTypeCode;
    }

    public PurposeType purposeTypeCode(String purposeTypeCode) {
        this.purposeTypeCode = purposeTypeCode;
        return this;
    }

    public void setPurposeTypeCode(String purposeTypeCode) {
        this.purposeTypeCode = purposeTypeCode;
    }

    public String getPurposeTypeDescription() {
        return purposeTypeDescription;
    }

    public PurposeType purposeTypeDescription(String purposeTypeDescription) {
        this.purposeTypeDescription = purposeTypeDescription;
        return this;
    }

    public void setPurposeTypeDescription(String purposeTypeDescription) {
        this.purposeTypeDescription = purposeTypeDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurposeType)) {
            return false;
        }
        return id != null && id.equals(((PurposeType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PurposeType{" +
            "id=" + getId() +
            ", purposeTypeCode='" + getPurposeTypeCode() + "'" +
            ", purposeTypeDescription='" + getPurposeTypeDescription() + "'" +
            "}";
    }
}
