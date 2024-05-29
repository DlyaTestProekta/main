package ru.pachan.main.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Schema(description = "Рефреш токен")
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Column(nullable = false)
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
