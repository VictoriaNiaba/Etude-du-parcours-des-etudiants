import { Step } from "./Step";

export class Formation {
    formation_code: string;
    formation_name: string;
    description: string;
    type: string;
    url: string;
    steps: Step[];
    add_date: Date;
    last_modification: Date;

    constructor(formation_code: string, formation_name: string, description: string, type: string, url: string, steps: Step[], add_date: Date, last_modification: Date) {
        this.formation_code = formation_code;
        this.formation_name = formation_name;
        this.description = description;
        this.type = type;
        this.url = url;
        this.steps = steps;
        this.add_date = add_date;
        this.last_modification = last_modification;
    }
    
}