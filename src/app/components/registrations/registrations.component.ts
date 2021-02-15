import { Component, OnInit } from '@angular/core';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-registrations',
  templateUrl: './registrations.component.html',
  styleUrls: ['./registrations.component.scss']
})
export class RegistrationsComponent implements OnInit {

  constructor(private registrationService: RegistrationService) { }

  totalRegistration = 0;

  ngOnInit(): void {
    this.totalRegistration = this.registrationService.getAllRegistrations();
  }

  getNumberByYear(year:number):number {
    return this.registrationService.getNumberOfRegistrationsByYear(year);
  }

  getYears() {
    return this.registrationService.getAllYearAvailable();
  }

  getYearTimeStamp(year:number) {
    return this.registrationService.getTimestampOfRegistrationByYear(year);
  }

}
