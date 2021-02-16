import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Formation } from 'src/app/models/formation';
import { FormationService } from 'src/app/services/formation.service';

@Component({
  selector: 'app-formation-edit',
  templateUrl: './formation-edit.component.html',
  styleUrls: ['./formation-edit.component.scss']
})
export class FormationEditComponent implements OnInit {

  formation: Formation;
  editForm: FormGroup;
  isAddMode: Boolean;
  submitted = false;
  id: string;

  constructor(private formationService: FormationService, private route: ActivatedRoute, private router: Router, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if(this.id === null) {this.isAddMode = true;}
    else {this.isAddMode = false;}
    this.editForm = this.formBuilder.group({
      formation_code: ['', [Validators.required]],
      formation_name: ['', [Validators.required]],
      description: ['', Validators.required],
      type: ['', [Validators.required]],
      url: ['', [Validators.required]],
    });
    if (!this.isAddMode) {
      this.formation = this.formationService.getByCode(this.id)
      this.editForm.setValue({formation_code:this.formation.formation_code,formation_name:this.formation.formation_name, description:this.formation.description, type:this.formation.type,url:this.formation.url});
    }
    else{
    }
  }

  get formControl() { return this.editForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.editForm.invalid) {
        return;
    }

    if (this.isAddMode) {
        this.createSite();
    } else {
        this.updateSite();
    }
  }

  private createSite() {
    let newId = this.formationService.create(this.editForm.value);
    this.router.navigate(['/formation',newId]);
  }

  private updateSite() {
    this.formationService.update(this.id, this.editForm.value);
    this.router.navigate(['/formation',this.editForm.value.formation_code]);
  }

}
