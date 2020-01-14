package com.cdsadmin.dataservice.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @OneToOne
    @JoinColumn(unique = true)
    private Systems system;

    @OneToMany(mappedBy = "customer")
    private Set<Note> notes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Customer customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Systems getSystem() {
        return system;
    }

    public Customer system(Systems systems) {
        this.system = systems;
        return this;
    }

    public void setSystem(Systems systems) {
        this.system = systems;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Customer notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Customer addNote(Note note) {
        this.notes.add(note);
        note.setCustomer(this);
        return this;
    }

    public Customer removeNote(Note note) {
        this.notes.remove(note);
        note.setCustomer(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            "}";
    }
}
