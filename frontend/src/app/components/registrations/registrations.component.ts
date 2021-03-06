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
  isLoading = false;

  ngOnInit(): void {
    this.httpClientService.getRegistrations().subscribe(res => {
      this.registrations = res;
      this.totalRegistration = 0;
      this.registrations.forEach(registrations => {
        this.totalRegistration += registrations['registrationCount']
      });
    },
    (error) => {
        console.warn("Handle (getRegistrations) :", error)
      }
    );
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.isLoading = true;
    this.httpClientService.postFile(this.fileToUpload, "/registrations/_upload").subscribe(data => {
      this.ngOnInit();
      this.isLoading = false;
      window.location.reload();
    },
    (error) => {
        console.warn("Handle (postFile) :", error);
        this.ngOnInit();
        this.isLoading = false;
        window.location.reload();
      }
    );

    this.ngOnInit(); //reload
  }

}
