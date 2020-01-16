package com.cdsadmin.dataservice.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "systems")
    private Set<Merger> mergers = new HashSet<>();

    @OneToMany(mappedBy = "systems")
    private Set<Transfer> transfers = new HashSet<>();

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

    public Set<Merger> getMergers() {
        return mergers;
    }

    public Systems mergers(Set<Merger> mergers) {
        this.mergers = mergers;
        return this;
    }

    public Systems addMerger(Merger merger) {
        this.mergers.add(merger);
        merger.setSystems(this);
        return this;
    }

    public Systems removeMerger(Merger merger) {
        this.mergers.remove(merger);
        merger.setSystems(null);
        return this;
    }

    public void setMergers(Set<Merger> mergers) {
        this.mergers = mergers;
    }

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    public Systems transfers(Set<Transfer> transfers) {
        this.transfers = transfers;
        return this;
    }

    public Systems addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
        transfer.setSystems(this);
        return this;
    }

    public Systems removeTransfer(Transfer transfer) {
        this.transfers.remove(transfer);
        transfer.setSystems(null);
        return this;
    }

    public void setTransfers(Set<Transfer> transfers) {
        this.transfers = transfers;
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
