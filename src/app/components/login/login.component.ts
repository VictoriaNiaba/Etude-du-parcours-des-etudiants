import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { AuthentificationService } from '../../services/authentification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  signInForm: FormGroup;
  submitted = false;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authentificationService: AuthentificationService
  ) {
  }

  ngOnInit() {
    if (this.authentificationService.authentificated) {
      this.router.navigate(['admin']);
    }

    this.signInForm = this.formBuilder.group({
      email: ['', [Validators.email, Validators.required]],
      password: ['', Validators.required],
    });
  }

  get formControl() {
    return this.signInForm.controls;
  }

  onSubmit() {
    //formulaire
    this.submitted = true;
    if (this.signInForm.invalid) {
      return;
    }
    const credentials = {
      username: this.signInForm.value['email'],
      password: this.signInForm.value['password'],
    };

    //login httpclient
    this.authentificationService.authentificate(credentials, () => {
      this.router.navigate(['admin']);
    });
    return false;
  }

  autoLogin() {
    this.signInForm = this.formBuilder.group({
      email: [
        'admin.email@email.email',
        [Validators.email, Validators.required],
      ],
      password: ['psw', Validators.required],
    });
  }
}
