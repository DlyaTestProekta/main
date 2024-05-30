package ru.pachan.main.model.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Schema(description = "Сотрудник")
@Table(name = "persons")
public class Person {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surname;

    @Column
    @Digits(integer = 10, fraction = 2)
    @Schema(description = "Максимум 10 знаков до и 2 знака после запятой")
    private BigDecimal salaryRub;

    @Column
    private String hobby;

    @Column(name = "fk_organization_id")
    long organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_organization_id",
            referencedColumnName = "organization_id",
            insertable = false,
            updatable = false
    )
    @JsonIgnore
    @ToString.Exclude
    private Organization organization;

    @Column(name = "fk_certificate_id", unique = true)
    long certificateId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_certificate_id",
            referencedColumnName = "certificate_id",
            insertable = false,
            updatable = false
    )
    @ToString.Exclude
    private Certificate certificate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "persons_skills",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    )
    @ToString.Exclude
    private Set<Skill> skills;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
}
