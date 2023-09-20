define(function(require, exports) {
  var $ = require('lui/jquery')
  var env = require('lui/util/env')

  // 日志后端接口
  var logApi = require('sys/attachment/viewer/resource/common/js/logApi')

  // 每多少秒更新一次
  var sec = 10
  // 更新并发控制
  var updating = false
  // 上次记录时间
  var lastTime = 0
  // 日志主键
  var fdLogId = null
  // 附件主键
  var fdAttId = null
  // 类型
  var type = 'audio'

  // 音频对象
  var audio = null

  var isChrome = function() {
    return typeof chrome !== 'undefined'
  }

  // 更新时间
  function setTime(fdParam) {
    updating = true

    if (fdLogId) {
      logApi.update(fdLogId, fdParam).then(function(data) {
        updating = false
      })
    } else {
      logApi.add(fdAttId, fdParam, type).then(function(data) {
        fdLogId = data.fdId
        updating = false
      })
    }
  }

  // 获取时间
  function getTime() {
    logApi.find(fdAttId, type).then(function(data) {
      if (data.fdId) {
        fdLogId = data.fdId
        lastTime = parseInt(data.fdParam)
        currentTime(audio, lastTime)
      }
    })
  }

  // 设置||获取播放进度
  function currentTime(obj, time) {
    if (!time) {
      return obj[0].currentTime
    }
    obj[0].currentTime = time
  }

  // 初始化视频
  exports.init = function(id) {
    fdAttId = id
    // 签名信息获取
    $.getJSON(
      env.fn.formatUrl(
        '/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttToken&fdId=' +
          fdAttId
      ),
      function(data) {
        if (data && data.token) {
          var url = env.fn.formatUrl(
            '/sys/attachment/mobile/viewer/play.jsp?token=' + data.token
          )

          audio = $(
            '<audio src="' +
              url +
              '" type="audio/mp3" id="mp3" style="height: 100%; width: 100%" controls="controls"></audio>'
          )

          audio.appendTo($('.lui-att-mp3-content'))

          if (isChrome()) {
            getTime()
          } else {
            audio.on('canplay', function() {
              getTime()
            })
          }

          // 兼容进度变化
          audio.on('timeupdate', function(evt) {
            if (updating) {
              return
            }

            if (Math.abs(currentTime(audio) - lastTime) >= sec) {
              lastTime = currentTime(audio)
              setTime(lastTime)
            }
          })
        }
      }
    )
  }
})
