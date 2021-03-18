import { StepPath } from "./Step";

export class Path {
    path_steps:StepPath[]; 

    constructor(path_steps?: StepPath[]) {
        if(path_steps) {
            this.path_steps = path_steps;
        }
        this.path_steps = new Array<StepPath>();
    }
    getMeanStudents():number{
        if(this.path_steps.length == 0)
            return null;
        return (this.getNbStudent()/this.path_steps.length);
    }
    getNbStudent(): number{
        let tmp = 0;
        this.path_steps.forEach(step => {
            tmp += step.step_number;
        });
        return tmp;
    }
    addStep(step: StepPath){
        this.path_steps.push(step);
    }
    setStep(path_steps: StepPath[]) {
        this.path_steps = path_steps;
    }
    getSteps(index:number) {
        return this.path_steps[index];
    }
}