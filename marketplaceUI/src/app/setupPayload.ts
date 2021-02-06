import { Pokemon } from './Pokemon';
import { Items } from './Items';

export class SetupPayload {

  clientName: string;
	password: string;
  constructor(clientName: string, password: string) {

    this.clientName = clientName;
    this.password = password;

  }
}
