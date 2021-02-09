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

  add(formation: Formation) {
    if(this.getByCode(formation.formation_code) == null) {
      this.formations.push(formation);
      return true;
    }
    return false;
  }

  update(formation: Formation) {
    let formation_tmp = this.getByCode(formation.formation_code);
    if(formation_tmp != null) {
        this.formations[this.formations.indexOf(formation_tmp)] = formation;
      return [true, `Formation updated`];
    }
    return [false, "This formation doesn't exist, update failed"];
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
