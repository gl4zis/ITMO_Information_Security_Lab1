package ru.itmo.infsec.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "twit")
@Data
public class Twit {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "login", nullable = false)
    private Account owner;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @ElementCollection
    @CollectionTable(
            name = "twit_tag",
            joinColumns = @JoinColumn(name = "twit_id")
    )
    @Column(name = "tag", length = 64)
    private Set<String> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "twit_mention",
            joinColumns = @JoinColumn(name = "twit_id"),
            inverseJoinColumns = @JoinColumn(name = "login", referencedColumnName = "login")
    )
    private Set<Account> mentions = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
