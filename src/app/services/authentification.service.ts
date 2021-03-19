import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { User } from '../models/User';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root',
})
export class AuthentificationService {
  public currentUser: User;
  authenticated: boolean = false;

  constructor(
    private httpClientService: HttpClientService,
    private router: Router
  ) {
    this.authenticate(undefined, undefined);
  }

  authenticate(credentials, callback) {
    this.httpClientService.login(credentials).subscribe((response) => {
      if (response['name']) {
        console.log('user logged-in: ' + response['name']);
        this.authenticated = true;
        this.currentUser = new User(response['name']);
      } else {
        this.authenticated = false;
        this.currentUser = null;
      }
      return callback && callback();
    });
  }

  logout() {
    this.httpClientService
      .logout()
      .pipe(
        finalize(() => {
          this.authenticated = false;
          this.currentUser = null;
          this.router.navigate(['sign-in']);
        })
      )
      .subscribe();
  }
}
