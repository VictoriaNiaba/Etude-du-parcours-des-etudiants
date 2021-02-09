import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  all_registrations: number;
  nb_registrations: number;
  timestamp: Date;

  constructor() { 
    this.all_registrations = 81000;
    this.nb_registrations = 13000;
    this.timestamp = new Date();
  }

  getAllRegistrations():number{
    return this.all_registrations;
  }

  getNumberOfRegistrationsByYear(year: number):number{
    return this.nb_registrations;
  }

  getTimestampOfRegistrationByYear(year: number):Date{
    return this.timestamp;
  }
}
