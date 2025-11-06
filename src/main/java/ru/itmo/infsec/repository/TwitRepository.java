package ru.itmo.infsec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.infsec.entity.Twit;

public interface TwitRepository extends JpaRepository<Twit, Long> {
}
