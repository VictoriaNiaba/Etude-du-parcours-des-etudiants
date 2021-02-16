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

  coucou:string;
  getCoucou() {
    this.httpClientService.getCoucou().subscribe(res => {this.coucou = res.message});
  }

}
