package com.kameleoon.main;

import com.kameleoon.main.model.User;
import com.kameleoon.main.repository.QuoteRepository;
import com.kameleoon.main.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    private final QuoteRepository quoteRepository;

    public LoginController(UserRepository userRepository, QuoteRepository quoteRepository) {
        this.userRepository = userRepository;
        this.quoteRepository = quoteRepository;
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new User());
        model.addAttribute("quoteList", quoteRepository.findAll());
        return "login_page";
    }


    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String add(@RequestBody Map<String, String> data) {
            User newUser = new User();
            newUser.setLogin(data.get("login"));
            newUser.setPassword(data.get("password"));
            newUser.setRegTime(LocalDateTime.now());
            int id = newUser.getId();
            userRepository.save(newUser);
            return "redirect:/" + id +"/quotes";
    }
}
