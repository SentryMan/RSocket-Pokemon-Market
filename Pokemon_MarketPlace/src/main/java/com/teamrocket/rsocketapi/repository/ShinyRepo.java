package com.teamrocket.rsocketapi.repository;

import java.math.BigInteger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.teamrocket.rsocketapi.model.ShinyPokemon;

@Repository
public interface ShinyRepo extends ReactiveMongoRepository<ShinyPokemon, BigInteger> {}
