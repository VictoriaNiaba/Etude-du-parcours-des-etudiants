import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-registrations',
  templateUrl: './registrations.component.html',
  styleUrls: ['./registrations.component.scss']
})
export class RegistrationsComponent implements OnInit {

  constructor(private httpClientService: HttpClientService) { }

  totalRegistration = 0;
  registrations: any;

  fileToUpload: File = null;

  ngOnInit(): void {
    this.httpClientService.getRegistrations().subscribe(res => {
      //console.info(res);
      this.registrations = res;
      this.totalRegistration = 0;
      this.registrations.forEach(registrations => {
        this.totalRegistration += registrations['registrationCount']
      });
    });
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.httpClientService.postFile(this.fileToUpload, "/registrations/_upload").subscribe(data => {
      this.ngOnInit();
    }, error => {
      //console.log(error);
      this.ngOnInit();
    });

    this.ngOnInit(); //reload
  }

}
