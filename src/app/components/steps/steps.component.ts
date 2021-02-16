import { Component, OnInit } from '@angular/core';
import { STEPS } from 'src/app/mock/STEPS';
import { Step } from 'src/app/models/Step';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-steps',
  templateUrl: './steps.component.html',
  styleUrls: ['./steps.component.scss']
})
export class StepsComponent implements OnInit {
  fileToUpload: File = null;
  steps: Array<Step> = [];
  pageSize = 10;
  page: number;
  collectionSize: number;

  constructor(private uploadService: UploadService) { }

  ngOnInit(): void {
    this.steps = STEPS;
    this.collectionSize = this.steps.length;
    this.page = this.collectionSize/25;
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
