/**
 * 列表支持单页面适配器
 */
define(function(require, exports, module) {
  var SpaConst = require('../const')
  var topic = require('lui/topic')
  var env = require('lui/util/env')
  var panel = require('lui/panel')

  var listviewAdapter = {
    initProps: function($super, cfg) {
      this.spa = env.fn.getConfig().isSpa

      $super(cfg)

      // list:listview 配置在门户中，极速模式下不加载列表
      if ('false' === cfg.spa) {
        this.spa = false
      }

      if (!this.spa) return

      topic.subscribe(SpaConst.SPA_CHANGE_VALUES, this.spaListviewValues, this)

      this.on('layoutLoad', this.spaLayoutDone, this)
    },

    startup: function($super) {
      if (this.spa)
        // 不自己初始化
        this.criteriaInit = true

      $super()
    },

    spaCriteria: function(criteriaHashs) {
      if (!criteriaHashs) return

      var hashArray = criteriaHashs.split(';')

      var criterions = [],
        criMap = {}

      for (var i = 0; i < hashArray.length; i++) {
        var p = hashArray[i].split(':')
        var val = criMap[p[0]]
        if (val == null) {
          val = []
          criMap[p[0]] = val
        }

        var value = p[1]
        // 值中带有冒号特殊处理
//      if (p.length > 2) {
//		value = value + ":" + p[2]
//	}
	   for(var j = 2; j < p.length; j ++){
		value = value+ ":" + p[j];
	    }
        val.push(value)
      }

      for (var k in criMap) {
        criterions.push({
          key: k,
          value: criMap[k]
        })
      }

      return criterions
    },

    // 频道处理
    criteriaChannel: function() {
      if (this.channel == null) {
        return 'cri.q'
      }
      return 'cri.' + this.channel + '.q'
    },

    spaLayoutDone: function(evt) {
      if (!evt) return

      if (!this.isSpaLayoutDone()) return

      this.__isSpaLayoutDone = true

      this.spaListviewValues(this.spaEvt)
    },

    // 解决偶发加载顺序问题
    isSpaLayoutDone: function() {
      if (this.table && this.table.className == 'columnTable')
        this.__isSpaLayoutDone = true

      return !this.__isSpaLayoutDone && this.table
    },

    spaListviewValues: function(evt) {
      if(!evt) {
        return;
      }
      // 防止在多页签下初始化时多次渲染
      var parent = this.parent
      if (parent && parent instanceof panel.Content) {
        if (parent.isLazy) {
          return
        }

        if (evt.$parent && evt.$parent != parent) {
          return
        }
      }

      if (!evt || !evt.value) return

      this.spaEvt = evt

      if (this.isSpaLayoutDone()) {
        return
      }

      var criterions = []

      for (var key in evt.value) {
        if (this.criteriaChannel() == key) {
          // 筛选器值
          if (evt.value[key])
            criterions = criterions.concat(this.spaCriteria(evt.value[key]))
          continue
        }

        // 例外其他频道筛选器值
        if (key.indexOf('cri.') >= 0) {
          continue
        }

        // 其他值
        var val = evt.value[key]

        if (!val) continue

        var vals = val.split(',')

        if (vals.length > 1) val = vals[0]

        criterions.push({
          key: key,
          value: [val]
        })
      }

      /*if (this.spaCriterions) {
        // 参数没变
        if (
          this.serializeParams(this.spaCriterions) ==
          this.serializeParams(criterions)
        ) {
          return
        }
      }*/

      this.spaCriterions = criterions

      this.criteriaChange({
        criterions: criterions
      })
    }
  }

  module.exports = listviewAdapter
})
