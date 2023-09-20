define([
  'dojo/_base/declare',
  'sys/attachment/mobile/js/AttachmentLogApi',
  'dojox/mobile/sniff'
], function(declare, AttachmentLogApi, has) {
  return declare('sys.attachment.video.videoLogMixin', [AttachmentLogApi], {
    // 上次记录时间
    lastTime: 0,
    // 每多少秒更新一次
    sec: 10,
    // 类型
    type: 'video',
    // 更新并发控制
    loging: false,

    startup: function() {
      this.inherited(arguments)
      if (!has('ios')) {
        this.getTime()
      }
    },

    // 播放进度更新
    timeupdate: function() {
      if (this.loging) {
        return
      }

      if (Math.abs(this.videoNode.currentTime - this.lastTime) >= this.sec) {
        this.lastTime = this.videoNode.currentTime
        this.setTime()
      }
    },

    // 获取上次播放时间
    getTime: function() {
      this.find(this.fdAttId)
    },

    // ios需要在准备好播放后才能设置当前时间
    canplay: function() {
      if (has('ios')) {
        this.getTime()
      }
    },

    // 更新当前播放进度
    setTime: function() {
      this.loging = true
      this.log(this.fdAttId, this.lastTime)
    },

    addDone: function(data) {
      this.inherited(arguments)
      if (data) {
        this.loging = false
      }
    },

    updateDone: function() {
      this.inherited(arguments)
      this.loging = false
    },

    findDone: function(data) {
      this.inherited(arguments)
      if (data && data.fdParam) {
        if (data.fdParam) {
          this.lastTime = parseInt(data.fdParam)
          this.videoNode.currentTime = this.lastTime
        }
      }
    }
  })
})
