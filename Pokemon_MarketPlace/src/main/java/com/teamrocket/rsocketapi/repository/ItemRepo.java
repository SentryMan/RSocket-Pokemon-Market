package com.teamrocket.rsocketapi.repository;

import java.math.BigInteger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.teamrocket.rsocketapi.model.Item;

@Repository
public interface ItemRepo extends ReactiveMongoRepository<Item, BigInteger> {}
