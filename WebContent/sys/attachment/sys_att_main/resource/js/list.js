seajs.use(
  ['lui/util/env', 'lui/jquery', 'lui/dialog', 'lui/export/export','lang!sys-attachment'],
  function(env, $, dialog, exportss,lang) {
    // 下载
    window.downloadClick = function(id) {
      var borrowUrl = env.fn.formatUrl(
        '/sys/attachment/sys_att_main/sysAttMain.do?method=statistics&fdId=' +
          id
      )
      Com_OpenWindow(borrowUrl)
    }

    // 借阅
    window.borrowClick = function(id) {
      var downloadUrl = env.fn.formatUrl(
        '/sys/attachment/sys_att_main/sysAttMain.do?method=statistics&forward=statistics_borrow&fdId=' +
          id
      )
      Com_OpenWindow(downloadUrl)
    }

    // 知识转换
    window.addShare = function() {
      var checked = $('input[name="List_Selected"]:checked')
      var fdAttIds = ''
      checked.each(function(index, item) {
        var value = $(item).val()
        if (index == 0) {
          fdAttIds += 'fdAttIds=' + value
        } else {
          fdAttIds += '&fdAttIds=' + value
        }
      })
      var tip = lang['attachment.transfo.tip'];
      if (!fdAttIds) {
        dialog.alert(tip)
        return
      }

      var modelName =
        'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory'
      var url =
        '/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=!{id}&' +
        fdAttIds
      dialog.simpleCategoryForNewFile(
        modelName,
        url,
        false,
        null,
        null,
        null,
        '_blank',
        true,
        {
          fdTemplateType: '1,3'
        }
      )
    }

    // 新建借阅
    window.addBorrow = function(id, method) {
      if (!id) {
        var checked = $('input[name="List_Selected"]:checked')
        var tip = lang['attachment.borrow.tip'];
        if (checked.length > 1) {
          dialog.alert(tip)
          return
        }

        if (checked.length == 1) {
          id = checked.val()
        }
      }

      var url = env.fn.formatUrl(
        '/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=add'
      )

      if (id) {
        url += '&fdAttId=' + id
      }

      if (method) {
        switch (method) {
          case 'download':
            url += '&fdDownloadEnable=' + true
            break
          case 'view':
            url += '&fdReadEnable=' + true
            break
          case 'readDownload':
            url += '&fdReadEnable=' + true
            break
          case 'print':
            url += '&fdPrintEnable=' + true
            break
          default:
            break
        }
      }

      Com_OpenWindow(url)
    }

    // 知识转换，有下载权限的附件才可以

    var File_EXT_PRINT = [
        '.doc',
        '.xls',
        '.ppt',
        '.xlc',
        '.docx',
        '.xlsx',
        '.mppx',
        '.pptx',
        '.xlcx',
        '.wps',
        '.et',
        '.vsd',
        '.rtf;'
      ],
      FIEL_EXT_READDOWNLOAD = ['.gif', '.jpg', '.jpeg', '.bmp', '.png', '.tif'],
      File_EXT_READ = [
        '.doc',
        '.xls',
        '.ppt',
        '.xlc',
        '.docx',
        '.xlsx',
        '.mppx',
        '.pptx',
        '.xlcx',
        '.wps',
        '.et',
        '.vsd',
        '.rtf',
        '.flv',
        '.mp4',
        '.f4v',
        '.mp4',
        '.webm',
        '.ogg',
        '.mp4',
        '.avi',
        '.mpg',
        '.wmv',
        '.3gp',
        '.mov',
        '.wmv9',
        '.rm',
        '.rmvb',
        '.wrf',
        '.m4v',
        '.mp3',
        '.pdf'
      ]

    function getUrl(method, fileId) {
      return (
        '/sys/attachment/sys_att_main/sysAttMain.do?method=' +
        method +
        '&fdId=' +
        fileId
      )
    }

    function getFileExt(fileName) {
      return fileName.substring(fileName.lastIndexOf('.'))
    }

    window.operationClick = function(fileId, method) {
      var url = getUrl(method, fileId)
      if (method == 'download') {
        url += '&downloadType=manual&downloadFlag=' + new Date().getTime()
      }
      var tip = lang['attachment.borrow.tip1'];
      if (authCheck(url) == 'true') {
        Com_OpenWindow(env.fn.formatUrl(url))
      } else {
          if (method == 'view' || method == 'readDownload') {
              alert(lang['attachment.deny.tip']);
          }else if(method == 'download'){
          	  alert(lang['attachment.deny.tip']);
          }else if(method == 'print'){
          	  alert(lang['attachment.deny.tip']);
          }else{
  	        dialog.confirm(tip, function(value) {
  	          if (value) {
  	            addBorrow(fileId, method)
  	          }
  	        })
          }
      }
    }

    function authCheck(url) {
      var hasAuth = 'false'
      $.ajax({
        url: env.fn.formatUrl(
          '/sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth'
        ),
        dataType: 'json',
        type: 'post',
        async: false,
        data: {
          data: JSON.stringify([['1', url]])
        },
        success: function(datas) {
          if (datas.length > 0) {
            hasAuth = datas[0]['1']
          }
        }
      })
      return hasAuth
    }

    window.GetOperations = function(fileName, fileId) {
      var fileExt = getFileExt(fileName)
      var canRead = $.inArray(fileExt.toLowerCase(), File_EXT_READ) > -1
      var canPrint = $.inArray(fileExt.toLowerCase(), File_EXT_PRINT) > -1
      var operations = ''
      var canReadDown =
        $.inArray(fileExt.toLowerCase(), FIEL_EXT_READDOWNLOAD) > -1

      // 阅读
      if (canRead) {
        operations +=
          '<div class="lui-upload-list-opt-view" onClick="operationClick(\'' +
          fileId +
          "','view')\"></div>"
      }

      if (canReadDown) {
        operations +=
          '<div class="lui-upload-list-opt-view" onClick="operationClick(\'' +
          fileId +
          "','readDownload')\"></div>"
      }

      // 下载
      operations +=
        '<div class="lui-upload-list-opt-down" onClick="operationClick(\'' +
        fileId +
        "','download')\"></div>"

      // 打印
      if (canPrint) {
        operations +=
          '<div class="lui-upload-list-opt-print" onClick="operationClick(\'' +
          fileId +
          "','print')\"></div>"
      }
      return operations
    }

    window.lisOpenView = function(fileName, fileId) {
      var fileExt = getFileExt(fileName)
      var canRead = $.inArray(fileExt.toLowerCase(), File_EXT_READ) > -1
      var canPrint = $.inArray(fileExt.toLowerCase(), File_EXT_PRINT) > -1
      var operations = ''
      var canReadDown =
        $.inArray(fileExt.toLowerCase(), FIEL_EXT_READDOWNLOAD) > -1

      // 阅读
      if (canRead) {
    	  operationClick(fileId,'view');
      }

      if (canReadDown) {
    	  operationClick(fileId,'readDownload');
      }
    }    
    
    function IEVersion() {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
        var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
        var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
        if(isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            if(fIEVersion == 7) {
                return 7;
            } else if(fIEVersion == 8) {
                return 8;
            } else if(fIEVersion == 9) {
                return 9;
            } else if(fIEVersion == 10) {
                return 10;
            } else {
                return 6;//IE版本<=7
            }   
        } else if(isEdge) {
            return 'edge';//edge
        } else if(isIE11) {
            return 11; //IE11  
        }else{
            return -1;//不是ie浏览器
        }
    }
    
    /**
     * excel导出
     * 只支持标准列表
     * @param {*} id 列表id
     */
    window.exportExcel = function(id) {
    var ieTip = lang['attachment.export.ieTip'];  
  	if(IEVersion() == 8 || IEVersion() == 9){
  	  dialog.alert(ieTip)
  	}
  	
      var obj = $('input[name="List_Selected"]:checked')
      // 没有勾选
      var tip = lang['attachment.export.tip'];
      if (obj == null || obj.length == 0) {
        dialog.alert(tip)
        return
      }

      // 需要导出的记录
      var values = []
      for (var i = 0; i < obj.length; i++) {
        if (obj[i].checked) {
          values.push(obj[i].value)
        }
      }

      // 格式化列表中的数据
      var datas = LUI(id)._data
      // 黑名单
      var blacklist = ['fdId']

      // 表头
      var sheetHeader = []
      // 表格内容
      var sheetData = []

      // 封装表头
      $.each(datas.columns, function(index, item) {
        // 没有标题或者黑名单的表头不做导出
        if (item.title && $.inArray(item.property, blacklist) < 0) {
          sheetHeader.push(env.fn.clearHtml(item.title))
        }
      })

      // 封装表格内容
      $.each(datas.datas, function(index, item) {
        var data = {}

        $.each(item, function(i, col) {
          // 并不是选中的数据，不做导出
          if (col.col == 'fdId' && $.inArray(col.value, values) < 0) {
            return false
          }

          // 黑名单的字段不做导出
          if ($.inArray(col.col, blacklist) < 0) {
        	  var rex = /<[^>]+>/g;
        	  if(rex.test(col.value)){
        		  data[col.col] = $(col.value).html();
        	  } else {
        		  data[col.col] = col.value;
        	  }
          }
        })

        if (!$.isEmptyObject(data)) {
        	var fdCreatorId = data.fdCreatorId;
        	sheetData.push(data);
        }
      })

      // 导出生成excel
      var exportTip = lang['attachment.export.fileTip'];
      var option = { fileName: exportTip }
      option.datas = [
        {
          sheetData: sheetData,
          sheetHeader: sheetHeader
        }
      ]
      var toExcel = new window['js-export-excel'](option)
      toExcel.saveExcel()
    }
  }
)
