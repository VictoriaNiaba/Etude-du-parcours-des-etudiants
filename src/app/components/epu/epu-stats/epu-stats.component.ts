import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-epu-stats',
  templateUrl: './epu-stats.component.html',
  styleUrls: ['./epu-stats.component.scss']
})
export class EpuStatsComponent implements OnInit {

  constructor(private httpClient: HttpClientService) { }

  ngOnInit(): void {
  }

  /*stats*/
  options_stats_2 = {
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
        value: 335,
        name: "M1 informatique"
      }, {
        value: 310,
        name: "Autre"
      }, {
        value: 1548,
        name: "L3 informatique"
      }],
      labeLine: {
        show: true
      },
      emphasis: {
        scale: true
      },
      label: {
        alignTo: "labelLine",
        show: true,
        position: "outside"
      },
      radius: ["25%", "50%"],
      animation: true,
      animationType: "scale",
      animationEasing: "bounceOut",
      selectedMode: "single"
    }]
  }
  
  chartInstance: any;
  onChartInit(e: any) {
    this.chartInstance = e;
    console.log('on chart init:', e);
  }
}
/* https://echarts.apache.org/en/option.html#title */