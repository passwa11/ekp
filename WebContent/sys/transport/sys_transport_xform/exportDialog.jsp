<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/transport/sys_transport_xform/resource/css/common.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/transport/sys_transport_xform/resource/css/dialog.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/transport/sys_transport_xform/resource/css/override.css?s_cache=${LUI_Cache}" />
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/transport/sys_transport_xform/resource/css/showFilters.css" />
</head>
<body>
<style>
#selectedlist {
   padding-top: 0px;
    margin-top: 0px;
}
.selectSort{
	width: 100%;
	height: 40px;
}
</style>
<script>
	Com_IncludeFile("data.js");
	Com_IncludeFile("dialog.js");
	Com_IncludeFile('jquery.ui.js', 'js/jquery-ui/');
	Com_IncludeFile("selectPanel.js", "${LUI_ContextPath}/sys/transport/sys_transport_xform/resource/js/", "js", true);
	Com_IncludeFile("jquery.dragsort.js", Com_Parameter.ContextPath+'sys/transport/sys_transport_xform/resource/js/', 'js', true);
	Com_IncludeFile("localValidate.js", Com_Parameter.ContextPath+'sys/transport/sys_transport_xform/resource/js/', 'js', true);
</script>
<div class="lui_custom_list_container" id="exportDiv" name="exportDiv" style="min-width: 490px">
	<!-- 主要内容 Starts -->
	<%-- 导出数量选择 --%>
	<div style="margin: 10px 20px">		
	<b>请选择导出数据的范围:</b>
	</div>
	<div style="margin: 10px 20px">
		
	<input type="radio" id="allSelected "  name="radioid" value="1" />导出所有数据
		(共<span id="totalnum"></span>条)
	</div>
	<div style="margin: 10px 20px">
    <input type="radio" name="radioid" value="2" />指定数量
		<%--<span><bean:message bundle="sys-search" key="search.export.specified.num"/></span>--%>
		<span><bean:message bundle="sys-search" key="search.export.num.start"/>
			<input name="fdNumStart" class="inputsgl" value="1" style="width:40px"/>&nbsp;&nbsp;<bean:message key="page.row"/>
			<bean:message bundle="sys-search" key="search.export.num.end"/>
			<input name="fdNumEnd" class="inputsgl" style="width:40px"/>&nbsp;&nbsp;<bean:message key="page.row"/>
			
		</span>
	</div>
	<div style="margin: 10px 20px">
	<input type="radio" id="CheckRadio "  name="radioid" value="3" />导出勾选数据
		(共<span id="selectedNum"></span>条)
	</div>
	<div style="margin: 10px 20px">
		<span style="color:red;font-size: 13px">温馨提示：导出的是已提交数据，您在当前页面修改的数据，不做导出处理。</span>
	</div>
	<div style="margin: 20px 10px 0px 20px">
	<b>请选择导出的字段:</b>
	</div>
	<%-- 导出字段选择 --%>
	<div class="lui_custom_list_box">
		<div class="lui_custom_list_box_content">
			<div class="lui_custom_list_box_content_container">
				<div class="lui_custom_list_box_content_row">
					<div class="lui_custom_list_box_content_col" style="width: 40% ;padding-right: 12px; box-sizing: border-box;">
						<div class="item">
							<div class="lui_custom_list_box_content_col_header">
								<span class="lui_custom_list_box_content_header_word"><bean:message bundle='sys-transport' key='sysTransport.label.toBeSelected'/></span>
								<!-- 全选 -->
								<label class="lui_custom_list_checkbox right">
									<a href="javascript:void(0)" onclick="selectAll()">
										${lfn:message('sys-transport:sysTransport.label.selectAll') }
									</a>
								</label>
							</div>
							<div class="lui_custom_list_box_content_col_content" id="unselectedlist">
							</div>
						</div>
					</div>
					<div class="lui_custom_list_box_content_col" style="width: 60%">
						<div class="item">
							<div class="lui_custom_list_box_content_col_header">
								<span class="lui_custom_list_box_content_header_word">${lfn:message('sys-transport:sysTransport.label.selected') }</span>
								<span class="lui_custom_list_box_content_link_right">
								  <a href="javascript:void(0)" onclick="cancleAll()">
									  ${lfn:message('sys-transport:sysTransport.label.cancleAll') }
								  </a>
								</span>
							</div>
							<div class="lui_custom_list_box_content_col_content" id="selectedlist">
							</div>
						</div>
					</div>
				</div>
				<div class="lui_custom_list_box_content_col_btn">
					<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="ok()">${lfn:message('button.ok') }</a>
					<a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="cancle()">${lfn:message('button.cancel') }</a>
				</div>
			</div>
		</div>
	</div>
	<!-- 主要内容 Ends -->
</div>
<script type="text/javascript">
	//display:显示项 condition:搜索项
	var type = '${param.type}';
	var totalNum = parseInt('${param.totalNum}');
	var selectedNum = parseInt('${param.selectedNum}');
	function initModelInfo(info){
		var modelIds = info.modelIds.split(";");
		var modelNames = info.modelNames.split(";");
		var rs = {};
		for(var i = 0;i < modelIds.length;i++){
			rs[modelIds[i]] = {"field" : modelIds[i],"text" : modelNames[i]};
		}
		return rs;
	}

	function cancle(){
		$dialog.hide();
	}



	// 选择全部
	function selectAll(){
		$("#unselectedlist").find("[data-value]").trigger($.Event("click"));
	}

	// 取消全部
	function cancleAll(){
		$("#selectedlist").find(".lui_custom_list_box_btn_quit").trigger($.Event("click"));
	}

	// 上移
	function toUp(dom){
		var $div = $(dom).closest(".lui_custom_list_box_content_col_content_line");
		$div.prev().before($div);
	}

	// 下移
	function toDown(dom){
		var $div = $(dom).closest(".lui_custom_list_box_content_col_content_line");
		$div.next().after($div);
	}

	// 删除选项
	function unselectOption(dom){
		var $div = $(dom).closest(".lui_custom_list_box_content_col_content_line");
		var value = $div.attr("data-value");
		$("#unselectedlist").append(buildUnselectedOption(selectPanel.getItemById(value)));
		$div.remove();
		selectPanel.changeItem(value,"selected");
	}

	// 选择选项
	function selectOption(dom){
		var value = $(dom).attr("data-value");
		$("#selectedlist").append(buildSelectedOption(selectPanel.getItemById(value)));
		$(dom).remove();
		selectPanel.changeItem(value,"unselected");
	}

	function buildUnselectedOption(item){
		if(!item)
			return "";
		var html = "";
		html += '<div class="lui_custom_list_checkbox" data-value="'+ item.name +'" onclick="selectOption(this);">';
		html += '<div style="display:inline-block;width:60%;" class="drag_item" title="'+(item.label || "")+'"><span>'+ (item.label || "") +'</span></div>';
		html += '</div>';
		return html;
	}

	function buildSelectedOption(item){
		if(!item)
			return "";
		var html = "";
		html += '<div data-value="'+ item.name +'" class="lui_custom_list_box_content_col_content_line" >';
		 html += '<div style="display:inline-block;width:60%;" class="drag_item" title="'+(item.label || "")+'"><span>'+ (item.label || "") +'</span></div>';


	        	html += '<div class="lui_custom_list_box_content_col_content_left">';
		        html += '<span class="lui_custom_list_box_content_col_content_widthchange">';		    
		      	html += '</span>';
		      	html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);"></span>';
		html += '</div>';
		html += '</div>';
		return html;
	}

	var selected;
	var allField;

	function loadDictProperties(url) {
		var kmssData = new KMSSData();
		var datas = kmssData.AddBeanData(url).GetHashMapArray();

		if(datas[0] && datas[0].key0){
			allField = datas[0].key0;
		}
		if(datas[1] && datas[1].key0){
			selected = datas[1].key0;
		}
	}

	function init(){
		/***************** 初始化 列数据 start **********************/
		$('input[name="fdNumEnd"]').val(totalNum > 5000 ? 5000 : totalNum);
		$('#totalnum')[0].innerHTML = totalNum;
		$('#selectedNum')[0].innerHTML = selectedNum;
		var allProperties = [];
		if(typeof Xform_ObjectInfo =="undefined"){
			if(parent && parent.Xform_ObjectInfo){
				allProperties = $.extend(true,[], parent.Xform_ObjectInfo.properties);
			}
		}else{
			allProperties = $.extend(true, [], Xform_ObjectInfo.properties);
		}
		
		var detailId = '${param.detailId}';
		var propertyLabel = [];
		var dataJson = [];
		if(parent && parent.DocList_GetDetailsTableXformflag){
			dataJson = parent.DocList_GetDetailsTableXformflag("TABLE_DL_" + detailId, null, null, false);
		}
		//不需要导出的控件类型
		var notExportControlType = ['xform_relation_attachment','divcontrol','xform_validator','hidden','relevance'];
		if(allProperties && allProperties.length>0){
			$.each(allProperties,function(i,property){
				if(property.name.indexOf(detailId)>-1){
					if($.inArray(property.businessType, notExportControlType) > -1 || (typeof property.isEncrypt !='undefined' && property.isEncrypt)){
						return;
					}else{
						propertyLabel.push(property);

					}
				}

			});
			var properties = [];
			// 以控件id结尾才显示字段
			$.each(dataJson,function(j,data){
				$.each(propertyLabel,function(p,label){
					var reg = new RegExp(data.fieldId+"$","g");
					if(reg.test(label.name)){
						properties.push(label);
					}
				});
			});
			propertyLabel = properties;
		}
		allField = propertyLabel;
		//默认选择是全选还是勾选
		var hasSelected ='${param.hasSelected}';
		if("true"===hasSelected){
			$(":radio[name='radioid'][value='3']").attr("checked","checked");
		}else{
			$(":radio[name='radioid'][value='1']").attr("checked","checked");
			
		}
		//处理现有值 列表默认展示列
		var currentValues = "";
		var colCfgs = null;
		selected = allField;
		if(selected){
			try{
				colCfgs = selected;
				for(var i = 0;i < colCfgs.length;i++){
					var col = colCfgs[i];
					if(col.label){
						var label = col.label;
						if(label.indexOf(".")>-1){
							label = label.substr(label.indexOf(".") + 1);
							currentValues += label + ";";
						}
					}
				}
				if(currentValues.length > 0){
					currentValues = currentValues.substring(0,currentValues.length-1);
				}
			}catch(e){
				console.error("历史数据造成的报错，可忽略：selected=" + selected, e);
			}
		}

		// 处理所有选项
		if(!allField){
			allField = "{}";
		}

		var allOptions = allField;
		var items = {};
		// 明细表内控件字段从0开始
		for(var i = 0;i < allOptions.length;i++){
			var option = allOptions[i];
			if(option.label){
				var label = option.label;
				if(label.indexOf(".")>-1){
					label = label.substr(label.indexOf(".") + 1);
					option.label = label;
				}
			}
			items[option.name] = option;
		}

		/***************** 初始化 列数据 end **********************/
		// 全局变量
		selectPanel = new SelectPanel({items: items,currentValues:currentValues});
		// 画已选
		var selectedValues = selectPanel.unselectedValues;
		var selectedHtml = "";
		for(var i = 0;i < selectedValues.length;i++){
			let item = selectPanel.getItemById(selectedValues[i]);
			if(!item)
				continue;
			var selectedItem = $.extend({}, item);
			selectedHtml += buildSelectedOption(selectedItem);
		}
		$("#selectedlist").append(selectedHtml);
		// 画待选
		var unselectedValues = selectPanel.selectedValues;
		var unselectedHtml = "";
		for(var i = 0;i < unselectedValues.length;i++){
			unselectedHtml += buildUnselectedOption(selectPanel.getItemById(unselectedValues[i]));
		}
		$("#unselectedlist").append(unselectedHtml);
		/* 筛选项和显示项拖动排序 */
		var sortableParam = {
			items: ".lui_custom_list_box_content_col_content_line",
			placeholder: 'selectSort'
		}
		var num = setInterval(function(){//兼容Safari最新版没办法动态加载COM_INCLUDEFILE导致的sortable找不到问题 122229
			if(typeof $("#selectedlist").sortable !== 'undefined' && $("#selectedlist").sortable){
				clearInterval(num);
				$("#selectedlist").sortable(sortableParam);
			}
		}, 200);

		function dragEnd() {
			var $this = $(this);
			var data = $("#selectedlist").each(function () {
				return $(this).children().html();
			}).get();
		}
	}

	var interval = setInterval(beginInit, "50");
	function beginInit(){
		if(!window['$dialog'])
			return;
		clearInterval(interval);
		init();
	}


		function ok(){
			seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
				var colCfg = {};
				colCfg["fdColumns"] = "";
				var selectedItems = $("#selectedlist").find("[data-value]");
				for(var i = 0;i < selectedItems.length;i++){
					var selectedItemDom = selectedItems[i];
					var item = selectPanel.getItemById($(selectedItemDom).attr("data-value"));
					colCfg["fdColumns"] += item.name + ";";
				}
				colCfg["fdNumStart"] = parseInt($('input[name="fdNumStart"]').val());
				colCfg["fdNumEnd"] = parseInt($('input[name="fdNumEnd"]').val());
				colCfg["fdExportType"] = $(":radio[name='radioid']:checked").val();
				var selectedNum = parseInt('${param.selectedNum}');

				if ($(":radio[name='radioid']:checked").val()==3 && selectedNum<1){
					dialog.alert('无法导出！当前勾选数据为空');
				}else if ($(":radio[name='radioid']:checked").val()==2 && colCfg["fdNumStart"]<1||colCfg["fdNumEnd"]>totalNum) {
					dialog.alert('无法导出！请选择正确的数据');
				}else if ($(":radio[name='radioid']:checked").val()==2 && colCfg["fdNumStart"]>colCfg["fdNumEnd"]) {
					dialog.alert('导出数据的"开始数"不能大于"终止数"！');
				}else if(selectedItems.length<=0){
					dialog.alert('请选择需要导出的字段');
				}else{
					$dialog.hide(colCfg);
				}
			});
	}


</script>
</body>
</html>
