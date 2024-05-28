package ru.pachan.main.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pachan.main.model.main.Certificate;
import ru.pachan.main.model.main.Skill;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
