;(function() {
  CKEDITOR.plugins.add('pasteimage', {
    requires: 'clipboard',
    lang:
      'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn',
    hidpi: true,
    init: function(editor) {
      var userAgent = navigator.userAgent
      var isIE =
        userAgent.indexOf('compatible') > -1 && userAgent.indexOf('MSIE') > -1
      var isEdge = userAgent.indexOf('Edge') > -1 && !isIE
      var isIE11 =
        userAgent.indexOf('Trident') > -1 && userAgent.indexOf('rv:11.0') > -1

      var config = editor.config,
        lang = editor.lang.pasteimage,
		showMark = false;

      // 多图片上传
      function imageUpload(items, cb, loop) {
        for (var i = 0; i < items.length; ++i) {
          var item = items[i]

          if (item.type.indexOf('image') >= 0) {
            var d = new FormData()
            d.append('json', true)
            d.append('NewFile', item.getAsFile ? item.getAsFile() : item)
            ajax_upload(d, cb, item.img)
            if (!loop) break
          }
        }
      }

      // 提交流到后台
      function ajax_upload(d, cb, img) {
        $.ajax({
          url: config.getFilebrowserImageUploadUrl(editor),
          data: d,
          contentType: false,
          processData: false,
          type: 'POST',
          dataType: 'json',
          success: function(data) {
            if (data && data.status == '1') {
              var src =
                config.downloadUrl + '?fdId=' + data.filekey + '&picthumb=big&isSupportDirect=false'

              if (cb) {
                cb(src, img)
                return
              }

              var elem = editor.document.createElement('img')
              elem.setAttribute('alt', ''),
                elem.setAttribute('src', src),
                editor.insertElement(elem)
            }
          }
        })
      }

      // 获取提示层
      function getMask() {
        return $(editor.document.$.body).find('[data-lui-mask="mask"]')
      }

      // 隐藏提示层
      function hideMask() {
        //getMask().hide()
    	// 隐藏属性不生效，在页面上还是显示了遮罩，参考RDM:90878
    	getMask().remove()
      }

      // 显示提示层
      function showMask() {
        var doc = $(editor.document.$.body)
        var mask = doc.find('[data-lui-mask="mask"]')
        if (mask.length > 0) {
          mask.show()
          return
        }
        var mask = $('<div data-lui-mask="mask">')
        mask.css({
          border: '3px dashed #999',
          'z-index': 100,
          'background-color': '#fff',
          opacity: 0.6,
          position: 'fixed',
          'border-radius': 4,
          left: 3,
          right: 3,
          top: 3,
          bottom: 3
        })
        doc.append(mask)
      }

      // 将base64转换为文件
      function dataURLtoFile(dataurl, filename) {
        var arr = dataurl.split(','),
          mime = arr[0].match(/:(.*?);/)[1],
          bstr = atob(arr[1]),
          n = bstr.length,
          u8arr = new Uint8Array(n)
        while (n--) {
          u8arr[n] = bstr.charCodeAt(n)
        }

        return new Blob([u8arr], {
          type: mime
        })
      }

      function extractFromRtf(a) {
        var b = [],
          c = /\{\\pict[\s\S]+?\\bliptag\-?\d+(\\blipupi\-?\d+)?(\{\\\*\\blipuid\s?[\da-fA-F]+)?[\s\}]*?/,
          d
        a = a.match(
          new RegExp('(?:(' + c.source + '))([\\da-fA-F\\s]+)\\}', 'g')
        )
        if (!a) return b
        for (var e = 0; e < a.length; e++)
          if (c.test(a[e])) {
            if (-1 !== a[e].indexOf('\\pngblip')) d = 'image/png'
            else if (-1 !== a[e].indexOf('\\jpegblip')) d = 'image/jpeg'
            else continue
            b.push({
              hex: d ? a[e].replace(c, '').replace(/[^\da-fA-F]/g, '') : null,
              type: d
            })
          }
        return b
      }

      function convertHexStringToBytes(a) {
        var f = [],
          c = a.length / 2,
          g
        for (g = 0; g < c; g++) f.push(parseInt(a.substr(2 * g, 2), 16))
        return f
      }

      function convertBytesToBase64(a) {
        var f = '',
          c = a.length,
          g
        for (g = 0; g < c; g += 3) {
          var b = a.slice(g, g + 3),
            e = b.length,
            d = [],
            m
          if (3 > e) for (m = e; 3 > m; m++) b[m] = 0
          d[0] = (b[0] & 252) >> 2
          d[1] = ((b[0] & 3) << 4) | (b[1] >> 4)
          d[2] = ((b[1] & 15) << 2) | ((b[2] & 192) >> 6)
          d[3] = b[2] & 63
          for (m = 0; 4 > m; m++)
            f =
              m <= e
                ? f +
                  'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'.charAt(
                    d[m]
                  )
                : f + '\x3d'
        }
        return f
      }

      // 正文中解析出图片
      function extractTagsFromHtml(a) {
        for (
          var b = /<img[^>]+src="([^"]+)[^>]+/g, c = [], d;
          (d = b.exec(a));

        )
          c.push(d[1])
        return c
      }

      // Chrome下word图片黏贴
      editor.on('paste', function(evt) {
        if (CKEDITOR.env.chrome || CKEDITOR.env.gecko) {
          var data = evt.data.dataTransfer.getData('text/rtf')

          var imgs = extractTagsFromHtml(evt.data.dataValue)

          var b = extractFromRtf(data)
          var d = []
          for (var i = 0; i < b.length; i++) {
            var a = b[i]
            d.push(
              a.type
                ? 'data:' +
                    a.type +
                    ';base64,' +
                    convertBytesToBase64(convertHexStringToBytes(a.hex))
                : null
            )
          }

          for (var b = 0; b < imgs.length; b++) {
            0 === imgs[b].indexOf('file://') &&
              d[b] &&
              (evt.data.dataValue = evt.data.dataValue.replace(imgs[b], d[b]))
          }
        }
      })
      editor.on('instanceReady', function() {
        if (typeof dataURLtoFile != 'undefined' && window.atob) {
          this.on('change', function(e) {
            var doc = e.editor.document
            var imgs = doc.find('img')

            var count = imgs.count()

            for (var i = 0; i < count; i++) {
              var img = imgs.getItem(i).$
              var src = $(img).attr('src')

              if (src.indexOf('data:image') >= 0) {
                var item = dataURLtoFile(src, 'image')
                item.type = 'image'
                imageUpload([item],( function(img) {
                  return function(src){
                    $(img).attr('src', src)
                    $(img).attr('data-cke-saved-src', src)}
                }(img)))
              }

              // 移除Safari截图后自动生成的图片
              if (src.indexOf('blob:') >= 0) {
                $(img).remove()
              }
            }
          })
        }

        var doc = this.document
        // 禁止内部图片拖动
        doc.$.ondragstart = function() {
          return false
        }

        // 是否支持拖动上传
        function isSupportDrop(event) {
          return event.dataTransfer && !Com_Parameter.IE
        }

        doc.on('drop', function(evt) {
            var event = evt.data.$
            event.stopPropagation()
            event.preventDefault()
            hideMask()
            var items = []
            if (isSupportDrop(event)) {
              items = event.dataTransfer.files
              if (!items) {
                items = event.dataTransfer.files
              }
            } else return
            imageUpload(items, null, true)
          })
          doc.on('dragenter', function(evt) {
            if (isSupportDrop(event)) {
				// 标记显示遮罩
				showMark = true;
				showMask();
			}
          })
          doc.on('dragover', function(evt) {
            var event = evt.data.$
            event.stopPropagation()
            event.preventDefault()
          })
          doc.on('dragleave', function(evt) {
            var event = evt.data.$
            event.stopPropagation()
            event.preventDefault()
			setTimeout(function() {
				// 如果遮罩处于显示中，需要清除
				if (showMark) {
					hideMask()
					// 标记隐藏遮罩
					showMark = false
				}
			}, 500); // 适当延长时间，让遮罩闪跳的时间不会太历害
          })
        doc.on('paste', function(evt) {
          var items = []
          if (evt.data.$.clipboardData && evt.data.$.clipboardData.items) {
            items = evt.data.$.clipboardData.items
          } else return

          if (items.length > 0) {
            imageUpload([items[0]])
          }
        })
      })

      /***************************************************
       * 加载金格控件--开始
       **************************************************/

      if (!('ActiveXObject' in window)) return
      var webOffice
      // 判断当前页面是否已经存在金格控件
      if (top.JG_GetWebOffice) {
        webOffice = top.JG_GetWebOffice()
        config.webOffice = webOffice
      } else {
        webOffice = top.document.getElementById('JGWebOffice_ckeditor_upload')
        config.webOffice = webOffice
        if (!webOffice) {
          $(top.document.body).append(
            (webOffice = $(
              '<object style="display:none" classid="clsid:' + config.jgClassId + '" id="JGWebOffice_ckeditor_upload"></object>'
            ))
          )
          webOffice.attr('codebase', config.jgPath)
          config.webOffice = webOffice[0]
          Com_AddEventListener(top, 'unload', function() {
            try {
              if (config.webOffice) config.webOffice.WebClose()
            } catch (e) {
              if (window.console) console.log('金格控件未安装成功！')
            }
          })
        }
      }
      config.webOffice.WebUrl =
        Com_GetCurDnsHost() +
        Com_Parameter.ContextPath +
        'sys/attachment/sys_att_main/jg_service.jsp'
      /***************************************************
       * 加载金格控件--结束
       **************************************************/
    }
  })
})()
