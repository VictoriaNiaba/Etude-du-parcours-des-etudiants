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
    //formulaire
    this.submitted = true;
    if (this.signInForm.invalid) {
        return;
    }
    const email = this.signInForm.value['email'];
    const psw = this.signInForm.value['password'];

    //login hhtpclient
    this.AuthentificationService.login(email, psw);
  }

  autoLogin() {
    this.signInForm = this.formBuilder.group({
      email: ['admin.email@email.email',[Validators.email,Validators.required]],
      password: ['psw',Validators.required]
    });
  }

}
