export class Step {
    step_code: string;
    step_name: string;
    steps_in = new Array<{step_code:string, number:number}>();
    steps_out = new Array<{step_code:string, number:number}>();
    average_repeat: number;

    constructor(step_code: string, step_name: string, steps_in?: Array<{step_code:string, number:number}>, steps_out?: Array<{step_code:string, number:number}>, average_repeat?: number) {
        this.step_code = step_code;
        this.step_name = step_name;
        if(steps_in) {
            this.steps_in = steps_in;
            console.log("steps_in", this.steps_in);
        } else {
            this.steps_in = [];
        }
        if(steps_out) {
            this.steps_out = steps_out;
            console.log("steps_out", this.steps_out);
        } else {
            this.steps_out = [];
        }
        this.average_repeat = average_repeat ? average_repeat : this.average_repeat;
    }

    setStats(steps_in: Array<{step_code:string, number:number}>, steps_out: Array<{step_code:string, number:number}>, average_repeat: number) {
        this.steps_in = steps_in;
        this.steps_out = steps_out;
        this.average_repeat = average_repeat;
    }
    getNumberIncoming():number {
        let total = 0;
        console.log("incoming", this.steps_in);
        let tempSteps = this.steps_in;
        tempSteps.forEach(step => {
            console.log(step.step_code, step.number);
        });
        return total;
    }
    getNumberOutcoming():number {
        let total = 0;
        this.steps_out.forEach(step => total += step.number);
        return total;
    }
}

export class StepPath {
    step_code: string;
    step_name: string;
    step_number: number; //nombre entrant dans ce step

    constructor(step_code: string, step_name: string, step_number?: number) {
        this.step_code = step_code;
        this.step_name = step_name;

        this.step_number = step_number ? step_number : null;
    }
}