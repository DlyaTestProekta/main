package ru.pachan.main.service.main;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.dto.auth.main.PersonDTO;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Person;
import ru.pachan.main.repository.main.PersonRepository;

import java.util.List;

import static ru.pachan.main.util.enums.ExceptionEnum.OBJECT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Override
    public PaginatedResponse<PersonDTO> getAll(Pageable pageable, String firstName, List<String> firstNames) {
        Page<Person> persons = repository.findAllPersonsWithFilters(firstName, firstNames, pageable);

        List<PersonDTO> result = persons.stream().map(
                it -> new PersonDTO(it.getId(), it.getFirstName(), it.getSurname(), it.getOrganization().getName())
        ).toList();

        return new PaginatedResponse<>(persons.getTotalElements(), result);
    }

    @Override
    public Person getOne(long id) throws RequestException {
        return repository.findById(id).orElseThrow(() ->
                new RequestException(OBJECT_NOT_FOUND.getMessage(), HttpStatus.GONE));

    }

    @Override
    public Person createOne(Person person) {
        return repository.save(person);
    }

    @Override
    public Person updateOne(long id, Person person) {
        person.setId(id);
        return repository.save(person);
    }

    @Override
    public void deleteOne(long id) {
        repository.deleteById(id);
    }
}
