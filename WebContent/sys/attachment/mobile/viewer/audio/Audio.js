define([
  'dojo/_base/declare',
  'dijit/_WidgetBase',
  'dojo/dom-construct',
  'mui/util',
  'dojo/topic',
  'sys/attachment/mobile/viewer/audio/AudioLogMixin'
], function(declare, _WidgetBase, domConstruct, util, topic, AudioLogMixin) {
  return declare('sys.attachment.Audio', [_WidgetBase, AudioLogMixin], {
    src: null,
    // 开始
    START: 'mui/audio/start',
    // 暂停
    STOP: 'mui/audio/stop',
    // 加载
    LOAD: 'mui/audio/load',
    // 播放时间
    UPDATE_TIME: 'mui/audio/updatetime',
    // 是否显示工具栏
    controls: '',

    buildRendering: function() {
      this.inherited(arguments)

      this.audioNode = domConstruct.create(
        'audio',
        {
          innerHTML:
            '<source src="' +
            util.formatUrl(this.src) +
            '" type="audio/mpeg"></source>',
          className: 'muiAttViewerAudio',
          controls: this.controls
        },
        this.domNode
      )

      this.subscribe(this.START, 'start')
      this.subscribe(this.LOAD, 'load')
      this.subscribe(this.STOP, 'stop')
      this.connect(this.audioNode, 'timeupdate', 'timeupdate')
      this.connect(this.audioNode, 'canplay', 'canplay')
    },

    load: function() {
      this.audioNode.load()
    },

    start: function() {
      this.audioNode.play()
    },

    stop: function() {
      this.audioNode.pause()
    },

    timeupdate: function(evt) {
      this.inherited(arguments)
      topic.publish(this.UPDATE_TIME)
    },

    total: function(evt) {}
  })
})
