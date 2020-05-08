<template>
  <div class="zingchart-vue-test">
    <h1>zingchart-vue</h1>
    <a href="https://www.npmjs.com/package/zingchart-vue">zinchart-vue official</a>
    <zingchart :data="chartData" :width="700"></zingchart>
  </div>
</template>

<script>
  import Vue from 'vue';

  import vueMoment from 'vue-moment';
  import zingchartVue from 'zingchart-vue';

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
            format:"$%v"
          },
          'scale-y-2':{
            visible: true
          },
          series: [
            {
              type: 'line',
              scales: 'scale-x,scale-y',
              values: [4,5,3,3,4,4]
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-2",
              values: [2,3,2,3,2,3]
            }
          ]
        }
      }
    }
  }
</script>

<style scoped>

</style>
