package ru.pachan.main.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Schema(description = "Пользователь")
@Table(name = "users")
public class User {

    @Column(nullable = false, unique = true)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column
    short roleId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private Set<User> user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private long id;
}
