package com.kameleoon.main;

import com.kameleoon.main.model.Quote;
import com.kameleoon.main.model.QuoteVote;
import com.kameleoon.main.repository.QuoteRepository;
import com.kameleoon.main.repository.UserRepository;
import com.kameleoon.main.repository.VotesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Controller
public class ApiGeneralController {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;

    private final VotesRepository votesRepository;

    public ApiGeneralController(QuoteRepository quoteRepository, UserRepository userRepository, VotesRepository votesRepository) {
        this.quoteRepository = quoteRepository;
        this.userRepository = userRepository;
        this.votesRepository = votesRepository;
    }


    @GetMapping("/quotes")
    public String quotes(Model model) {
        model.addAttribute("quote", new Quote());
        model.addAttribute("quoteList", quoteRepository.findAll());
        return "quotes";
    }

    @PostMapping(value = "{idOfUser}/quotes", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add(@RequestBody Map<String, String> data,
                                 @PathVariable int idOfUser
    ) {

            Quote newQuote = new Quote();
            newQuote.setText(data.get("text"));
            newQuote.setUserId(idOfUser);
            quoteRepository.save(newQuote);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{idOfUser}/quotes/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<Quote> optionalQuote = quoteRepository.findById(id);

        if (!optionalQuote.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
            return new ResponseEntity(optionalQuote.get(), HttpStatus.OK);
    }


    @PatchMapping(value = "{idOfUser}/quotes/{idOfQuote}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> patch(@RequestBody Map<String, String> data,
                                                 @PathVariable int idOfUser,
                                                 @PathVariable int idOfQuote) {

        if (data.containsKey("value")) {
            Iterable<QuoteVote> quoteVotes = votesRepository.findAll();
            for (QuoteVote quoteVote : quoteVotes) {
                if (quoteVote.getQuoteId() == idOfQuote) {
                    quoteVote.setValue(Integer.parseInt(data.get("value")));
                    quoteVote.setUserId(idOfUser);
                    quoteVote.setTime(LocalDateTime.now());
                    votesRepository.save(quoteVote);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        if (data.containsKey("text")) {
            Iterable<Quote> quotesOfUser = quoteRepository.findAll();
            for (Quote quote : quotesOfUser) {
                if (quote.getId() == idOfQuote && quote.getUserId() == idOfUser) {
                        quote.setText(data.get("text"));
                        quoteRepository.save(quote);
                        return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("{idOfUser}/quotes/{idOfQuote}")
    public ResponseEntity<?> delete(@PathVariable int idOfUser,
                                    @PathVariable int idOfQuote) {

        Iterable<Quote> quotesOfUser = quoteRepository.findAll();
        for (Quote quote : quotesOfUser) {
            if (quote.getId() == idOfQuote && quote.getUserId() == idOfUser) {
                quoteRepository.deleteById(idOfQuote);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
