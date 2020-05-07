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
import ZingIndex from "../components/ZingIndex";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/zingchart',
      name: 'ZingIndex',
      component: ZingIndex
    }
  ]
})

```

