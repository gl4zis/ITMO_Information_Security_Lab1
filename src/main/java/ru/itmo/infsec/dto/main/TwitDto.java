package ru.itmo.infsec.dto.main;

import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record TwitDto(
        long id,
        String author,
        String message,
        Set<String> tags,
        Instant createdAt
) {

    public TwitDto {
        tags = Set.copyOf(tags);
    }

    @Override
    public String author() {
        return HtmlUtils.htmlEscape(author);
    }

    @Override
    public Set<String> tags() {
        return tags.stream()
                .map(HtmlUtils::htmlEscape)
                .collect(Collectors.toSet());
    }

    @Override
    public String message() {
        return HtmlUtils.htmlEscape(message);
    }
}
