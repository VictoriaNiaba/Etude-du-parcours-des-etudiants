import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  baseUrl = "http://localhost:3000";
  private resultTemp: any={};

  constructor(private httpClient:HttpClient) { }

  httpHeader = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  
  getCoucou():string{
    this.httpClient.get(`${this.baseUrl}/salutations`).subscribe(res => {
      //this.blablabla(res);
      this.resultTemp=res
      console.warn("RT : " + this.resultTemp.message);
    });
    //var tmp: string = this.resultTemp.message;
    //console.warn("RT tmp : " + tmp);
    //this.zzzz()
    return this.resultTemp.message;
  }/*
  blablabla(blabla){
    this.resultTemp = blabla;
    console.warn("RT blabla : " + this.resultTemp.message);
  }
  zzzz(){
    console.warn("RT zzzzzz : " + this.resultTemp.message);
    return this.resultTemp.message
  }*/


  login(email: string, password: string): User {
    let result: any = {};
    this.httpClient.get<User>(`${this.baseUrl}/users?email=${email}&password=${password}`).pipe(retry(1), catchError(null)).subscribe((res: {}) => {
      result = res;
    });
    return result;
  }
}