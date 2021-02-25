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
  setFormation(step: Step) {
    this.step = step;
    this.changeOptions();
  }

  ngOnInit(): void {
  }

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
  options: any;
  changeOptions() {

    //on peut ajouter des infos dans data autant qu'on veut.
    //il faudra peut être faire évoluer step ou donner plus d'info à epu-stats par "setFormation(...)"
    let data = [];
    if(this.step.step_stats_in > 0) {
      data.push(
        {
          value: this.step.step_stats_in,
          name: /*this.step.step_name*/"Entrants"
        }
      );
    }
    if(this.step.step_stats_repeating > 0) {
      data.push(
        {
          value: this.step.step_stats_repeating,
          name: "Redoublants"
        }
      );
    }
    if(this.step.step_stats_other > 0) {
      data.push(
        {
          value: this.step.step_stats_other,
          name: "Autre"
        }
      );
    }
    /**/

    // sert à avoir la liste de "string" pour les ajouter dans la légende.
    let temp = [];
    data.forEach(element => {
      temp.push(element.name);
    });

    this.options = {
      legend: {
        orient: "horizontal",
        left: "center",
        data: temp
      },
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
/*
  La logique de stats est la même que celle de l'affichage par graphe (echart les 2).
  les stats prennent des options; il faut lui donner la variable options et pas une fonction retournant options (sinon y a des lags)
  du coup options dont être déclarée puis modifiée par une fonction changeOptions() modifie this.options.

  J'ai créé dans changeOptions() une variable data contenant name et value que j'intègre directement dans options.
  Le graphe devrait être géré de la même manière normalement.
*/