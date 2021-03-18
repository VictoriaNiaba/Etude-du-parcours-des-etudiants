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

  chartInstance: any;
  chartOptions: any;

  constructor(private httpClient: HttpClientService, private stepsService: StepsService, private router: Router) { }

  ngOnInit(): void {
    //init déplacé dans le onChartInit
    this.searchInit();
    this.changeOptions();
  }

  onChartInit(e: any) {
    this.chartInstance = e;
    //console.log('on chart init:', e);
    this.getPaths("", "");
  }

  getMeanStudents(path: Path) {
    return path.getMeanStudents();
  }

  paths: Path[] = new Array<Path>();
  totalStudentPaths: number = 1;
  pathStats: number[] = [];
  firstStep: StepPath = new StepPath("POST-BAC", "POST-BAC");

  //Pour la sélection du cheminement à mettre en surbrillance
  uniquePaths: Path[] = new Array<Path>();
  pathSelectedIndex: number = 0;
  slideValue: number =  1;

  getFirstStep(): StepPath {
    if(this.firstStep == null) return new StepPath("Autre", "Autre");
    else return this.firstStep;
  }

  setFirstStep(code: string){
    if(code === ""){
      this.firstStep = new StepPath("POST-BAC", "POST-BAC");
    }
    else this.firstStep = null;
  }

  pop(index:number) {
    let result = "";
    this.uniquePaths[index-1].path_steps.forEach(
      step => result += step.step_code+" > "
    )
    result = result.slice(0, -3)
    return result;
  }

  helpMode = false;
  helpButtonText= "";
  //si on clique sur une node aïe aïe aïe
  help() {
    this.helpMode = !this.helpMode;
    if(this.helpMode) {
      this.helpButtonText = "Retour";
      this.helpMode = true;
      this.firstStep = new StepPath("1", "Formation 1", 100);
      let paths = new Array<Path>();

      paths.push(new Path());
      paths.push(new Path());

      paths[0].addStep(this.firstStep)
      paths[0].addStep(new StepPath("2", "Formation 2", 80))
      paths[0].addStep(new StepPath("3A", "Formation 3-A", 40))

      paths[1].addStep(this.firstStep);
      paths[1].addStep(new StepPath("2", "Formation 2", 80));
      paths[1].addStep(new StepPath("3B", "Formation 3-B", 30));
      paths[1].addStep(new StepPath("4B", "Formation 4-B", 10));

      this.paths = paths;

      this.initPaths();
      //this.changeOptions()
    } else {
      this.search();
    }
  }

  highlight(id:string) {
    document.getElementById(id).style.animationName = "_highlight";
    (async () => { 
      await new Promise( resolve => { setTimeout(resolve, 500) });
      document.getElementById(id).style.animationName = "";
    })();
    /*
    if(id != "_search")
      document.getElementById("_search").style.animationName = "";
    if(id != "_slider")
      document.getElementById("_slider").style.animationName = "";
    if(id != "_pathlist")
      document.getElementById("_pathlist").style.animationName = "";*/
  }

  //renseigne "paths" un tableau de path... suivant la base de données
  getPaths(stepsStart: string, stepsEnd: string) {
    this.helpButtonText = "Aide";
    this.helpMode = false;
    this.setFirstStep(stepsStart);
    //Pour bien former la requête attendu au près du backend
    if(stepsStart === "") stepsStart = null;
    if(stepsEnd === "") stepsEnd = null;
    
    this.paths = new Array<Path>();
    this.pathStats = [];
    
    this.httpClient.getPaths(stepsStart, stepsEnd).subscribe(res => {
      res.forEach(path => {
        let pathTemp = new Path();
        for (let i = 0; i < path['steps'].length; i++) {
          //console.warn("epu-graphe component",path['steps'][i]);
          let stepName = this.stepsService.getByCode(path['steps'][i])
          let step = new StepPath( //init
            path['steps'][i],
            stepName,
            path['registered'][i]);
          pathTemp.addStep(step);
        }
        this.paths.push(pathTemp);
        this.pathStats.push(pathTemp.getMeanStudents());
      });
      this.totalStudentPaths = 0;
      this.paths.forEach(path => {
        this.totalStudentPaths += path.getMeanStudents();
      });
      if(this.totalStudentPaths > 0)
        for(let i=0; i<this.pathStats.length; i++)
          this.pathStats[i] = this.pathStats[i]*100/this.totalStudentPaths;
      this.initPaths();
    });
  }

  private initPaths() {

    //permet de reset l'affichage
    //this.slideValue = 1;
    this.stepClick(null);

    this.uniquePaths = new Array<Path>();
    this.displayUniquePaths();
    
    this.changeOptions();
    this.pathSelectedIndex = 0;
    this.slideValue =  Math.min(5, this.uniquePaths.length); //max de 5 par défaut
  }

  //Récupère la valeure du slider
  valueChanged(e){
    this.slideValue = e;
  }

  //Permet d'éviter les cheminements avec des redoublements et ceux qui ont la même suite d'étapes
  displayUniquePaths(){
    let tmpPaths = [];
    this.paths.forEach(path => {
      tmpPaths.push(path.path_steps.map(p => p.step_code));
    });

    for(let i=0; i<this.paths.length; i++){
      let tmpPath = this.paths[i].path_steps.map(item => item.step_code);
      if(!tmpPaths.includes(tmpPath) && new Set(tmpPath).size == tmpPath.length)
        this.uniquePaths.push(this.paths[i]);
    }

    this.uniquePaths = this.uniquePaths.sort((a,b) => {
      if(a.path_steps[a.path_steps.length-1].step_number < b.path_steps[b.path_steps.length-1].step_number)
      return 1;
      if(a.path_steps[a.path_steps.length-1].step_number > b.path_steps[b.path_steps.length-1].step_number)
      return -1;
      return 0;
    });
  }

  switchPath(index: number){
    let pathTmp = this.paths.find(element => element == this.uniquePaths[index]);
    this.paths[this.paths.indexOf(pathTmp)] = this.paths[0];
    this.paths[0] = pathTmp;
    this.pathSelectedIndex = index;
    this.changeOptions();
  }

  //change les options du graphique
  private changeOptions() {
    let data: any[] = [];
    let links: any[] = [];
    let linksDuplicate: any[] = [];
    let isPostBacUse = false;

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
            cat: incrementCat,
            type: 'node',
            itemStyle: {
              color: incrementCat==0 ? "rgba(255, 113, 113, 1)" : "rgba(0, 0, 0, 0.1)"
            }
          }
          data.push(tmpData);
        }

        let fisrtStepPath = pathSteps[0].step_code.substring(3,4);
        if(index != 0 || (fisrtStepPath ===  "1" || fisrtStepPath ===  "P")){
          //On cherche les liens qui peuvent être dupliqués
          let currentSourceStepCode: String = index - 1 < 0 ? this.getFirstStep().step_code : pathSteps[index - 1].step_code;
          let linkFilter = links.filter(link =>
            link.source == currentSourceStepCode
            && link.target == pathSteps[index].step_code
          );

          //Savoir si le noeud post bac est utilisé
          if(currentSourceStepCode === "POST-BAC"){isPostBacUse = true;}

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
              color: incrementCat==0 ? "rgba(255, 113, 113, 1)" : "rgba(0, 0, 0, 0.1)"
            }
          }

          //Si le lien n'existe pas déjà on le crée et on évite les liens sur eux-mêmes
          if (linkFilter.length == 0 && pathSteps[index].step_code != currentSourceStepCode) {
            links.push(tmpLink);
          }
          //On stock les liens qui sont dupliqués
          else {
            linksDuplicate.push(tmpLink);
          }
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

    //On enlève le noeud Post-Bac si il n'est pas utilisé
    if(isPostBacUse == false) data = data.filter(element => element.name !== "POST-BAC");

    let tmpNodeIndex; //le dernier index dans data de la dernière node du parcours affiché
    for(let i=0; i < data.length; i++) {
      if(data[i].cat == 0)
      tmpNodeIndex = i+1;
    }

    if(this.chartInstance && data[0]) {
      let width = this.chartInstance['getWidth']();
      let height = this.chartInstance['getHeight']();
      data[0].fixed = true;
      data[0].x = 0;
      data[0].y = height/2;
      if(data.length > 2) {
        for(let i=1; i<data.length; i++) {
          data[i].fixed = false;
        }
        data[tmpNodeIndex-1].fixed = true;
        data[tmpNodeIndex-1].x = width;
        data[tmpNodeIndex-1].y = height/2;
        
        /*
        data[Math.round((tmpNodeIndex-1)/2)].fixed = true;
        data[Math.round((tmpNodeIndex-1)/2)].x = 500/2;
        data[Math.round((tmpNodeIndex-1)/2)].y = 200;
        */
      }
    }


    this.chartOptions = this.getOptions(data, links);
  }

  private getOptions(data:any[], links:any[]) {
    return {
      /*title: {
        text: 'EPU'
      },*/
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
            gravity: 0.0,
            repulsion: 1000,
            edgeLength: Math.min(this.chartInstance?this.chartInstance['getWidth']()/2:0, 200)
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
          //categories: data
        }
      ]
    };
  }


  /* https://echarts.apache.org/en/api.html#echartsInstance.on 
  >>>  If Object, one or more properties below can be included, and any of them is optional.
  */
  @Input() statsComponent: EpuStatsComponent;
  chartClicked(e: any) {
    if (e.dataType === 'node') {
      this.stepClick(e.name);
    }
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
      {
        return this.statsComponent.setFormation(res.step_code);
      }
    return this.statsComponent.setFormation("");
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
          formation_steps: res[i].steps,
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
          formation_steps: null,
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
      if(element.formation_steps != null){
        element.formation_steps.forEach(step => {
          stepsStart = stepsStart.concat(step.step_code+',');
        });
      }
      else stepsStart = stepsStart.concat(element.code+',');
    });
    //De même pour la recherche d'arrivée
    nodesCodeEnd.forEach(element => {
      if(element.formation_steps != null){
        element.formation_steps.forEach(step => {
          stepsEnd = stepsEnd.concat(step.step_code+',');
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
