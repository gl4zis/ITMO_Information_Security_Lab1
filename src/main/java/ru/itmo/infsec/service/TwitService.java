package ru.itmo.infsec.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.itmo.infsec.dto.main.TwitDto;
import ru.itmo.infsec.dto.main.TwitRequest;
import ru.itmo.infsec.dto.main.TwitsResponse;
import ru.itmo.infsec.entity.Account;
import ru.itmo.infsec.entity.Twit;
import ru.itmo.infsec.repository.AccountRepository;
import ru.itmo.infsec.repository.TwitRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TwitService {

    private final TwitRepository twitRepository;
    private final AccountRepository accountRepository;

    public TwitService(
            TwitRepository twitRepository,
            AccountRepository accountRepository
    ) {
        this.twitRepository = twitRepository;
        this.accountRepository = accountRepository;
    }

    public TwitsResponse getTwits() {
        List<Twit> twits = twitRepository.findAll();
        List<TwitDto> dtos = twits.stream().map(this::mapToDto).toList();
        return new TwitsResponse(dtos);
    }

    public void addTwit(TwitRequest request, String user) {
        var twit = new Twit();
        twit.setMessage(request.getMessage());
        twit.setTags(request.getTags());

        var ownerO = accountRepository.findByLogin(user);
        Set<Account> mentions = accountRepository.findAllByLoginIn(request.getMentions());
        if (ownerO.isEmpty() || mentions.size() != request.getMentions().size()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "No such users");
        }

        twit.setOwner(ownerO.get());
        twit.setMentions(mentions);
        twitRepository.save(twit);
    }

    private TwitDto mapToDto(Twit twit) {
        return new TwitDto(
                twit.getId(),
                twit.getOwner().getLogin(),
                getLogins(twit.getMentions()),
                twit.getTags(),
                twit.getMessage(),
                twit.getCreatedAt()
        );
    }

    private Set<String> getLogins(Collection<Account> accounts) {
        return accounts.stream().map(Account::getLogin).collect(Collectors.toSet());
    }
}
