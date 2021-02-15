import { Component, OnInit } from '@angular/core';
import { Formation } from 'src/app/models/formation';
import { FormationService } from 'src/app/services/formation.service';

@Component({
  selector: 'app-formations',
  templateUrl: './formations.component.html',
  styleUrls: ['./formations.component.scss']
})
export class FormationsComponent implements OnInit {
  formations: Array<Formation>;

  constructor(private formationService: FormationService) { }

  ngOnInit(): void {
    this.formations = this.formationService.getAll();
  }

}
