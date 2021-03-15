import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Formation } from 'src/app/models/Formation';
import { User } from 'src/app/models/User';
import { AuthentificationService } from 'src/app/services/authentification.service';
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

  constructor(private route: ActivatedRoute,private authenticationService: AuthentificationService, private httpClientService: HttpClientService, private router: Router) { }

  ngOnInit(): void {
    this.httpClientService.getFormationByCode(this.route.snapshot.paramMap.get('code')).subscribe(res => {
      this.formation=res;
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

  delete(formation_code) {
    if (window.confirm('Voulez-vous vraiment supprimer cette formation ?')){
      this.httpClientService.deleteFormation(this.formation.formation_code).subscribe(res => {
        this.router.navigate(['/admin']);
      })
    }
  }
}
