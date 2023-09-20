<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>

<html>
<head>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/util.js"></script>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/jquery.js"></script>
</head>
<body>
<div id="optBarDiv">
	<input type="button" class="btnopt" value="<bean:message key="button.save"/>" onclick="_submit();" />
</div>
<p class="txttitle">
	<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.defaultModeConfigInfo"/>
</p>
<form id="formId" action="${KMSS_Parameter_ContextPath}sys/ftsearch/expand/snapshotConfig.do">
	<input name="method" style="display:none;" value="saveSearchModeconfig" />
	<input id="defaultFtSearchModeConfigVal" name="defaultFtSearchModeConfigVal" style="display:none;" />
	<table class="tb_normal" width=100%>
		<tbody>
			<tr id="title">
				<td class="td_normal_title" width="15%">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.defaultModeConfigInfo"/>
				</td>
				<td class="td_normal_title" width="85%">
				<%
			    String defaultFtsearchModeConfig = request.getParameter("defaultFtsearchModeConfig");
				%>
					<input id="one" type="radio"" name="defaultFtSearchModeConfig" value="or" />
					<span><bean:message bundle="sys-ftsearch-db" key="search.ftsearch.bond.or"/></span>
					<span class="txtstrong">*</span>
					<input id="all" type="radio"" name="defaultFtSearchModeConfig" value="and" />
					<span><bean:message bundle="sys-ftsearch-db" key="search.ftsearch.bond.and"/></span>
					<span class="txtstrong">*</span>
					<input id="only" type="radio"" name="defaultFtSearchModeConfig" value="like" />
					<span><bean:message bundle="sys-ftsearch-db" key="search.ftsearch.bond.like"/></span>
					<span class="txtstrong">*</span>
				</td>
			</tr>
		</tbody>
	</table>
</form>
</body>
<script type="text/javascript">
init();
function init(){
	var val = '${defaultFtsearchModeConfig}';
	if (val == 'or'){
	    $("#one").attr("checked","checked");
	}
	if (val == 'and'){
	    $("#all").attr("checked","checked");
	}
	if (val == 'like'){
	    $("#only").attr("checked","checked");
	}
}


function _submit() {
	//var url = "${KMSS_Parameter_ContextPath}sys/ftsearch/expand/snapshotConfig.do?method=saveSearchModeconfig";
	if(_check()) {
		var defaultFtSearchModeConfigVal = $("input[name='defaultFtSearchModeConfig']:checked").val();
		$("#defaultFtSearchModeConfigVal").val(defaultFtSearchModeConfigVal);
		$("#formId").submit();
		/*;
		$.ajax( {
            url : url,
            method : "post",
            dataType : "text",
            data : {"defaultFtsearchModeConfig":defaultFtSearchModeConfigVal},
            success : function(data) {
            	alert("<bean:message bundle='sys-ftsearch-expand' key='sysFtsearch.save.success'/>");
		}
		});*/
	}
}
function _check() {
	return true;
}
</script>
</html>
