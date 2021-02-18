import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-formations',
  templateUrl: './formations.component.html',
  styleUrls: ['./formations.component.scss']
})
export class FormationsComponent implements OnInit {
  formations: any;
  pageSize = 10;
  page: number;
  collectionSize: number;
  fileToUpload: File = null;

  constructor(private uploadService: UploadService, private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getFormations().subscribe(res => { 
      this.formations=res;
      console.log(this.formations)
      this.collectionSize = this.formations.length;
      this.page = this.collectionSize/25;
    });
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.uploadService.postFile(this.fileToUpload, "http://localhost:8080/steps").subscribe(data => {

      }, error => {
        console.log(error);
      });
  }

}
