import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import * as echarts from 'echarts';

@Component({
  selector: 'app-epu-stats',
  templateUrl: './epu-stats.component.html',
  styleUrls: ['./epu-stats.component.scss']
})
export class EpuStatsComponent implements OnInit {

  constructor(private httpClient: HttpClientService) { }

  formationName: string;

  //temp
  max = 200;
  min = 50;
  options = {
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
        value: Math.floor(Math.random() * (this.max - this.min + 1) + this.min),
        name: "M1 informatique"
      }, {
        value: Math.floor(Math.random() * (this.max - this.min + 1) + this.min),
        name: "Autre"
      }, {
        value: Math.floor(Math.random() * (this.max - this.min + 1) + this.min),
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

  ngOnInit(): void {
  }

  /**/
  setFormation(formationName: string) {
    this.formationName = formationName;
    if(this.chartStats1 != undefined && this.chartStats2 != undefined) {
      this.changeOptions();
    }
  }

  chartStats1: any;
  chartStats2: any;
  onChartInit1(e: any) {
    this.chartStats1 =  echarts.getInstanceByDom(document.getElementById('chartStats1'));
    //console.log('on chart init 1:', e);
  }
  onChartInit2(e: any) {
    this.chartStats2 =  echarts.getInstanceByDom(document.getElementById('chartStats2'));
    //console.log('on chart init 2:', e);
  }
  changeOptions() {
    this.options = {
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
          value: Math.floor(Math.random() * (this.max - this.min + 1) + this.min),
          name: "M1 informatique"
        }, {
          value: Math.floor(Math.random() * (this.max - this.min + 1) + this.min),
          name: "Autre"
        }, {
          value: Math.floor(Math.random() * (this.max - this.min + 1) + this.min),
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
  getOptions() {
    return this.options;
  }
}
/* https://echarts.apache.org/en/option.html#title */