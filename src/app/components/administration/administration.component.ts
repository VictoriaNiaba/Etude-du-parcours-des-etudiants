import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../../services/http-client.service';

@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.scss']
})
export class AdministrationComponent implements OnInit {

  constructor(private httpClientService:HttpClientService) { }

  ngOnInit(): void {
  }

  responseTemp = "";

  getCoucou() {
    
    /*this.httpClientService.getCoucou().subscribe(
      response =>this.handleSuccessfulResponse(response),
     );*/
     this.handleSuccessfulResponse("Coucou works (mock)");
  }
  handleSuccessfulResponse(response)
  {
      this.responseTemp=response;
  }

}
