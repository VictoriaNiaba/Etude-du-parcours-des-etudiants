import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  constructor(private httpClient: HttpClient) { }

  postFile(fileToUpload: File, endpoint: string): Observable<boolean> {
    let h = new HttpHeaders();
    h.append('Accept', 'application/json, text/plain,');
    const formData: FormData = new FormData();
    formData.append('fichier', fileToUpload, fileToUpload.name);
    console.log(fileToUpload.name);
    console.log(fileToUpload);
    const reader = new FileReader();
    reader.onload = (e) => {
        const text = reader.result.toString().trim();
        console.log(text);
    }
    reader.readAsText(fileToUpload);
    console.log(formData);
    return this.httpClient
      .post(endpoint, formData, { headers: h })
      .pipe(map(() => { return true; }));
  }

  handleError(e: any) {
    throw new Error('Method not implemented.');
  }
}
