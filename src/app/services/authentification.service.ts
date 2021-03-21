import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root',
})
export class AuthentificationService {
  public authentificated: boolean = false;

  constructor(
    private httpClientService: HttpClientService,
    private router: Router
  ) {
    this.authentificate(undefined, undefined);
  }

  authentificate(credentials, callback) {
    this.httpClientService.login(credentials).subscribe(
      (response) => {
        if (response['name']) {
          this.authentificated = true;
        }
        else {
          this.authentificated = false;
        }
        return callback && callback();
      },
      (error) => {
        if(error.status == 401) {
          //do nothing, on connait l'erreur et elle ne nous intéresse pas ici, l'utilisateur peut ne pas être connecté donc le service d'authentification ne doit pas être capable de le log dans son constructeur, donc on a un retour 401 (mais c'est admissible)
        } else {
          console.warn(error)
        }
      }
    );
  }

  logout() {
    this.httpClientService
      .logout()
      .pipe(
        finalize(() => {
          this.authentificated = false;
          this.router.navigate(['sign-in']);
        })
      )
      .subscribe();
  }
}
