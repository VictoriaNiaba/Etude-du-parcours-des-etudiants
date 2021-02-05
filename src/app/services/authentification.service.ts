import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/User';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  public currentUser: User;

  constructor(private user: UserService, private router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if(this.currentUser) console.log(this.currentUser.lastName);
  }

  login(email: string, password: string) {
    let tmp: User = this.user.getByEmail(email);
    if(tmp == null || tmp.password != password) return null;
    console.log("Auth");
    localStorage.setItem('currentUser', JSON.stringify(tmp));
    //todo: remove ??
    window.location.reload();
    return tmp;
  }

  logout() {
    this.currentUser = null;
    localStorage.removeItem('currentUser');
    this.router.navigate(['']);
    //todo: remove ??
    window.location.reload();
  }
}