package org.adorno.learning.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "personSequence",
        sequenceName = "person_id_seq",
        allocationSize = 1,
        initialValue = 10000)
public class Person extends PanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSequence")
    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        id = newId;
    }

    public String name;
    public LocalDate birth;
    @Enumerated(EnumType.STRING)
    public Status status;


}
