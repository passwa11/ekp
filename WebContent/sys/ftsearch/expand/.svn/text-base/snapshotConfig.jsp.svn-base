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
	<input type="button" class="btnopt" value="<bean:message key="button.save"/>" onclick="_submitForm();" />
</div>
<p class="txttitle">
	<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.snapshotConfig"/>
</p>
<form id="formId" action="${KMSS_Parameter_ContextPath}sys/ftsearch/expand/snapshotConfig.do">
	<input name="method" style="display:none;" value="save" />
	<table class="tb_normal" width=100%>
		<tbody>
			<tr id="title">
				<td class="td_normal_title" width="15%">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.snapshotMaxLength"/>
				</td>
				<td class="td_normal_title" width="85%">
					<input class="inputsgl" name="SnapshotMaxLength" style="width:150px" value="${SnapshotMaxLength}" />
					<span class="txtstrong">*</span>
					<span class="message">
						<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.snapshotMaxLengthInfo"/>
					</span>
				</td>
			</tr>
		</tbody>
	</table>
</form>
</body>
<script type="text/javascript">
function _submitForm() {
	if(_check()) {
		$("#formId").submit();
	}
}
function _check() {
	var num = $("input[name=SnapshotMaxLength]").val();
	if($.isNumeric(num)) {
		num = parseInt(num);
		if(num < 20 ) {
			$($("input[name=SnapshotMaxLength]").siblings("span")[0]).html("请输入大于等于20的整数").css("color","red");
			return false;
		} else {
			$($("input[name=SnapshotMaxLength]").siblings("span")[0]).html("√").css("color", "green");
		}
	} else {
		$($("input[name=SnapshotMaxLength]").siblings("span")[0]).html("请输入大于等于20的整数").css("color","red");
		return false;
	}
	return true;
}
</script>
</html>
