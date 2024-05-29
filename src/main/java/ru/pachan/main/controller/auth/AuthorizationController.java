package ru.pachan.main.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pachan.main.dto.auth.Authorization;
import ru.pachan.main.dto.auth.RefreshData;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.service.auth.AuthorizationService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Tag(name = "Authorization")
class AuthorizationController {
    public final AuthorizationService service;

    @Operation(
            summary = "Генерация JWT",
            description = "Генерирует JWT, если переданы верный логин и пароль"
    )
    @PostMapping("/generate")
    public ResponseEntity<RefreshData> generate(
            @RequestBody Authorization authorization
    ) throws RequestException {
        return ResponseEntity.ok(service.generate(authorization));
    }

    @Operation(
            summary = "Обновление JWT",
            description = "Обновляет JWT, по переданному старому JWT"
    )
    @GetMapping("/refresh")
    public ResponseEntity<RefreshData> refresh(
            @RequestHeader("Authorization") String authToken
    ) throws RequestException {
        return ResponseEntity.ok(service.refresh(authToken));
    }

}