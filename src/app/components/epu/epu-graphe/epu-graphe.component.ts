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

  constructor(private httpClient: HttpClientService, private router: Router) { }

  ngOnInit(): void {
    this.getPaths();
    this.initSearch();
  }

  paths: Path[] = new Array<Path>();
  totalStudentPaths: number;
  firstStep: Step = new Step("POST-BAC", "POST-BAC");
  lastStep: string = "PRSIN5AI";
  getFirstStep(): Step {
    //Temporaire
    return this.firstStep ? this.firstStep : new Step("POST-BAC", "POST-BAC");
  }
  getPaths() {
    this.httpClient.getPaths(this.firstStep.step_name, this.lastStep).subscribe(res => {
      //Temporaire
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
      this.changeOptions()
    })
  }

  changeOptions() {
    let data: any[] = [];
    let links: any[] = [];
    let linksDuplicate: any[] = [];

    //Noeud Post BAC
    if (this.getFirstStep().step_code == "POST-BAC") {
      let postBacNode = {
        name: 'POST-BAC',
        value: 'POST-BAC',
        type: 'node'
      }
      data.push(postBacNode);
    }

    this.paths.forEach(path => {
      let pathSteps = path.path_steps;
      //On crée nos noeuds sans duplication
      for (let index = 0; index < pathSteps.length; index++) {
        if (data.length == 0 || data.filter(element => element.name == pathSteps[index].step_code).length == 0) {
          let tmpData = {
            name: pathSteps[index].step_code,
            value: pathSteps[index].step_name,
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
