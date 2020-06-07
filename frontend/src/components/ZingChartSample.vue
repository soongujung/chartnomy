<template>
  <div class="zingchart-vue-test">
    <a href="/">
      <h1>GO HOME</h1>
    </a>
    <zingchart class="chart-container" :data="chartData" :width="1000" ></zingchart>
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
    name: "ZingChartSample",
    components: {
      zingchart: zingchartVue
    },
    data(){
      return{
        apiData: [],
        kospiPrice: [],
        loanUsPrice: [],
        loanKrPrice: [],
        exchangeWonDallor: [],
        dateAxisDd: [],
        arr_columns : ['kospiPrice', 'loanUsPrice', 'loanKrPrice', 'exchangeWonDallor']
      }
    },
    mounted() {
      this.fetchData()
    },
    computed: {
      chartData(){
        return {
          type: 'line',
          title:{
            text: "Index Chart (KOSPI, 정책금리(한국/미국), 환율(원/달러)"
          },
          plot: {
            marker:{
              visible: false
            }
          },
          plotarea:{
            // margin: '60 50 40 0',
          },
          scaleX:{
            // 'min-value': 1420218000000,
            step: "day",
            values:this.dateAxisDd,
            item: {
              fontColor: '#222',
              fontFamily: 'Open Sans'
            },
            transform:{
              type: "date",
              all: "%y/%m/%d"
            },
            zooming: {
              shared: true
            }
          },
          legend:{

          },
          tooltip: {
            text: 'Close: %v',
            backgroundColor: '#BBB',
            borderColor: 'transparent'
          },
          scaleY:{
            visible: true,
            format:"%v",
            placement: 'default',
            guide: {
              lineColor: '#222',
              lineStyle: 'solid',
              visible: true
            },
            item: {
              fontColor: '#222',
              fontFamily: 'Open Sans'
            }
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
              // values: [4,5,3,3,4,4],
              values: this.kospiPrice,
              text: '코스피 (￦)'
            },
            {
              type: 'line',
              scales: "scale-x,scale-y",
              values: this.exchangeWonDallor,
              text: '환율 (￦)',
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-3",
              values: this.loanKrPrice,
              text: '정책금리(한국) (%)'
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-3",
              values: this.loanUsPrice,
              text: '정책금리(미국) (%)'
            }
          ]
        }
      }
    },
    methods: {
      fetchData(){
        api.get('/web/stock/trending')
        .then(res => {
          this.apiData = res.data;
          this.generateDateAxis()
          this.generateChartData()
          this.chartData.scaleX.values = this.dateAxisDd

          // this.chartData.series[0].values = this.kospiPrice
          // this.chartData.series[1].values = this.exchangeWonDallor
          // this.chartData.series[2].values = this.loanKrPrice
          // console.log('chartData >>> ', this.chartData)
        })
        .catch(err=>{
          console.log('err >>> ', err)
        })
      },
      generateDateAxis(){
        this.apiData.forEach(obj => {
          let str_date = obj.date
          let dt_date = this.$moment(str_date, 'YYYYMMDD').toDate()
          this.dateAxisDd.push(dt_date.getTime())
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
          let data = obj[columnNm] === null ? undefined : obj[columnNm]
          // console.log('columnNm, data >>> ', columnNm, ', ', data)
          target.push(data)
        })
      },
    }
  }
</script>

<style scoped>
.zingchart-vue-test{
  /*display: inline-block;*/
  position: absolute;
  /*top: 25%;*/
  /*left: 25%;*/
  /*width: 100%;*/
  /*height: 100%;*/
  /*margin: 0 auto;*/
}
.chart-container{
  padding-left: 20px;
  padding-right: 20px;
  width: 100%;
  height: 100%;
  min-height: 800px;
  margin: 0 auto;
}
</style>
