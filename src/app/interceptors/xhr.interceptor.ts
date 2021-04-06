import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpInterceptor,
  HttpHeaders
} from '@angular/common/http';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    /*let httpHeaders = new HttpHeaders({
      'X-Requested-With': 'XMLHttpRequest',
      'Referrer-Policy':'no-referrer-when-downgrade' // :'(
    });
    //'Access-Control-Allow-Origin': '*',

    const xhr = req.clone({
      headers: httpHeaders
    });*/

    //req.headers.set('Referrer-Policy', 'no-referrer-when-downgrade'); // :'( https://searchengineland.com/need-know-referrer-policy-276185
    // samesite : https://stackoverflow.com/questions/62857541/angular-how-to-fix-samesite-cookie-issue //avoir la main sur le serveur....
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest'),
    });
    return next.handle(xhr);
  }
}
