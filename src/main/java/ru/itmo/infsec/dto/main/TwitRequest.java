package ru.itmo.infsec.dto.main;

import lombok.Data;

import java.util.Set;

@Data
public class TwitRequest {
    private String message;
    private Set<String> tags = Set.of();
    private Set<String> mentions = Set.of();
}
