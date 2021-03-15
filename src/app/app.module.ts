import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NgxEchartsModule } from 'ngx-echarts';
import { LoginComponent } from './components/login/login.component';
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
import { AutocompleteLibModule } from 'angular-ng-autocomplete';
import { StepsComponent } from './components/steps/steps.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { EpuGrapheComponent } from './components/epu/epu-graphe/epu-graphe.component';
import { EpuStatsComponent } from './components/epu/epu-stats/epu-stats.component';
import { EpuPageComponent } from './components/epu/epu-page/epu-page.component';
import { StepsService } from './services/steps.service';
import {ScrollingModule} from '@angular/cdk/scrolling';

export function initializeApp1(stepsService: StepsService) {
  return (): Promise<any> => { 
    return stepsService.init();
  }
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavigationComponent,
    FooterComponent,
    AdministrationComponent,
    FormationsComponent,
    RegistrationsComponent,
    FormationDetailsComponent,
    FormationEditComponent,
    StepsComponent,
    EpuGrapheComponent,
    EpuStatsComponent,
    EpuPageComponent
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
    NgbPaginationModule,
    AutocompleteLibModule,
    DragDropModule,
    ScrollingModule
  ],
  providers: [
    StepsService,
    { provide: APP_INITIALIZER, useFactory: initializeApp1, deps: [StepsService], multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
