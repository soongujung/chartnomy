<template>
  <zingchart :data="chartData" :width="700"></zingchart>
</template>

<script>
  import Vue from 'vue';

  import vueMoment from 'vue-moment';
  import zingchartVue from 'zingchart-vue';

  Vue.use(vueMoment)
  Vue.use(zingchartVue)

  export default {
    name: "ZingIndex",
    components: {
      zingchart: zingchartVue
    },
    data(){
      return{
        chartData: {
          type: 'line',
          series: [{
            values: [4,5,3,3,4,4]
          }]
        }
      }
    }
  }
</script>

<style scoped>

</style>
