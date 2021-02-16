import { Component, OnInit } from '@angular/core';
import { RegistrationService } from 'src/app/services/registration.service';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-registrations',
  templateUrl: './registrations.component.html',
  styleUrls: ['./registrations.component.scss']
})
export class RegistrationsComponent implements OnInit {

  constructor(private registrationService: RegistrationService, private uploadService: UploadService) { }

  totalRegistration = 0;
  fileToUpload: File = null;

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

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.uploadService.postFile(this.fileToUpload).subscribe(data => {

      }, error => {
        console.log(error);
      });
  }

}
