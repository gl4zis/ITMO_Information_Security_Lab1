package ru.itmo.infsec.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itmo.infsec.dto.main.TwitRequest;
import ru.itmo.infsec.dto.main.TwitsResponse;
import ru.itmo.infsec.service.TwitService;

@RestController
@RequestMapping("/api")
public class TwitController {

    private final TwitService twitService;

    public TwitController(TwitService twitService) {
        this.twitService = twitService;
    }

    @GetMapping("/data")
    public TwitsResponse getTwits() {
        return twitService.getTwits();
    }

    @PostMapping("/twit")
    public void twit(@RequestBody TwitRequest request, @AuthenticationPrincipal UserDetails user) {
        twitService.addTwit(request, user.getUsername());
    }
}
