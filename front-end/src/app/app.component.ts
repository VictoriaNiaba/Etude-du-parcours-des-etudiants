import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { finalize } from 'rxjs/operators';

import { Component } from '@angular/core';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(
    private app: AppService,
    private http: HttpClient,
    private router: Router
  ) {
    this.app.authenticate(undefined, undefined);
  }
  logout() {
    this.http
      .post('/api/logout', {})
      .pipe(
        finalize(() => {
          this.app.authenticated = false;
          this.router.navigateByUrl('/login');
        })
      )
      .subscribe();
  }
}
