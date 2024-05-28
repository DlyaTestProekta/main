package ru.pachan.main.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pachan.main.model.main.Person;
import ru.pachan.main.model.main.Skill;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
