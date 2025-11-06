package ru.itmo.infsec.dto.main;

import java.util.List;

public record TwitsResponse(List<TwitDto> twits) {

    public TwitsResponse {
        twits = List.copyOf(twits);
    }

    @Override
    public List<TwitDto> twits() {
        return List.copyOf(twits);
    }
}
