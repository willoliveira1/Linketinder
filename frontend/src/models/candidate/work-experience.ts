import { ContractType } from "../enums/contract-type";
import { LocationType } from "../enums/location-type";
import { State } from "../enums/state";

export default class WorkExperience {

    id: number = 0;
    title: string = "";
    companyName: string = "";
    contractType: ContractType = ContractType.Aprendiz;
    locationType: LocationType = LocationType.Presencial;
    city: string = "";
    state: State = State.AC;
    currentlyWork: boolean = false;
    description: string = "";

    constructor(id: number, title: string, companyName: string, contractType: ContractType, locationType: LocationType,
        city: string, state: State, currentlyWork: boolean, description: string) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.contractType = contractType;
        this.locationType = locationType;
        this.city = city;
        this.state = state;
        this.currentlyWork = currentlyWork;
        this.description = description;
    }
    
}
