package ru.pachan.main.service.main;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.dto.main.PersonDTO;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Person;

import java.util.List;

public interface PersonService {

    @Transactional
    PaginatedResponse<PersonDTO> getAll(Pageable pageable, String firstName, List<String> firstNames);

    Person getOne(long id) throws RequestException;

    Person createOne(Person person);

    Person updateOne(long id, Person person);

    void deleteOne(long id);

}
