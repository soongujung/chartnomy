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
        chart : {},
        apiResult : {},
        chartOptions: {
          valueAxisPercent: null,
          valueAxisWon: null,
          dateAxis: null,
          DATE: 'date',
          USD: {
            color: '#d9480f',
            valueNm: 'price',
            valueColumn: 'USD',
            tooltipText: '환율 : {valueY.value} (원)',
            legendName: '환율 (달러)',
            seriesLine: null,
            valueAxis: null,
          },
          KOSPI: {
            color: '#339af0',
            valueNm: 'price',
            valueColumn: 'KOSPI',
            tooltipText: 'kospi : {valueY.value} (원)',
            legendName: 'KOSPI 지수',
            seriesLine: null,
            valueAxis: null,
          },
          LOAN_KR: {
            color: '#51cf66',
            valueNm: 'rate',
            valueColumn: 'LOAN_KR',
            tooltipText: '정책 금리(한국) : {valueY.value} (%)',
            legendName: '정책 금리(한국)',
            seriesLine: null,
            valueAxis: null,
          },
          LOAN_US: {
            color: '#868e96',
            valueNm: 'rate',
            valueColumn: 'LOAN_US',
            tooltipText: '정책 금리(미국) : {valueY.value} (%)',
            legendName: '정책 금리(미국)',
            seriesLine: null,
            valueAxis: null,
          }
        },
      };
    },
    async created(){
      // this.fetchIndexResult()
      await this.getApiResult()
      await this.renderSeriesChart()
    },
    mounted() {
      this.renderChart()
      console.log('renderChart >>> ')
    },
    beforeDestroy() {
      if (this.chart) {
        this.chart.dispose();
      }
    },
    methods:{
      async getApiResult(){
        await this.getLoanKr()
        console.log('getLoanKr >>> ')

        await this.getLoanUs()
        console.log('getLoanUs >>> ')

        await this.getExchangeRateUs()
        console.log('getExchangeRateUs >>> ')

        await this.getKospi()
        console.log('getKospi >>> ')
      },
      renderSeriesChart(){
        const chartOption = this.chartOptions

        let series_LOAN_KR = chartOption.LOAN_KR.seriesLine
        series_LOAN_KR.data = this.apiResult['LOAN_KR']

        let series_LOAN_US = chartOption.LOAN_US.seriesLine
        series_LOAN_US.data = this.apiResult['LOAN_US']

        let series_USD = chartOption.USD.seriesLine
        series_USD.data = this.apiResult['USD']

        let series_KOSPI = chartOption.KOSPI.seriesLine
        series_KOSPI.data = this.apiResult['KOSPI']

        this.chart.validateData()
      },
      async fetchIndexResult(){

        const chartOption = this.chartOptions

        await this.getLoanKr()
        console.log('getLoanKr >>> ')
        let series_LOAN_KR = chartOption.LOAN_KR.seriesLine
        series_LOAN_KR.data = this.apiResult['LOAN_KR']

        await this.getLoanUs()
        console.log('getLoanUs >>> ')
        let series_LOAN_US = chartOption.LOAN_US.seriesLine
        series_LOAN_US.data = this.apiResult['LOAN_US']

        await this.getExchangeRateUs()
        console.log('getExchangeRateUs >>> ')
        let series_USD = chartOption.USD.seriesLine
        series_USD.data = this.apiResult['USD']

        await this.getKospi()
        console.log('getKospi >>> ')
        let series_KOSPI = chartOption.KOSPI.seriesLine
        series_KOSPI.data = this.apiResult['KOSPI']

        // this.chart.invalidateData()
        this.chart.validateData()

      },
      bindingResult(column_name, response_data){
        this.apiResult[column_name] = response_data;
        this.apiResult[column_name].forEach(obj => {
          obj['date'] = this.$moment(obj['date'], 'YYYYMMDD').toDate();
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
        am4core.options.minPolylineStep
        am4core.options.onlyShowOnViewport = true;
        am4core.options.queue = true;

        this.chart = chart;
        chart.paddingRight = 20;

        let dateAxis = chart.xAxes.push(new am4charts.DateAxis());
        this.chartOptions.dateAxis = dateAxis;

        // dateAxis.renderer.grid.template.location = 0;
        dateAxis.renderer.grid.template.location = 0.5;
        dateAxis.renderer.minGridDistance = 50;
        dateAxis.renderer.ticks.template.length = 8;
        dateAxis.renderer.ticks.template.strokeOpacity = 0.1;
        dateAxis.renderer.grid.template.disabled = true;
        dateAxis.renderer.ticks.template.disabled = false;
        dateAxis.renderer.ticks.template.strokeOpacity = 0.2;
        dateAxis.renderer.minLabelPosition = 0.01;
        dateAxis.renderer.maxLabelPosition = 0.99;
        dateAxis.keepSelection = true;
        dateAxis.minHeight = 30;

        dateAxis.groupData = true;
        dateAxis.minZoomCount = 5;

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
        this.chartOptions.valueAxisWon = valueAxisWon;

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

        // this.chartOptions.valueAxisPercent = valueAxisPercent;
        // this.chartOptions.valueAxisWon = valueAxisWon;

        this.chartOptions.KOSPI.valueAxis = valueAxisWon;
        this.chartOptions.USD.valueAxis = valueAxisWon;
        this.chartOptions.LOAN_KR.valueAxis = valueAxisPercent;
        this.chartOptions.LOAN_US.valueAxis = valueAxisPercent;

        let series_KOSPI    = this.createSeries(chart,  'KOSPI');
        let series_USD      = this.createSeries(chart,  'USD');
        let series_LOAN_KR  = this.createSeries(chart,  'LOAN_KR');
        let series_LOAN_US  = this.createSeries(chart,  'LOAN_US');

        let scrollbarX = new am4charts.XYChartScrollbar();
        scrollbarX.series.push(series_KOSPI);
        scrollbarX.series.push(series_USD);
        scrollbarX.series.push(series_LOAN_KR);
        scrollbarX.series.push(series_LOAN_US);

        // chart.scrollbarX = scrollbarX;

      },
      createSeries(chart, indexType){
        console.log('createSeries :: indexType >>> ', indexType)
        // let valueNm = 'value';
        let chartOption = this.chartOptions[indexType]
        let valueNm = chartOption.valueNm;
        let dateColumnNm = this.chartOptions['DATE']
        let color = chartOption.color;

        // let seriesLine = new am4charts.LineSeries();
        // chart.series.push(seriesLine);
        let seriesLine = chart.series.push(new am4charts.LineSeries())
        chartOption.seriesLine = seriesLine;

        // seriesLine.data = this.apiResult[indexType]
        seriesLine.data = null;
        seriesLine.yAxis = chartOption.valueAxis;

        seriesLine.dataFields.valueY = valueNm;
        seriesLine.dataFields.dateX = dateColumnNm;
        seriesLine.strokeWidth = 2;                     // 선의 굵기
        seriesLine.minBulletDistance = 20;
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

        // Add scrollbar
        // chart.scrollbarX = new am4charts.XYChartScrollbar();
        // chart.scrollbarX.series.push(seriesLine);

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
