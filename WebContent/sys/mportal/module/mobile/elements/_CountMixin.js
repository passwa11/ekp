/**
 * 此插件专门做统计
 */
define([
  "dojo/_base/declare",
  "dojo/request",
  "dojo/Deferred",
  "mui/util",
  "dojo/_base/lang",
  "dojo/_base/array"
], function(declare, request, Deferred, util, lang, array) {
  return declare("sys.mportal.module.CountMixin", null, {
    // 统计数链接
    countUrl: "",
    // 统计数
    count: "",
    // 统计包解析路径
    countPath: "count",

    counting: function(callback) {
      var paths = this.countPath.split(".")
      var deferred = new Deferred()
      if (this.count) {
        deferred.resolve({count: this.count})
      } else if (this.countUrl) {
        // 缓存请求
        if (!window.countUrls) {
          window.countUrls = {}
        }

        var countCache = window.countUrls[this.countUrl]
        if (countCache) {
          // 未缓存统计数据，代表未请求完毕
          if (!countCache.count) {
            countCache.defer.promise.then(function(count) {
              countCache.count = count
              deferred.resolve(countCache.count)
            })
          } else {
            deferred.resolve(countCache.count)
          }
        } else {
          countCache = window.countUrls[this.countUrl] = {defer: deferred}

          request
            .get(util.formatUrl(this.countUrl), {
              handleAs: "json"
            })
            .then(function(data) {
              deferred.resolve(data)
            })
        }
      }

      deferred.promise.then(
        lang.hitch(this, function(data) {
          array.forEach(paths, function(path) {
            data = data[path]
          })
          var count = parseInt(data)
          if (callback) {
            callback.call(this, count)
          }
        })
      )
    }
  })
})
