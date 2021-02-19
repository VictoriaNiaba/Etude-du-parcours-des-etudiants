import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import { EpuStatsComponent } from '../epu-stats/epu-stats.component';

@Component({
  selector: 'app-epu-graphe',
  templateUrl: './epu-graphe.component.html',
  styleUrls: ['./epu-graphe.component.scss']
})
export class EpuGrapheComponent implements OnInit {

  constructor(private httpClient: HttpClientService) { }

  ngOnInit(): void {
    this.getPaths();
  }


  paths: any;
  totalStudentPaths: number;
  localStudentPaths = [];
  firstStep: string = null;
  lastStep: string = "PRSIN5AI";
  getFirstStep() {
    return this.firstStep ? this.firstStep : "POST-BAC";
  }
  getPaths(){
    this.httpClient.getPaths(this.firstStep, this.lastStep).subscribe(res => {
      this.paths = res;
      this.totalStudentPaths = 0;
      this.paths.forEach(path => {
        let localTemp = 0;
        path['registered'].forEach(stepRegistrationNumber => {
          localTemp += stepRegistrationNumber;
          this.totalStudentPaths += stepRegistrationNumber;
        });
        this.localStudentPaths.push(localTemp); //moyenne : localTemp/path['registered'].length
      });
    });
  }


  /* https://echarts.apache.org/en/api.html#echartsInstance.on 
  >>>  If Object, one or more properties below can be included, and any of them is optional.
  */
  @Input() statsComponent: EpuStatsComponent;
  chartClicked(e: any) {
    if (e.dataType === 'node')
      alert(e.name)
      this.statsComponent.setFormation(e.name);
  }


  title = 'pfe-frontend';
  options = {
    title: {
      text: 'Test'
    },
    animationDurationUpdate: 1500,
    animationEasingUpdate: 'quinticInOut',
    series: [
      {
        type: 'graph',
        layout: 'none',
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
        data: [{
          name: 'Node 1',
          value: 'BAC S',
          type: 'node',
          x: 300,
          y: 300
        }, {
          name: 'Node 2',
          value: 'Licence informatique',
          type: 'node',
          x: 500,
          y: 200
        }, {
          name: 'Node 3',
          value: 'Master informatique',
          type: 'node',
          x: 700,
          y: 300
        }, {
          name: 'Node 4',
          value: 'BTS SNIR',
          type: 'node',
          x: 500,
          y: 400
        }],
        tooltip: {
          trigger: 'item',
          showDelay: 0.1,
          transitionDuration: 0.2,
          show: true,
          formatter: function (params) {
            if (params.type === 'node')
              return `${params.name}`;
            else
              return "";
          }
        },
        links: [{
          source: 'Node 1',
          target: 'Node 2',
          label: {
              show: true
          }
        }, {
          source: 'Node 2',
          target: 'Node 3',
          label: {
              show: true
          }
        }, {
          source: 'Node 4',
          target: 'Node 2',
          label: {
              show: true
          }
        }, {
          source: 'Node 1',
          target: 'Node 4',
          label: {
              show: true
          }
        }],
        lineStyle: {
            opacity: 0.9,
            width: 2,
            curveness: 0.1
        },
        zoom: 0.8
      }
    ]
  };

  /**/
  keyword = 'name';
  getFormation() {
    return [
        {
        id: 1,
        name: 'Bac S'
        },
        {
        id: 2,
        name: 'Master informatique'
        },
        {
        id: 3,
        name: 'BTS SNIR'
        },
        {
        id: 4,
        name: 'Licence Informatique'
        }
    ];
  }

  selectEvent(event) {
    console.info(`Selection : ${event.name}`);
  }
  onChangeSearch(event) {
    console.info(`Change : ${event.name}`);
  }
  onFocused(event) {
    console.info(`Focus : ${event.name}`);
  }
}
