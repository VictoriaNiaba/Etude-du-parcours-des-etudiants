import { Component, OnInit } from '@angular/core';
import { Formation } from 'src/app/models/formation';
import { FormationService } from 'src/app/services/formation.service';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-formations',
  templateUrl: './formations.component.html',
  styleUrls: ['./formations.component.scss']
})
export class FormationsComponent implements OnInit {
  formations: Array<Formation>;
  pageSize = 10;
  page: number;
  collectionSize: number;
  fileToUpload: File = null;

  constructor(private formationService: FormationService, private uploadService: UploadService) { }

  ngOnInit(): void {
    this.formations = this.formationService.getAll();
    this.collectionSize = this.formations.length;
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
