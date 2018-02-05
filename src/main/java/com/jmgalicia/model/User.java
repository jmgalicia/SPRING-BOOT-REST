package com.jmgalicia.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "user")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column()
    private String password;

    @Column()
    private String address;

    public User() {
    }

    public User(String email, String password, String address) {
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public long getId() {
        return this.id;
    }

    // for tests ONLY
    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address +
                '}';
    }
}
