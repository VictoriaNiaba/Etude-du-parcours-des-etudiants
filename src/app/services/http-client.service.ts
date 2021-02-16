import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ok } from 'assert';
import { Observable } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  private baseUrl = "http://localhost:3000";

  constructor(private httpClient:HttpClient) { }
  
  getCoucou()
  {
    console.log("test get coucou");
    return this.httpClient.get<string>(this.baseUrl+"/salutations");
    /*
      sources :
        https://medium.com/@rameez.s.shaikh/angular-7-spring-boot-application-hello-world-example-43588fbcd039
        https://blog.bitsrc.io/mock-apis-different-techniques-for-react-and-angular-156284b757f4 (Http Service)
    */
  }

  login(email: string, password: string): Observable<any> {
    console.log(
      this.httpClient.get<User>(this.baseUrl+"/users").subscribe(data => {
        data.email
      }).toString()
    );
    return this.httpClient.get(`${this.baseUrl}/users?email=${email}&password=${password}`);
  }//je suis pas innactif
}