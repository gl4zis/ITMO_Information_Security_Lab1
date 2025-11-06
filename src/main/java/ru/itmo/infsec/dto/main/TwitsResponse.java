package ru.itmo.infsec.dto.main;

import java.util.List;

public record TwitsResponse(List<TwitDto> twits) {
}
