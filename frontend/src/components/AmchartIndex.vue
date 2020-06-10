<template>
  <div>
    <a href="/">
      <h1>GO HOME</h1>
    </a>
    <h1>Index Chart (KOSPI, 정책금리(한국/미국), 환율(원/달러)</h1>
    <div class="hello" ref="chartdiv">
    </div>
  </div>
</template>

<script>
  import * as am4core from "@amcharts/amcharts4/core";
  import * as am4charts from "@amcharts/amcharts4/charts"
  import am4thems_animated from "@amcharts/amcharts4/themes/animated";
  import api from '@/api';

  import Vue from 'vue';
  import vueMoment from 'vue-moment';
  Vue.use(vueMoment)

  export default {
    name: 'AmchartIndex',
    data(){
      return {
        chartData : [],
        chart : {},
        apiResult : {},
        LOAN_KR: [],
        LOAN_US: [],
        USD: [],
        KOSPI: [],
        DATE: [],
        chartOptions: {
          DATE: 'DATE',
          USD: {
            color: '#d9480f',
            valueColumn: 'USD',
            tooltipText: '환율 : {valueY.value} (원)',
            legendName: '환율 (달러)',
          },
          KOSPI: {
            color: '#339af0',
            valueColumn: 'KOSPI',
            tooltipText: 'kospi : {valueY.value} (원)',
            legendName: 'KOSPI 지수',
          },
          LOAN_KR: {
            color: '#51cf66',
            valueColumn: 'LOAN_KR',
            tooltipText: '정책 금리(한국) : {valueY.value} (%)',
            legendName: '정책 금리(한국)',
          },
          LOAN_US: {
            color: '#868e96',
            valueColumn: 'LOAN_US',
            tooltipText: '정책 금리(미국) : {valueY.value} (%)',
            legendName: '정책 금리(미국)',
          }
        }
      };
    },
    created(){
      // this.fetchIndexResult()
      // this.renderChart()
    },
    mounted() {
      // this.renderChart();
      this.fetchIndexResult()
    },
    beforeDestroy() {
      // if (this.chart) {
      //   this.chart.dispose();
      // }
    },
    methods:{
      async fetchIndexResult(){

        await this.getLoanKr()
        console.log('getLoanKr >>> ')

        await this.getLoanUs()
        console.log('getLoanUs >>> ')

        await this.getExchangeRateUs()
        console.log('getExchangeRateUs >>> ')

        await this.getKospi()
        this.renderChart()
        console.log('getKospi >>> ')

        // let promise_LOAN_KR = this.getLoanKr()
        // let promise_LOAN_US = this.getLoanUs()
        // let promise_USD = this.getExchangeRateUs()
        // let promise_KOSPI = this.getKospi()
        // let renderChart = this.renderChart

        // Promise.all([
        //   promise_KOSPI, promise_LOAN_KR, promise_LOAN_US, promise_USD
        // ])
        // .then(function(result){
        //   renderChart()
        // })

      },
      bindingResult(column_name, response_data){
        this.apiResult[column_name] = response_data;
      },
      convertObjArrToSeries(column_name){
        let target = this[column_name]
        this.apiResult[column_name].forEach(obj => {
          let data;
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
              data = this.$moment(obj['date'], 'YYYYMMDD').toDate();
              break;
          }
          data = data == null ? 0 : data;
          target.push(data)
        })
      },
      generateChartData(){  // transform 된 데이터를 적용
        let chartData = this.chartData
        let apiResult = this.apiResult
        return new Promise(function(resolve, reject){
          let arr_columns = ['DATE', 'KOSPI', 'LOAN_KR', 'LOAN_US', 'USD']
          let chart_data = chartData;

          apiResult['DATE'].forEach(dt => {
            let element = new Object();
            element.date = dt;
            chart_data.push(element)
          })

          arr_columns.forEach(function(column_name, i, arr){

          })
        })
      },
      getLoanKr(){
        let promise = api.get('/web/trending/index/loan/LOAN_KR')
        .then(res => {
          this.bindingResult('LOAN_KR', res.data);
          this.bindingResult('DATE', res.data);

        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getLoanUs(){
        let promise = api.get('/web/trending/index/loan/LOAN_US')
        .then(res => {
          this.bindingResult('LOAN_US', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getExchangeRateUs(){
        let promise = api.get('/web/trending/index/exchange/USD')
        .then(res => {
          this.bindingResult('USD', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getKospi(){
        let promise = api.get('/web/trending/index/KOSPI')
        .then(res => {
          this.bindingResult('KOSPI', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      renderChart(){
        let chart = am4core.create(this.$refs.chartdiv, am4charts.XYChart);
        this.chart = chart;
        chart.paddingRight = 20;
        // chart.data = this.chartData;

        let dateAxis = chart.xAxes.push(new am4charts.DateAxis());
        dateAxis.renderer.grid.template.location = 0;
        dateAxis.renderer.minGridDistance = 50;

        // 단위 (원)
        let valueAxisWon = new am4charts.ValueAxis();
        chart.yAxes.push(valueAxisWon);
        valueAxisWon.title.dy = -50;
        valueAxisWon.title.paddingRight = 0;
        valueAxisWon.title.paddingBottom = 400;
        valueAxisWon.renderer.opposite = false;
        valueAxisWon.title.fontWeight = 600;
        valueAxisWon.title.fontSize = 14;
        valueAxisWon.tooltip.disabled = true;
        valueAxisWon.title.text = "원";
        valueAxisWon.title.rotation = 0;
        valueAxisWon.title.align = "top";
        valueAxisWon.renderer.grid.template.strokeOpacity = 0;
        valueAxisWon.renderer.grid.template.disabled = true;

        let valueAxisPercent = new am4charts.ValueAxis();
        chart.yAxes.push(valueAxisPercent);

        valueAxisPercent.title.dy = -50;
        valueAxisPercent.title.paddingRight = 0;
        valueAxisPercent.title.paddingBottom = 400;
        valueAxisPercent.renderer.opposite = true;
        valueAxisPercent.title.fontWeight = 600;
        valueAxisPercent.title.fontSize = 14;
        valueAxisPercent.tooltip.disabled = true;
        valueAxisPercent.title.text = "%";
        valueAxisPercent.title.rotation = 0;
        valueAxisPercent.title.align = "top";
        valueAxisPercent.renderer.grid.template.strokeOpacity = 0;
        valueAxisPercent.renderer.grid.template.disabled = true;

        let series_KOSPI    = this.createSeries(chart, valueAxisWon, 'KOSPI');
        let series_USD      = this.createSeries(chart, valueAxisWon, 'USD');
        let series_LOAN_KR  = this.createSeries(chart, valueAxisPercent, 'LOAN_KR');
        let series_LOAN_US  = this.createSeries(chart, valueAxisPercent, 'LOAN_US');

        let scrollbarX = new am4charts.XYChartScrollbar();
        scrollbarX.series.push(series_KOSPI);
        scrollbarX.series.push(series_USD);
        scrollbarX.series.push(series_LOAN_KR);
        scrollbarX.series.push(series_LOAN_US);

        chart.scrollbarX = scrollbarX;


      },
      createSeries(chart, valueAxis, indexType){
        console.log('indexType >>> ', indexType)
        let chartOption = this.chartOptions[indexType]
        let dateColumnNm = this.chartOptions['DATE']

        let seriesLine = new am4charts.LineSeries();
        let color = chartOption.color;
        // let chart = this.chart;

        // seriesLine.data = this[indexType]
        chart.series.push(seriesLine);

        let valueNm;
        switch (indexType) {
          case 'LOAN_KR':
          case 'LOAN_US':
            valueNm = 'rate'
            break;
          case 'USD':
          case 'KOSPI':
            valueNm = 'price'
            break;
          // case 'DATE':
          //   valueNm = this.$moment(obj['date'], 'YYYYMMDD').toDate();
          //   break;
        }

        this.apiResult[indexType].forEach(obj=>{
          obj['date'] = this.$moment(obj['date'], 'YYYYMMDD').toDate();
        })

        seriesLine.data = this.apiResult[indexType]

        seriesLine.dataFields.valueY = valueNm;
        seriesLine.dataFields.dateX = dateColumnNm;
        seriesLine.strokeWidth = 2;                     // 선의 굵기
        seriesLine.minBulletDistance = 10;
        seriesLine.stroke = am4core.color(color);       // 선의 색상
        seriesLine.fill = am4core.color(color);         // 선의 내부
        seriesLine.tensionX = 0.8;

        seriesLine.tooltipText = chartOption.tooltipText;
        seriesLine.tooltip.pointerOrientation = "vertical";
        seriesLine.tooltip.getFillFromObject = false;
        seriesLine.tooltip.label.fill = am4core.color(color);           // 툴팁 내부 폰트 색상
        seriesLine.tooltip.background.cornerRadius = 20;                // 툴팁 테두리 Radius
        seriesLine.tooltip.background.fillOpacity = 0.8;                // 툴팁 투명도
        seriesLine.tooltip.label.padding(12,12,12,12);
        seriesLine.name = chartOption.legendName;

        seriesLine.yAxis = valueAxis;
        // Add scrollbar
        chart.scrollbarX = new am4charts.XYChartScrollbar();
        chart.scrollbarX.series.push(seriesLine);

        return seriesLine;
      }
    },

  }
</script>

<style scoped>
  .hello {
    width: 100%;
    height: 500px;
  }
</style>
