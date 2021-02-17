import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-registrations',
  templateUrl: './registrations.component.html',
  styleUrls: ['./registrations.component.scss']
})
export class RegistrationsComponent implements OnInit {

  constructor(private uploadService: UploadService, private httpClient: HttpClientService) { }

  totalRegistration = 0;
  registrations: any;



  fileToUpload: File = null;

  ngOnInit(): void {
    this.httpClient.getRegistrations().subscribe(res => {
      console.info(res);
      this.registrations = res;
      this.totalRegistration = 0;
      this.registrations.forEach(registrations => {
        this.totalRegistration += registrations['nb_registrations']
      });
      this.totalRegistration = 10000; // ;) ;) ;) ;)
    });
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.uploadService.postFile(this.fileToUpload, "http://localhost:8080/registration").subscribe(data => {

      }, error => {
        console.log(error);
      });

      this.ngOnInit(); //reload
  }

}
