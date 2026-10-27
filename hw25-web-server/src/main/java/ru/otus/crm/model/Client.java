package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "client", orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        setPhones(phones);
    }

    public Client(String name, Address address, List<Phone> phones) {
        this.name = name;
        this.address = address;
        setPhones(phones);
    }

    public void setPhones(List<Phone> phones) {
        this.phones = new ArrayList<>();
        if (phones != null) {
            phones.forEach(this::addPhone);
        }
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setClient(this);
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        return new Client(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
