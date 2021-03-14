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

  constructor(private httpClient: HttpClientService) {
    //this.setFormation("SSV2AT") //temporairePourTest
  }

  /**/
  step: Step;
  setFormation(stepCode: string) {
    this.httpClient.getStepByCode(stepCode).subscribe(res => {
      let steps_in_sorted = res.steps_in.sort((a,b) => {
        if (a.number > b.number)
          return -1;
        if (a.number < b.number)
          return 1;
        return 0;
      });
      let steps_out_sorted = res.steps_out.sort((a,b) => {
        if (a.number > b.number)
          return -1;
        if (a.number < b.number)
          return 1;
        return 0;
      });
      this.step = new Step(
        res.step_code,
        res.step_name,
        steps_in_sorted,
        steps_out_sorted,
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

  options1: any;
  changeOptions1() {
    let data = [];
    let otherIn = [];
    let totalStepIn = this.step.getNumberIncoming();
    let percentageShow = 1;
    if(this.step.steps_in) {
      this.step.steps_in.forEach(step => {
        if(step.number > percentageShow*totalStepIn/100) {
          data.push(
            {
              value: step.number,
              name: step.step_code //get le nom ?
            });
        } else {
          otherIn.push(
            {
              value: step.number,
              name: step.step_code //get le nom ?
            });
        }
      });
      if(otherIn.length > 0) {
        let other = {value:0, name: "Autre"};
        otherIn.forEach(step => {
          other.value += step.value;
        });
        data.push(other);
        data = data.sort((a,b) => {
          if (a.value > b.value)
            return -1;
          if (a.value < b.value)
            return 1;
          return 0;
        });
      }
      // sert à avoir la liste de "string" pour les ajouter dans la légende.
      /*data.forEach(element => {
        temp.push(element.name);
      });*/
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
    let otherOut = [];
    let totalStepOut = this.step.getNumberOutcoming();
    let percentageShow = 1;
    if(this.step.steps_out) {
      this.step.steps_out.forEach(step => {
        if(step.number > percentageShow*totalStepOut/100) {
          data.push(
            {
              value: step.number,
              name: step.step_code //get le nom ?
            }
          );
        } else {
          otherOut.push(
            {
              value: step.number,
              name: step.step_code //get le nom ?
            });
        }
      });
      if(otherOut.length > 0) {
        let other = {value:0, name: "Autre"};
        otherOut.forEach(step => {
          other.value += step.value;
        });
        data.push(other);
        data = data.sort((a,b) => {
          if (a.value > b.value)
            return -1;
          if (a.value < b.value)
            return 1;
          return 0;
        });
      }
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