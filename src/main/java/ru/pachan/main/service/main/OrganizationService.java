package ru.pachan.main.service.main;

import org.springframework.data.domain.Pageable;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Organization;

public interface OrganizationService {

    PaginatedResponse<Organization> getAll(Pageable pageable);

    Organization getOne(long id) throws RequestException;

    Organization createOne(Organization organization);

    Organization updateOne(long id, Organization organization);

    void deleteOne(long id);

}
