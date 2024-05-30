package ru.pachan.main.service.main;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Organization;
import ru.pachan.main.repository.main.OrganizationRepository;

import static ru.pachan.main.util.enums.ExceptionEnum.OBJECT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    public PaginatedResponse<Organization> getAll(Pageable pageable) {
        Page<Organization> result = repository.findAll(pageable);

        return new PaginatedResponse<>(result.getTotalElements(), result.getContent());
    }

    @Override
    public Organization getOne(long id) throws RequestException {
        return repository.findById(id).orElseThrow(() ->
                new RequestException(OBJECT_NOT_FOUND.getMessage(), HttpStatus.GONE));

    }

    @Override
    public Organization createOne(Organization organization) {
        return repository.save(organization);
    }

    @Override
    public Organization updateOne(long id, Organization organization) {
        organization.setId(id);
        return repository.save(organization);
    }

    @Override
    public void deleteOne(long id) {
        repository.deleteById(id);
    }
}
