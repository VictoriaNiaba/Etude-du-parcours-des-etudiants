import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Formation } from 'src/app/models/formation';
import { Step } from 'src/app/models/Step';
import { HttpClientService } from 'src/app/services/http-client.service';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

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
  steps: Array<Step> = [];
  stepsOfFormation: Array<Step> = [];
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
    this.httpClientService.getSteps().subscribe(res=>{
      this.steps = res;
    });
    if (!this.isAddMode) {
      this.httpClientService.getFormationByCode(this.route.snapshot.paramMap.get('id')).subscribe(res => {
        this.formation = res[0];
        this.editForm.setValue({ id: this.formation.id, formation_name: this.formation.formation_name, description: this.formation.description, type: this.formation.type, url: this.formation.url });
        this.formation.steps.forEach(element => {
          this.httpClientService.getStepByCode(element).subscribe(res => { 
            this.stepsOfFormation.push(res[0])
          });
        });
        //Pour éviter des duplications dans les listes
        if(this.stepsOfFormation != null && this.steps != null){
          let stepsTmp = this.steps
          this.steps = this.stepsOfFormation.concat(stepsTmp);
          this.steps = this.steps.filter((item,index) => this.steps.indexOf(item)==index);
          this.steps = [...new Set([...stepsTmp, ...this.stepsOfFormation])]
        }
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
      //Pour obtenir seulement les codes
      let tmpList: string[] = [];
      this.stepsOfFormation.forEach(element => tmpList.push(element.step_code));
      this.formation = new Formation(this.editForm.value.id,this.editForm.value.formation_name,this.editForm.value.description,this.editForm.value.type,this.editForm.value.url, tmpList,new Date,new Date);
      this.create(this.formation);
      console.log(this.formation);
    } else {
      this.formation.id = this.editForm.value.id;
      this.formation.formation_name = this.editForm.value.formation_name;
      this.formation.description = this.editForm.value.description;
      this.formation.type = this.editForm.value.type;
      this.formation.url = this.editForm.value.url;
      //Pour obtenir seulement les codes
      this.formation.steps = [];
      this.stepsOfFormation.forEach(element => this.formation.steps.push(element.step_code));
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

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
    }
  }
}
