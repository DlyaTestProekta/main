package ru.pachan.main.service.main;

import org.springframework.data.domain.Pageable;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.dto.main.PersonDto;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Person;
import ru.pachan.main.model.main.PersonQueryBulder;

import java.util.List;

public interface PersonService {

    PaginatedResponse<PersonDto> getAll(Pageable pageable, String firstName, List<String> firstNames);

    PaginatedResponse<PersonQueryBulder> getAllSqlQueryBuilder(String firstName, List<String> firstNames);

    Person getOne(long id) throws RequestException;

    Person createOne(Person person);

    Person updateOne(long id, Person person);

    void deleteOne(long id);

}
