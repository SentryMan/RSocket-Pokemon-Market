package com.teamrocket.rsocketapi.controllers;

import javax.mail.MessagingException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import com.teamrocket.rsocketapi.dto.ItemDTO;
import com.teamrocket.rsocketapi.dto.PokemonDTO;
import com.teamrocket.rsocketapi.dto.ShinyDTO;
import com.teamrocket.rsocketapi.model.MailModel;
import com.teamrocket.rsocketapi.model.SetupPayload;
import com.teamrocket.rsocketapi.services.ItemService;
import com.teamrocket.rsocketapi.services.MailService;
import com.teamrocket.rsocketapi.services.PokeService;
import com.teamrocket.rsocketapi.services.ShinyService;
import io.rsocket.exceptions.RejectedSetupException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class RsocketController {

  private final PokeService pokeService;
  private final ItemService itemService;
  private final ShinyService shinyService;
  private final MailService mailService;

  @MessageExceptionHandler(MethodArgumentResolutionException.class)
  public Mono<RejectedSetupException> inavlidPayload() {

    return Mono.error(new RejectedSetupException("Invalid Setup Payload"));
  }

  @ConnectMapping
  public Mono<Void> onConnect(
      RSocketRequester rSocketRequester, @Payload SetupPayload setupPayload) {

    if (!setupPayload.getPassword().contains("SECRET"))
      return Mono.error(
          new RejectedSetupException(
              "Connection to " + setupPayload.getClientName() + " is not authenticated"));
    else
      rSocketRequester
          .rsocket()
          .onClose()
          .doFirst(
              () -> System.out.println("Client: " + setupPayload.getClientName() + " CONNECTED."))
          .doOnError(
              error ->
                  // Warn when channels are closed by clients
                  System.out.println(
                      "Channel to client " + setupPayload.getClientName() + " CLOSED"))
          .doFinally(
              f -> System.out.println("Client " + setupPayload.getClientName() + " DISCONNECTED"))
          .subscribe(
              null,
              error -> System.err.println("Client Closed With Error"),
              () -> System.out.println("Connection Closed"));

    return Mono.empty();
  }

  @MessageMapping("api.pokemon")
  public Flux<PokemonDTO> streamPoke(String r) {

    return pokeService.getAll();
  }

  @MessageMapping("api.shiny")
  public Flux<ShinyDTO> streamShiny() {

    return shinyService.getAll();
  }

  @MessageMapping("api.items")
  public Flux<ItemDTO> streamItem() {

    return itemService.getAll();
  }

  @MessageMapping("api.email")
  public void sendEmail(MailModel email) throws MessagingException {

    mailService.sendEmail(email);
  }
}
