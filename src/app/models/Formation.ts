import { Step } from "./Step";

export class Formation {
    formation_code: string;
    formation_name: string;
    description: string;
    type: string;
    url: string;
    steps: Step[];

    constructor(formation_code: string, formation_name: string, description: string, type: string, url: string, steps: Step[]) {
        this.formation_code = formation_code;
        this.formation_name = formation_name;
        this.description = description;
        this.type = type;
        this.url = url;
        this.steps= steps;
    }
    
}