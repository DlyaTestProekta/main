package ru.pachan.main.service.main;

import org.springframework.data.domain.Pageable;
import ru.pachan.main.dto.dictionary.PaginatedResponse;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.main.Certificate;

public interface CertificateService {

    PaginatedResponse<Certificate> getAll(Pageable pageable);

    Certificate getOne(long id) throws RequestException;

    Certificate createOne(Certificate certificate);

    Certificate updateOne(long id, Certificate certificate);

    void deleteOne(long id);

}
