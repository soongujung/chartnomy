<template>
  <div class="zingchart-vue-test">
    <h1>zingchart-vue</h1>
    <a href="https://www.npmjs.com/package/zingchart-vue">zinchart-vue official</a>
    <zingchart class="chart-container" :data="chartData" :width="800"></zingchart>
  </div>
</template>

<script>
  import Vue from 'vue';

  import vueMoment from 'vue-moment';
  import zingchartVue from 'zingchart-vue';
  // import {zingchart, ZC} from 'zingchart/es6.js';
  // import './zingchart/modules-es6/zingchart-pareto.min.js';

  Vue.use(vueMoment)
  Vue.use(zingchartVue)

  export default {
    name: "ZingIndexVue",
    components: {
      zingchart: zingchartVue
    },
    data(){
      return{
        chartData: {
          type: 'line',
          // type: 'mixed',
          title:{
            text: "mixed chart"
          },
          'scale-x':{
            'min-value': 1420218000000,
            step: "day",
            transform:{
              type: "date",
              all: "%y/%m/%d"
            },
            'items-overlap': true,
            'max-items': 5
          },
          'scale-y':{
            format:"%v ($)"
          },
          'scale-y-2':{
            visible: true,
            format:"%v (￦)",
            placement: 'default'
          },
          'scale-y-3':{
            visible: true,
            format:"%v (%)",
          },
          preview:{},
          series: [
            {
              type: 'line',
              scales: 'scale-x,scale-y',
              values: [4,5,3,3,4,4],
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-2",
              values: [2,3,2,3,2,3]
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-3",
              values: [5,1,2,4,3,2]
            }
          ]
        }
      }
    }
  }
</script>

<style scoped>
.zingchart-vue-test{
  /*display: inline-block;*/
  position: absolute;
  top: 25%;
  left: 25%;
  width: 100%;
  height: 100%;
  margin: 0 auto;
}
.chart-container{
  width: 100%;
  height: 100%;
  min-height: 800px;
  margin: 0 auto;
}
</style>
