import { resolve } from '@angular/compiler-cli/src/ngtsc/file_system';
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
      });
    });
  }

  getByCode(code: string): string {
    if(this.steps.length != 0) {
      let step = this.steps.find(step => step.step_code == code);
      if(!step) {
        console.error(`No step ${code} found`)
        return;
      }
      return step.step_name.replace("AMU.","");
    }
  }
}
