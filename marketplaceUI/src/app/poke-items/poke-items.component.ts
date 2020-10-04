import { Component, OnInit } from '@angular/core';
import { Items } from '../Items';
import { CartService } from '../cart.service';
import { RSocketService } from '../rsocket-service';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-poke-items',
  templateUrl: './poke-items.component.html',
  styleUrls: ['./poke-items.component.css']
})
export class PokeItemsComponent implements OnInit {
  name;
  items: Items[] = [];
  active = false;

  p: number

  constructor(private service: RSocketService, protected cartService: CartService) { }

  getItem(): void {
    this.service.getItems()
      .subscribe(item => { this.items.push(item); this.setRealID(); });
  }

  getInput(n: string) {
    n = n[0].toUpperCase() + n.substr(1).toLowerCase();
    this.name = n;
    this.active = true;
    if (n.length === 0) {
      this.active = false;
    }

  }

  setRealID() {
    for (let i = 0; i < this.items.length; i++) {
      this.items[i].realId = i + 1;
    }
  }

  // sets delay for the added to cart alert
  async delay(ms: number, item: Items) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => item.hideAlert = false);
  }

  addToCart(item: Items) {
    this.cartService.addItem(item);
  }

  ngOnInit() {

    if (this.service.rsocket !== undefined){
      this.getItem();
    }

    else {
     this.service.socketReady.pipe(take(1)).subscribe(b => this.getItem());
    }
  }



}
