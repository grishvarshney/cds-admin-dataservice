package com.cdsadmin.dataservice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Systems.
 */
@Entity
@Table(name = "systems")
public class Systems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "systems_entity")
    private String systemsEntity;

    @ManyToOne
    @JsonIgnoreProperties("systems")
    private Merger merger;

    @ManyToOne
    @JsonIgnoreProperties("systems")
    private Transfer transfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemsEntity() {
        return systemsEntity;
    }

    public Systems systemsEntity(String systemsEntity) {
        this.systemsEntity = systemsEntity;
        return this;
    }

    public void setSystemsEntity(String systemsEntity) {
        this.systemsEntity = systemsEntity;
    }

    public Merger getMerger() {
        return merger;
    }

    public Systems merger(Merger merger) {
        this.merger = merger;
        return this;
    }

    public void setMerger(Merger merger) {
        this.merger = merger;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public Systems transfer(Transfer transfer) {
        this.transfer = transfer;
        return this;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Systems)) {
            return false;
        }
        return id != null && id.equals(((Systems) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Systems{" +
            "id=" + getId() +
            ", systemsEntity='" + getSystemsEntity() + "'" +
            "}";
    }
}
