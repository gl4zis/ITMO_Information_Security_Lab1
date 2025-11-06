package ru.itmo.infsec.dto.main;

import java.time.Instant;
import java.util.Set;

public record TwitDto(
        long id,
        String author,
        Set<String> mentions,
        Set<String> tags,
        String message,
        Instant createdAt
) {

    public TwitDto {
        mentions = Set.copyOf(mentions);
        tags = Set.copyOf(tags);
    }

    @Override
    public Set<String> mentions() {
        return Set.copyOf(mentions);
    }

    @Override
    public Set<String> tags() {
        return Set.copyOf(tags);
    }
}
