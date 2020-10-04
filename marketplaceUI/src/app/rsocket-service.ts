import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Subject } from 'rxjs';
import { Pokemon } from './Pokemon';
import { Mail } from './mail';
import { RSocketClient, JsonSerializer, IdentitySerializer, Encodable } from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';
import { ReactiveSocket } from 'rsocket-types'
import { Flowable} from 'rsocket-flowable';
import { Items } from './Items';


@Injectable({
  providedIn: 'root'
})
export class RSocketService {


  client: RSocketClient<any, Encodable>;
  rsocket: ReactiveSocket<any, Encodable>;
  socketReady = new Subject();

  constructor(private http: HttpClient) {

    // rsocket client
    this.client = new RSocketClient({
      serializers: {
        data: JsonSerializer,
        metadata: IdentitySerializer
      },
      setup: {
        keepAlive: 29000,
        lifetime: 180000,
        dataMimeType: 'application/json',
        metadataMimeType: 'message/x.rsocket.routing.v0',
        payload: { data: "SECRET" }
      },
      transport: new RSocketWebSocketClient({
        url: 'wss://team-rocket-market.herokuapp.com/rsocket'
      })
    })

    this.connectSocket()

  }


  //Pokemon RSocket
  getPokemon(): Flowable<Pokemon> {
    return this.rsocket.requestStream({
      metadata: String.fromCharCode('api.pokemon'.length) + 'api.pokemon',
    }).map(p =>
      p.data);
  }

  // Shiny Pokemon RSocket
  getShinyPokemon(): Flowable<Pokemon> {
    return this.rsocket.requestStream({
      metadata: String.fromCharCode('api.shiny'.length) + 'api.shiny'
    }).map(p =>
      p.data);

  }

  // Item RSockets
  getItems(): Flowable<Items> {
    return this.rsocket.requestStream({
      metadata: String.fromCharCode('api.items'.length) + 'api.items'
    }).map(p =>
      p.data);

  }

  // Email RSocket
  sendEmail(email: Mail): void {
    return this.rsocket.fireAndForget({
      data: email,
      metadata: String.fromCharCode('api.email'.length) + 'api.email'
    });

  }

  connectSocket() {
    this.client
      .connect()
      .then(sock=>this.publishConnectEvent(sock),
        err => { console.log("Failed to Connect to RSocket", err) });
  }

  publishConnectEvent(sock: ReactiveSocket<any, Encodable>){

    sock.connectionStatus().subscribe(status =>{
      console.log("RSocket Connection is: "+status.kind);
      if (status.kind!=="CONNECTED"){
        this.rsocket = undefined;
        console.log("Attempting Reconnect");
        this.connectSocket;
      }
      else{
        this.rsocket = sock;
        this.socketReady.next("ready");
      }
    })


  }

}

