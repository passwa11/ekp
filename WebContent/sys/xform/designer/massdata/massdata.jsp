<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	Com_IncludeFile("md5-min.js",Com_Parameter.ContextPath + "sys/xform/designer/relation_event/style/js/","js",true);
	Com_IncludeFile("base64.js");
</script>
<xform:xtext property="${param.name}" showStatus="noShow"></xform:xtext>
<div data-lui-type="sys/xform/designer/massdata/massDataView!MassDataView" style="display:none;">
 	<script type="text/config">
 		{
			right : "${param.right}",
			controlId : "${param.controlId}",
			name : "${param.name}",
			inputParams : "${param.inputParams}",
			outputParams : "${param.outputParams}",
			excelcolumns : "${param.excelcolumns}",
			subject : "${param.subject}",
			columns : {},
			_source : "${param._source}",
			_key : "${param._key}",
			mainModelName : Xform_ObjectInfo.mainModelName,
			mainFormId : Xform_ObjectInfo.mainFormId
		}
 	</script>
 	<ui:source type="AjaxJson">
 		{ url : '/sys/xform/massdata/sysFormMassData.do?method=getMassData&mainFormId=!{mainFormId}&mainModelName=!{mainModelName}&controlId=!{controlId}'}
	</ui:source>
	<div data-lui-type="lui/view/render!Template" style="display:none;">
		<script type="text/config">
 		{
			src : '/sys/xform/designer/massdata/massDataTable.html#'
		}
 		</script>
	</div>
</div>