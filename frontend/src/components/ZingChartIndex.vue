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
    name: "TrendingIndexVue",
    data(){
      return {
        'chartData': [],
        'chart': {}
      };
    },
    methods:{
      fetchAll(){

      },
      fetchEach(indexType){

      },
      getLoanKr(){
        api.get('/api/web/trending/index/loan/LOAN_KR')
        .then(res => {

        })
        .catch(err => {
          console.log('err >>> ', err);
        });
      },
      getLoanUs(){

      },
      getExchangeRateUs(){
        api.get('/api/web/trending/index/exchange/USD')
        .then(res => {

        })
        .catch(err => {

        });
      },
      getKospi(){
        api.get('/api/web/trending/index/kospi')
        .then(res => {

        })
        .catch(err => {

        })
      },
      mounted(){
        //
      },
      beforeDestroy(){
        //
      }
    }
  }
</script>

<style scoped>

</style>
