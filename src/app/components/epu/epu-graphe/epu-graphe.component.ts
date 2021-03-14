import { Input, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Path } from 'src/app/models/Path';
import { StepPath } from 'src/app/models/Step';
import { HttpClientService } from 'src/app/services/http-client.service';
import { StepsService } from 'src/app/services/steps.service';
import { EpuStatsComponent } from '../epu-stats/epu-stats.component';

@Component({
  selector: 'app-epu-graphe',
  templateUrl: './epu-graphe.component.html',
  styleUrls: ['./epu-graphe.component.scss']
})
export class EpuGrapheComponent implements OnInit {

  chartOptions: any;

  constructor(private httpClient: HttpClientService, private stepsService: StepsService, private router: Router) { }

  ngOnInit(): void {
    this.getPaths("SIN1T0", "SIN5AB");
    this.searchInit();
  }

  paths: Path[] = new Array<Path>();
  totalStudentPaths: number;
  firstStep: StepPath = new StepPath("POST-BAC", "POST-BAC");

  //Pour la sélection du cheminement à mettre en surbrillance
  uniquePaths = [];
  pathSelectedIndex: number = 0;
  slideValue: number =  1;

  getFirstStep(): StepPath {
    return this.firstStep;
  }

  setFirstStep(code: string){
    if(code != ""){
      this.httpClient.getStepByCode(code).subscribe(res => {
        this.firstStep = res;
      });
    }
    else this.firstStep = new StepPath("POST-BAC", "POST-BAC");
  }

  //renseigne "paths" un tableau de path... suivant la base de données
  getPaths(stepsStart: string, stepsEnd: string) {
    //permet d'obtenir seulement le premier code
    this.setFirstStep(stepsStart.slice(0, 6));

    //Pour bien former la requête attendu au près du backend
    if(stepsStart === "") stepsStart = null;
    if(stepsEnd === "") stepsEnd = null;
    
    this.paths = new Array<Path>();

    this.httpClient.getPaths(stepsStart, stepsEnd).subscribe(res => {
      this.getFirstStep();

      res.forEach(path => {
        let pathTemp = new Path();
        for (let i = 0; i < path['steps'].length; i++) {
          let stepName = this.stepsService.getByCode(path['steps'][i]);
          let step = new StepPath( //init
            path['steps'][i],
            stepName,
            path['registered'][i]);
          pathTemp.addSteps(step);
        }
        this.paths.push(pathTemp);
      });
      this.totalStudentPaths = 0;
      this.paths.forEach(path => {
        this.totalStudentPaths += path.getNbStudent();
      });
      this.uniquePaths = [];
      this.displayUniquePaths();
      this.changeOptions();
      this.pathSelectedIndex = 0;
    });
  }

  //Permet d'éviter les cheminements avec des redoublements et ceux qui ont la même suite d'étapes
  displayUniquePaths(){
    let tmpPaths = [];
    this.paths.forEach(element => {
      tmpPaths.push(element.path_steps.map(item => item.step_code));
    });

    for(let i=1; i<this.paths.length-1; i++){
      let tmpPath = this.paths[i].path_steps.map(item => item.step_code);
      if(!tmpPaths.includes(tmpPath) && new Set(tmpPath).size == tmpPath.length) this.uniquePaths.push(this.paths[i]);
    }
  }

  switchPath(index: number){
    let pathTmp = this.uniquePaths[index];
    this.uniquePaths[index] = this.paths[0];
    this.paths[0] = pathTmp;
    this.pathSelectedIndex = index;
    this.changeOptions();
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
          value: pathSteps[index].step_number,
          label: {
            show: true,
            formatter: function (params) {
              return params['value']
            }
          },
          lineStyle: {
            color: incrementCat==0 ? "rgba(255, 113, 113, 1)" : "rgba(0, 0, 0, 1)"
          },
          itemStyle: {
            color: incrementCat==0 ? "rgba(255, 113, 113, 1)" : "rgba(0, 0, 0, 1)",
            borderColor: "rgba(0, 0, 0, 1)"
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

      //incrementCat++;
      incrementCat = 1;
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
            return `<div class="text-secondary" style="font-size:10">${params.value}</div>${params.name}`;
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
                return labelText.slice(4, nb2show) + "...";
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
          itemStyle: {},
          zoom: 0.6,
          categories: data
        }
      ]
    };
    console.log("Graph updated");
  }


  /* https://echarts.apache.org/en/api.html#echartsInstance.on 
  >>>  If Object, one or more properties below can be included, and any of them is optional.
  */
  @Input() statsComponent: EpuStatsComponent;
  chartClicked(e: any) {
    if (e.dataType === 'node')
      this.stepClick(e.name);
  }
  stepClick(code: string) {
    //trouver le step cliqué dans paths puis récupérer les statistiques et envoyer à la place de name puis modifier setFormation + affichage
    let res: StepPath;
    this.paths.forEach(path => {
      path.path_steps.filter(x => {
        if (x.step_code === code) {
          res = x;
          return;
        }
      });
      if (res) return;
    });
    if (res)
      return this.statsComponent.setFormation(res.step_code);
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

    //Strings finales pour obtenir les codes des étapes pour générer les cheminements
    let stepsStart = "";
    let stepsEnd = "";

    //On récupère les codes des étapes de la formation sinon le code de l'étape directement
    nodesCodeStart.forEach(element => {
      if(element.type === "formation"){
        this.httpClient.getFormationByCode(element.code).subscribe(res => {
          res.steps.forEach(step => {
            stepsStart.concat(step+',');
          });
        });
      }
      else stepsStart = stepsStart.concat(element.code+',');
    });
    //De même pour la recherche d'arrivée
    nodesCodeEnd.forEach(element => {
      if(element.type === "formation"){
        this.httpClient.getFormationByCode(element.code).subscribe(res => {
          res.steps.forEach(step => {
            stepsStart.concat(step+',');
          });
        });
      }
      else stepsEnd = stepsEnd.concat(element.code+',');
    });

    //Pour enlever la dernière virgule
    stepsStart = stepsStart.slice(0, stepsStart.length-1);
    stepsEnd = stepsEnd.slice(0, stepsEnd.length-1);
    console.log("Steps Start", stepsStart);
    console.log("Steps End", stepsEnd);

    //On appelle la génération du graphe
    this.getPaths(stepsStart,stepsEnd);
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
      alert("Node déjà ajouté.")
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
      alert("Node déjà ajouté.")
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
