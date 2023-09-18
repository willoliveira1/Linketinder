import { State } from "../enums/state";

export default interface Person {

    id: number;
    name: string;
    email: string;
    city: string;
    state: State;
    cep: string;
    description: string;

}
