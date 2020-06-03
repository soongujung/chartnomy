import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import ZingIndexSample from "@/components/ZingIndexSample";
import ZingChartDemo from "@/components/ZingChartDemo";
import ChartMenu from "@/components/ChartMenu";
import ZingChartIndex from "@/components/ZingChartIndex";
import AmChartIndexSample from "../components/AmChartIndexSample";

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
      path: '/amchart4-vue',
      name: 'AmChartIndexSample',
      component: AmChartIndexSample
    },
    {
      path: '/zingchart-vue',
      name: 'ZingIndexSample',
      component: ZingIndexSample
    },
    {
      path: '/zingchart-api',
      name: 'ZingChartIndex',
      component: ZingChartIndex
    },
    {
      path: '/zingchart-org',
      name: 'ZingChartDemo',
      component: ZingChartDemo
    },
  ]
})
