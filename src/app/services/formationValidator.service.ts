import { Injectable } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class FormationValidatorService {

  constructor(private httpClientService: HttpClientService) { }

  codeValidator(codeOfFormation: string): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.checkIfUniqueCode(control.value, codeOfFormation).pipe(
        map(res => {
          // if res is true, code exists, return true
          return res ? { uniqueId: true } : null;
          // NB: Return null if there is no error
        })
      );
    };
  }

  checkIfUniqueCode(code: string, codeOfFormation: string): Observable<boolean>{
    this.getFormationsCode(codeOfFormation);
    return of (this.formationsCode.includes(code));
  }

  formationsCode: Array<string> = new Array<string>();
  getFormationsCode(codeOfFormation: string){
    this.httpClientService.getFormations().subscribe(res => {
      res.forEach(formation => this.formationsCode.push(formation.formation_code));
      //Pour ne pas comparer avec son propre code
      const index = this.formationsCode.indexOf(codeOfFormation, 0);
      if (index > -1) {
        this.formationsCode.splice(index, 1);
      }
      return this.formationsCode;
    });
  }
}
