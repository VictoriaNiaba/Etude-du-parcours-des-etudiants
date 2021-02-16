import { Injectable } from '@angular/core';
import { Formation } from '../models/Formation';
import { FORMATIONS } from '../mock/FORMATIONS';

@Injectable({
  providedIn: 'root'
})
export class FormationService {
  formations: Formation[];

  constructor() {
    this.formations = FORMATIONS;
  }

  getAll() {
    return this.formations;
  }

  getByCode(code: string) {
    let formation = this.formations.find(frm => frm.formation_code === code);
    if(!formation) {
      console.info(`No formation found with code ${code}`);
      return null;
    } else {
      return formation;
    }
  }

  create(params): string {
    //FORMATIONS.push(new Formation(params.formation_code, params.formation_name, params.description, params.type, params.url, []));
    //return params.code_formation;
    return "";
  }

  update(code: string, params) {
    let index = FORMATIONS.findIndex((f => f.formation_code === code));
    FORMATIONS[index].formation_code = params.formation_code;
    FORMATIONS[index].formation_name = params.formation_name;
    FORMATIONS[index].description = params.description;
    FORMATIONS[index].type = params.type;
    FORMATIONS[index].url = params.url;
  }

  delete(formation: Formation) {
    let formation_tmp = this.getByCode(formation.formation_code);
    if(formation_tmp != null) {
      this.formations = this.formations.filter(obj => obj !== formation);
      return [true, `Formation deleted`];
    }
    return [false, "This formation doesn't exist, deletion failed"];
  }
}
