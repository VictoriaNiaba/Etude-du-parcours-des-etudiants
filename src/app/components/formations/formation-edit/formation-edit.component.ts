import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Formation } from 'src/app/models/formation';
import { Step } from 'src/app/models/Step';
import { HttpClientService } from 'src/app/services/http-client.service';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

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
  code: string = null;

  constructor(private route: ActivatedRoute, private router: Router, private formBuilder: FormBuilder, private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    //-------------Initialisation----------------
    this.code = this.route.snapshot.paramMap.get('code');
    if (this.code === null) { this.isAddMode = true; }
    else { this.isAddMode = false; }

    //Custom Validator pour savoir si le code utilisé n'est pas déjà pris
    const uniqueIdValidator = (control: AbstractControl): { [key: string]: boolean } | null => {
      let formationTmp: Formation [] = [];
      this.httpClientService.getFormations().subscribe(res => {
        formationTmp = res;
        formationTmp.forEach(f =>{
          if(control.value === f.formation_code && control.value !== undefined && control.value != this.code){
            console.log(f.formation_code, control.value)
            return { uniqueId: true };
          }
        });
      });
      return null;
    }

    this.editForm = this.formBuilder.group({
      formation_code: ['', [Validators.required, uniqueIdValidator]],
      formation_name: ['', [Validators.required]],
      description: ['', Validators.required],
      type: ['', [Validators.required]],
      url: ['', [Validators.required]],
    });


    //Préparation des listes pour le drag and drop
    //On récupère toutes les étapes
    this.httpClientService.getSteps().subscribe(res => {
      this.steps = res;
      if (!this.isAddMode) {
        //On récupère les infos de la formation
        this.httpClientService.getFormationByCode(this.route.snapshot.paramMap.get('code')).subscribe(res => {
          this.formation = res;
          this.editForm.setValue({ formation_code: this.formation.formation_code, formation_name: this.formation.formation_name, description: this.formation.description, type: this.formation.type, url: this.formation.url });
          this.formation.steps.forEach(element => {
            //Récupérer l'étape à partir du code
            this.httpClientService.getStepByCode(element).subscribe(res => {
              //On l'ajoute à la liste des étapes de la formation
              this.stepsOfFormation.push(res);
              //Pour le faire qu'une fois
              if (this.formation.steps.indexOf(element) == this.formation.steps.length - 1) {
                //Pour éviter des duplications dans les listes
                this.stepsOfFormation.forEach(sf => {
                  this.steps.forEach(step => {
                    if (sf.step_code === step.step_code) {
                      //On enlève l'item dupliqué dans la liste d'étapes
                      this.steps.splice(this.steps.indexOf(step), 1);
                    }
                  })
                })
              }
            });
          });
        });
      }
    });
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

      //On crée une nouvelle formation
      this.formation = new Formation(this.editForm.value.formation_code, this.editForm.value.formation_name, this.editForm.value.description, this.editForm.value.type, this.editForm.value.url, tmpList, new Date, new Date);
      this.create(this.formation);
      console.log(this.formation);
    } else {
      //On met à jour les valeurs en mode édition
      this.formation.formation_code = this.editForm.value.formation_code;
      this.formation.formation_name = this.editForm.value.formation_name;
      this.formation.description = this.editForm.value.description;
      this.formation.type = this.editForm.value.type;
      this.formation.url = this.editForm.value.url;
      this.formation.last_modification = new Date;

      //Pour obtenir seulement les codes
      this.formation.steps = [];
      this.stepsOfFormation.forEach(element => this.formation.steps.push(element.step_code));

      this.update(this.formation);
    }
  }

  create(data: Formation) {
    this.httpClientService.addFormation(data).subscribe((data: {}) => {
      this.router.navigate(['/formation', this.formation.formation_code]);
    })
  }

  update(data) {
    this.httpClientService.updateFormation(this.code, data).subscribe(res => {
      this.router.navigate(['/formation', this.editForm.value.formation_code]);
    })
  }

  //Pour le drag and drop des étapes
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
