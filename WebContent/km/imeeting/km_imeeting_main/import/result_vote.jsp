<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var headers = data.headers;
var targets = data.targets;
if(headers && targets){
	{$
	<table style="width:100%;margin-top: 10px;" class="lui_result_tb">
		<tr>
			<th>
				投票对象
			</th>
	$}
	for(var i = 0;i< headers.length;i++){
		{$
			<th>
				{%headers[i].headerName%}
			</th>
		$}
	}
	{$
		</tr>
	$}
	for(var j = 0;j < targets.length;j++){
	{$
		<tr align="center">
			<td>{%targets[j].targetName%}</td>	
			<td>{%targets[j].total%}</td>
			<td>{%targets[j].quit%}</td>
	$}
		var choices = targets[j].choices;
		for(var k = 0;k < choices.length;k++){
		{$
			<td>{%choices[k].choiceNumber%}</td>
		$}
		}
	{$
		</tr>
	$}
	}
	{$
	</table>
	$}
}else{
	{$
	<bean:message key="return.noRecord" />
	$}
}

