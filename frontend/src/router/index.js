import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import TrendingIndex from "@/components/TrendingIndex";
import ZingIndexVue from "@/components/ZingIndexVue";
import ZingIndex from "@/components/ZingIndex";

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
      name: 'TrendingIndex',
      component: TrendingIndex
    },
    {
      path: '/zingchart-vue',
      name: 'ZingIndexVue',
      component: ZingIndexVue
    },
    {
      path: '/zingchart-org',
      name: 'ZingIndexOrg',
      component: ZingIndex
    }
  ]
})
