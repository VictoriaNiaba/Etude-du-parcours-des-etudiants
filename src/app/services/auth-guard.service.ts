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
    const currentUser = this.authenticationService.currentUser;

    //if logged
    if(currentUser) {
      //console.log("AuthGuard : OK");
      return true;
    }

    //if not logged => redirect to signIn page !
    this.router.navigate(['/']);
    //console.error("Restricted page reached");
    return false;
  }
}
