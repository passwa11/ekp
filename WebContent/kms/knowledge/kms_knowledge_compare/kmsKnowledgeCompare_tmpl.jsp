<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

var rowData = row;
var attListStr = rowData["attList"];
var attList = JSON.parse(attListStr);
var index = rowData["index"];
var fdId = rowData["fdId"];
var fdVersion = rowData["fdVersion"];
{$
<div kmss_fdId="{%fdId%}" style="border:1px solid #d5d5d5;padding-bottom:10px;margin:10px;width:98%;margin-bottom:15px;">
	<div style="padding:8px;border-bottom:1px solid #d5d5d5;font-size:16px;font-weight:bold;">
		${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.version")}：{%fdVersion%}</div>
	<div>
		$}
		if(attList.length > 0){
		{$<table class="lui_listview_columntable_listtable" style="width:100%">
		<thead class="lui_listview_listtable_thead">
		<tr>
			<th style="text-align:center;" width="10%"></th>
			<th style="text-align:center;" width="40%">
				${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.filename")}
			</th>
			<th style="text-align:center;" width="25%">
				${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.createtime")}
			</th>
			<th style="text-align:center;" width="25%">
				${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.creator")}
			</th>
		</tr>
		</thead>
		$}
		for(var j=0;j < attList.length;j++){
		{$<tr>
		<td style="text-align:center;">
			<input type="checkbox" data-lui-mark="table.content.checkbox" value="{%attList[j].fdId%}" name="List_Selected">
		</td>
		<td style="text-align:center;color:#2f84fb;">
			{%attList[j].fdFileName%}
			<input name="docCreatorId" type="hidden" value="{%attList[j].docCreatorId%}">
			<input name="docCreatorName" type="hidden" value="{%attList[j].docCreatorName%}">
			<input name="fdFileName" type="hidden" value="{%attList[j].fdFileName%}">
			<input name="sysAttMainId" type="hidden" value="{%attList[j].fdId%}">
		</td>
		<td style="text-align:center;">
			{%attList[j].docCreateTime%}
		</td>
		<td style="text-align:center;">
			{%attList[j].docCreatorName%}
		</td>
	</tr>
		$}
		}
		{$</table>$}
		}else{
		{$<div style="padding:10px;padding-bottom:0;color:red;">
			${lfn:message("kms-knowledge:kmsKnowledgeCompare.tab.compare.nofiletocompare")}
	</div>$}
		}
		{$</div>
</div>
$}
