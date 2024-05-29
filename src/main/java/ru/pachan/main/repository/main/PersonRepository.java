package ru.pachan.main.repository.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pachan.main.model.main.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(
            "FROM Person WHERE " +
                    "(:firstName IS NULL OR LOWER(firstName) LIKE CONCAT('%', LOWER(CAST(:firstName AS text)), '%')) AND " +
                    "(:firstNames IS NULL OR LOWER(firstName) IN (:firstNames))"
    )
    Page<Person> findAllPersonsWithFilters(
            @Param("firstName") String firstName,
            @Param("firstNames") List<String> firstNames,
            Pageable pageable
    );
}
