<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no" >
<template:replace name="body">
<script>
	seajs.use("kms/knowledge/kms_knowledge_portlet_ui/style/portlet.css");
</script>

<div>
	<div class="cur_credit">
		<div class="container">
			<div class="kms_knowledge_portlet_le_info_task learn_task fatBox">
			<kmss:ifModuleExist path="/kms/knowledge/">
				<table class="kms_knowledge_portlet_le_info" >
					<tr id="data1" class='dataBodya' style="height:24px;"></tr>
				</table>
				<table class="kms_knowledge_portlet_le_info" >
					<tr class='dataBodya' style="height:24px;"></tr>
				</table>
				<table class="kms_knowledge_portlet_le_info" >
					<tr class='dataBodya' style="height:24px;"></tr>
				</table>
				</kmss:ifModuleExist>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	 domain.autoResize();
</script>
<script>
seajs.use(['lui/jquery','lui/dialog', 'lang!kms-knowledge'],function($,dialog,lang){
	var data = JSON.parse('${data}');
	// #92925 注意：IE8第一次加载console会报错
	// console.log(data);
	var docView = '${data.docView}';
	var dayView = '${data.dayView}';
	var weekView = '${data.weekView}';
	var monthView = '${data.monthView}';
	var dayReaderView = '${data.dayReaderView}';
	var weekReaderView = '${data.weekReaderView}';
	var monthReaderView = '${data.monthReaderView}';
	var dayPublicView = '${data.dayPublicView}';
	var weekPublicView = '${data.weekPublicView}';
	var monthPublicView = '${data.monthPublicView}';
	var table = $(".kms_knowledge_portlet_le_info");
	var sum = 0;
	if(docView){
		sum++;
		$("#data1").html("<td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.totality']+": </td><td class='numTd'>"+data.docCount+"</td>");
	}
	if(dayView){
		sum++;
		table.eq(0).append("<tr class='dataBodyb'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.fdDay']+": </td><td class='numTd'>"+data.dayCount+"</td></tr>");
	}
	if(weekView){
		sum++;
		table.eq(0).append("<tr class='dataBodyc'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.fdWeek']+": </td><td class='numTd'>"+data.weekCount+"</td></tr>");
	}
	if(monthView){
		sum++;
		table.eq(0).append("<tr class='dataBodyd'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.fdMonth']+": </td><td class='numTd'>"+data.monthCount+"</td></tr>");
	}
	if(dayReaderView){
		sum++;
		if(sum <= 3){
			table.eq(0).append("<tr class='dataBodyb'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.reader.fdDay']+": </td><td class='numTd'>"+data.dayReaderCount+"</td></tr>");
		} else {
			table.eq(1).append("<tr class='dataBodyb'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.reader.fdDay']+": </td><td class='numTd'>"+data.dayReaderCount+"</td></tr>");
		}
	}
	if(weekReaderView){
		sum++;
		if(sum <= 3){
			table.eq(0).append("<tr class='dataBodyc'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.reader.fdWeek']+": </td><td class='numTd'>"+data.weekReaderCount+"</td></tr>");
		} else {
			table.eq(1).append("<tr class='dataBodyc'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.reader.fdWeek']+": </td><td class='numTd'>"+data.weekReaderCount+"</td></tr>");
		}
	}
	if(monthReaderView){
		sum++;
		if(sum <= 3){
			table.eq(0).append("<tr class='dataBodyd'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.reader.fdMonth']+": </td><td class='numTd'>"+data.monthReaderCount+"</td></tr>");
		} else {
			table.eq(1).append("<tr class='dataBodyd'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.reader.fdMonth']+": </td><td class='numTd'>"+data.monthReaderCount+"</td></tr>");
		}
	}
	if(dayPublicView){
		sum++;
		if(sum <= 3){
			table.eq(0).append("<tr class='dataBodyb'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdDay']+": </td><td class='numTd'>"+data.dayPublicCount+"</td></tr>");
		} else if(sum >3 && sum <= 6) {
			table.eq(1).append("<tr class='dataBodyb'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdDay']+": </td><td class='numTd'>"+data.dayPublicCount+"</td></tr>");
		} else {
			table.eq(2).append("<tr class='dataBodyb'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdDay']+": </td><td class='numTd'>"+data.dayPublicCount+"</td></tr>");
		}
	}
	if(weekPublicView){
		sum++;
		if(sum <= 3){
			table.eq(0).append("<tr class='dataBodyc'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdWeek']+": </td><td class='numTd'>"+data.weekPublicCount+"</td></tr>");
		} else if(sum >3 && sum <= 6) {
			table.eq(1).append("<tr class='dataBodyc'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdWeek']+": </td><td class='numTd'>"+data.weekPublicCount+"</td></tr>");
		} else {
			table.eq(2).append("<tr class='dataBodyc'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdWeek']+": </td><td class='numTd'>"+data.weekPublicCount+"</td></tr>");
		}
	}
	if(monthPublicView){
		sum++;
		if(sum <= 3){
			table.eq(0).append("<tr class='dataBodyd'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdMonth']+": </td><td class='numTd'>"+data.monthPublicCount+"</td></tr>");
		} else if(sum >3 && sum <= 6) {
			table.eq(1).append("<tr class='dataBodyd'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdMonth']+": </td><td class='numTd'>"+data.monthPublicCount+"</td></tr>");
		} else {
			table.eq(2).append("<tr class='dataBodyd'><td><span class='lui_score_num_flag'></span></td><td>"+lang['kmsKnowledge.portlet.knowledge.public.fdMonth']+": </td><td class='numTd'>"+data.monthPublicCount+"</td></tr>");
		}
	}
})
</script>

</template:replace>
</template:include>