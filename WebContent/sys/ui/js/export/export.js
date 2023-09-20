// JavaScript Document
define(function(require, exports, module) {
	var dialog = require("lui/dialog");
	var $ = require("lui/jquery");
	var env = require("lui/util/env");
	var lang = require('lang!');
	var langAtt = require('lang!sys-attachment');
	require('lui/export/excel')
	
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
	
	var exportPdf = function(container,opts) {
		if(IEVersion() !== -1) {
			dialog.alert(lang['export.error.notsupport']);
			return false;
		}
		//#64518 有滚动条则截图页面不理想，暂时处理为滚动到顶部
		window.scrollTo(0,0);
		var options = {
			top:20,
			left:50,
			title:$.trim($("title").html()) || "content",
			callback:function() {}
		};
		if(opts) {
			$.extend(options,opts);
		}
		window.progress = dialog.progress();
		container = container || document.body;
		options.title = options.title.replace(/\n/g,'').replace(/\r/g,'');
        var originContainer = container;
        var isUnWrap = false;
		if(container !== document.body) {
            $(".tempTB").css("max-width", "1100px");
			var $container = $("<div id='temp_export_pdf_container' align='center'>").css({
				width:$(container).width()+2*options.left,
				margin:'0 auto',
				paddingTop:options.top+'px',
				paddingBottom:options.top+'px'
			});
			//#168945 新版本打印页面导出PDF，表格被缩小了很多  重新设置宽度，改为px
			$(container).css("width",$(container).width());
			$(container).wrap($container);
			container = $("#temp_export_pdf_container")[0];
            isUnWrap = true;
            $(".tempTB").css("max-width", "none");
		}
		// #168008 导出PDF前删除特殊的style属性，该属性会导致某些元素被遮挡
		$("span").css("background-color", "");
		require.async(['lui/export/html2canvas.js','lui/export/jsPdf.debug.js'], function() {
			html2canvas(container, {
		    }).then(function(canvas) {
				if(options.hasOwnProperty("pdfSplitePage")){
					// 得到canvas画布的单位是px 像素单位
					var contentWidth = canvas.width
					var contentHeight = canvas.height
					// 将canvas转为base64图片
					var pageData = canvas.toDataURL('image/jpeg', 1.0)

					// 设置pdf的尺寸，pdf要使用pt单位 已知 1pt/1px = 0.75   pt = (px/scale)* 0.75
					// 2为上面的scale 缩放了2倍
					var pdfX = (contentWidth + 10) / 2 * 0.75
					var pdfY = (contentHeight + 500) / 2 * 0.75 // 500为底部留白

					// 设置内容图片的尺寸，img是pt单位
					var imgX = pdfX;
					var imgY = (contentHeight / 2 * 0.75); //内容图片这里不需要留白的距离

					// 初始化jspdf 第一个参数方向：默认''时为纵向，第二个参数设置pdf内容图片使用的长度单位为pt，第三个参数为PDF的大小，单位是pt
					var PDF = jspdf.jsPDF('', 'pt', [pdfX, pdfY]);


					// 将内容图片添加到pdf中，因为内容宽高和pdf宽高一样，就只需要一页，位置就是 0,0
					PDF.addImage(pageData, 'jpeg', 0, 0, imgX, imgY)
					window.progress.hide();
					PDF.save(options.title + '.pdf')
				}else{
					//未生成pdf的html页面高度
					var leftHeight = canvas.height;

					var a4Width = 595.28, a4Height = 841.89;
					//一页pdf显示html页面生成的canvas高度;
					var a4HeightRef = Math.floor(canvas.width / a4Width * a4Height);

					//pdf页面偏移
					var position = 0;
					var pageData = canvas.toDataURL('image/jpeg', 1.0);
					var pdf = jspdf.jsPDF('x', 'pt', 'a4');
					var index = 1, canvas1 = document.createElement('canvas'), height;
					pdf.setDisplayMode('fullwidth', 'continuous', 'FullScreen');
					var pdfName = options.title;
					function createImpl(canvas) {
						if (leftHeight > 0) {
							index++;
							var checkCount = 0;
							if (leftHeight > a4HeightRef) {
								var i = position + a4HeightRef;
								for (i = position + a4HeightRef; i >= position; i--) {
									var isWrite = true
									for (var j = 0; j < canvas.width; j++) {
										var c = canvas.getContext('2d').getImageData(j, i, 1, 1).data
										if (c[0] != 0xff || c[1] != 0xff || c[2] != 0xff) {
											isWrite = false
											break
										}
									}
									if (isWrite) {
										checkCount++
										if (checkCount >= 10) {
											break
										}
									} else {
										checkCount = 0
									}
								}
								height = Math.round(i - position) || Math.min(leftHeight, a4HeightRef);
								if (height <= 0) {
									height = a4HeightRef;
								}
							} else {
								height = leftHeight;
							}

							canvas1.width = canvas.width;
							canvas1.height = height;

							var ctx = canvas1.getContext('2d');
							ctx.drawImage(canvas, 0, position, canvas.width, height, 0, 0, canvas.width, height);

							var pageHeight = Math.round(a4Width / canvas.width * height);
							if (position != 0) {
								pdf.addPage();
							}
							pdf.addImage(canvas1.toDataURL('image/jpeg', 1.0), 'JPEG', 0, 0, a4Width, a4Width / canvas1.width * height);
							leftHeight -= height;
							position += height;
							window.progress.setProgress(index, index + Math.ceil(leftHeight / a4HeightRef));
							if (leftHeight > 0) {
								setTimeout(createImpl, 500, canvas);
							} else {
								pdf.save(pdfName + '.pdf');
								window.progress.hide();
								if (isUnWrap && $(originContainer).closest("[id='temp_export_pdf_container']").length > 0) {
									$(originContainer).unwrap();
								}
							}
						}
					}
					//当内容未超过pdf一页显示的范围，无需分页
					if (leftHeight < a4HeightRef) {
						pdf.addImage(pageData, 'JPEG', 0, 0, a4Width, a4Width / canvas.width * leftHeight);
						pdf.save(pdfName + '.pdf')
						window.progress.hide();
						if (isUnWrap && $(originContainer).closest("[id='temp_export_pdf_container']").length > 0) {
							$(originContainer).unwrap();
						}
					} else {
						try {
							pdf.deletePage(0);
							$('.pdfTip').show();
							$('.pdfTotal').text(index + Math.ceil(leftHeight / a4HeightRef));
							setTimeout(createImpl, 500, canvas);
						} catch (err) {
							console.log(err);
						}
					}
					options.callback(pdf);
				}
			});
		});
	}
	
	var exportMht = function(opts) {
		var options = {
			url:window.location.href,
			title:'',
			sourceCode:''
		};
		if(opts) {
			$.extend(options,opts);
		}
		if(!document.export_mht_form) {
			var action = '/sys/common/export.do?method=outputMht';
			var _form = document.createElement('form');
			_form.name = 'export_mht_form';
			_form.method = 'post';
			if (Com_Parameter.dingXForm === "true") {
				_form.target='_self';
			} else {
				_form.target='_blank';
			}
			_form.action = env.fn.formatUrl(action);
			var _requestUrl = document.createElement('input');
			_requestUrl.type = 'hidden';
			_requestUrl.name = 'requestUrl';
			_form.appendChild(_requestUrl);
			var _title = document.createElement('input');
			_title.type = 'hidden';
			_title.name = 'title';
			_form.appendChild(_title);
			document.body.appendChild(_form);
		}
		document.export_mht_form.requestUrl.value = options.url;
		document.export_mht_form.title.value = options.title;
		document.export_mht_form.submit();
		return false;
	};

  /**
   * excel导出
   * 只支持标准列表
   * @param {*} id 列表id
   */
  var exportExcel = function(id) {
	  
	if(IEVersion() == 8 || IEVersion() == 9){
	  dialog.alert('不支持ie9及以下浏览器')
	}
	
    var obj = $('input[name="List_Selected"]:checked')
    // 没有勾选
    if (obj == null || obj.length == 0) {
      dialog.alert(langAtt['attachment.export.tip'])
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
          data[col.col] = env.fn.clearHtml(col.value)
        }
      })

      if (!$.isEmptyObject(data)) {
        sheetData.push(data)
      }
    })

    // 导出生成excel
    var option = { fileName: '导出文件' }
    option.datas = [
      {
        sheetData: sheetData,
        sheetHeader: sheetHeader
      }
    ]
    var toExcel = new window['js-export-excel'](option)
    toExcel.saveExcel()
  }

  module.exports.exportPdf = exportPdf;
  module.exports.exportMht = exportMht;
  module.exports.exportExcel = exportExcel
})
