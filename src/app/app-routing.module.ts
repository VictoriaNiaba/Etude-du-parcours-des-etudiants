import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { AdministrationComponent } from './components/administration/administration.component';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';
import { EpuComponent } from './components/epu/epu.component';
import { LoginComponent } from './components/login/login.component';
import { FormationDetailsComponent } from './components/formations/formation-details/formation-details.component';
import { FormationEditComponent } from './components/formations/formation-edit/formation-edit.component';

const routes: Routes = [
  {
    path: 'sign-in', component: LoginComponent
  },
  {
    path: 'admin', component: AdministrationComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'formation/new',component: FormationEditComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'formation/:id', component: FormationDetailsComponent,
  },
  {
    path: 'formation/:id/edit', component: FormationEditComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '', component: EpuComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
