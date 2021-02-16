import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/User';
import { HttpClientService } from './http-client.service';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  public currentUser: User;

  constructor(private httpClientService: HttpClientService, private router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if(this.currentUser) console.log(this.currentUser.email);
  }

  login(email: string, password: string) {
    
    let user:User = null;
    this.httpClientService.login(email, password)
      .subscribe( data => {
        user = data.result;
      });
      alert(user.email);
    if(user === null) return null;
    console.log("Auth");
    localStorage.setItem('currentUser', JSON.stringify(user));
    //todo: remove ??
    window.location.reload();
    return user.email;
  }

  logout() {
    this.currentUser = null;
    localStorage.removeItem('currentUser');
    this.router.navigate(['']);
    //todo: remove ??
    window.location.reload();
  }
}