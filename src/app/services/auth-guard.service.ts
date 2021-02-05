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
      //check role
      if(route.data.roles && route.data.roles.indexOf(currentUser.role) === -1) {
        // role not authorised => redirect to home page
        console.error("AuthGuard : 1K :: role not allowed on this page");
        this.router.navigate(['/']);
        return false;
      }
      //role ok
      console.log("AuthGuard : OK");
      return true;
    }

    //if not logged => redirect to signIn page !
    this.router.navigate(['/']);
    console.error("AuthGuard : 1K :: you have to be logged to show this page");
    return false;
  }
}
