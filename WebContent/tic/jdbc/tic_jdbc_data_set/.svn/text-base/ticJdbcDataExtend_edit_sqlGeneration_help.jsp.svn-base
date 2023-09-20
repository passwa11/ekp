<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<html>
<head>
<style>
body, td, input, select, textarea{
	font-size: 12px;
	color: #333333;
	line-height: 20px;
}
body{
	margin: 0px;
	padding: 20 0;
}
.tb_normal{
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	background-color: #FFFFFF;
	margin-bottom: 20px;
	width: 100%;
}
.td_normal, .tb_normal td{
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	padding:3px;
}
.tr_normal_title{
	background-color: #F0F0F0;
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	padding:3px;
	text-align:center;
	word-break:keep-all;
}
.td_normal_title{
	background-color: #F0F0F0;
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	padding:3px;
	word-break:keep-all;
}
.inputsgl{
	color: #0066FF;
	border-color: #999999;
	border-style: solid;
	border-width: 0px 0px 1px 0px;
}
.btn{
	color: #0066FF; 
	background-color: #F0F0F0; 
	border: 1px #999999 solid; 
	font-weight: normal; 
	padding: 0px 1px 1px 0px;
	height: 18px;
	clip:  rect();
}
</style>
<title>
<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.jspHelp"/>
</title>
</head>
<body>
<center>
<div style="width: 750px">
<h1 style="font-size: 13px;"><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.extendDataSqlHelp"/></h1>

<table class="tb_normal">
	<tr class="tr_normal_title">
	<td><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.overview"/></td>
	</tr>
	<tr>
	<td><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.extendDataDesc1"/><br>
		<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.extendDataDesc2"/>
	</td>
	</tr>
</table>

<table class="tb_normal">
	<tr class="tr_normal_title">
		<td colspan="3"><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.systemParameters"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.inputParameter"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.inputParameterDesc"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchParameter"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchParameterDesc"/>搜索参数格式，用于搜索的时候被替换，其中Mysql 模糊查询 like '%'{?name}'%'; SQLserver 模糊查询 like '%'+{?name}+'%'; Oralce 模糊查询 like '%'||{?name}||'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			{startIndex}
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.startRowDesc"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			{endIndex} 
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.endRowDesc"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			{pageSize}
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageDesc"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			[]
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSqlDesc"/>
		</td>
	</tr>
</table>

<table class="tb_normal">
	<tr class="tr_normal_title">
		<td colspan="2"><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.mysqlSample"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.commonSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.regularQueryDesc"/></br>
			select * from km_review_main where 1=1 and doc_subject like '%'{docsubject}'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchQueryDesc"/></br>
			select * from km_review_main where 1=1 and doc_status = '30' and doc_subject like '%'{?docsubject}'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageQueryDesc"/></br>
			select * from km_review_main where doc_subject like '%'{docsubject}'%' limit {startIndex},{pageSize} 
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSQL"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSqlQueryDesc"/></br>
			select * from km_review_main where 1=1 [and doc_subject like '%'{docsubject}'%'] limit {startIndex},{pageSize} 
		</td>
	</tr>
</table>

<table class="tb_normal">
	<tr class="tr_normal_title">
		<td colspan="2"><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.sqlserverSample"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.commonSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.regularQueryDesc"/></br>
			select * from km_review_main where 1=1 and doc_subject like '%'+{docsubject}+'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchQueryDesc"/></br>
			select * from km_review_main where 1=1 and doc_status = '30' and doc_subject like '%'+{?docsubject}+'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageQueryDesc"/></br>
			select * from (select row_number() over(order by fd_id desc)as rn,* from km_review_main where 1=1 and doc_subject like '%'+{docsubject}+'%')as kmreviewmain where kmreviewmain.rn > {startIndex} and kmreviewmain.rn <= {endIndex}
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSQL"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSqlQueryDesc"/></br>
			select * from (select row_number() over(order by fd_id DESC)as rn,* from km_review_main where 1=1 [and doc_subject like '%'+{docsubject}+'%'])as kmreviewmain where kmreviewmain.rn > {startIndex} and kmreviewmain.rn <= {endIndex}
		</td>
	</tr>
</table>

<table class="tb_normal">
	<tr class="tr_normal_title">
		<td colspan="2"><bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.oracleSample"/></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.commonSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.regularQueryDesc"/></br>
			select * from km_review_main where 1=1 and DOC_SUBJECT like '%'||{DOCSUBJECT}||'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.searchQueryDesc"/></br>
			select * from km_review_main where 1=1 and DOC_STATUS = '30' and DOC_SUBJECT like '%'||{?DOCSUBJECT}||'%'
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageSelect"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.pageQueryDesc"/></br>
			select * from (select ROWNUM rn,kmreviewmain.* from km_review_main kmreviewmain where 1=1 and DOC_SUBJECT like '%'||{DOCSUBJECT}||'%') where rn > {startIndex} and rn <= {endIndex}
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSQL"/>
		</td>
		<td>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.help.dynamicSqlQueryDesc"/></br>
			select * from km_review_main where 1=1 [and DOC_SUBJECT like '%'||{DOCSUBJECT}||'%']
		</td>
	</tr>
</table>
</div>
</center>
</body>
</html>