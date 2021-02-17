import { isNull } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Formation } from 'src/app/models/formation';
import { User } from 'src/app/models/User';
import { AuthentificationService } from 'src/app/services/authentification.service';
import { FormationService } from 'src/app/services/formation.service';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-formation-details',
  templateUrl: './formation-details.component.html',
  styleUrls: ['./formation-details.component.scss']
})
export class FormationDetailsComponent implements OnInit {
  formation: Formation;
  canEdit: boolean;
  currentUser: User;

  constructor(private formationService: FormationService, private route: ActivatedRoute,private authenticationService: AuthentificationService, private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    //this.formation = this.formationService.getByCode(this.route.snapshot.paramMap.get('id'));
    this.httpClientService.getFormationByCode(this.route.snapshot.paramMap.get('id')).subscribe(res => { 
      this.formation=res[0];
      console.log(this.formation)
    });
    this.canEdit=this.setEdit();
    this.currentUser = this.authenticationService.currentUser;
  }

  setEdit():boolean{
    /*
    if(this.currentUser.role==="ADMIN") return true;
    else return false;
    */
   return true;
  }
}
