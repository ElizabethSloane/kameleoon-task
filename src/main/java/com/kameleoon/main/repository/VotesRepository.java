package com.kameleoon.main.repository;

import com.kameleoon.main.model.QuoteVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesRepository extends CrudRepository<QuoteVote, Integer> {
}
