import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { AuthentificationService } from 'src/app/services/authentification.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  constructor(public router: Router, public authenticationService: AuthentificationService) { }

  ngOnInit(): void {
  }

  disconnect() {
    this.authenticationService.logout();
    this.ngOnInit();
  }

}
