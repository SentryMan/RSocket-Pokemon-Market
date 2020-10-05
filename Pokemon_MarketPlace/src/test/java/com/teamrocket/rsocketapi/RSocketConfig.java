package com.teamrocket.rsocketapi;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Profile("test")
@Configuration
public class RSocketConfig {

  @Bean
  public Mono<RSocketRequester> getRSocketRequester(RSocketRequester.Builder builder)
      throws URISyntaxException {

    return builder
        .rsocketConnector(
            rSocketConnector ->
                rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
        .dataMimeType(MediaType.APPLICATION_JSON)
        .setupData("SECRET")
        .connectWebSocket(new URI("ws://localhost:6565/rsocket"));
  }
}
