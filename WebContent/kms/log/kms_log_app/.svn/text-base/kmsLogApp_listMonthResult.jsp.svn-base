<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/kms/log/kms_log_app/kmsLogApp.do">
<style>
.tb_normal tr{
	line-height: 30px;
}
.tb_normal tr td{
	vertical-align: middle;
}
.td_normal_title{
	text-align: right;
	font-weight: bold;
	font-size: 14px;
}
.inputselectsgl{
	display:inline-block;
}
.inputselectsgl input {	
	color: #0066FF;
	border-color: #999999;
	border-style: solid;
	border-width: 0px 0px 1px 0px;
	font-size:12px
}
.td_search{
	display:inline-block;
	float:right;
	width:60px;
	text-align: right;
}
.validation-advice{
	display:inline-block;
	vertical-align: middle; 
	
}
.validation-advice td{
	color: red;
}
.input{
	width:100px;
}
</style>
<script>
Com_IncludeFile("dialog.js|plugin.js");
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/style/listview.css" />
<html:form method="POST" action="kms/log/kms_log_app/kmsLogApp.do?method=list">
<div class="filterDiv">
<table class="tb_normal" width="90%">
	<tr>
		<td class="td_normal_title" style="width:30px">
			<bean:message key="kmsLogApp.personal.timeArea" bundle="kms-log"/>：
		</td><td width="88%">
			 <c:out  value="${startDate}"/> ~ <c:out  value="${endDate }"/>
		</td>
	</tr>
	 
</table>	
</div>
</html:form>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable" style="word-break : break-all;">
		<tr>
			<td width="5%">
				<bean:message key="page.serial"/>
			</td>
			<td width="12%">
				<bean:message bundle="kms-log" key="kmsLogApp.fdCreateTime"/>
			</td>
			<td width="8%">
				<bean:message bundle="kms-log" key="kmsLogApp.fdOperator"/>
			</td>
			<td width="5%">
				<bean:message bundle="kms-log" key="kmsLogApp.fdOprateMethod"/>
			</td>
			<td width="10%">
				<bean:message bundle="kms-log" key="kmsLogApp.modelName"/>
			</td>
			<td width="20%">
				<bean:message bundle="kms-log" key="kmsLogApp.fdSubject"/>
			</td>
		</tr>
		<c:forEach items="${queryPage.list}" var="kmsLogApp" varStatus="vstatus">
			<tr>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<kmss:showDate value="${kmsLogApp.fdCreateTime}" />
				</td>
				<td>
					<c:out value="${kmsLogApp.fdOperatorName}" />
				</td>
				<td>
					<c:out value="${kmsLogApp.operateText}" />
				</td>
				<td>
					<c:out value="${kmsLogApp.moduleName}" />
				</td>
				<td>
					<c:out value="${kmsLogApp.fdSubject}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<script>
 
function gotoMonth(fdMonthInfo,type){
	if(fdMonthInfo!=""){
		var fdNewInfo;
		var fdYear=fdMonthInfo.substring(0,4);
		var fdMonth=fdMonthInfo.substring(4,6);
		var fdUrl=Com_Parameter.ContextPath+"kms/log/kms_log_app/kmsLogApp.do?method=gotoMonthResultView";
		if("last"==type){
				fdMonth=Number(fdMonth)-1;
				if(fdMonth==0){
					fdYear=Number(fdYear)-1;
					fdMonth=12;
					}
				fdNewInfo=fdYear.toString()
				if(fdMonth<10){
					fdNewInfo+="0"+fdMonth.toString();
					}else{
						fdNewInfo+=fdMonth.toString();
						}
			}else if("next"==type){
				fdMonth=Number(fdMonth)+1;
				if(fdMonth>12){
					fdYear=Number(fdYear)+1;
					fdMonth=1;
					}
				fdNewInfo=fdYear.toString()
				if(fdMonth<10){
					fdNewInfo+="0"+fdMonth.toString();
					}else{
						fdNewInfo+=fdMonth.toString();
						}
			}
		fdUrl+="&moduleType=${param.moduleType}&fdTimeUnit=${param.fdTimeUnit}&yearStartTime=${param.yearStartTime}&yearEndTime=${param.yearEndTime}";
		fdUrl+="&monthStartTime=${param.monthStartTime}&monthEndTime=${param.monthEndTime}&operatorId=${param.operatorId}&docCategoryId=${param.docCategoryId}";
		fdUrl+="&fdModelId=${param.fdModelId}&knowledgeType=${param.knowledgeType}&startTime=${param.startTime}&endTime=${param.endTime}&type=${param.type}";
		fdUrl+="&fdMonthInfo="+fdNewInfo;;
		window.location.href=fdUrl;
		}
	
	
}

</script>
<style>
	.trunMonth{
		text-decoration: underline;
		color: #47b5e6 ;
		cursor: pointer;
		padding-left: 5px;
	}
	
	.onlyshow{
		color: grey;
		cursor: default;
		text-decoration: none !important;
	}
</style>
<link href="${KMSS_Parameter_ResPath}style/common/list/list.css" rel="stylesheet" type="text/css" />
<div class="pageBox">
	<sunbor:page name="queryPage" rowsizeText="rowsizeText2" pagenoText="pagenoText2" pageListSize="5" pageListSplit="" textStyleClass="pagenav_input" >
	<table width="100%" height="22" cellspacing="0" cellpadding="0" border="0" class="pageNav_tb">
		<tr>
			<td width="2%" valign="top">&nbsp;</td>
			<td width="55%" valign="top">
				<div class="page_box">
				<sunbor:leftPaging><bean:message key="page.thePrev"/></sunbor:leftPaging>
				{11}
				<sunbor:rightPaging><bean:message key="page.theNext"/></sunbor:rightPaging>
				</div>
			</td>
			<td width="15%" valign="top">
				<bean:message key="page.to"/>&nbsp;<bean:message key="page.the"/>{9}<bean:message key="page.page"/><a href="javascript:void(0);" class="btn_go" title="<bean:message key="page.changeTo"/>"
				 onclick="{10}">Go</a>
			</td>
			<td width="10%" valign="top">
				<bean:message key="page.total"/>&nbsp;{3}&nbsp;<bean:message key="page.row"/>
			</td>
			<td width="5%" valign="top">
				<c:if test="${pageNavJsFunction==null}">
					<a class="refresh" href="javascript:location.reload();"><bean:message key="button.refresh"/></a>
				</c:if>
				<c:if test="${pageNavJsFunction!=null}">
					<a class="refresh" href="javascript:${pageNavJsFunction}(location.href);"><bean:message key="button.refresh"/></a>
				</c:if>
			</td>
			<td width="2%" valign="top">&nbsp;</td>
		</tr>
	</table>
	</sunbor:page>
</div>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
