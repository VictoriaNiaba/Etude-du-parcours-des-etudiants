import { Injectable } from '@angular/core';
import { Step } from '../models/Step';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class StepsService {
  steps = new Array<Step>();
  
  constructor(private httpClient: HttpClientService) {}

  init() {
    return new Promise<void>((resolve, reject) => {
      this.httpClient.getSteps().subscribe(res => {
        this.steps = res;
        resolve();
      },
      (error) => {
          console.warn("Handle : steps failed to load properly :", error)
        }
      );
    });
  }

  getByCode(code: string): string {
    if(this.steps.length != 0) {
      let step = this.steps.find(step => step.step_code == code);
      if(!step) {
        return code;
      }
      return step.step_name.replace("AMU.","");
    }
    return undefined;
  }
}
