import { BrowserModule } from '@angular/platform-browser';
import { Injectable, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  HttpClientModule,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login.component';
import { HomeComponent } from './home.component';
import { AppService } from './app.service';
import { AppRoutingModule } from './app-routing.module';


@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest'),
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [AppComponent, HomeComponent, LoginComponent],
  imports: [BrowserModule, HttpClientModule, FormsModule, AppRoutingModule],
  providers: [
    AppService,
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
