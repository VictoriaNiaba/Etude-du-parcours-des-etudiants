import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Formation } from 'src/app/models/formation';
import { HttpClientService } from 'src/app/services/http-client.service';

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

  constructor(private route: ActivatedRoute, private router: Router, private formBuilder: FormBuilder, private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    if (this.id === null) { this.isAddMode = true; }
    else { this.isAddMode = false; }
    this.editForm = this.formBuilder.group({
      id: ['', [Validators.required]],
      formation_name: ['', [Validators.required]],
      description: ['', Validators.required],
      type: ['', [Validators.required]],
      url: ['', [Validators.required]],
    });
    if (!this.isAddMode) {
      this.httpClientService.getFormationByCode(this.route.snapshot.paramMap.get('id')).subscribe(res => {
        this.formation = res[0];
        this.editForm.setValue({ id: this.formation.id, formation_name: this.formation.formation_name, description: this.formation.description, type: this.formation.type, url: this.formation.url });
      });
    }
  }

  get formControl() { return this.editForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.editForm.invalid) {
      return;
    }

    if (this.isAddMode) {
      this.formation = new Formation(this.editForm.value.id,this.editForm.value.formation_name,this.editForm.value.description,this.editForm.value.type,this.editForm.value.url,[],new Date,new Date);
      this.create(this.formation);
      console.log(this.formation);
    } else {
      this.formation.id = this.editForm.value.id;
      this.formation.formation_name = this.editForm.value.formation_name;
      this.formation.description = this.editForm.value.description;
      this.formation.type = this.editForm.value.type;
      this.formation.url = this.editForm.value.url;
      this.formation.last_modification = new Date;
      this.update(this.formation);
    }
  }

  create(data: Formation) {
    this.httpClientService.addFormation(data).subscribe((data: {}) => {
      this.router.navigate(['/formation', this.formation.id]);
    })
  }

  update(data) {
    this.httpClientService.updateFormation(this.id, data).subscribe(res =>{
      this.router.navigate(['/formation', this.editForm.value.id]);
    })
  }

}
