<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%
  pageContext.setAttribute("isEnableWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
%>
<link
	href="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/style/list.css?s_cache=${ LUI_Cache }"
	rel="stylesheet">

<link
	href="${ LUI_ContextPath}/sys/attachment/view/img/upload.css?s_cache=${ LUI_Cache }"
	rel="stylesheet">

<script type="text/javascript"
	src="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/js/list.js?s_cache=${ LUI_Cache }"></script>
	
<link
	href="${ LUI_ContextPath}/sys/attachment/view/img/upload.css?s_cache=${ LUI_Cache }"
	rel="stylesheet">
<!-- 操作栏 -->
<div class="lui_list_operation">
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top">
		</list:paging>
	</div>
</div>

<ui:fixed elem=".lui_list_operation"></ui:fixed>

<list:listview id="listview">
	<ui:source type="AjaxJson">
                    {url:'/sys/attachment/sys_att_main/sysAttMain.do?method=listHistory&rowsize=8&orderby=sysAttMain.fdVersion&ordertype=down&fdOriginId=${JsParam.fdOriginId }'}
            </ui:source>

	<%-- 列表视图--%>
	<list:colTable layout="sys.ui.listview.columntable" name="columntable">
		<list:col-serial title="${ lfn:message('page.serial') }"
			headerStyle="width:3%"></list:col-serial>
		<list:col-auto
			props="fdVersion" headerStyle="width:10px"/>					
		<list:col-html
			title="${lfn:message('sys-attachment:sysAttRecovery.fdName') }"
			styleClass="luiAttSubject">
					{$
						<img
				src="${KMSS_Parameter_ResPath}style/common/fileIcon/{% GetIconNameByFileName(row['fdFileName']) %}" />
			<span class="com_subject" style="cursor: pointer;" onClick="lisOpenView('{% (row['fdFileName']) %}','{% (row['fdId']) %}')">{%row['fdFileName']%}</span> 
					$}
		</list:col-html>

		<list:col-auto
			props="fdSize;fdUploaderId;fdUploadTime" />

		<list:col-html
			title="${lfn:message('sys-attachment:sysAttMain.operations') }"
			styleClass="upload_list_operation upload_list_tr" style="width:20px">
			{$
				<div class='upload_opt_icon upload_opt_view' onClick="readDoc('{% (row['fdId']) %}','{%(row['fdFileName'])%}')"><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>查看</i></span></div>
			$}
			<c:if test="${JsParam.realCanPrint == 'true'}">
			{$
				<div class='upload_opt_icon upload_opt_print' onClick="printDoc('{% (row['fdId']) %}','{%(row['fdFileName'])%}')"><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>打印</i></span></div>
			$}			
			</c:if>
			<c:if test="${JsParam.realCanDownload == 'true'}">
			{$
				<div class='upload_opt_icon upload_opt_down' onClick="downDoc('{% (row['fdId']) %}')"><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>下载</i></span></div>
			$}
			</c:if>	
		</list:col-html>
	</list:colTable>

</list:listview>

<list:paging></list:paging>
<script>Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);</script>
<script>
	Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
			+ "style/common/fileIcon/", "js", true);
	seajs.use('theme!module');
	
	window.readDoc = function(docId, fdFileName) {
		
		var isEnableWpsWebOffice = '${isEnableWpsWebOffice}';
		if(isEnableWpsWebOffice == 'true') //wps加载项
		{
			var fileName = fdFileName;
			var fileExt = fileName.substring(fileName.lastIndexOf("."));
			if(fileExt.toLowerCase()==".docx"||fileExt.toLowerCase()==".doc"||fileExt.toLowerCase()==".wps"){
				var wpsParam = {};
				wpsParam['signTrue']=this.signTrue;
				wpsParam['bookMarks']=this.bookMarks;
				wpsParam['wpsExtAppModel']=this.wpsExtAppModel;
				openWpsOAAssit(docId,wpsParam);
				return;
			}
			if(fileExt==".xlsx"||fileExt==".xls"){
				openExcelOAAssit(docId);
				return;
			}
			if(fileExt.toLowerCase()==".et"||fileExt==".XLSX"||fileExt==".XLS"){
				openEtOAAssit(docId);
				return;
			}
			/*if(fileExt==".pptx"||fileExt==".ppt"||fileExt==".dps"){
				openPptOAAssit(docId);
				return;
			}*/
		}
		var isReadDownLoad = "${JsParam.isReadDownLoad}";
		var fdForceUseJG = "${JsParam.fdForceUseJG}";
		if('true' == isReadDownLoad){
			var url = getUrl("readDownload", docId);
			url = Com_SetUrlParameter(url,"open","1");
			Com_OpenWindow(url, "_blank");
		}else{
			var url = getUrl("view", docId);
 			if(fdForceUseJG){
				url += "&fdForceUseJG=true"
			}
			Com_OpenWindow(url, "_blank");
		}	
	};
	
	window.printDoc = function(docId, fdFileName) {
		
		var isEnableWpsWebOffice = '${isEnableWpsWebOffice}';
		if(isEnableWpsWebOffice == 'true') //wps加载项
		{
			var fileName = fdFileName;
			var fileExt = fileName.substring(fileName.lastIndexOf("."));
			if(fileExt.toLowerCase()==".docx"||fileExt.toLowerCase()==".doc"||fileExt.toLowerCase()==".wps"){
				var wpsParam = {};
				wpsParam['signTrue']=this.signTrue;
				wpsParam['bookMarks']=this.bookMarks;
				wpsParam['wpsExtAppModel']=this.wpsExtAppModel;
				openWpsOAAssit(docId,wpsParam);
				return;
			}
			if(fileExt==".xlsx"||fileExt==".xls"){
				openExcelOAAssit(docId);
				return;
			}
			if(fileExt==".XLSX"||fileExt==".XLS" || fileExt.toLowerCase()==".et"){
				openEtOAAssit(docId);
				return;
			}
			if(fileExt.toLowerCase()==".pptx"||fileExt.toLowerCase()==".ppt"||fileExt.toLowerCase()==".dps"){
				openPptOAAssit(docId);
				return;
			}
		}
		else
		{
			var url = getUrl("print", docId);
			Com_OpenWindow(url, "_blank");
		}
		
	};
	
	window.downDoc = function(docId) {
		var url = getUrl("download", docId);
		url += "&downloadType=manual&downloadFlag="+(new Date()).getTime();  //记录下载日志标识
		window.open(url, "_blank");
	};
	
	window.getUrl = function(method, docId) {
		var url = Com_Parameter.ContextPath;
		if(url.substring(0,4) !== 'http') {
			url = getHost() + url;
		}
		url += "sys/attachment/sys_att_main/sysAttMain.do?method=" + method
		+ "&fdId=" + docId;
		return url;
	};
	
	window.getHost = function(){
		var host = location.protocol.toLowerCase()+"//" + location.hostname;
		if(location.port!='' && location.port!='80'){
			host = host+ ":" + location.port;
		}
		return host;
	}
</script>
