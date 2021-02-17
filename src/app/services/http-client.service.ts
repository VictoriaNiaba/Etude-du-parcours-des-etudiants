import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Output, SystemJsNgModuleLoader } from '@angular/core';
import { catchError, retry } from 'rxjs/operators';
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
    //return this.httpClient.get<any>(`${this.baseUrl}/registrations`);
    return this.httpClient.get<any>(`http://localhost:3000/registrations`);//passage via mock car non codé !
  }

  getPaths(firstStep: string, lastStep: string) {
    return this.httpClient.get<any>('http://localhost:3000/paths');
  }
}