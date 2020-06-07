import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import ZingChartSample from "@/components/ZingChartSample";
import ChartMenu from "@/components/ChartMenu";
import ZingChartIndex from "@/components/ZingChartIndex";
import AmchartSample from "../components/AmchartSample";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // }
    {
      path: '/',
      name: 'ChartMenu',
      component: ChartMenu
    },
    {
      path: '/amchart-sample',
      name: 'AmchartSample',
      component: AmchartSample
    },
    {
      path: '/zingchart-sample',
      name: 'ZingChartSample',
      component: ZingChartSample
    },
    {
      path: '/zingchart-index',
      name: 'ZingChartIndex',
      component: ZingChartIndex
    }
  ]
})
