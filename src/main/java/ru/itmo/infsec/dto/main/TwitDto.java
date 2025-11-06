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
) { }
