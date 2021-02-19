import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdministrationComponent } from './components/administration/administration.component';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';
import { LoginComponent } from './components/login/login.component';
import { EpuPageComponent } from './components/epu/epu-page/epu-page.component'
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
    path: '', component: EpuPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
