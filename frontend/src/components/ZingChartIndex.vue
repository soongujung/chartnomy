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
        'chartData': [],
        'chart': {},
        apiResult: {}
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
      fetchEach(indexType){

      },
      bindingResult(prop_name, response_data){
        this.apiResult[prop_name] = response_data;
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
        api.get('/web/trending/index/kospi')
        .then(res => {
          this.bindingResult('kospi', res.data);
        })
        .catch(err => {

        })
      }
    }
  }
</script>

<style scoped>

</style>
