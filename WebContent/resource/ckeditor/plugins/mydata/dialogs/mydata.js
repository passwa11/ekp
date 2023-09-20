(function () {
	var __HTML;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/maindata/dialog/mydata/myDataSelect.jsp",
		async: false,
		dataType: "html",
		success: function(html){
			__HTML = html;
		}
	});
	
	function findDialogByName(name) {
		if (!name) {
			return $();
		}
		name = name.replace("extendDataFormInfo.","");
		return $("[class*='" + name + "_dialog']");
	}
	
    function mydataDialog(editor) {
    	var mainDataModelName; // 主数据ModelName
    	var displayName; // 在RTF里显示的属性名称，需要在选择模板后，取主数据定义里的相关数据
    	var mainDataDefinitionId; // 主数据定义ID
    	var cansubmit = true;
    	var config = editor.config;
    	config.initClass = "lui_attachment_upload_btn_init";
    	
    	var mydataSelector = {
			type : 'html',
			id : 'mydataSelector',
			html : __HTML,
			onLoad : function(event) {
				dialog = event.sender;
			},
			validate: function() {
				return cansubmit;
            }
		};
    	
    	//　弹出分类选择框
    	window._select = function() {
    		// 隐藏主窗口
    		findDialogByName(editor.name).hide();
        	
    		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				dialog.dialogForNewFile({
					id: 'MyDataTypeDialog',
					url: '/sys/xform/maindata/dialog/mydata/category.jsp',
					modelName: 'com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory',
					winTitle: '选择主数据分类',
					sourceType: 1,
					okBtnMsg: '下一步',
					callback: _select_callback
				});
				try{ 
					$(LUI('MyDataTypeDialog').parent.element).css({'z-index':10011});
				}catch(e){
					
				}
				
    		});
		};
		
		// 选择好模板后，这里取到模板数据，进一步去查主数据
		window._select_callback = function(data) {
			if(data && typeof data === 'object') {
				// 显示主窗口
				findDialogByName(editor.name).show();
            	
				_set_path(data.id);
				if(window.dyniFrameSize){
					dyniFrameSize();
				}
//				if(typeof(eval('dyniFrameSize'))=="function") 
//				 {
//				   funcName();
//				 }

				
			} else {
				// 点击取消
				findDialogByName(editor.name).remove();
				$(".cke_dialog_background_cover").remove();
			}
		}
		
		// 设置选中的模板路径
		window._set_path = function(id) {
			$.ajax({
				url: Com_Parameter.ContextPath + "sys/xform/maindata/sysFormMyData.do?method=getTemplateData",
				data: {'id': id},
				dataType: 'json',
				success: function(data) {
					displayName = data.displayName;
					mainDataModelName = data.mainDataModelName;
					mainDataDefinitionId = data.templateId;
					$("#modelPath").text(data.path);
					_load_data();
				}
			});
		}
		
		window.enterTrigleSelect = function(event) {
			if (event && event.keyCode == '13') {
				searchData();
			}
			event.stopPropagation();
		}
		
		window.searchData = function() {
			if(typeof(mainDataDefinitionId) == 'undefined') {
				alert("请先选择分类模板！");
				return;
			}
			
			var keyword = $("input[name='keyword']").val();
			_load_data(keyword);
		}
		
		// 获取数据
		window._load_data = function(keyword) {
			var listview = LUI('Mydata_listview');
			var url = "/sys/xform/maindata/sysFormMyData.do?method=getMydatas&pageno=1&rowsize=10&id=" + mainDataDefinitionId;
			if(keyword) {
				url += "&keyword=" + encodeURI(keyword);
			}
			listview.source.setUrl(url);
			listview.source.get();
		}
		
		// 根据屏幕分辨率计算宽度和高度，适用于分类选择框
		window.sizeForNewFile = function() {
			var width = screen.width * 0.5;
			if(width < 800)
				width = 800;
			var height = screen.height * 0.48;
			if(height < 550)
				height = 550;
			return {width: width, height: height};
		};
 
		window._ok = function() {
            var selectDatas = [];
            var datas = LUI("Mydata_listview").table.kvData;
            // 获取所有已经选中的记录
            $("input[name='List_Selected']:checked").each(function() {
            	for(var i=0; i<datas.length; i++) {
            		if(datas[i].fdId == $(this).val()) {
            			selectDatas.push(datas[i]);
            			continue;
            		}
            	}
			});
            if(selectDatas.length < 1) {
            	alert("请选择主数据！");
            	return;
            }
            // 构建数据回写到RTF里
            for(var i=0; i<selectDatas.length; i++) {
            	if(i > 0) {
            		editor.insertElement(new CKEDITOR.dom.element.createFromHtml('<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'));
            	}
            	
            	var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=show&fdId=" + selectDatas[i].fdId + "&modelName=" + mainDataModelName + "&defId=" + mainDataDefinitionId;
            	var name = selectDatas[i][displayName];
            	var element = new CKEDITOR.dom.element.createFromHtml('<a href="'+url+'" target="_blank"><span class="com_btn_link">'+name+'</span></a>');
            	editor.insertElement(element);
            }
			
            _cancel();
		};
		
		window._cancel = function() {
			var listview = LUI('Mydata_listview');
			listview.source.setUrl("");
			listview.source.get();
			
			findDialogByName(editor.name).remove();
			$(".cke_dialog_background_cover").remove();
		};
		
		window.__rowClick = function(fdId) {
			// 点击行时，选择/取消
			$("#Mydata_listview").find(":checkbox[value='" + fdId + "']").click();
		}
		
        return {
            title: '主数据选择',
            minWidth: sizeForNewFile().width,
            minHeight: sizeForNewFile().height,
            contents : [{
				id : 'tab1',
				label : '主数据',
				title : '',
				expand : true,
				padding : 0,
				elements : [mydataSelector]
			}],
            
            // 弹出框加载完后执行
            onLoad: function () {
                // 因为弹出的页面中包含系统组件，所以需要重新渲染此部分的内容
                seajs.use([ 'lui/parser', 'lui/jquery' ], function(parser, $) {
        			$(document).ready(function() {
        				parser.parse($("#MydataTypesDiv"));
        			});
        		});
            },
            onShow: function () {
            	// 窗口打开时清空原有数据
            	$(".lui_listview_body").remove();
            	$(".lui_paging_content_centre").empty();
            	$("#modelPath").empty();
            	$("input[name='keyword']").val('');
            	
            	// 隐藏主窗口
            	findDialogByName(editor.name).hide();
            	// 移除按钮行
            	$(".cke_dialog_footer").hide();
            	
            	// 弹出分类选择框
            	_select();
            },
            onHide: function () {
            },
            resizable: CKEDITOR.DIALOG_RESIZE_HEIGHT
        };
    }
   
	CKEDITOR.dialog.add('mydata', function (editor) {
		return mydataDialog(editor);
	});
})();
