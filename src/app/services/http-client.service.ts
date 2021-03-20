import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Formation } from '../models/Formation';

/*
  Documentation :
    https://medium.com/@rameez.s.shaikh/angular-7-spring-boot-application-hello-world-example-43588fbcd039
    https://blog.bitsrc.io/mock-apis-different-techniques-for-react-and-angular-156284b757f4 (Http Service)
    https://www.positronx.io/make-http-requests-with-angular-httpclient-api/
*/

@Injectable({
  providedIn: 'root',
})
export class HttpClientService {
  constructor(private httpClient: HttpClient) {}

  httpHeader = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  getHello() {
    return this.httpClient.get<any>(`${environment.baseUrl}/salutations`);
  }

  login(credentials) {
    const headers = new HttpHeaders(
      credentials
        ? {
            authorization:
              'Basic ' +
              btoa(`${credentials.username}:${credentials.password}`),
          }
        : {}
    );

    return this.httpClient.get(`${environment.baseUrl}/user`, {
      headers: headers,
      withCredentials: true,
    });
  }

  logout() {
    return this.httpClient.post<any>(`${environment.baseUrl}/logout`, {});
  }

  getRegistrations() {
    return this.httpClient.get<any>(`${environment.baseUrl}/registrations`);
  }

  getPaths(firstStep: string, lastStep: string) {
    let request = `${environment.baseUrl}/paths`;
    if (firstStep || lastStep) {
      request += '?';
      if (firstStep && lastStep)
        request += `firststep=${firstStep}&laststep=${lastStep}`;
      if (firstStep && !lastStep) request += `firststep=${firstStep}`;
      if (!firstStep && lastStep) request += `laststep=${lastStep}`;
    }
    console.warn(request);
    return this.httpClient.get<any>(request);
  }

  getSteps() {
    return this.httpClient.get<any>(`${environment.baseUrl}/steps`);
  }

  getStepByCode(code) {
    return this.httpClient.get<any>(`${environment.baseUrl}/steps/${code}`);
  }

  getFormations() {
    return this.httpClient.get<Array<Formation>>(
      `${environment.baseUrl}/formations`
    );
  }

  getFormationByCode(code: string) {
    return this.httpClient.get<Formation>(
      `${environment.baseUrl}/formations/${code}`
    );
  }

  addFormation(data: Formation) {
    return this.httpClient.post<Formation>(
      `${environment.baseUrl}/formations`,
      JSON.stringify(data),
      this.httpHeader
    );
  }

  updateFormation(code, data: Formation) {
    console.log(data);
    return this.httpClient.put<Formation>(
      `${environment.baseUrl}/formations/${code}`,
      JSON.stringify(data),
      this.httpHeader
    );
  }

  deleteFormation(code) {
    return this.httpClient.delete<Formation>(
      `${environment.baseUrl}/formations/${code}`,
      this.httpHeader
    );
  }

  postFile(fileToUpload: File, endpoint: string): Observable<boolean> {
    let h = new HttpHeaders();
    h.set(
      'Accept',
      'application/json, text/plain, text/csv, application/vnd.ms-excel'
    );
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
      .post(`${environment.baseUrl}/${endpoint}`, formData, { headers: h })
      .pipe(
        map(() => {
          return true;
        })
      );
  }
}
