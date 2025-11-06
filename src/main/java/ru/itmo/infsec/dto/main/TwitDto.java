package ru.itmo.infsec.dto.main;

import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record TwitDto(
        long id,
        String author,
        Set<String> tags,
        String message,
        Instant createdAt
) {

    public TwitDto {
        tags = Set.copyOf(tags);
    }

    @Override
    public String author() {
        return author == null ? null : HtmlUtils.htmlEscape(author);
    }

    @Override
    public Set<String> tags() {
        return tags.stream()
                .filter(tag -> !Objects.isNull(tag))
                .map(HtmlUtils::htmlEscape)
                .collect(Collectors.toSet());
    }

    @Override
    public String message() {
        return message == null ? null : HtmlUtils.htmlEscape(message);
    }
}
