import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { AuthentificationService } from '../../services/authentification.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  message: string;
  signInForm: FormGroup;
  submitted = false;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private AuthentificationService: AuthentificationService
  ) {
    if (this.AuthentificationService.currentUser) { 
      console.debug("User already connected");
      this.router.navigate(['admin']);
    }
  }

  ngOnInit() {
    this.signInForm = this.formBuilder.group({
      email: ['',[Validators.email,Validators.required]],
      password: ['',Validators.required]
    });
  }

  get formControl() { return this.signInForm.controls; }

  onSubmit() {
    this.submitted = true;
    if (this.signInForm.invalid) {
        return;
    }
    const email = this.signInForm.value['email'];
    const psw = this.signInForm.value['password'];
    console.log(`${email}, ${psw}`);
    let result: User = this.AuthentificationService.login(this.signInForm.value['email'], this.signInForm.value['password']);
    if(result == null) this.message = ('Identifiants incorrect');
    else {
      this.message = null;
      this.router.navigate(['admin']);
    }
  }

  autoLogin() {
    this.signInForm = this.formBuilder.group({
      email: ['admin.email@email.email',[Validators.email,Validators.required]],
      password: ['psw',Validators.required]
    });
  }

}
