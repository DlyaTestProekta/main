package ru.pachan.main.controller.main;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.dto.auth.main.PersonDTO;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Person;
import ru.pachan.main.service.main.PersonService;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/main/person")
@Tag(name = "Person")
public class PersonController {

    private final PersonService service;

    @Operation(summary = "Возвращение всех с фильтрацией")
    @GetMapping
    public ResponseEntity<PaginatedResponse<PersonDTO>> getAll(
            @ParameterObject Pageable pageable,
            @Parameter(description = "Фильтр по имени сотрудника")
            @RequestParam(required = false) String firstName,
            @Parameter(description = "Фильтр по именам сотрудника")
            @RequestParam(required = false) List<String> firstNames
    ) {
        return ResponseEntity.ok(service.getAll(pageable, firstName, firstNames));
    }

    @Operation(summary = "Возвращение по переданному id")
    @GetMapping("/{id}")
    public ResponseEntity<Person> getOne(
            @PathVariable long id
    ) throws RequestException {
        return ResponseEntity.ok(service.getOne(id));
    }

    @Operation(summary = "Создание")
    @PostMapping
    public ResponseEntity<Person> createOne(
            @Valid @RequestBody Person person
    ) {
        return ResponseEntity.ok(service.createOne(person));
    }

    @Operation(summary = "Обновление")
    @PutMapping("/{id}")
    public ResponseEntity<Person> updateOne(
            @PathVariable long id,
            @Valid @RequestBody Person person
    ) {
        return ResponseEntity.ok(service.updateOne(id, person));
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "Удаление по переданному id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(
            @PathVariable long id
    ) {
        try {
            service.deleteOne(id);
            return ResponseEntity.status(NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(NO_CONTENT).build();
        }

    }
}