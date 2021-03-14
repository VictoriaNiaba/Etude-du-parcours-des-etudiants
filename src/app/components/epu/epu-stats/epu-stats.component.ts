import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import * as echarts from 'echarts';
import { Step } from 'src/app/models/Step';

@Component({
  selector: 'app-epu-stats',
  templateUrl: './epu-stats.component.html',
  styleUrls: ['./epu-stats.component.scss']
})
export class EpuStatsComponent implements OnInit {

  constructor(private httpClient: HttpClientService) { }

  /**/
  step: Step;
  stepInNumber: number = 0;
  stepOutNumber: number = 0;
  setFormation(stepCode: string) {
    this.httpClient.getStepByCode(stepCode).subscribe(res => {
      this.step = new Step(
        res.step_code,
        res.step_name,
        res.steps_in,
        res.steps_out,
        res.average_repeat
      );
      console.log("step found : ", res)
      this.changeOptions1();
      this.changeOptions2();
    });
  }

  ngOnInit(): void {}

  /* ça init une fois que les chats sont affichés, c'est call dans le html par (chartInit) */
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

  //faire changeOptions2() pour le out ??? à gerer avec la même logique
  options1: any;
  changeOptions1() {
    let data = [];
    let temp = [];

    if(this.step.steps_in) {
      this.step.steps_in.forEach(step => {
        data.push(
          {
            value: step.number,
            name: step.step_code //get le nom ?
          }
        );
      });

      // sert à avoir la liste de "string" pour les ajouter dans la légende.
      data.forEach(element => {
        temp.push(element.name);
      });
    }

    this.options1 = {
      tooltip: {
        trigger: 'item'
      },
      series: [{
        type: "pie",
        data: data,
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

  options2: any;
  changeOptions2() {
    let data = [];
    let temp = [];

    if(this.step.steps_out) {
      this.step.steps_out.forEach(step => {
        data.push(
          {
            value: step.number,
            name: step.step_code //get le nom ?
          }
        );
      });

      // sert à avoir la liste de "string" pour les ajouter dans la légende.
      data.forEach(element => {
        temp.push(element.name);
      });
    }

    this.options2 = {
      tooltip: {
        trigger: 'item'
      },
      series: [{
        type: "pie",
        data: data,
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
}
/* https://echarts.apache.org/en/option.html#title */