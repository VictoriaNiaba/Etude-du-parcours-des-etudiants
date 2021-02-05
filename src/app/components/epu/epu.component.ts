import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-epu',
  templateUrl: './epu.component.html',
  styleUrls: ['./epu.component.scss']
})
export class EpuComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  
  title = 'pfe-frontend';
  options = {
    title: {
      text: 'Test'
    },
    tooltip: {},
    animationDurationUpdate: 1500,
    animationEasingUpdate: 'quinticInOut',
    series: [
      {
        type: 'graph',
        layout: 'none',
        symbolSize: 60,
        roam: true,
        itemStyle: {
          borderColor: '#fff',
          borderWidth: 1,
          shadowBlur: 10,
          shadowColor: 'rgba(0, 0, 0, 0.3)'
        },
        label: {
          normal: {
            show: true,
            position: 'top',
            formatter: function (params) {
              return `${params['value']}`;
            }
          }
        },
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [4, 10],
        edgeLabel: {
          normal: {
            textStyle: {
              fontSize: 20
            }
          }
        },
        data: [{
          name: 'Node 1',
          value: 'BAC S',
          x: 300,
          y: 300
        }, {
          name: 'Node 2',
          value: 'Licence informatique',
          x: 500,
          y: 200
        }, {
          name: 'Node 3',
          value: 'Master informatique',
          x: 700,
          y: 300
        }, {
          name: 'Node 4',
          value: 'BTS SNIR',
          x: 500,
          y: 400
        }],
        tooltip: {
          trigger: 'item',
          showDelay: 0,
          transitionDuration: 0.2,
          formatter: function (params) {
            return `<b>${params['name']}</b> Formation : ${params['value']}`;
          }
        },
        force: {
          repulsion: 2000,
        },
        // links: [],
        links: [{
          source: 0,
          target: 1,
          symbolSize: [5, 20],
          label: {
            normal: {
              //show: true
            }
          },
          lineStyle: {
            normal: {
              width: 5,
              curveness: 0.2
            }
          }
        }, {
          source: 'Node 2',
          target: 'Node 3',
          lineStyle: {
            normal: { width: 5, curveness: -0.2 }
          }
        }, {
          source: 'Node 4',
          target: 'Node 2'
        }, {
          source: 'Node 1',
          target: 'Node 4'
        }],
        lineStyle: {
          normal: {
            opacity: 0.9,
            width: 2,
            curveness: 0.2
          }
        }
      }
    ]
  };

}
