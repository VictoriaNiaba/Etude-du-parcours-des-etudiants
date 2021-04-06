import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthentificationService } from './authentification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(
      private router: Router,
      private authenticationService: AuthentificationService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.authentificated;
    //if logged
    if(currentUser) {
      return true;
    }
    //if not logged => redirect to signIn page !
    this.router.navigate(['/']);
    return false;
  }
}
