define(function(require, exports) {
  // 新增日志接口
  var ADD_URL = '/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=save'
  // 更新日志接口
  var UPDATE_URL =
    '/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=update'
  // 获取日志接口
  var FIND_URL =
    '/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=viewByAttId'

  var $ = require('lui/jquery')
  var env = require('lui/util/env')

  // 开启
  var enable = false
  // 有效
  var support = true

  // 日志主键
  var fdLogId

  // 是否启用续播功能
  var isEnable = function() {
    return enable && support
  }

  // 设置是否支持
  exports.setSupport = function(flag) {
    support = flag
  }

  // 更新日志
  var update = (exports.update = function(fdId, fdParam) {
    if (!isEnable()) {
      return
    }
    return $.post(env.fn.formatUrl(UPDATE_URL), {
      fdId: fdId,
      fdParam: fdParam
    })
  })

  // 新增日志
  var add = (exports.add = function(fdAttId, fdParam, fdType) {
    if (!isEnable()) {
      return
    }
    return $.post(env.fn.formatUrl(ADD_URL), {
      fdAttId: fdAttId,
      fdParam: fdParam,
      fdType: fdType
    })
  })

  exports.log = function(fdAttId, fdParam, type) {
    if (fdLogId) {
      return update(fdLogId, fdParam)
    } else {
      var defer = $.Deferred()
      var callback = add(fdAttId, fdParam, type)
      callback && callback.then(function(data) {
        fdLogId = data.fdId
        defer.resolve(data)
      })
      return defer.promise()
    }
  }

  // 查看日志
  exports.find = function(fdAttId, fdType) {
    var defer = $.Deferred()
    $.getJSON(
      env.fn.formatUrl(FIND_URL + '&fdAttId=' + fdAttId + '&fdType=' + fdType)
    ).then(function(data) {
      enable = data.fdEnable
      fdLogId = data.fdId
      defer.resolve(data)
    })
    return defer.promise()
  }
})
