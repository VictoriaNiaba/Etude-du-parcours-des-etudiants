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

  //*
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
    return this.httpClient.get<User>(`${this.baseUrl}/users?email=${email}&password=${password}`);
  }

  //"year": 2000, "nb_registrations": 8001, "timeStamp": "01/01/2000" 
  getRegistrations() {
    return this.httpClient.get<any>(`${this.baseUrl}/registrations`);
  }
}