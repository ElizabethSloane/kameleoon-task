package com.kameleoon.main;

import com.kameleoon.main.model.Quote;
import com.kameleoon.main.model.QuoteVote;
import com.kameleoon.main.model.User;
import com.kameleoon.main.repository.QuoteRepository;
import com.kameleoon.main.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ApiGeneralController {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;


    public ApiGeneralController(QuoteRepository quoteRepository, UserRepository userRepository) {
        this.quoteRepository = quoteRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/quotes")
    public String quotes(Model model) {
        model.addAttribute("quote", new Quote());
        model.addAttribute("quoteList", quoteRepository.findAll());
        return "quotes";
    }

    @PostMapping(value = "/quotes", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add(@RequestBody Map<String, String> data) {
        if (data.containsKey("password") && isRegisteredUser(data.get("password")) != -1) {
            Quote newQuote = new Quote();
            newQuote.setText(data.get("text"));
            quoteRepository.save(newQuote);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<Quote> optionalQuote = quoteRepository.findById(id);

        if (!optionalQuote.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
            return new ResponseEntity(optionalQuote.get(), HttpStatus.OK);
    }


    @PatchMapping(value = "/quotes/{idOfQuote}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> patch(@RequestBody Map<String, String> data,
                                                 @PathVariable int idOfQuote) {

        Iterable<Quote> quotesOfUser = quoteRepository.findAll();
        if (data.containsKey("password") && isRegisteredUser(data.get("password")) != -1) {
            for (Quote quote : quotesOfUser) {

                if (quote.getId() == idOfQuote) {

                    if (data.containsKey("value")) {
                        List<QuoteVote> voteList = quote.getVotes();

                        QuoteVote quoteVote = new QuoteVote();
                        quoteVote.setValue(Integer.parseInt(data.get("value")));
                        quoteVote.setTime(LocalDateTime.now());

                        voteList.add(quoteVote);
                        quoteRepository.save(quote);
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                    if (data.containsKey("text")) {
                        quote.setText(data.get("text"));
                        quoteRepository.save(quote);
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/quotes/{idOfQuote}")
    public ResponseEntity<?> delete(@RequestBody Map<String, String> data,
                                    @PathVariable int idOfQuote) {
        if (data.containsKey("password") && isRegisteredUser(data.get("password")) != -1) {
            Iterable<Quote> quotesOfUser = quoteRepository.findAll();
            for (Quote quote : quotesOfUser) {
                if (quote.getId() == idOfQuote) {
                    quoteRepository.deleteById(idOfQuote);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    public Integer isRegisteredUser(String password) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getPassword(), password)) {
                return user.getId();
            }
        }
        return -1;
    }
}
