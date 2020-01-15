package com.cdsadmin.dataservice.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "customer_from")
    private Long customerFrom;

    @Column(name = "customer_to")
    private Long customerTo;

    @Column(name = "action")
    private String action;

    @Column(name = "transferring_entity")
    private String transferringEntity;

    @Column(name = "time_stamp")
    private ZonedDateTime timeStamp;

    @OneToMany(mappedBy = "transfer")
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "transfer")
    private Set<Systems> systems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public Transfer effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Long getCustomerFrom() {
        return customerFrom;
    }

    public Transfer customerFrom(Long customerFrom) {
        this.customerFrom = customerFrom;
        return this;
    }

    public void setCustomerFrom(Long customerFrom) {
        this.customerFrom = customerFrom;
    }

    public Long getCustomerTo() {
        return customerTo;
    }

    public Transfer customerTo(Long customerTo) {
        this.customerTo = customerTo;
        return this;
    }

    public void setCustomerTo(Long customerTo) {
        this.customerTo = customerTo;
    }

    public String getAction() {
        return action;
    }

    public Transfer action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTransferringEntity() {
        return transferringEntity;
    }

    public Transfer transferringEntity(String transferringEntity) {
        this.transferringEntity = transferringEntity;
        return this;
    }

    public void setTransferringEntity(String transferringEntity) {
        this.transferringEntity = transferringEntity;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Transfer timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Transfer notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Transfer addNote(Note note) {
        this.notes.add(note);
        note.setTransfer(this);
        return this;
    }

    public Transfer removeNote(Note note) {
        this.notes.remove(note);
        note.setTransfer(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<Systems> getSystems() {
        return systems;
    }

    public Transfer systems(Set<Systems> systems) {
        this.systems = systems;
        return this;
    }

    public Transfer addSystems(Systems systems) {
        this.systems.add(systems);
        systems.setTransfer(this);
        return this;
    }

    public Transfer removeSystems(Systems systems) {
        this.systems.remove(systems);
        systems.setTransfer(null);
        return this;
    }

    public void setSystems(Set<Systems> systems) {
        this.systems = systems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfer)) {
            return false;
        }
        return id != null && id.equals(((Transfer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transfer{" +
            "id=" + getId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", customerFrom=" + getCustomerFrom() +
            ", customerTo=" + getCustomerTo() +
            ", action='" + getAction() + "'" +
            ", transferringEntity='" + getTransferringEntity() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            "}";
    }
}
