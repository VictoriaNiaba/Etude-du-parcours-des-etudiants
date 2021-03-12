import { Input, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormControlName } from '@angular/forms';
import { Router } from '@angular/router';
import { element } from 'protractor';
import { Formation } from 'src/app/models/formation';
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

  constructor(private httpClient: HttpClientService, private router: Router) { }

  ngOnInit(): void {
    this.getPaths();
    this.searchInit();
  }

  paths: Path[] = new Array<Path>();
  totalStudentPaths: number;
  firstStep: Step = new Step("POST-BAC", "POST-BAC");
  lastStep: string = "PRSIN5AI";
  getFirstStep(): Step {
    //Temporaire
    return this.firstStep ? this.firstStep : new Step("POST-BAC", "POST-BAC");
  }

  //renseigne "paths" un tableau de path... suivant la base de données
  getPaths() {
    this.httpClient.getPaths(this.firstStep.step_name, this.lastStep).subscribe(res => {

      this.getFirstStep();

      res.forEach(path => {
        let pathTemp = new Path();
        for (let i = 0; i < path['steps'].length; i++) {
          let step = new Step( //init
            path['steps'][i],
            //TODO: récupérer le nom des steps
            path['steps'][i],
            path['registered'][i],
            0, //other
            0); //redoublement
          pathTemp.addSteps(step);
        }
        this.paths.push(pathTemp);
      });
      this.totalStudentPaths = 0;
      this.paths.forEach(path => {
        this.totalStudentPaths += path.getNbStudent();
      });
      this.changeOptions()
    })
  }

  //change les options du graphique
  private changeOptions() {
    let data: any[] = [];
    let links: any[] = [];
    let linksDuplicate: any[] = [];

    //Ajoute le noeud Post BAC si nécessaire
    if (this.getFirstStep().step_code == "POST-BAC") {
      let postBacNode = {
        name: 'POST-BAC',
        value: 'POST-BAC',
        type: 'node'
      }
      data.push(postBacNode);
    }

    let incrementCat = 0;
    this.paths.forEach(path => {
      let pathSteps = path.path_steps;
      //On crée nos noeuds sans duplication
      for (let index = 0; index < pathSteps.length; index++) {
        if (data.length == 0 || data.filter(element => element.name == pathSteps[index].step_code).length == 0) {
          let tmpData = {
            name: pathSteps[index].step_code,
            value: pathSteps[index].step_name,
            category: incrementCat,
            type: 'node'
          }
          data.push(tmpData);
        }


        //On cherche les liens qui peuvent être dupliqués
        let currentSourceStepCode: String = index - 1 < 0 ? this.getFirstStep().step_code : pathSteps[index - 1].step_code;
        let linkFilter = links.filter(link =>
          link.source == currentSourceStepCode
          && link.target == pathSteps[index].step_code
        );

        let tmpLink = {
          source: currentSourceStepCode,
          target: pathSteps[index].step_code,
          value: pathSteps[index].getStatsTotal(),
          label: {
            show: true,
            formatter: function (params) {
              return params['value']
            }
          }
        }

        //Si le lien n'existe pas déjà on le crée
        if (linkFilter.length == 0) {
          links.push(tmpLink);
        }
        //On stock les liens qui sont dupliqués
        else {
          linksDuplicate.push(tmpLink);
        }
      }

      incrementCat++;
    });

    //On ajoute la valeur des liens dupliqués au liens existants
    linksDuplicate.forEach(linkDuplicate => {
      links.forEach(link => {
        if (linkDuplicate.source == link.source && linkDuplicate.target == link.target) {
          link.value += linkDuplicate.value;
        }
      })
    })

    this.chartOptions = {
      title: {
        text: 'Graphe'
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
      /*legend: [{
          data: data.map(category => category['name'])
      }],*/
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
            repulsion: 1000,
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
          zoom: 0.6,
          categories: data,
        }
      ]
    };
    console.log("Graph updated")
  }


  /* https://echarts.apache.org/en/api.html#echartsInstance.on 
  >>>  If Object, one or more properties below can be included, and any of them is optional.
  */
  @Input() statsComponent: EpuStatsComponent;
  chartClicked(e: any) {
    if (e.dataType === 'node')
      this.stepClick(e.name);
  }
  stepClick(name: string) {
    //trouver le step cliqué dans paths puis récupérer les statistiques et envoyer à la place de name puis modifier setFormation + affichage
    let res: Step;
    this.paths.forEach(path => {
      path.path_steps.filter(x => {
        if (x.step_name === name) {
          res = x;
          return;
        }
      });
      if (res) return;
    });
    if (res)
      return this.statsComponent.setFormation(res);
    return console.error('Click failed');
  }



  /*-----------------------------Recherche------------------------------------*/
  keyword = 'key';
  dataSearch: any[] =[];
  nodesStart = new Array<any>();
  nodesEnd = new Array<any>();

  searchInit() {
    this.httpClient.getFormations().subscribe(res => {
      for(let i=0; i<res.length; i++){
        let tmpData = {
          code: res[i].formation_code,
          name: res[i].formation_name,
          type: "formation",
          //permet la recherche multiple
          key: res[i].formation_code + res[i].formation_name
        }
        this.dataSearch.push(tmpData);
      }
    });

    this.httpClient.getSteps().subscribe(res => {
      for(let i=0; i<res.length; i++){
        let tmpData = {
          code: res[i].step_code,
          name: res[i].step_name,
          type: "step",
          //permet la recherche multiple
          key: res[i].step_code + res[i].step_name
        }
        this.dataSearch.push(tmpData);
      }
    })
  }

  search() {
    let nodesCodeStart = this.nodesStart.map(node => node);
    let nodesCodeEnd = this.nodesEnd.map(node => node);
    console.info("Recherche :");
    console.info("START", nodesCodeStart);
    console.info("END", nodesCodeEnd);

    //Listes finales pour get les cheminements
    let stepsStart = [];
    let stepsEnd = [];

    //On récupère les codes des étapes de la formation sinon le code de l'étape directement
    nodesCodeStart.forEach(element => {
      if(element.type === "formation"){
        this.httpClient.getFormationByCode(element.code).subscribe(res => {
          res.steps.forEach(step => {
            this.httpClient.getStepByCode(step).subscribe(res => {
              stepsStart.push(res.step_code);
            })
          });
        });
      }
      else stepsStart.push(element.code);
    });
    //De même pour la recherche d'arrivée
    nodesCodeEnd.forEach(element => {
      if(element.type === "formation"){
        this.httpClient.getFormationByCode(element.code).subscribe(res => {
          res.steps.forEach(step => {
            this.httpClient.getStepByCode(step).subscribe(res => {
              stepsStart.push(res.step_code);
            })
          });
        });
      }
      else stepsEnd.push(element.code);
    });
    console.log("Steps Start", stepsStart);
    console.log("Steps End", stepsEnd);

    /*
    this.httpClient.getPaths(stepsStart, stepsEnd).subscribe( ... ); //pour la mise en place de la recherche multiple
    */
  }

  removeFromArray(array: any[], item: any) {
    array.forEach((element,index)=>{
      if(item==element) array.splice(index,1);
    });
  }
  removeFromNodeStart(item: any) {
    this.removeFromArray(this.nodesStart, item);
    this.dataSearch.push(item);
    this.search();
  }
  removeFromNodeEnd(item: any) {
    this.removeFromArray(this.nodesEnd, item);
    this.dataSearch.push(item);
    this.search();
  }

  @ViewChild('searchStart') searchStart;
  selectEventStart(event) {
    if(this.nodesStart.find(node => node.code == event.code)) {
      alert("Node déjà ajoutée.")
      return;
    }
    this.nodesStart.push(
      this.dataSearch.find(node => node.code == event.code)
    );
    this.removeFromArray(
      this.dataSearch,
      this.dataSearch.find(node => node.code == event.code)
    );
    this.searchStart.clear();
    this.search();
  }

  @ViewChild('searchEnd') searchEnd;
  selectEventEnd(event) {
    if(this.nodesEnd.find(node => node.code == event.code)) {
      alert("Formation déjà ajoutée.")
      return;
    }
    this.nodesEnd.push(
      this.dataSearch.find(node => node.code == event.code)
    );
    this.removeFromArray(
      this.dataSearch,
      this.dataSearch.find(node => node.code == event.code)
    );
    this.searchEnd.clear();
    this.search();
  }
}
