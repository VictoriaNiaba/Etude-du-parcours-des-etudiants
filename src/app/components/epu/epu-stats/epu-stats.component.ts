import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import { EChartsOption } from 'echarts';
import * as echarts from 'echarts';
import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-epu-stats',
  templateUrl: './epu-stats.component.html',
  styleUrls: ['./epu-stats.component.scss']
})
export class EpuStatsComponent implements OnInit {

  constructor(private httpClient: HttpClientService) { }

  formationName: string;

  ngOnInit(): void {
  }

  /**/
  testRandomValue = 100;
  setFormation(formationName: string) {
    this.formationName = formationName;
    let max = 300; let min = 20;
    this.testRandomValue = Math.floor(Math.random() * (max - min + 1) + min);
    this.setOptions();
  }

  setOptions() {
    //soucis avec le set option qui set mais qui n'affiche pas
    //dom incorrect, j'ai essayé avec @ViewChild('chartStats1') chartStats1; etc mais je galère, après faudrait mettre le dom en global aussi...
    let _chartStats1 = echarts.init(document.getElementById("chartStats1"));
    let _chartStats2 = echarts.init(document.getElementById("chartStats2"));
    _chartStats1.setOption(
      {
        legend: {
          orient: "horizontal",
          left: "center",
          data: ["M1 informatique", "L3 informatique", "Autre"]
        },
        tooltip: {
          trigger: 'item'
        },
        series: [{
          type: "pie",
          data: [{
            value: this.testRandomValue,
            name: "M1 informatique"
          }, {
            value: 310,
            name: "Autre"
          }, {
            value: 1548,
            name: "L3 informatique"
          }],
          emphasis: {
            scale: true
          },
          label: {
            alignTo: "labelLine",
            show: true,
            position: "outer"
          },
          radius: ["25%", "50%"],
          animation: true,
          animationType: "scale",
          animationEasing: "bounceOut",
          selectedMode: "single"
        }]
      }
    );
    _chartStats2.setOption(_chartStats1.getOption());
  }
 
  getOptions() {
    return {
      legend: {
        orient: "horizontal",
        left: "center",
        data: ["M1 informatique", "L3 informatique", "Autre"]
      },
      tooltip: {
        trigger: 'item'
      },
      labeLine: {
        show: true
      },
      series: [{
        type: "pie",
        data: [{
          value: this.testRandomValue,
          name: "M1 informatique"
        }, {
          value: 310,
          name: "Autre"
        }, {
          value: 1548,
          name: "L3 informatique"
        }],
        emphasis: {
          scale: true
        },
        label: {
          alignTo: "labelLine",
          show: true,
          position: "outer"
        },
        radius: ["25%", "50%"],
        animation: true,
        animationType: "scale",
        animationEasing: "bounceOut",
        selectedMode: "single"
      }]
    };
  }

  chartInstance1: any;
  onChartInit1(e: any) {
    this.chartInstance1 = e;
    console.log('on chart init 1:', e);
  }

  chartInstance2: any;
  onChartInit2(e: any) {
    this.chartInstance1 = e;
    console.log('on chart init 2:', e);
  }
}
/* https://echarts.apache.org/en/option.html#title */