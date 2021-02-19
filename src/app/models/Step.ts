export class Step {
    step_code: string;
    step_name: string;
    step_stats_in: number;
    step_stats_other: number;
    step_stats_repeating: number;

    constructor(step_code: string, step_name: string, step_stats_in?:number, step_stats_other?:number, step_stats_repeating?:number) {
        this.step_code = step_code;
        this.step_name = step_name;
        this.step_stats_in = step_stats_in;
        this.step_stats_other = step_stats_other;
        this.step_stats_repeating = step_stats_repeating;
    }

    setStats(step_stats_in:number, step_stats_other:number, step_stats_repeating:number) {
        this.step_stats_in = step_stats_in;
        this.step_stats_other = step_stats_other;
        this.step_stats_repeating = step_stats_repeating;
    }
    getStatsTotal():number {
        return this.step_stats_in + this.step_stats_other + this.step_stats_repeating;
    }
    clearStats() {
        this.step_stats_in = undefined;
        this.step_stats_other = undefined;
        this.step_stats_repeating = undefined;
    }
}