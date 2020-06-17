<template>
  <div>
    <a href="/">
      <h1>GO HOME</h1>
    </a>
<!--    <h1>Index Chart (KOSPI, 정책금리(한국/미국), 환율(원/달러)</h1>-->
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
          valueAxisUSD: null,
          dateAxis: null,
          DATE: 'date',
          USD: {
            requestUrl: '/web/trending/index/exchange/USD',
            color: '#d9480f',
            valueNm: 'price',
            valueColumn: 'USD',
            tooltipText: '환율 : {valueY.value} (달러/원)',
            legendName: '환율 (달러)',
            seriesLine: null,
            valueAxis: null,
          },
          KOSPI: {
            requestUrl: '/web/trending/index/KOSPI',
            color: '#339af0',
            valueNm: 'price',
            valueColumn: 'KOSPI',
            tooltipText: 'kospi : {valueY.value} (원)',
            legendName: 'KOSPI 지수',
            seriesLine: null,
            valueAxis: null,
          },
          LOAN_KR: {
            requestUrl: '/web/trending/index/loan/LOAN_KR',
            color: '#51cf66',
            valueNm: 'rate',
            valueColumn: 'LOAN_KR',
            tooltipText: '정책 금리(한국) : {valueY.value} (%)',
            legendName: '정책 금리(한국)',
            seriesLine: null,
            valueAxis: null,
          },
          LOAN_US: {
            requestUrl: '/web/trending/index/loan/LOAN_US',
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
        this.renderTooltip('LOAN_KR')

        let series_LOAN_US = chartOption.LOAN_US.seriesLine
        series_LOAN_US.data = this.apiResult['LOAN_US']
        this.renderTooltip('LOAN_US')

        let series_USD = chartOption.USD.seriesLine
        series_USD.data = this.apiResult['USD']
        this.renderTooltip('USD')

        let series_KOSPI = chartOption.KOSPI.seriesLine
        series_KOSPI.data = this.apiResult['KOSPI']
        this.renderTooltip('KOSPI')

        this.chart.validateData()
        this.chart.legend = new am4charts.Legend();
      },
      renderTooltip(indexTypeNm){
        let chartOption = this.chartOptions[indexTypeNm];
        let seriesLine = chartOption.seriesLine;
        let color = chartOption.color;

        seriesLine.minBulletDistance = 20;
        seriesLine.name = chartOption.legendName;
        seriesLine.tensionX = 0.8;

        seriesLine.tooltipText = chartOption.tooltipText;
        // seriesLine.tooltip.pointerOrientation = "vertical";
        seriesLine.tooltip.getFillFromObject = false;
        seriesLine.tooltip.getStrokeFromObject = true;
        seriesLine.tooltip.label.fill = am4core.color(color);           // 툴팁 내부 폰트 색상
        seriesLine.tooltip.background.cornerRadius = 20;                // 툴팁 테두리 Radius
        // seriesLine.tooltip.background.fillOpacity = 0.8;                // 툴팁 투명도
        seriesLine.tooltip.background.fill = am4core.color('#222');
        seriesLine.tooltip.background.strokeWidth = 2;
        seriesLine.tooltip.label.fill = seriesLine.stroke;
        // seriesLine.tooltip.label.padding(12,12,12,12);
        seriesLine.name = chartOption.legendName;
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
      getFromToDate(){
        let from_date = this.$moment().add(-5, 'Y').format('YYYY')
        from_date = from_date + '0101000001'

        let curr_meoment = this.$moment()
        let curr_year = curr_meoment.format('YYYY')
        let to_date = curr_year + '1231235959'

        return {
          from: from_date,
          to: to_date
        }
      },
      buildDateQueryParam(requestUrl, obj){
        let from = obj.from
        let to = obj.to
        let result = requestUrl + '?' + 'from=' + from +'&' + 'to=' + to;
        return result
      },
      getLoanKr(){
        let requestUrl = this.chartOptions.LOAN_KR.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getFromToDate())

        // let promise = api.get('/web/trending/index/loan/LOAN_KR')
        let promise = api.get(queryString)
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
        let requestUrl = this.chartOptions.LOAN_US.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getFromToDate())

        let promise = api.get(queryString)
        .then(res => {
          this.bindingResult('LOAN_US', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getExchangeRateUs(){
        let requestUrl = this.chartOptions.USD.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getFromToDate())

        // let promise = api.get('/web/trending/index/exchange/USD')
        let promise = api.get(queryString)
        .then(res => {
          this.bindingResult('USD', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getKospi(){
        let requestUrl = this.chartOptions.KOSPI.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getFromToDate())

        // let promise = api.get('/web/trending/index/KOSPI')
        let promise = api.get(queryString)
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
        this.chartOptions.valueAxisWon = valueAxisWon;
        valueAxisWon.title.dy = -50;
        valueAxisWon.title.paddingRight = 0;
        valueAxisWon.title.paddingBottom = 400;
        valueAxisWon.renderer.opposite = false;
        valueAxisWon.title.fontWeight = 600;
        valueAxisWon.title.fontSize = 14;
        valueAxisWon.tooltip.disabled = false;
        valueAxisWon.title.text = "원";
        valueAxisWon.title.rotation = 0;
        valueAxisWon.title.align = "top";
        valueAxisWon.renderer.grid.template.strokeOpacity = 0;
        valueAxisWon.renderer.grid.template.disabled = true;

        let valueAxisPercent = new am4charts.ValueAxis();
        chart.yAxes.push(valueAxisPercent);
        this.chartOptions.valueAxisPercent = valueAxisPercent
        valueAxisPercent.title.dy = -50;
        valueAxisPercent.title.paddingRight = 0;
        valueAxisPercent.title.paddingBottom = 400;
        valueAxisPercent.renderer.opposite = true;
        valueAxisPercent.title.fontWeight = 600;
        valueAxisPercent.title.fontSize = 14;
        valueAxisPercent.tooltip.disabled = false;
        valueAxisPercent.title.text = "%";
        valueAxisPercent.title.rotation = 0;
        valueAxisPercent.title.align = "top";
        valueAxisPercent.renderer.grid.template.strokeOpacity = 0;
        valueAxisPercent.renderer.grid.template.disabled = true;

        let valueAxisUSD = new am4charts.ValueAxis();
        chart.yAxes.push(valueAxisUSD);
        this.chartOptions.valueAxisUSD = valueAxisUSD;
        valueAxisUSD.title.dy = -50;
        valueAxisUSD.title.paddingRight = 0;
        valueAxisUSD.title.paddingBottom = 400;
        valueAxisUSD.renderer.opposite = false;
        valueAxisUSD.title.fontWeight = 600;
        valueAxisUSD.title.fontSize = 14;
        valueAxisUSD.tooltip.disabled = false;
        valueAxisUSD.title.text = "환율 (원)";
        valueAxisUSD.title.rotation = 0;
        valueAxisUSD.title.align = "top";
        valueAxisUSD.renderer.grid.template.strokeOpacity = 0;
        valueAxisUSD.renderer.grid.template.disabled = true;

        this.chartOptions.KOSPI.valueAxis = valueAxisWon;
        this.chartOptions.USD.valueAxis = valueAxisUSD;
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

        chart.scrollbarX = scrollbarX;
        chart.cursor = new am4charts.XYCursor();
      },
      createSeries(chart, indexType){
        console.log('createSeries :: indexType >>> ', indexType)

        let chartOption = this.chartOptions[indexType]
        let valueNm = chartOption.valueNm;
        let dateColumnNm = this.chartOptions['DATE']
        let color = chartOption.color;

        let seriesLine = chart.series.push(new am4charts.LineSeries())
        chartOption.seriesLine = seriesLine;

        // seriesLine.data = this.apiResult[indexType]
        seriesLine.data = null;
        seriesLine.yAxis = chartOption.valueAxis;
        seriesLine.showOnInit = false;

        seriesLine.dataFields.valueY = valueNm;
        seriesLine.dataFields.dateX = dateColumnNm;

        seriesLine.stroke = am4core.color(color);       // 선의 색상
        seriesLine.fill = am4core.color(color);         // 선의 내부
        seriesLine.strokeWidth = 2;                     // 선의 굵기

        // seriesLine.minBulletDistance = 20;
        // seriesLine.name = chartOption.legendName;
        // seriesLine.tensionX = 0.8;
        //
        // seriesLine.tooltipText = chartOption.tooltipText;
        // seriesLine.tooltip.pointerOrientation = "vertical";
        // seriesLine.tooltip.getFillFromObject = false;
        // seriesLine.tooltip.label.fill = am4core.color(color);           // 툴팁 내부 폰트 색상
        // seriesLine.tooltip.background.cornerRadius = 20;                // 툴팁 테두리 Radius
        // seriesLine.tooltip.background.fillOpacity = 0.8;                // 툴팁 투명도
        // seriesLine.tooltip.label.padding(12,12,12,12);
        // seriesLine.name = chartOption.legendName;
        // seriesLine.yAxis = chartOption.valueAxis;

        // let bullet = seriesLine.bullets.push(new am4charts.CircleBullet());
        // bullet.circle.fill = am4core.color('#000');
        // bullet.circle.strokeWidth = 2;
        // bullet.circle.propertyFields.radius = "townSize";
        //
        // let state = bullet.states.create("hover");
        // state.properties.scale = 1.2;
        //
        // let label = seriesLine.bullets.push(new am4charts.LabelBullet());
        // label.label.text=chartOption.tooltipText;
        // label.label.horizontalCenter = "left";
        // label.label.dx = 14;
        //
        // chart.legend = new am4charts.Legend();
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
