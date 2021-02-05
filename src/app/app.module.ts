import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxEchartsModule } from 'ngx-echarts';
import { LoginComponent } from './components/login/login.component';
import { EpuComponent } from './components/epu/epu.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { FooterComponent } from './components/footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdministrationComponent } from './components/administration/administration.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    EpuComponent,
    NavigationComponent,
    FooterComponent,
    AdministrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
