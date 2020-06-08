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
  // import am4thems_animated from "@amcharts/amcharts4/themes/animated";
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
        KOSPI: []
      };
    },
    created(){
      this.getLoanKr()
      this.getLoanUs()
      this.getExchangeRateUs()
      this.getKospi()
    },
    beforeDestroy(){

    },
    methods:{
      getTrendingIndexResult(){
        // ...
        // this.renderChart();
        // ...
      },
      bindingResult(column_name, response_data){
        this.apiResult[column_name] = response_data;
        // this.convertObjArrToSeries(column_name)
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
          data = data == null ? 0 : data;
          target.push(data)
        })
      },
      getLoanKr(){
        api.get('/web/trending/index/loan/LOAN_KR')
        .then(res => {
          console.log('>>> ', res.data);
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
        let chart = am4core.create(this.$refs.chartdiv, am4charts.XYChart);

        chart.paddingRight = 20;

        let data = [];
        let visits = 10;

        this.chartData.map(obj=>{
          let str_date = obj.date;
          let dt_date = this.$moment(str_date, 'YYYYMMDD').toDate();
          obj.date = dt_date;
          // console.log(obj);
        });

        chart.data = this.chartData;

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

        // 환율(원/달러)
        // let valueAxisWonDollarExchange = new am4charts.ValueAxis();
        // chart.yAxes.push(valueAxisWonDollarExchange);

        // valueAxisWonDollarExchange.title.dy = -50;
        // valueAxisWonDollarExchange.title.paddingRight = 0;
        // valueAxisWonDollarExchange.title.paddingBottom = 400;
        // valueAxisWonDollarExchange.renderer.opposite = false;
        // valueAxisWonDollarExchange.title.fontWeight = 600;
        // valueAxisWonDollarExchange.title.fontSize = 14;
        // valueAxisWonDollarExchange.tooltip.disabled = true;
        // valueAxisWonDollarExchange.title.text = "환율 (원)";
        // valueAxisWonDollarExchange.title.rotation = 0;
        // valueAxisWonDollarExchange.title.align = "top";
        // valueAxisWonDollarExchange.renderer.grid.template.strokeOpacity = 0;
        // valueAxisWonDollarExchange.renderer.grid.template.disabled = true;

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

        let seriesLineKospi = this.createLineSeries(chart, valueAxisWon, "KOSPI", "date", "#339af0", "kospi : {valueY.value} (원)", "KOSPI 지수");
        let seriesLineExchangeRate = this.createLineSeries(chart, valueAxisWon, "USD", "date", "#d9480f", "환율 : {valueY.value} (원)", "환율 (달러)");
        let loanKrPrice = this.createLineSeries(chart, valueAxisPercent, "LOAN_KR", "date", "#51cf66", "정책 금리(한국) : {valueY.value} (%)", "정책 금리(한국)");
        let loanUsPrice = this.createLineSeries(chart, valueAxisPercent, "LOAN_US", "date", "#868e96", "정책 금리(미국) : {valueY.value} (%)", "정책 금리(미국)");

        let scrollbarX = new am4charts.XYChartScrollbar();
        scrollbarX.series.push(seriesLineKospi);
        scrollbarX.series.push(seriesLineExchangeRate);
        scrollbarX.series.push(loanKrPrice);
        scrollbarX.series.push(loanUsPrice);

        chart.scrollbarX = scrollbarX;

        this.chart = chart;
      },
      createLineSeries(chart, valueAxis, valueColumn, dateColumn, color, tooltipText, legendName){
        let seriesLine = new am4charts.LineSeries();
        chart.series.push(seriesLine);

        seriesLine.dataFields.valueY = valueColumn;
        seriesLine.dataFields.dateX = dateColumn;
        seriesLine.strokeWidth = 2;                     // 선의 굵기
        seriesLine.minBulletDistance = 10;
        seriesLine.stroke = am4core.color(color);       // 선의 색상
        seriesLine.fill = am4core.color(color);         // 선의 내부
        seriesLine.tensionX = 0.8;

        seriesLine.tooltipText = tooltipText;
        seriesLine.tooltip.pointerOrientation = "vertical";
        seriesLine.tooltip.getFillFromObject = false;
        seriesLine.tooltip.label.fill = am4core.color(color);           // 툴팁 내부 폰트 색상
        seriesLine.tooltip.background.cornerRadius = 20;                // 툴팁 테두리 Radius
        seriesLine.tooltip.background.fillOpacity = 0.8;                // 툴팁 투명도
        seriesLine.tooltip.label.padding(12,12,12,12);
        seriesLine.name = legendName;

        seriesLine.yAxis = valueAxis;
        // Add scrollbar
        chart.scrollbarX = new am4charts.XYChartScrollbar();
        chart.scrollbarX.series.push(seriesLine);

        return seriesLine;
      }
    },
    mounted() {
      this.getTrendingIndexResult();
    },
    beforeDestroy() {
      if (this.chart) {
        this.chart.dispose();
      }
    }
  }
</script>

<style scoped>
  .hello {
    width: 100%;
    height: 500px;
  }
</style>
