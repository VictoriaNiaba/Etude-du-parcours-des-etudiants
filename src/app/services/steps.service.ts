import { Injectable } from '@angular/core';
import { Step } from '../models/Step';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class StepsService {
  steps = new Array<Step>();
  
  constructor(private httpClient: HttpClientService) {
    httpClient.getSteps().subscribe(res => this.steps = res);
  }

  getByCode(code: string): string {
    let step = this.steps.find(step => step.step_code == code);
    if(!step) {
      console.error("not step found")
      return;
    }
    return step.step_name;
  }
}
