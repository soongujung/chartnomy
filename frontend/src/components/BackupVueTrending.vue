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
  import api from '@/api';

  Vue.use(vueMoment)
  Vue.use(zingchartVue)

  export default {
    name: "BackupVueTrending",
    components: {
      zingchart: zingchartVue
    },
    mounted() {
      this.fetchDataAndRender()
    },
    // beforeDestroy() {
    //   if (this.chart){
    //     this.chart.dispoose();
    //   }
    // },
    data(){
      return{
        chartData: {
          type: 'line',
          // type: 'mixed',
          title:{
            text: "mixed chart"
          },
          scaleX:{
            minValue: 1420218000000,
            // step: "day",
            // values: this.dateAxisDd,
            // transform:{
            //   type: "date",
            //   all: "%y/%m/%d"
            // },
            // itemsOverlap: true,
            // 'max-items': 5
          },
          scaleY:{
            format:"%v ($)"
          },
          scaleY2:{
            visible: true,
            format:"%v (￦)",
            placement: 'default'
          },
          scaleY3:{
            visible: true,
            format:"%v (%)",
          },
          preview:{},
          series: [
            {
              type: 'line',
              scales: 'scale-x,scale-y',
              values: this.kospiPrice,
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-2",
              values: this.exchangeWonDallor
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-3",
              values: this.loanKrPrice
            }
          ]
        },
        apiData: [],
        kospiPrice: [],
        loanUsPrice: [],
        loanKrPrice: [],
        exchangeWonDallor: [],
        dateAxisDd: [],
        arr_columns : ['kospiPrice', 'loanUsPrice', 'loanKrPrice', 'exchangeWonDallor']
      }
    },
    methods:{
      fetchDataAndRender(){
        api.get('/web/stock/trending')
        .then(res => {
          this.apiData = res.data;
          this.generateDateAxis()
          this.generateChartData()
          this.chartData.scaleX.values = this.dateAxisDd
          this.chartData.series[0].values = this.kospiPrice
          this.chartData.series[1].values = this.exchangeWonDallor
          this.chartData.series[2].values = this.loanKrPrice
          console.log('chartData >>> ', this.chartData)
        })
        .catch(err=>{
          console.log('err >>> ', err)
        })
      },
      generateDateAxis(){
        this.apiData.forEach(obj => {
          let str_date = obj.date
          let dt_date = this.$moment(str_date, 'YYYYMMDD').toDate()
          // this.dateAxisDd.push(dt_date.getTime())
          this.dateAxisDd.push(dt_date)
        })
        this.chartData.scaleX.values = this.dateAxisDd
      },
      generateChartData(){
        let arr_columns = this.arr_columns
        arr_columns.forEach(columnNm => {
          this.convertObjArrToSeries(columnNm)
        })
      },
      convertObjArrToSeries(columnNm){
        let target = this[columnNm]
        this.apiData.forEach(obj => {
          let data = obj[columnNm] === null ? null : obj[columnNm]
          // console.log('columnNm, data >>> ', columnNm, ', ', data)
          target.push(data)
        })
      },
      renderChart(){
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
