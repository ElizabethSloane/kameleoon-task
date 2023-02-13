package com.kameleoon.main.repository;

import com.kameleoon.main.model.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Integer> {
}
