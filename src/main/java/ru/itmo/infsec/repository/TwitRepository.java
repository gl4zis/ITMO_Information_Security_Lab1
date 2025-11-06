package ru.itmo.infsec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.infsec.entity.Twit;

@Repository
public interface TwitRepository extends JpaRepository<Twit, Long> {
}
