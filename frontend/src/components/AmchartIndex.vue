<template>
  <div>
    <a href="/">
      <h1>GO HOME</h1>
    </a>
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
          DATE: {
            requestUrl: '/web/trending/index/DATE',
            valueNm: 'date',
            valueColumn: 'DATE'
          },
          USD: {
            requestUrl: '/web/trending/index/exchange/USD',
            color: '#d9480f',
            valueNm: 'value',
            valueColumn: 'USD',
            tooltipText: '환율 : {valueY.value} (달러/원)',
            legendName: '환율 (달러)',
            seriesLine: null,
            valueAxis: null,
          },
          KOSPI: {
            requestUrl: '/web/trending/index/KOSPI',
            color: '#339af0',
            valueNm: 'value',
            valueColumn: 'KOSPI',
            tooltipText: 'kospi : {valueY.value} (원)',
            legendName: 'KOSPI 지수',
            seriesLine: null,
            valueAxis: null,
          },
          LOAN_KR: {
            requestUrl: '/web/trending/index/loan/LOAN_KR',
            color: '#51cf66',
            valueNm: 'value',
            valueColumn: 'LOAN_KR',
            tooltipText: '정책 금리(한국) : {valueY.value} (%)',
            legendName: '정책 금리(한국)',
            seriesLine: null,
            valueAxis: null,
          },
          LOAN_US: {
            requestUrl: '/web/trending/index/loan/LOAN_US',
            color: '#868e96',
            valueNm: 'value',
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
        await this.fetchData(this.chartOptions.DATE.valueColumn)
        console.log('Date Series')

        await this.fetchData(this.chartOptions.LOAN_KR.valueColumn)
        console.log('LOAN_KR')

        await this.fetchData(this.chartOptions.LOAN_US.valueColumn)
        console.log('LOAN_US')

        await this.fetchData(this.chartOptions.USD.valueColumn)
        console.log('USD')

        await this.fetchData(this.chartOptions.KOSPI.valueColumn)
        console.log('KOSPI')
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
      bindingResult(column_name, response_data){
        this.apiResult[column_name] = response_data;
        this.apiResult[column_name].forEach(obj => {
          obj['date'] = this.$moment(obj['date'], 'YYYYMMDD').toDate();
        })
      },
      // TODO (2)
      // Nodejs 오픈소스 중에 queryString을 조합해주는 모듈이 있는데 해당 내용을 찾아서 새로운 버전을 만들자.
      buildDateQueryParam(requestUrl, obj){
        let from = obj.from
        let to = obj.to
        let result = requestUrl + '?' + 'from=' + from +'&' + 'to=' + to;
        return result
      },
      getDefaultDateParameter(){
        let from_date = this.$moment().add(-5, 'Y').format('YYYY')
        from_date = from_date + '0101000001'

        let curr_moment = this.$moment()
        let curr_year = curr_moment.format('YYYY')
        let to_date = curr_year + '1231235959'

        return {
          from: from_date,
          to: to_date
        }
      },
      fetchData(indexTypeNm){
        let chartOption = this.chartOptions[indexTypeNm]
        let requestUrl = chartOption.requestUrl
        let queryString = this.buildDateQueryParam(requestUrl, this.getDefaultDateParameter())

        let promise = api.get(queryString)
          .then(res => {
            this.bindingResult(indexTypeNm, res.data)
          })
          .catch(err => {
            console.log('err >>> ', err)
          })

        return promise
      },
      getDateSeries(){
        let requestUrl = this.chartOptions.DATE.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getDefaultDateParameter())

        let promise = api.get(queryString)
        .then(res => {
          this.bindingResult('DATE', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getLoanKr(){
        let requestUrl = this.chartOptions.LOAN_KR.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getDefaultDateParameter())

        let promise = api.get(queryString)
        .then(res => {
          this.bindingResult('LOAN_KR', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getLoanUs(){
        let requestUrl = this.chartOptions.LOAN_US.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getDefaultDateParameter())

        let promise = api.get(queryString)
        .then(res => {
          this.bindingResult('LOAN_US', res.data);
        })
        .catch(err => {
          console.log('err >>> ', err);
        });

        return promise;
      },
      getUSD(){
        let requestUrl = this.chartOptions.USD.requestUrl;
        let queryString = this.buildDateQueryParam(requestUrl, this.getDefaultDateParameter())

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
        let queryString = this.buildDateQueryParam(requestUrl, this.getDefaultDateParameter())

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
        let dateColumnNm = this.chartOptions['DATE'].valueNm;
        let color = chartOption.color;

        let seriesLine = chart.series.push(new am4charts.LineSeries())
        chartOption.seriesLine = seriesLine;

        seriesLine.data = null;
        seriesLine.yAxis = chartOption.valueAxis;
        seriesLine.showOnInit = false;

        seriesLine.dataFields.valueY = valueNm;
        seriesLine.dataFields.dateX = dateColumnNm;

        seriesLine.stroke = am4core.color(color);       // 선의 색상
        seriesLine.fill = am4core.color(color);         // 선의 내부
        seriesLine.strokeWidth = 2;                     // 선의 굵기

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
