package ru.pachan.main.model.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Schema(description = "Удостоверение")
@Table(name = "certificates")
public class Certificate {

    @Column(nullable = false)
    private String code;

    @OneToOne(mappedBy = "certificate", optional = false, cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Person person;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private long id;
}
