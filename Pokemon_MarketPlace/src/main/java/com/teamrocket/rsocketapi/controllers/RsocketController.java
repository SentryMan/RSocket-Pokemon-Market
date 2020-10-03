package com.teamrocket.rsocketapi.controllers;

import javax.mail.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import com.teamrocket.rsocketapi.dto.ItemDTO;
import com.teamrocket.rsocketapi.dto.PokemonDTO;
import com.teamrocket.rsocketapi.dto.ShinyDTO;
import com.teamrocket.rsocketapi.model.MailModel;
import com.teamrocket.rsocketapi.services.ItemService;
import com.teamrocket.rsocketapi.services.MailService;
import com.teamrocket.rsocketapi.services.PokeService;
import com.teamrocket.rsocketapi.services.ShinyService;
import io.rsocket.RSocket;
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

  @ConnectMapping
  public void onConnect(RSocketRequester rSocketRequester, @Payload String setupPayload) {

    final RSocket requestSocket = rSocketRequester.rsocket();
    if (!setupPayload.contains("\"SECRET\"")) requestSocket.dispose();
    else
      requestSocket
          .onClose()
          .doFirst(() -> System.out.println("Client Connected "))
          .subscribe(
              null,
              error -> System.err.println("Client Closed With Error"),
              () -> System.out.println("Connection Closed"));
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
  public Mono<String> sendEmail(MailModel email) throws MessagingException {

    return mailService.sendEmail(email);
  }
}
