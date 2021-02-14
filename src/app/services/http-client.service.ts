import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  private baseUrl = 'http://localhost:8000/xxx';

  constructor(private httpClient:HttpClient) { }

  getCoucou()
  {
    console.log("test get coucou");
    return this.httpClient.get<any>(this.baseUrl);
    //return this.httpClient.get<String>('http://localhost:8080/xxxx');
    /*
      sources :
        https://medium.com/@rameez.s.shaikh/angular-7-spring-boot-application-hello-world-example-43588fbcd039
        https://blog.bitsrc.io/mock-apis-different-techniques-for-react-and-angular-156284b757f4 (Http Service)
    */
  }
}