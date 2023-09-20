<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/showFilters.css" />
</head>
<body>
<style>
#selectedlist {
   padding-top: 0px;
    margin-top: 0px;
}
.tips{
	display: inline-block;
	margin: 5px 0 10px 0;
	font-size: 12px;
	padding-right: 10px;
	padding-left: 20px;
	color: #FF9431;
}
</style>
<script>
	Com_IncludeFile("data.js");
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("selectPanel.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", "js", true);
	Com_IncludeFile("jquery.dragsort.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
	Com_IncludeFile("localValidate.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
</script>
<div class="lui_custom_list_container" style="min-width: 490px">
	<!-- 主要内容 Starts -->
	<%-- 导出数量选择 --%>
	<div style="margin: 10px 20px">		
	<b>${lfn:message('sys-modeling-main:modeling.select.exported.data') }:</b>
	</div>
	<div style="margin: 10px 20px">
		
	<input type="radio" id="allSelected "  name="radioid" value="1" />${lfn:message('sys-modeling-main:modeling.export.all.data') }
		(${lfn:message('sys-modeling-main:modeling.total') }<span id="totalnum"></span>${lfn:message('sys-modeling-main:modeling.strip') })
	</div>
	<div style="margin: 10px 20px" class="select-num-range">
    <input type="radio" name="radioid" value="2" />${lfn:message('sys-modeling-main:modeling.specified.quantity') }
		<%--<span><bean:message bundle="sys-search" key="search.export.specified.num"/></span>--%>
		<span><bean:message bundle="sys-search" key="search.export.num.start"/>
			<input name="fdNumStart" class="inputsgl" value="1" style="width:40px"/>&nbsp;&nbsp;<bean:message key="page.row"/>
			<bean:message bundle="sys-search" key="search.export.num.end"/>
			<input name="fdNumEnd" class="inputsgl" style="width:40px"/>&nbsp;&nbsp;<bean:message key="page.row"/>
			
		</span>
	</div>
	<div style="margin: 10px 20px">
	<input type="radio" id="CheckRadio "  name="radioid" value="3" />${lfn:message('sys-modeling-main:modeling.export.check.data') }
		(${lfn:message('sys-modeling-main:modeling.total') }<span id="selectedNum"></span>${lfn:message('sys-modeling-main:modeling.strip') })
	</div>
	<div class="tips">${lfn:message('sys-modeling-main:modeling.export.tips') }</div>
	<div style="margin: 10px 10px 0px 20px">
	<b>${lfn:message('sys-modeling-main:modeling.select.exported.field') }:</b>
	</div>
	<%-- 导出字段选择 --%>
	<div class="lui_custom_list_box">
		<div class="lui_custom_list_box_content">
			<div class="lui_custom_list_box_content_container">
				<div class="lui_custom_list_box_content_row">
					<div class="lui_custom_list_box_content_col" style="width: 40% ;padding-right: 12px; box-sizing: border-box;">
						<div class="item">
							<div class="lui_custom_list_box_content_col_header">
								<span class="lui_custom_list_box_content_header_word"><bean:message bundle='sys-modeling-base' key='modeling.common.toBeSelected'/></span>
								<!-- 全选 -->
								<label class="lui_custom_list_checkbox right">
									<a href="javascript:void(0)" onclick="selectAll()">
										${lfn:message('sys-modeling-base:modeling.common.selectAll') }
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
								<span class="lui_custom_list_box_content_header_word">${lfn:message('sys-modeling-base:modeling.common.selected') }</span>
								<span class="lui_custom_list_box_content_link_right">
								  <a href="javascript:void(0)" onclick="cancleAll()">
									  ${lfn:message('sys-modeling-base:modeling.common.cancleAll') }
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
		html += '<div class="lui_custom_list_checkbox" data-value="'+ item.field +'" onclick="selectOption(this);">';
		html += '<div style="display:inline-block;width:60%;" class="drag_item" title="'+(item.text || "")+'"><span>'+ (item.text || "") +'</span></div>';
		html += '</div>';
		return html;
	}

	function buildSelectedOption(item){
		if(!item)
			return "";
		var html = "";
		html += '<div data-value="'+ item.field +'" class="lui_custom_list_box_content_col_content_line" >';
		 html += '<div style="display:inline-block;width:60%;" class="drag_item" title="'+(item.text || "")+'"><span>'+ (item.text || "") +'</span></div>';


	        	html += '<div class="lui_custom_list_box_content_col_content_left">';
		        html += '<span class="lui_custom_list_box_content_col_content_widthchange">';		    
		      	html += '</span>';
		      	html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);"></span>';
		html += '</div>';
		html += '</div>';
		return html;
	}

	function makeUpAttr(item,cfgs){
		for(var i = 0;i < cfgs.length;i++){
			var col = cfgs[i];
			if(col["field"] == item['field']){
				if(col["width"]){
					item["width"] = col["width"];
				}
				if(col["expend"]){
					item["expend"] = col["expend"];
				}
				if(col["muti"]){
					item["muti"] = col["muti"];
				}
			}
		}
	}

	var selected;
	var allField;

	function loadDictProperties(url) {
		var kmssData = new KMSSData();console.log(url)
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
		// 从后台查询
		var viewId = '${param.viewId}';
		var viewType =  '${param.viewType}';
		var dialogType =  '${param.dialogType}';
		var dictBeanValue = "modelingAppListviewExportDictService&viewId=" + viewId+"&viewType=" + viewType+"&dialogType="+dialogType;

		//如果是看板视图，指定数量不能选择
		if(viewType == "2"){
			$(".select-num-range").find("input").attr("disabled","disabled");
		}
		loadDictProperties(dictBeanValue);
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
		if(selected){
			try{
				colCfgs = $.parseJSON(selected.replace(/&quot;/g,"\""));
				for(var i = 0;i < colCfgs.length;i++){
					var col = colCfgs[i];
					currentValues += col["field"] + ";";
				}
				if(currentValues.length > 0){
					currentValues = currentValues.substring(0,currentValues.length - 1);
				}
			}catch(e){
				console.error("历史数据造成的报错，可忽略：selected=" + selected, e);
			}
		}

		// 处理所有选项
		if(!allField){
			allField = "[]";
		}

		if(typeof(allField) === "string"){
			allField = $.parseJSON(allField.replace(/&quot;/g,"\""));
		}
		var allOptions = allField;
		var items = {};
		for(var i = 0;i < allOptions.length;i++){
			var option = allOptions[i];
			items[option["field"]] = option;
		}

		/***************** 初始化 列数据 end **********************/
		// 全局变量
		selectPanel = new SelectPanel({items: items,currentValues:currentValues});
		// 画已选
		var selectedValues = selectPanel.selectedValues;
		var selectedHtml = "";
		for(var i = 0;i < selectedValues.length;i++){
			let item = selectPanel.getItemById(selectedValues[i]);
			if(!item)
				continue;
			var selectedItem = $.extend({}, item);
			// 初始化时，需要补全属性
			if(colCfgs){
				makeUpAttr(selectedItem, colCfgs);
			}
			selectedHtml += buildSelectedOption(selectedItem);
		}
		$("#selectedlist").append(selectedHtml);
		// 画待选
		var unselectedValues = selectPanel.unselectedValues;
		var unselectedHtml = "";
		for(var i = 0;i < unselectedValues.length;i++){
			unselectedHtml += buildUnselectedOption(selectPanel.getItemById(unselectedValues[i]));
		}
		$("#unselectedlist").append(unselectedHtml);
		/* 筛选项和显示项拖动排序 */
		setTimeout(function(){ 
			$("#selectedlist").dragsort({
		 	        dragSelector: ".lui_custom_list_box_content_col_content_line",
		 	        dragSelectorExclude: "input",
		 	        dragEnd: dragEnd,
		 	        dragBetween: true,
		 	        placeHolderTemplate: "<div></div>"
		 	}); 
			
	 		function dragEnd() {
	 	         var $this = $(this);
	 	         var data = $("#selectedlist").each(function () {
	 	             return $(this).children().html();
	 	         }).get();
	 	    }
		}, 1000); 
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
					colCfg["fdColumns"] += item.field + ";";
				}
				colCfg["fdNumStart"] = $('input[name="fdNumStart"]').val();
				colCfg["fdNumEnd"] = $('input[name="fdNumEnd"]').val();
				colCfg["fdKeepRtfStyle"] = "true";
				colCfg["fdExportType"] = $(":radio[name='radioid']:checked").val();
				var selectedNum = parseInt('${param.selectedNum}');
				var fdNumStart = parseInt(colCfg["fdNumStart"]);
				var fdNumEnd = parseInt(colCfg["fdNumEnd"]);
				if ($(":radio[name='radioid']:checked").val()==3 && selectedNum<1){
					dialog.alert('${lfn:message('sys-modeling-main:modeling.currently.checked.data.empty') }');
				}else if ($(":radio[name='radioid']:checked").val()==2 && fdNumStart<1||fdNumEnd>totalNum) {
					dialog.alert('${lfn:message('sys-modeling-main:modeling.unable.export.select.correct.data') }');
				}else if ($(":radio[name='radioid']:checked").val()==2 && fdNumStart>fdNumEnd) {
					dialog.alert('${lfn:message('sys-modeling-main:modeling.start.cannot.greater.stop.number') }');
				}else  {
					$dialog.hide(colCfg);
				}
			});
	}


</script>
</body>
</html>
