package com.teamrocket.rsocketapi;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.teamrocket.rsocketapi.dto.ItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PokemonMarketPlaceApplicationTests {

  @Autowired private Mono<RSocketRequester> rSocketRequester;
  

  //@Test
  public void testItemRoute() {
    
    Flux<ItemDTO> requestMono =
        this.rSocketRequester
            .map(r -> r.route("api.items"))
            .flatMapMany(r -> r.retrieveFlux(ItemDTO.class))
            .doOnNext(r -> System.out.println(r))
            .doOnError(r -> System.err.println(r));

    StepVerifier.create(requestMono.collectList()).expectNextCount(0);
  }
}
