import { Step } from "./Step";

export class Path {
    path_steps:Step[];

    constructor() {
        this.path_steps = new Array<Step>();
    }
    getMeanStudents():number{
        let tmp_nb = 0;
        this.path_steps.forEach(step => {
            tmp_nb += step.step_stats_in + step.step_stats_other; //pas les redoublants car ils seront comptés 2x sinon
        });
        return tmp_nb/this.path_steps.length;
    }
    getNbStudentIndependant(): number{ //pour le moment on fait ça mais il faudrait que paths contienne l'info suivante : nombre d'étudiants INDEPENDANTS
        let tmp = 0;
        this.path_steps.forEach(step => {
            tmp += step.getStatsTotal() - step.step_stats_repeating;
        });
        return tmp;
    }
    addSteps(step: Step){
        this.path_steps.push(step);
    }
    setSteps(path_steps: Step[]) {
        this.path_steps = path_steps;
    }
    getSteps(index:number) {
        return this.path_steps[index];
    }
}