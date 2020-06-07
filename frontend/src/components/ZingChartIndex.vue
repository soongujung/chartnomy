<template>
  <div>
    <a href="/">
      <h1>GO HOME</h1>
    </a>
    <h1> 경제 지표 추이 - KOSPI, 정책금리(한국/미국), 환율(원/달러)</h1>
    <div class="hello" ref="chartdiv">
    </div>
  </div>
</template>

<script>
  import api from '@/api';

  import Vue from 'vue';
  import vueMoment from 'vue-moment';
  Vue.use(vueMoment)


  export default {
    name: "ZingChartIndex",
    data(){
      return {
        chartData: [],
        chart: {},
        apiResult: {},
        LOAN_KR: [],
        LOAN_US: [],
        USD: [],
        KOSPI: []
      };
    },
    mounted(){
      this.getLoanKr()
      this.getLoanUs()
      this.getExchangeRateUs()
      this.getKospi()
    },
    beforeDestroy(){
      //
    },
    methods:{
      fetchAll(){

      },
      bindingResult(column_name, response_data){
        this.apiResult[column_name] = response_data;
        this.convertObjArrToSeries(column_name)
      },
      convertObjArrToSeries(column_name){
        let target = this[column_name]
        // console.log(' >>> ', this.apiResult[column_name])
        this.apiResult[column_name].forEach(obj => {
          // let data = obj[column_name] === null ? null : obj[column_name];
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
            default:
              data = data === null ? null : data;
              // data = obj[column_name] === null ? null : obj[column_name];
                break;
          }
          target.push(data)
        })
      },
      getLoanKr(){
        api.get('/web/trending/index/loan/LOAN_KR')
        .then(res => {
          this.bindingResult('LOAN_KR', res.data);
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

        });
      },
      getKospi(){
        api.get('/web/trending/index/KOSPI')
        .then(res => {
          this.bindingResult('KOSPI', res.data);
        })
        .catch(err => {

        })
      }
    }
  }
</script>

<style scoped>

</style>
