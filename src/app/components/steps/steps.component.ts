import { Component, OnInit } from '@angular/core';
import { Step } from 'src/app/models/Step';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-steps',
  templateUrl: './steps.component.html',
  styleUrls: ['./steps.component.scss']
})
export class StepsComponent implements OnInit {
  fileToUpload: File = null;
  steps: Array<Step> = [];
  pageSize = 30;
  page: number;
  collectionSize: number;
  searchWord: String;

  constructor(private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getSteps().subscribe(res => { 
      this.steps=res;
      console.log(this.steps)
      this.collectionSize = this.steps.length;
      this.page = this.collectionSize/this.collectionSize;
    });
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.httpClientService.postFile(this.fileToUpload, "/steps/_upload").subscribe(data => {

      }, error => {
        console.log(error);
      });
  }

  search(){
    if(this.searchWord != ""){
      this.steps = this.steps.filter(res => {
        return res.step_code.toLocaleLowerCase().match(this.searchWord.toLocaleLowerCase());
      });
    }else if(this.searchWord == ""){
      this.ngOnInit();
    }
  }
}
