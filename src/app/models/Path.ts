import { Step } from "./Step";

export class Path {
    path_steps: Step[];

    constructor(path_steps?: Step[]) {
        this.path_steps = path_steps;
    }
    getMeanStudents():number{
        let tmp_nb = 0;
        this.path_steps.forEach(step => {
            tmp_nb += step.step_stats_in + step.step_stats_other; //pas les redoublants car ils seront comptés 2x sinon
        });
        return tmp_nb/this.path_steps.length;
    }
}