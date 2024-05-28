package ru.pachan.main.model.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Schema(description = "Организация")
@Table(name = "organizations")
public class Organization {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Person> person;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private long id;
}
