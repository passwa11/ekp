<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<script type="text/javascript">
	seajs.use(['theme!form']);
	Com_IncludeFile("validation.js|plugin.js");
</script>
<head>
	<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/dingTop.css?s_cache=${LUI_Cache}" />
	</c:if>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/designer/relevance/css/relevance_dialog.css">
</head>
<body style="overflow-x: hidden;padding-bottom: 48px;">
<script>
	//$dialog.dialogInfo 窗口的全局变量，有当前选择模板的fdKey、fdTemplateId、value
	var relevance_validtion = $KMSSValidation();
	
	var fdControlId = "${JsParam.fdControlId}";
	
	var inputParams = "${JsParam.inputParams}";
	
	var isMul = "${JsParam.isMul}";
	//当前路径 更新路径
	function updatePath(modelPath){
		$("#modelPath").text(modelPath);
	}

	//数据展示区 刷新list列表
	function updateList(fdTemplateId,modelPath,fdKey,mainModelName,isBase){
		// 含有子model的，不需要查询
		if(isBase && isBase == "true"){
			return;
		}
		parentParams.dialogInfo.fdKey = fdKey;
		parentParams.dialogInfo.fdTemplateId = fdTemplateId;
		relevanceLoadingShow();
		var url = Com_Parameter.ContextPath + 'sys/xform/controls/relevance.do?method=updateList&fdKey=' + fdKey + '&fdTemplateId='+fdTemplateId+'&modelPath='+encodeURI(modelPath)
				+'&orderby=fdId&ordertype=down&mainModelName=' + mainModelName + '&fdControlId=' + fdControlId +'&extendXmlPath=' 
				+ top.Xform_ObjectInfo.formFilePath + '&inputParams=' + encodeURIComponent(inputParams) + "&isMul=" + isMul;
		var iframe = document.getElementById('dataShowList');
		iframe.setAttribute('src',url);
		Com_AddEventListener(iframe, 'load', relevanceLoadinghide);
	}

	//自适应gaod
	function setHeight(obj){
		var win = obj;
		if (document.getElementById)
	    {
	        if (win && !window.opera)
	        {
	        	setTimeout(function(){
	        		var div = win.contentDocument.getElementById("listtable_box");
					if (div){
						win.height = div.offsetHeight+40;
					}
	        	},100);
	        }
	    } 
	}

	//数据展示区 加载列表图标
	function relevanceLoadingShow(){
		var loadingDom = document.getElementById('relevance_dialog_loading');
		if(loadingDom){
			loadingDom.style.display = '';
		}
	}

	//数据展示区 隐藏图标
	function relevanceLoadinghide(){
		var loadingDom = document.getElementById('relevance_dialog_loading');
		if(loadingDom){
			loadingDom.style.display = 'none';
		}
	}

	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event,self){
		if(self.value != ''){
			$('.relevance_dislog_moduleSelect_delWord').css('display','inline');
		}else{
			$('.relevance_dislog_moduleSelect_delWord').hide();
		}	
		if (event && event.keyCode == '13') {
			relevance_dialog_moduleSelect();
		}
	}

	//清除搜索框里面的内容
	function relevance_dialog_delWord(){
		$('.relevance_dislog_moduleSelect_input').val('');	
		$('.relevance_dislog_moduleSelect_delWord').hide();
	}
	
	//模块搜索 模块搜索
	function relevance_dialog_moduleSelect(){
		
		//对字符串编码
		var selectField = encodeURIComponent ($('.relevance_dislog_moduleSelect_input').val());
		//不支持全模块搜索
		if(parentParams.dialogInfo.fdKey == null || parentParams.dialogInfo.fdKey == ''){
			alert('<bean:message bundle="sys-xform" key="sysFormMain.relevance.pleaseChosseCategoryFirst" />');//请先选择分类！
			return false;
		}
		var selectType = $(".relevance_search_select").val();
		var url = Com_Parameter.ContextPath + 'sys/xform/controls/relevance.do?method=selectModuleWithField&fdKey='+parentParams.dialogInfo.fdKey+'&fdTemplateId='
				+ parentParams.dialogInfo.fdTemplateId+'&selectType='+selectType+'&fdControlId=' + fdControlId +'&extendXmlPath=' 
				+ top.Xform_ObjectInfo.formFilePath+'&rowsize=9&orderby=fdId&ordertype=down&inputParams=' + encodeURIComponent(inputParams) + "&isMul=" + isMul;
		if(selectType=="createTime"){
			var startTime = $("input[name='startTime']");
			var endTime = $("input[name='endTime']");
			//调用校验框架校验是否为时间类型
			var bool = relevance_validtion.getValidator(startTime.attr("validate")).test(startTime.val()) && relevance_validtion.getValidator(endTime.attr("validate")).test(endTime.val());
			if(bool){
				url += '&startTime='+startTime.val()+'&endTime='+endTime.val();
			}else{
				return;
			}
		}else{
			url += '&selectField='+selectField;
		}
		relevanceLoadingShow();
		var iframe = document.getElementById('dataShowList');
		iframe.setAttribute('src',url);
		Com_AddEventListener(iframe, 'load', relevanceLoadinghide);
	}

	//操作区 确定
	function updateMainDoc(){
		//data:{{fdDocId:XXXX,fdModelName:XXX,fdSubject:XXX,isCreator:true|false},{fdDocId:XXXX,fdModelName:XXX,fdSubject:XXX,isCreator:true|false}}
		//$dialog是当前窗口的对象
		parentParams.relevanceObj.updateDataList(true);
		$dialog.hide(null);
	}

	//操作区 取消
	function cancelDialogOpera(){
		$dialog.hide(null);
	}

	//数据展示区 初始化
	function relevance_dialog_init(){
		//已选列表展示iframe
		updateList('','','','${param.mainModelName}');
	}

	var interval = setInterval(____Interval, "50");
	function ____Interval() {
		if (!window['$dialog'])
			return;
		window.parentParams = $dialog.___params;
		relevance_dialog_init();
		clearInterval(interval);
	}
	
	// 延迟100，等$dialog初始化完
	/* Com_AddEventListener(window,"load",function(){
		setTimeout(relevance_dialog_init,100);
	});  */
	
	function setSearchField(select){
		if($(select).val()=="createTime"){
			$(".relevance_startTime_Field").show();
			$(".relevance_dialog_moduleSelect").hide();
		}else{
			$(".relevance_startTime_Field").hide();
			$(".relevance_dialog_moduleSelect").show();
			var option = $(select).find("option:selected");
			var type = option.text();
			var placeholderMsg = "<bean:message bundle="sys-xform" key="sysFormMain.relevance.enter" />";
			$(".relevance_dislog_moduleSelect_input").attr("placeholder",placeholderMsg+type);
		}
		$(".relevance_dislog_moduleSelect_input").val("");
		$("input[name='startTime']").val("");
		$("input[name='endTime']").val("");
	}
</script>
<div class="relevance_doc_div_dialog" style="height: 32px;background-color:#f3f9fd;">
	<!-- 分类导航 -->
	<div>
		<div id="__categoryNavigation__" class="relevance_doc_div" style="color: #008cee;font-size:13px; line-height:32px;height:32px;cursor:pointer; padding:0 5px;width:260px;">
			<bean:message bundle="sys-xform" key="sysFormMain.relevance.categotyNavigation"></bean:message>
			<img src='${KMSS_Parameter_ContextPath }sys/xform/designer/relevance/icon/select.png' style='display:inline-block;width:16px;height:16px;position:absolute;top:7px;'/>
		</div>
		<ui:popup align="down-left" positionObject="#__categoryNavigation__" style="background:white;">
			<div style="width:260px;">
				<ui:menu layout="sys.ui.menu.ver.default">
					<ui:menu-source autoFetch="true" target="_self" href="javascript:updateList('!{fdTemplateId}','!{modelPath}','!{fdKey}',null,'!{isBase}');">
						<ui:source type="AjaxJsonp">
							{"url":"/sys/xform/controls/relevance.do?method=portlet&parentId=!{value}&fdKey=!{fdKey}&pAdmin=!{pAdmin}&noNext=!{noNext}&modelPath=!{modelPath}&fdControlId=" + fdControlId 
									+ "&extendXmlPath=" + top.Xform_ObjectInfo.formFilePath}
						</ui:source>
					</ui:menu-source>
				</ui:menu>
			</div>
		</ui:popup>
	</div>
	<!-- 模块搜索 -->
	<select class="relevance_search_select" onchange="setSearchField(this);">
		<option value="subject"><bean:message bundle="sys-xform" key="sysFormMain.relevance.subject" /></option>
		<option value="number"><bean:message bundle="sys-xform" key="sysFormMain.relevance.number" /></option>
		<option value="createTime"><bean:message bundle="sys-xform" key="sysFormMain.relevance.createTime" /></option>
		<option value="creator"><bean:message bundle="sys-xform" key="sysFormMain.relevance.creator" /></option>
	</select>
	<div class="relevance_dialog_moduleSelect">
		<input type='text' class='relevance_dislog_moduleSelect_input' onkeyup='enterTrigleSelect(event,this);' placeholder='${lfn:message("sys-xform:sysFormMain.relevance.inputSubject")}'></input>
		<div class='relevance_dislog_moduleSelect_delWord' style='display:none;' onclick='relevance_dialog_delWord();'></div>
		<input type='button' class='relevance_dislog_moduleSelect_select' title='${lfn:message("sys-xform:sysFormMain.relevance.search")}' onclick='relevance_dialog_moduleSelect();'></input>
	</div>
	<div class="relevance_startTime_Field" style="display:none;">
	<!-- 因校验框架使用需要父节点中有TD，故这里使用table标签 -->
	<table>
		<tr>
			<td>
				<xform:datetime property="startTime" showStatus="edit" dateTimeType="date" subject="${lfn:message('sys-xform:sysFormMain.relevance.startTime')}" placeholder="yyyy-MM-dd" style="display:inline-block;width:120px;height:26px;"></xform:datetime>
			</td>
			<td>
				<span style="top:-10px;position:relative;color:#4285f4;">__</span>
			</td>
			<td>
				<xform:datetime property="endTime" showStatus="edit" dateTimeType="date" subject="${lfn:message('sys-xform:sysFormMain.relevance.endTime')}" placeholder="yyyy-MM-dd" style="display:inline-block;width:120px;height:26px;"></xform:datetime>
			</td>
			<td>
				<ui:button text="${lfn:message('sys-xform:sysFormMain.relevance.search')}" title="${lfn:message('sys-xform:sysFormMain.relevance.search')}" style="width:60px;position:relative;top:-2px;" onclick="relevance_dialog_moduleSelect();"></ui:button>
			</td>
		</tr>
	</table>
	</div>
	
</div>
<div class='relevance_dialog_wrap'>
	<!-- 当前路径 -->
	<div>
	 <span class='txtlistpath' style='padding:10px 0px 10px 0px'><bean:message key="page.curPath"/><span id="modelPath"></span></span>
	</div>
	<!-- 数据展示区 -->
	<div id="dataShowDiv" style="min-height:200px;">
		<iframe id="dataShowList" width="100%" scrolling="no" frameborder="no" onload="setHeight(this);" style="border:0px;min-height:200px;"></iframe>
		<div id="relevance_dialog_loading" class="relevance_dialog_loading" title='${lfn:message("sys-xform:sysFormMain.relevance.dataLoading")}' style="diplay:none;"></div>
	</div>
	<!-- 已选列表区
	<div style="height:120px;border-top:solid 1px #797874;" id="selectedArea">
		<iframe src="${KMSS_Parameter_ContextPath}sys/xform/designer/relevance/relevance_main_dialog_selectBox.jsp" id="relevance_dialog_selectBoxIframe" scrolling="auto" width="100%" height="100%" frameborder="no" style="border:0px;"></iframe>
	</div>
	 -->
	<!-- 操作区 -->
	<div class="relevance_main_buttons_container">
		<ui:button text='${lfn:message("button.ok")}' style="width:72px;padding-right:8px;"
				onclick="updateMainDoc();">
		</ui:button>
		<!--<ui:button text='${lfn:message("sys-xform:sysFormMain.relevance.delete")}' style="width:72px;padding-right:8px;"
				onclick="relevance_dialog_selected_deleteAll();">
		</ui:button>-->
		<ui:button text='${lfn:message("button.cancel")}' style="width:72px"
				onclick="cancelDialogOpera();">
		</ui:button>
	</div>
</div>
</body>