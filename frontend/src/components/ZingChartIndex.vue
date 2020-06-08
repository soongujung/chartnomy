<template>
  <div>
    <a href="/">
      <h1>GO HOME</h1>
    </a>
    <zingchart class="chart-container" :data="chartData" :width="1000"></zingchart>
  </div>
</template>

<script>
  import api from '@/api';

  import Vue from 'vue';
  import vueMoment from 'vue-moment';
  import zingchartVue from 'zingchart-vue';

  Vue.use(vueMoment)
  Vue.use(zingchartVue)

  export default {
    name: "ZingChartIndex",
    components: {
      zingchart: zingchartVue
    },
    data(){
      return {
        chart: {},
        apiResult: {},
        LOAN_KR: [],
        LOAN_US: [],
        USD: [],
        KOSPI: [],
        DATE: []
      };
    },
    mounted(){
      this.getLoanKr()
      this.getLoanUs()
      this.getExchangeRateUs()
      this.getKospi()
    },
    computed: {
      chartData(){
        return this.renderChart()
        // return
      }
    },
    beforeDestroy(){
      //
    },
    methods:{
      bindingResult(column_name, response_data){
        this.apiResult[column_name] = response_data;
        this.convertObjArrToSeries(column_name)
      },
      convertObjArrToSeries(column_name){
        let target = this[column_name]
        this.apiResult[column_name].forEach(obj => {
          let data;
          // 수정 필요... (backend, frontend 모두)
          switch (column_name) {
            case 'LOAN_KR':
            case 'LOAN_US':
              data = obj['rate']
              break;
            case 'USD':
            case 'KOSPI':
              data = obj['price']
              break;
            case 'DATE':
              data = obj['date']
              break;
          }
          data = data == undefined ? 0 : data;
          target.push(data)
        })
      },
      getLoanKr(){
        api.get('/web/trending/index/loan/LOAN_KR')
        .then(res => {
          this.bindingResult('LOAN_KR', res.data);
          this.bindingResult('DATE', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });
      },
      getLoanUs(){
        api.get('/web/trending/index/loan/LOAN_US')
        .then(res => {
          this.bindingResult('LOAN_US', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });
      },
      getExchangeRateUs(){
        api.get('/web/trending/index/exchange/USD')
        .then(res => {
          this.bindingResult('USD', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });
      },
      getKospi(){
        api.get('/web/trending/index/KOSPI')
        .then(res => {
          this.bindingResult('KOSPI', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        })
      },
      renderChart(){
        return {
          type: 'line',
          title:{
            text: "Index Chart (KOSPI, 정책금리(한국/미국), 환율(원/달러)"
          },
          plot: {
            marker:{
              visible: true
            }
          },
          plotarea:{
            // margin: '60 50 40 0',
          },
          scaleX:{
            step: "day",
            values: this.DATE,
            item: {
              fontColor: '#222',
              fontFamily: 'Open Sans'
            },
            transform:{
              type: "DATE",
              all: "%y/%m/%d"
            },
            zooming: {
              shared: true
            }
          },
          legend:{

          },
          tooltip: {
            backgroundColor: '#222',
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
              values: this.KOSPI,
              text: '코스피 (￦)'
            },
            {
              type: 'line',
              scales: "scale-x,scale-y",
              values: this.USD,
              text: '환율 (￦)',
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-3",
              values: this.LOAN_KR,
              text: '정책금리(한국) (%)'
            },
            {
              type: 'line',
              scales: "scale-x,scale-y-3",
              values: this.LOAN_US,
              text: '정책금리(미국) (%)'
            }
          ]
        }
      }
    }
  }
</script>

<style scoped>

</style>
