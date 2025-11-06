package ru.itmo.infsec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.infsec.entity.Account;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByLogin(String login);
    Set<Account> findAllByLoginIn(Collection<String> logins);
}
