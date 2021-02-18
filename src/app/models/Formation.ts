import { Step } from "./Step";

export class Formation {
    id: string;
    formation_name: string;
    description: string;
    type: string;
    url: string;
    steps: [];
    add_date: Date;
    last_modification: Date;

    constructor(id: string, formation_name: string, description: string, type: string, url: string, steps: [], add_date: Date, last_modification: Date) {
        this.id = id;
        this.formation_name = formation_name;
        this.description = description;
        this.type = type;
        this.url = url;
        this.steps = steps;
        this.add_date = add_date;
        this.last_modification = last_modification;
    }
    
}