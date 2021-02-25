import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Path } from 'src/app/models/Path';
import { Step } from 'src/app/models/Step';
import { HttpClientService } from 'src/app/services/http-client.service';
import { EpuStatsComponent } from '../epu-stats/epu-stats.component';

@Component({
  selector: 'app-epu-graphe',
  templateUrl: './epu-graphe.component.html',
  styleUrls: ['./epu-graphe.component.scss']
})
export class EpuGrapheComponent implements OnInit {

  chartOptions: any;

  constructor(private httpClient: HttpClientService,private router: Router) { }

  ngOnInit(): void {
    this.getPaths();
    this.initSearch();
    this.changeOptions();
  }

  changeOptions() {

    //for...
    let data:any[] = [
      {
        name: 'MESSIN-PRSIN5AA',
        value: 'BAC S',
        type: 'node',
        //x: 300,
        //y: 300
      }, {
        name: 'MESSIN-PRSIN5AB',
        value: 'Licence informatique',
        type: 'node',
        //x: 500,
        //y: 200
      }, {
        name: 'MESSIN-PRSIN5AC',
        value: 'Master informatique',
        type: 'node',
        //x: 700,
        //y: 300
      }, {
        name: 'MESSIN-PRSIN5AI',
        value: 'BTS SNIR',
        type: 'node',
        //x: 500,
        //y: 400
      }
    ];

    let links:any = [
      {
        source: 'MESSIN-PRSIN5AA',
        target: 'MESSIN-PRSIN5AB',
        value: 11, //valeur sur le label
        label: { //obligatoire pour afficher value (??? pas trouvé mieux donc faut répéter ?)
            show: true,
            formatter: function (params) {
              return params['value']
            }
        }
      }, {
        source: 'MESSIN-PRSIN5AB',
        target: 'MESSIN-PRSIN5AC',
        value: 22,
        label: {
            show: true,
            formatter: function (params) {
              return params['value']
            }
        }
      }, {
        source: 'MESSIN-PRSIN5AI',
        target: 'MESSIN-PRSIN5AB',
        value: 33,
        label: {
            show: true,
            formatter: function (params) {
              return params['value']
            }
        }
      }, {
        source: 'MESSIN-PRSIN5AA',
        target: 'MESSIN-PRSIN5AI',
        value: 44,
        label: {
            show: true,
            formatter: function (params) {
              return params['value']
            }
        }
      }
    ];

    this.chartOptions = {
      title: {
        text: 'Test'
      },
      tooltip: {
        trigger: 'item',
        showDelay: 0.1,
        transitionDuration: 0.2,
        show: true,
        formatter: function (params) {
          if (params.dataType == 'node')
            return `<div class="text-secondary" style="font-size:10">${params.name}</div>${params.value}`;
          else
            return "";
        }
      },
      animationDurationUpdate: 1500,
      animationEasingUpdate: 'quinticInOut',
      series: [
        {
          type: 'graph',
          layout: 'force', //none
          draggable: true, //only force
          force: { //only force
            //initLayout: 'circular',
            gravity: 0,
            repulsion: 999,
            edgeLength: 200
          },
          symbolSize: 60,
          roam: true,
          label: {
              show: true,
              position: 'top',
              formatter: function (params) {
                let labelText: string = params['value'];
                let nb2show = 15;
                if (labelText.length < nb2show)
                  return labelText;
                else
                  return labelText.slice(0, nb2show) + "...";
            }
          },
          edgeSymbol: ['circle', 'arrow'],
          edgeSymbolSize: [4, 10],
          data: data,
          links: links,
          lineStyle: {
              opacity: 0.9,
              width: 2,
              curveness: 0.1
          },
          zoom: 0.8
        }
      ]
    };
    console.log("Graph updated")
  }


  paths: Path[] = new Array<Path>();
  totalStudentPaths: number;
  firstStep: string = null;
  lastStep: string = "PRSIN5AI";
  getFirstStep() {
    return this.firstStep ? this.firstStep : "POST-BAC";
  }
  getPaths(){
    this.httpClient.getPaths(this.firstStep, this.lastStep).subscribe(res => {

      res.forEach(path => {
        let pathTemp = new Path();
        for(let i=0; i<path['steps'].length; i++) {
          let step = new Step( //init
            path['steps'][i],
            path['steps'][i],
            path['registered'][i],
            0, //other
            0); //redoublement
          pathTemp.addSteps(step);
          //console.log('>>',step.step_name);
        }
        //console.log('>> mean : ',pathTemp.getMeanStudents());
        this.paths.push(pathTemp);
        //console.log('>> -----');
      });

      this.totalStudentPaths = 0;
      this.paths.forEach(path => {
        this.totalStudentPaths += path.getNbStudent(); 
      });
    });
  }


  /* https://echarts.apache.org/en/api.html#echartsInstance.on 
  >>>  If Object, one or more properties below can be included, and any of them is optional.
  */
  @Input() statsComponent: EpuStatsComponent;
  chartClicked(e: any) {
    if (e.dataType === 'node')
      this.stepClick(e.name);
  }
  stepClick(name:string){
    //trouver le step cliqué dans paths puis récupérer les statistiques et envoyer à la place de name puis modifier setFormation + affichage
    let res: Step;
    this.paths.forEach(path => {
      path.path_steps.filter( x => {
        if(x.step_name === name) {
          res = x;
          return;
        }
      });
      if(res) return;
    });
    if(res)
       return this.statsComponent.setFormation(res);
    return console.error('Click failed');
  }

  /*Recherche*/
  keyword = 'formation_name';
  formationSearch: any[];
  initSearch() {
    this.httpClient.getFormations().subscribe(res => {
      this.formationSearch = res; //peut être limité à id + formation_name sauf si on doit utiliser toutes les données de formation dans le component !
    });
  }
  selectEvent(event) {
    console.info(`Selection : ${event.id}`);
  }
  onChangeSearch(event) {
    console.info(`Change : ${event.id}`);
  }
}
