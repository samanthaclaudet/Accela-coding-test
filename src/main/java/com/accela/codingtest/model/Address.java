package com.accela.codingtest.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Address entity.
 * An address is defined by a street, a city, a state and a postal code.
 * It belongs to a Person
 * @see Person
 */

@Entity
@Table(name="address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "street", nullable = false)
    @NotBlank(message = "Street is mandatory")
    private String street;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City is mandatory")
    private String city;

    @Column(name = "state", nullable = false)
    @NotBlank(message = "State is mandatory")
    private String state;

    @Column(name = "postal_code", nullable = false)
    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp="\\d{5}", message = "Postal code must be 5 digits")
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person person;

    public Address() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "address: " + street + ", " + postalCode + " " + city + " (" + state + ")";
    }
}
