import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { AdministrationComponent } from './components/administration/administration.component';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';
import { Role } from './utils/Role';
import { EpuComponent } from './components/epu/epu.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {
    path: 'sign-in', component: LoginComponent
  },
  {
    path: 'admin', component: AdministrationComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.Admin]}
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
