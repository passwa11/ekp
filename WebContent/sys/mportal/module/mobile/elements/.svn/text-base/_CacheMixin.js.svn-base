/**
 * 此插件专门做缓存
 */
define(["dojo/_base/declare"], function(declare) {
  return declare("sys.mportal.module.CacheMixin", null, {
    // 是否进行缓存
    cache: false,

    cacheInit: false,

    buildRendering: function() {
      this.inherited(arguments)

      this.cacheKey = location.pathname + ":" + this.id
      if (this.url) {
        if (this.cache) {
          var datas = localStorage.getItem(this.cacheKey)
          if (datas) {
            datas = JSON.parse(datas)
            datas = datas.datas
            this.onComplete(datas)
          }
        }
      }
    },

    onComplete: function(datas) {
      if (this.cache) {
        if (this.cacheInit) {
          var _datas = localStorage.getItem(this.cacheKey)
          if (_datas) {
            var cacheData = {cache: dojoConfig.cacheBust, datas: datas}
            if (_datas == JSON.stringify(cacheData)) {
              this.datas = null
              return
            }
          }
        }

        this.cacheInit = true
        var cacheData = {cache: dojoConfig.cacheBust, datas: datas}
        localStorage.setItem(this.cacheKey, JSON.stringify(cacheData))
      }
    }
  })
})
