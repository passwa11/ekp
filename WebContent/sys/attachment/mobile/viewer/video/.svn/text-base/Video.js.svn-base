define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/dom-style",
  "mui/util",
  "dojo/topic",
  "sys/attachment/mobile/viewer/video/VideoLogMixin"
], function(
  declare,
  _WidgetBase,
  domConstruct,
  domStyle,
  util,
  topic,
  VideoLogMixin
) {
  return declare("sys.attachment.Video", [_WidgetBase, VideoLogMixin], {
    src: null,
    // 开始
    START: "mui/video/start",
    // 暂停
    STOP: "mui/video/stop",
    // 加载
    LOAD: "mui/video/load",
    // 播放时间
    UPDATE_TIME: "mui/video/updatetime",
    // 是否显示工具栏
    controls: "",

    thumbSrc: null,

    authDownload: false,

    buildRendering: function() {
      this.inherited(arguments)

      var controlslist = ""

      if (!this.authDownload) controlslist = "nodownload"

      this.videoNode = domConstruct.create(
        "video",
        {
          poster: util.formatUrl(this.thumbSrc),
          innerHTML:
            '<source src="' +
            util.formatUrl(this.src) +
            '" type="video/mp4"></source>',
          className: "muiAttViewerVideo",
          controls: this.controls,
          controlslist: controlslist
        },
        this.domNode
      )

      this.onResize()

      this.subscribe(this.START, "start")
      this.subscribe(this.LOAD, "load")
      this.subscribe(this.STOP, "stop")
      this.connect(this.videoNode, "timeupdate", "timeupdate")
      this.connect(this.videoNode, "seeked", "seeked")
      this.connect(this.videoNode, "canplay", "canplay")

      // 视频播放页面强制横屏
      // adapter.setOrientation('landscape')
    },

    // 横向
    onOriY: function() {
      // adapter.setOrientation(2)
      this.onResize()
    },

    // 重置宽高
    onResize: function() {
      setTimeout(
        function() {
          var w_h = util.getScreenSize()
          domStyle.set(this.videoNode, {
            height: w_h.h + "px"
          })
        }.bind(this),
        400
      )
    },

    // 竖向
    onOriX: function() {
      // adapter.setOrientation(1)
      this.onResize()
    },

    load: function() {
      this.videoNode.load()
    },

    start: function() {
      this.videoNode.play()
    },

    stop: function() {
      this.videoNode.pause()
    },

    timeupdate: function(evt) {
      this.inherited(arguments)
      topic.publish(this.UPDATE_TIME)
    },

    // 进度跳动
    seeked: function(evt) {
      this.inherited(arguments)
    },
    // 准备好播放
    canplay: function() {
      this.inherited(arguments)
    }
  })
})
