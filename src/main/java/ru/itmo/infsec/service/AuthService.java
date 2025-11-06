package ru.itmo.infsec.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.itmo.infsec.dto.auth.AuthRequest;
import ru.itmo.infsec.dto.auth.AuthResponse;
import ru.itmo.infsec.entity.Account;
import ru.itmo.infsec.jwt.JwtManager;
import ru.itmo.infsec.repository.AccountRepository;

@Service
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;

    public AuthService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            JwtManager jwtManager
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtManager = jwtManager;
    }

    public AuthResponse login(AuthRequest authRequest) {
        var accountO = accountRepository.findByLogin(authRequest.getLogin());
        if (accountO.isPresent()) {
            if (passwordEncoder.matches(authRequest.getPassword(), accountO.get().getPassword())){
                return token(authRequest);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        }

        var account = new Account();
        account.setLogin(authRequest.getLogin());
        account.setPassword(encodedPassword(authRequest));
        accountRepository.save(account);

        return token(authRequest);
    }

    private AuthResponse token(AuthRequest request) {
        return new AuthResponse(jwtManager.generateToken(request.getLogin()));
    }

    private String encodedPassword(AuthRequest request) {
        return passwordEncoder.encode(request.getPassword());
    }
}
