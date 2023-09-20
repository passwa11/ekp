<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var datas = data;
var columnNum = template.parent.columnNum;
var width = 100/columnNum+'%';
if (datas.length!=0){
	var obj = datas[0];
	var pageno = obj['pageno'].text;
	var total = obj['total'].text;
	document.getElementById("pageno").value=pageno;
	document.getElementById("total").value=total;
}


{$
 
	<table class="shortCut_UL">$}
for (var i = 0; i < datas.length; i ++) {
	var grid = datas[i];
	
	if (i % columnNum === 0){
		{$<tr class="shortCut_li">$}
	}
			{$<td width="{%width%}">
			<c:set var="fdIdInfo" value="{%grid['fdId'].text%}"></c:set>
				  <div class="note_box" onclick="showInfo('${fdIdInfo}')">
                        <div class="note_box_con">
							<h3>{%grid['fdCourse'].text%}</h3>
							<div class="name"><span class="date">{%grid['docCreateTime'].text%}</span>{%grid['docCreator'].text%}</div>
							<div class="detail">{%grid['fdNotesContent'].text%}
								
							</div>
							<div class="discuss"> <bean:message	bundle="kms-common" key="kmsCommon.praise" />( {%grid['docPraiseCount'].text%} )&nbsp;&nbsp;|&nbsp;&nbsp;<bean:message	bundle="kms-common" key="kmsCommon.evaluate" />( {%grid['docEvalCount'].text%} )</div>
						</div>
                    </div>
			</td>$}
	if (i % columnNum === columnNum-1){
		{$</tr>$}
	}
}
var _num = datas.length % columnNum; 
if(_num>0){
	for(var j = 0;j<columnNum-_num;j++){
		{$
			<td width="{%width%}"></td>
		$}
	}
	{$ </tr> $}
}

{$</table>$}