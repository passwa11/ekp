define([
  'dojo/_base/declare',
  'dojo/text!./tmpl/audioViewer.jsp',
  'sys/attachment/mobile/viewer/base/BaseViewer',
  'mui/util',
  'dojo/dom-attr',
  'dojo/dom-style',
  'dojox/mobile/View',
  'sys/attachment/mobile/viewer/audio/Audio',
  'dojo/dom-construct',
  'dojox/mobile/ViewController',
  'dojox/mobile/viewRegistry',
  'dojo/request'
], function(
  declare,
  tmpl,
  BaseViewer,
  util,
  domAttr,
  domStyle,
  View,
  Audio,
  domConstruct,
  ViewController,
  viewRegistry,
  request
) {
  return declare('sys.attachment.AudioViewer', [BaseViewer], {
    templateString: tmpl,
    buildRendering: function() {
      this.inherited(arguments)

      var self = this
      request
        .get(
          util.formatUrl(
            '/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttToken&fdId=' +
              this.fdId
          ),
          {
            handleAs: 'json'
          }
        )
        .then(function(data) {
          if (data && data.token) {
            self.audio = new Audio({
              src:
                '/sys/attachment/mobile/viewer/play.jsp?token=' +
                data.token +
                '&fdId=' +
                self.fdId,
              fdAttId: self.fdId
            })
            self.audio.startup()
            domConstruct.place(self.audio.domNode, self.audioNode, 'last')
          }
        })
      var w_h = util.getScreenSize()
      domStyle.set(this.pageContent, {
        height: w_h.h + 'px',
        'line-height': w_h.h + 'px'
      })
    }
  })
})
