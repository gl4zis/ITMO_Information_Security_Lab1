package ru.itmo.infsec.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    private String login;

    @Column(nullable = false)
    private String password;
}
