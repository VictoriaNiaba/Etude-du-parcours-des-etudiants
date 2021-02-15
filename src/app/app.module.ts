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
import { FormationsComponent } from './components/formations/formations-list/formations.component';
import { RegistrationsComponent } from './components/registrations/registrations.component';
import { HttpClientModule } from '@angular/common/http';
import { FormationDetailsComponent } from './components/formations/formation-details/formation-details.component';
import { FormationEditComponent } from './components/formations/formation-edit/formation-edit.component';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    EpuComponent,
    NavigationComponent,
    FooterComponent,
    AdministrationComponent,
    FormationsComponent,
    RegistrationsComponent,
    FormationDetailsComponent,
    FormationEditComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    }),
    HttpClientModule,
    NgbModule,
    NgbPaginationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
