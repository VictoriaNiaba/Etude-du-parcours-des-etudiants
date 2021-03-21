import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-formations',
  templateUrl: './formations.component.html',
  styleUrls: ['./formations.component.scss']
})
export class FormationsComponent implements OnInit {
  formations: any;
  pageSize = 30;
  page: number;
  collectionSize: number;
  fileToUpload: File = null;
  searchWord: String;

  constructor(private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getFormations().subscribe(res => { 
      this.formations=res;
      this.collectionSize = this.formations.length;
      this.page = this.collectionSize/this.collectionSize;
    },
    (error) => {
        console.warn("Handle (getFormations) :", error)
      }
    );
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToService() {
    this.httpClientService.postFile(this.fileToUpload, "/formations/_upload").subscribe(data => {
        this.ngOnInit();
      },
      (error) => {
          console.warn("Handle (postFile) :", error)
          this.ngOnInit();
        }
      );
  }

  search(){
    if(this.searchWord != ""){
      this.formations = this.formations.filter(res => {
        return res.formation_code.toLocaleLowerCase().match(this.searchWord.toLocaleLowerCase());
      });
    } else if(this.searchWord == "") {
      this.ngOnInit();
    }
  }
}
