// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Vuex from 'vuex'
import router from './router'
import api from '@/api'
import { setTitleHack } from './assets/js/utils.js'

// 引入样式
import '@/assets/css/normalize.css'
import '@/assets/iconfont/iconfont.css'

// 引用kkjdk
import kk from 'kkjs'

// element引用
import { Upload } from 'element-ui'

import {
  TransferDom,
  XButton,
  Confirm,
  Group,
  GroupTitle,
  Cell,
  CellBox,
  XInput, 
  XTextarea,
  Popup,
  Tabbar,
  TabbarItem,
  Search,
  Swiper,
  SwiperItem,
  Tab,
  TabItem,
  Card,
  Datetime,
  XCircle,
  XAddress,
  Picker,
  PopupPicker,
  PopupHeader,
  Timeline,
  TimelineItem,
  XSwitch,
  Toast,
  LoadingPlugin,
  ToastPlugin,
  Qrcode,
  Swipeout, 
  SwipeoutItem, 
  SwipeoutButton
} from 'vux'

Vue.directive('transfer-dom', TransferDom)
Vue.component('x-button', XButton)
Vue.component('confirm', Confirm)
Vue.component('Group', Group)
Vue.component('Cell', Cell)
Vue.component('CellBox', CellBox)
Vue.component('XInput', XInput)
Vue.component('XTextarea', XTextarea)
Vue.component('GroupTitle', GroupTitle)
Vue.component('Popup', Popup)
Vue.component('Tabbar', Tabbar)
Vue.component('TabbarItem', TabbarItem)
Vue.component('Search', Search)
Vue.component('Swiper', Swiper)
Vue.component('SwiperItem', SwiperItem)
Vue.component('Tab', Tab)
Vue.component('TabItem', TabItem)
Vue.component('Card', Card)
Vue.component('Datetime', Datetime)
Vue.component('XCircle', XCircle)
Vue.component('x-address', XAddress)
Vue.component('picker', Picker)
Vue.component('popup-picker', PopupPicker)
Vue.component('popup-header', PopupHeader)
Vue.component('Timeline', Timeline)
Vue.component('TimelineItem', TimelineItem)
Vue.component('toast', Toast)
Vue.component('x-switch', XSwitch)
Vue.component(Upload.name, Upload)
Vue.component('qrcode', Qrcode)
Vue.component('swipeout', Swipeout)
Vue.component('swipeout-item', SwipeoutItem)
Vue.component('swipeout-button', SwipeoutButton)

Vue.config.productionTip = false

Vue.use(Vuex)
Vue.use(ToastPlugin)
Vue.use(LoadingPlugin)

Vue.prototype.$api = api

// 全局过滤器 类名判断
Vue.filter('typeClass', function (value) {
  if (value === 'taxi') {
    return 'fs-iconfont-dache'
  } else if (value === 'hotel') {
    return 'fs-iconfont-jiudian'
  } else if (value === 'airplane') {
    return 'fs-iconfont-jipiao'
  } else {
    return 'fs-iconfont-qianbi1'
  }
})

router.beforeEach((to, from, next) => {
  if (to.meta.name) {
    setTitleHack(to.meta.name)
    // 修改kk的标题
    if (kk.isKK()) {
      kk.app.setTitle(to.meta.name)
    }
  }
  next()
})

const store = new Vuex.Store({
  state: {
    accountJson: {},
    detailJson: []
  },
  mutations: {
    setDetailJson (state, detailJson) {
      state.detailJson = detailJson
    },
    setAccountJson (state, accountJson) {
      state.accountJson = accountJson
    }
  }
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  template: '<App/>'
})

// 数字增加动画
Vue.prototype.$countNumber = (key, vue, data) => {
  let range = data[key]
  let count = 0; Math.pow(10, String(range).length - 1)
  // 动态计算每次变动的时间
  let time = 20
  let step = 10
  let add = range / time

  let timer = setInterval(() => {
    count = count + add
    let a = parseInt(count)
    vue.$set(data, key, a)
    if (a >= range) {
      vue.$set(data, key, range)
      clearInterval(timer)
    }
  }, step)
}
