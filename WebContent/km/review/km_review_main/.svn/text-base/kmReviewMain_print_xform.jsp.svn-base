<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
seajs.use(['theme!form']);
</script>
<script>
Com_IncludeFile("jquery.js");
Com_IncludeFile("previewdesign.js",Com_Parameter.ContextPath+"sys/print/import/","js",true);
</script>
<style>
body, input, textarea, select, div, a, table, tr, td, th{font-family:"Microsoft YaHei"}
.upload_list_tr .upload_list_operation {display: none;}
.lui_upload_img_box .upload_opt_td { display: none;}
.upload_list_tr .upload_list_download_count {display: none;}
.upload_list_tr .upload_list_size {display: none;}
.upload_list_tr .upload_opt_td {display: none;}
.upload_list_title {display: none;}
.upload_list_tr .upload_list_ck {display: none;}
</style>
<body class="lui_print_body">
<%-- 表单 --%>
	<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmReviewMainForm" />
		<c:param name="fdKey" value="reviewMainDoc" />
		<c:param name="fdModelId" value="${HtmlParam.fdId}" />
		<c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
		<c:param name="useTab" value="false" />
		<c:param name="isPrint" value="true" />
	</c:import>
<script>
function resetTableSize(){
	var tables = $(".sysDefineXform table[fd_type='standardTable']");
	for(var i = 0 ;i < tables.length;i++){
		var table = tables[i];
		//表格宽度调整
		$(table).css('width','100%');
		$(table).css('table-layout','fixed');
		var tbWidth = $(table).width();
		//计算原始宽度
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var cellsCount = cells.length;
			for(var k = 0;k < cellsCount;k++){
				var w = $(cells[k]).width();
				$(cells[k]).attr('cfg-width',w * 980/(tbWidth * 2));
			}
		}
		//重置宽度
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var cellsCount = cells.length;
			for(var k = 0;k < cellsCount;k++){
				$(cells[k]).css('width',$(cells[k]).attr('cfg-width'));
			}
		}
	}
}
Com_AddEventListener(window, "load", function() {
	// 调整高度
	$('.lui_print_body')[0].style.overflow = "hidden";
	resetTableSize();
	sysPreviewDesign.resetBoxWidth($('.sysDefineXform')[0]);
	window.frameElement.height = $('.lui_print_body').height();
});
</script>
</body>
</html>
