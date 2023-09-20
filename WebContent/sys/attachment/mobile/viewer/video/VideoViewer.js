define([
  'dojo/_base/declare',
  'dojo/text!./tmpl/videoViewer.jsp',
  'sys/attachment/mobile/viewer/base/BaseViewer',
  'mui/util',
  'sys/attachment/mobile/viewer/video/Video',
  'dojo/dom-construct',
  'dojo/request'
], function(declare, tmpl, BaseViewer, util, Video, domConstruct, request) {
  return declare('sys.attachment.VideoViewer', [BaseViewer], {
    authDownload: false,
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
            self.video = new Video({
              src: '/sys/attachment/mobile/viewer/play.jsp?token=' + data.token,
              thumbSrc:
                '/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdId=' +
                self.fdId,
              fdAttId: self.fdId,
              authDownload: this.authDownload
            })
            self.video.startup()
            domConstruct.place(self.video.domNode, self.videoNode, 'last')
          }
        })
    }
  })
})
