# ZingChart
# install
```bash
$ npm install zingchart-vue --save
```
  
# router 추가
## src/router/index.js
```javascript
import Vue from 'vue'
import Router from 'vue-router'

// ...
import TrendingIndex from "@/components/TrendingIndex";
import ZingIndexVue from "@/components/ZingIndexVue";
import ZingIndex from "@/components/ZingIndex";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
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

```
# src/components/ZingIndexVue.vue
```vue
<template>
  <div class="zingchart-vue-test">
    <h1>zingchart-vue</h1>
    <a href="https://www.npmjs.com/package/zingchart-vue">zinchart-vue official</a>
    <zingchart :data="chartData" :width="700"></zingchart>
  </div>
</template>

<script>
  import Vue from 'vue';

  import vueMoment from 'vue-moment';
  import zingchartVue from 'zingchart-vue';

  Vue.use(vueMoment)
  Vue.use(zingchartVue)

  export default {
    name: "ZingIndexVue",
    components: {
      zingchart: zingchartVue
    },
    data(){
      return{
        chartData: {
          type: 'line',
          series: [{
            values: [4,5,3,3,4,4]
          }]
        }
      }
    }
  }
</script>

<style scoped>

</style>
```


