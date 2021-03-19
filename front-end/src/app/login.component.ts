import { Component } from '@angular/core';
import { AppService } from './app.service';
import { Router } from '@angular/router';

@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent {
  error: any;
  credentials = { username: '', password: '' };

  constructor(
    private app: AppService,
    private router: Router
  ) {}

  login() {
    this.app.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/');
    });
    return false;
  }
}
