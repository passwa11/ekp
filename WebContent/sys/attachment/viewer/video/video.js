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
  // 视频类型
  var contentType = null
  // 附件主键
  var fdAttId = null
  // 视频对象
  var video = null
  // 类型
  var type = 'video'

  // 是否为chrome
  var isChrome = function() {
    return typeof chrome !== 'undefined' && isMp4()
  }

  // 是否是mp4文件
  var isMp4 = function() {
    return contentType == 'video/mp4'
  }

  // 检查是否支持
  var checkEnable = function() {
    logApi.setSupport(isMp4())
  }

  // 更新时间
  function setTime(fdParam) {
    updating = true

    if (fdLogId) {
      var promiss = logApi.update(fdLogId, fdParam)
      if (promiss) {
        promiss.then(function(data) {
          updating = false
        })
      }
    } else {
      var promiss = logApi.add(fdAttId, fdParam, type)
      if (promiss) {
        promiss.then(function(data) {
          fdLogId = data.fdId
          updating = false
        })
      }
    }
  }

  // 获取
  function getTime() {
    var promiss = logApi.find(fdAttId, type)
    if (promiss) {
      promiss.then(function(data) {
        if (data.fdId) {
          fdLogId = data.fdId
          lastTime = parseInt(data.fdParam)

          currentTime(video, lastTime)
        }
      })
    }
  }

  // 设置||获取播放进度
  function currentTime(obj, time) {
    if (isChrome()) {
      if (!time) {
        return obj[0].currentTime
      }
      obj[0].currentTime = time
    } else {
      if (!time) {
        return obj.currentTime()
      } else {
        obj.currentTime(time)
      }
    }
  }

  // 构建video通用信息
  function buildCommonVideo() {
    // 视频封面图
    var posterUrl = env.fn.formatUrl(
      '/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdId=' +
        fdAttId
    )

    var videoDomNode = $(
      '<video id="video-component" controlslist="nodownload" class="video-js" controls preload="auto" style="width: 100%; height: 100%" poster="' +
        posterUrl +
        '"></video>'
    )

    videoDomNode.appendTo($('#video'))

    // 没下载权限禁用右键
    if (!authDownload) {
      videoDomNode.bind('contextmenu', function() {
        return false
      })
    }

    return videoDomNode
  }

  // 初始化chrome下的video信息
  function initChromeVideo(url) {
    video = buildCommonVideo()
    $('<source type="' + contentType + '" src="' + url + '">').appendTo(video)
    getTime()
  }

  // 初始化非chrome的video信息
  function initVideo(url) {
    video = buildCommonVideo()

    videojs.options.flash.swf = env.fn.formatUrl(
      '/sys/attachment/sys_att_main/video/video-js.swf'
    )

    video = videojs('video-component', {}, function() {
      this.on('loadedmetadata', function() {
        getTime()
      })
    })

    video.src({ src: url, type: contentType })
    
    if(window.isOpen == "false") {
    	//解决ie下不能在附件播放器里面点最大化没反应的问题
    	if (!document.fullscreenEnabled
				&& !document.webkitFullscreenEnabled
				&& !document.mozFullScreenEnabled
				&& !document.msFullscreenEnabled) {

			video.on('fullscreenchange', function() {

				this.pause();

				newwindow = window.open(window.location.href
						+ "&isOpen=true");

				if (window.focus) {
					newwindow.focus()
				}

			})
		}
    }
  }

  // 初始化视频
  exports.init = function(id, type) {
    contentType = type
    fdAttId = id
    checkEnable()
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

          // chrome 在做mov文件移动时，最后一帧有问题，暂时用原生video去兼容
          if (isChrome()) {
            initChromeVideo(url)
          } else {
            initVideo(url)
          }

          // 兼容进度变化
          video.on('timeupdate', function(evt) {
            if (updating) {
              return
            }

            if (Math.abs(currentTime(video) - lastTime) >= sec) {
              lastTime = currentTime(video)
              setTime(lastTime)
            }
          })
        }
      }
    )
  }
})
