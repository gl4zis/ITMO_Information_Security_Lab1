package ru.itmo.infsec.service;

import org.springframework.stereotype.Service;
import ru.itmo.infsec.dto.main.TwitDto;
import ru.itmo.infsec.dto.main.TwitRequest;
import ru.itmo.infsec.dto.main.TwitsResponse;
import ru.itmo.infsec.entity.Twit;
import ru.itmo.infsec.repository.AccountRepository;
import ru.itmo.infsec.repository.TwitRepository;

import java.util.List;

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

        var owner = accountRepository.findByLogin(user)
                        .orElseThrow(() -> new RuntimeException("Unexpected error"));

        twit.setOwner(owner);
        twitRepository.save(twit);
    }

    private TwitDto mapToDto(Twit twit) {
        return new TwitDto(
                twit.getId(),
                twit.getOwner().getLogin(),
                twit.getTags(),
                twit.getMessage(),
                twit.getCreatedAt()
        );
    }
}
