package com.cdsadmin.dataservice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

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

    @ManyToOne
    @JsonIgnoreProperties("transfers")
    private Note note;

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

    public Note getNote() {
        return note;
    }

    public Transfer note(Note note) {
        this.note = note;
        return this;
    }

    public void setNote(Note note) {
        this.note = note;
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
