<template>
  <div class="hello" ref="chartdiv">
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
    name: 'TrendingIndex',
    data(){
      return {
        'chartData': [],
        'chart': {}
      };
    },
    methods:{
      getTrendingIndexResult(){
        api.get('/web/stock/trending')
          .then(res=>{
            this.chartData = res.data;
            this.renderChart();
            // this.backupChart();
          })
          .catch(err=>{
            console.log('err >>> ', err)
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

        let valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
        valueAxis.tooltip.disabled = true;
        valueAxis.renderer.minWidth = 35;

        let series_kospi = chart.series.push(new am4charts.LineSeries());
        series_kospi.dataFields.dateX = "date";
        series_kospi.dataFields.valueY = "kospiPrice";

        series_kospi.tooltipText = "kospi : {valueY.value}";
        chart.cursor = new am4charts.XYCursor();

        let series_loanKr = chart.series.push(new am4charts.LineSeries());
        series_loanKr.dataFields.dateX = "date";
        series_loanKr.dataFields.valueY = "loanKrPrice";

        series_loanKr.tooltipText = "정책금리 (한국) : {valueY.value}";
        // chart.cursor = new am4charts.XYCursor();

        let series_loanUs = chart.series.push(new am4charts.LineSeries());
        series_loanUs.dataFields.dateX = "date";
        series_loanUs.dataFields.valueY = "loanUsPrice";

        series_loanUs.tooltipText = "정책금리 (미국) : {valueY.value}";

        let series_exchangeWonDallor = chart.series.push(new am4charts.LineSeries());
        series_exchangeWonDallor.dataFields.dateX = "date";
        series_exchangeWonDallor.dataFields.valueY = "exchangeWonDallor";

        series_exchangeWonDallor.tooltipText = "환율 (원/달러) : {valueY.value}";

        let scrollbarX = new am4charts.XYChartScrollbar();
        scrollbarX.series.push(series_kospi);
        scrollbarX.series.push(series_loanKr);

        chart.scrollbarX = scrollbarX;

        this.chart = chart;
      },
      backupChart(){
        let chart = am4core.create(this.$refs.chartdiv, am4charts.XYChart);

        chart.paddingRight = 20;

        let data = [];
        let visits = 10;
        for (let i = 1; i < 366; i++) {
          visits += Math.round((Math.random() < 0.5 ? 1 : -1) * Math.random() * 10);
          data.push({ date: new Date(2018, 0, i), name: "name" + i, value: visits });
        }

        chart.data = data;
        this.chartData = data;

        let dateAxis = chart.xAxes.push(new am4charts.DateAxis());
        dateAxis.renderer.grid.template.location = 0;

        let valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
        valueAxis.tooltip.disabled = true;
        valueAxis.renderer.minWidth = 35;

        let series = chart.series.push(new am4charts.LineSeries());
        series.dataFields.dateX = "date";
        series.dataFields.valueY = "value";

        series.tooltipText = "{valueY.value}";
        chart.cursor = new am4charts.XYCursor();

        let scrollbarX = new am4charts.XYChartScrollbar();
        scrollbarX.series.push(series);
        chart.scrollbarX = scrollbarX;

        this.chart = chart;
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
