import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Formation } from '../models/formation';
import { User } from '../models/User';

/*
  Documentation :
    https://medium.com/@rameez.s.shaikh/angular-7-spring-boot-application-hello-world-example-43588fbcd039
    https://blog.bitsrc.io/mock-apis-different-techniques-for-react-and-angular-156284b757f4 (Http Service)
    https://www.positronx.io/make-http-requests-with-angular-httpclient-api/
*/

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  //ajouter/retirer un / à la ligne suivante pour switch
  /*
  //json_server
  baseUrl = "http://localhost:3000";
  /*/
  //spring server
  baseUrl = "http://localhost:8080";
  //*/
  

  constructor(private httpClient:HttpClient) { }

  httpHeader = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  
  getHello(){
    return this.httpClient.get<any>(`${this.baseUrl}/salutations`);
  }
  
  login(email: string, password: string) {
    //return this.httpClient.get<User>(`${this.baseUrl}/users?email=${email}&password=${password}`);
    return this.httpClient.get<User>(`http://localhost:3000/users?email=${email}&password=${password}`); //passage via mock car non codé !
  }

  getRegistrations() {
    return this.httpClient.get<any>(`http://localhost:3000/registrations`);
  }

  getPaths(firstStep: string, lastStep: string) {
    let request = `${this.baseUrl}/paths`;
    if(firstStep || lastStep) {
      request += "?"
      if(firstStep && lastStep)
        request += `firststep=${firstStep}&laststep=${lastStep}`
      if(firstStep && !lastStep)
        request += `firststep=${firstStep}`
      if(!firstStep && lastStep)
        request += `laststep=${lastStep}`
    }
    console.warn(request)
    return this.httpClient.get<any>(request);
  }

  getSteps(){
    return this.httpClient.get<any>(`${this.baseUrl}/steps`);
  }

  getStepByCode(code){
    return this.httpClient.get<any>(`${this.baseUrl}/steps/${code}`);
  }

  getFormations(){
    return this.httpClient.get<Array<Formation>>(`${this.baseUrl}/formations`);
  }

  getFormationByCode(code: string){
    return this.httpClient.get<Formation>(`${this.baseUrl}/formations/${code}`);
  }

  addFormation(data: Formation){
    return this.httpClient.post<Formation>(`${this.baseUrl}/formations/`, JSON.stringify(data), this.httpHeader);
  }

  updateFormation(code, data: Formation){
    console.log(data)
    return this.httpClient.put<Formation>(`${this.baseUrl}/formations/` + code, JSON.stringify(data), this.httpHeader);
  }

  deleteFormation(code){
    return this.httpClient.delete<Formation>(`${this.baseUrl}/formations/` + code, this.httpHeader);
  }

  postFile(fileToUpload: File, endpoint: string): Observable<boolean> {
    let h = new HttpHeaders();
    h.set('Accept', 'application/json, text/plain, text/csv, application/vnd.ms-excel');
    const formData: FormData = new FormData();
    formData.append('csvfile', fileToUpload, fileToUpload.name);
    //#region debug
    console.log(fileToUpload.name);
    console.log(fileToUpload);
    /*
    const reader = new FileReader();
    reader.onload = (e) => {
        const text = reader.result.toString().trim();
        console.log(text);
    }
    reader.readAsText(fileToUpload);
    */
    console.log(formData);
    //#endregion debug
    return this.httpClient
      .post(this.baseUrl + endpoint, formData, { headers: h })
      .pipe(map(() => { return true; }));
  }
}