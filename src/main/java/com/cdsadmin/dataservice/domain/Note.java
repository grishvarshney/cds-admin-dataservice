package com.cdsadmin.dataservice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note_no")
    private String noteNo;

    @Column(name = "industry")
    private String industry;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "instrument_type")
    private String instrumentType;

    @OneToMany(mappedBy = "note")
    private Set<Merger> mergers = new HashSet<>();

    @OneToMany(mappedBy = "note")
    private Set<Transfer> transfers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("notes")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteNo() {
        return noteNo;
    }

    public Note noteNo(String noteNo) {
        this.noteNo = noteNo;
        return this;
    }

    public void setNoteNo(String noteNo) {
        this.noteNo = noteNo;
    }

    public String getIndustry() {
        return industry;
    }

    public Note industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public Note effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public Note instrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
        return this;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public Set<Merger> getMergers() {
        return mergers;
    }

    public Note mergers(Set<Merger> mergers) {
        this.mergers = mergers;
        return this;
    }

    public Note addMerger(Merger merger) {
        this.mergers.add(merger);
        merger.setNote(this);
        return this;
    }

    public Note removeMerger(Merger merger) {
        this.mergers.remove(merger);
        merger.setNote(null);
        return this;
    }

    public void setMergers(Set<Merger> mergers) {
        this.mergers = mergers;
    }

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    public Note transfers(Set<Transfer> transfers) {
        this.transfers = transfers;
        return this;
    }

    public Note addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
        transfer.setNote(this);
        return this;
    }

    public Note removeTransfer(Transfer transfer) {
        this.transfers.remove(transfer);
        transfer.setNote(null);
        return this;
    }

    public void setTransfers(Set<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Note customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return id != null && id.equals(((Note) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", noteNo='" + getNoteNo() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", instrumentType='" + getInstrumentType() + "'" +
            "}";
    }
}
