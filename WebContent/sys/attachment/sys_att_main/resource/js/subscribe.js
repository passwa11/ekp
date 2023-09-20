define(function(require, exports, module) {
  var Module = require('lui/framework/module')
  var lang = require('lang!sys-attachment')

  Module.install('sysAttachment', {
    // 模块变量
    $var: {},
    // 模块多语言
    $lang: {}
  })

  var module = Module.find('sysAttachment')

  module.controller(function($var, $lang, $function, $router) {
    $router.define({
      startpath: '/all',

      routes: [
        {
          path: '/all', // 所有
          action: {
            type: 'content',
            options: {
              cri: {}
            }
          }
        },
        {
          path: '/upload', // 我上传的
          action: {
            type: 'content',
            options: {
              cri: { myAtt: 'upload,' + lang['sysAttMain.myUpload'] }
            }
          }
        },
        {
          path: '/download', // 我下载的
          action: {
            type: 'content',
            options: {
              cri: { myAtt: 'download,' + lang['sysAttMain.list.myDownload'] }
            }
          }
        },
        {
          path: '/borrow', // 我借阅的
          action: function() {
            openPage(
              $var.$contextPath +
                '/sys/attachment/sys_att_borrow/sysAttBorrow_list.jsp?type=my'
            )
          }
        },
        {
			path : '/management', // 后台管理
			action : {
				type : 'pageopen',
				options : {
					url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/sys/attachment/tree.jsp',
					target : '_rIframe'
				}
			}
		}
      ]
    })
  })
})
