import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Formation } from 'src/app/models/Formation';
import { AuthentificationService } from 'src/app/services/authentification.service';
import { HttpClientService } from 'src/app/services/http-client.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-formation-details',
  templateUrl: './formation-details.component.html',
  styleUrls: ['./formation-details.component.scss']
})
export class FormationDetailsComponent implements OnInit {
  formation: Formation;
  canEdit: boolean;

  constructor(private route: ActivatedRoute,private authenticationService: AuthentificationService, private httpClientService: HttpClientService, private router: Router, private location: Location) { 
    this.location = location;
  }

  ngOnInit(): void {
    this.httpClientService.getFormationByCode(this.route.snapshot.paramMap.get('code')).subscribe(res => {
        this.formation=res;
        this.canEdit = this.authenticationService.authentificated;
      },
      (error) => {
        console.warn(error)
        this.router.navigate(['/admin']);
        this.canEdit = false;
      }
    );
  }

  back(){
    this.location.back();
  }

  delete() {
    if (window.confirm('Voulez-vous vraiment supprimer cette formation ?')){
      this.httpClientService.deleteFormation(this.formation.formation_code).subscribe(res => {
        this.router.navigate(['/admin']);
      },
      (error) => {
          console.warn("Handle (deleteFormation) :", error)
        }
      );
    }
  }
}
