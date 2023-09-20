<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ page import="java.util.*,com.landray.kmss.util.*,com.landray.kmss.tic.core.cacheindb.datalist.util.*"%>
<script type="text/javascript" src="<c:url value='/tic/core/cacheindb/datalist/json2.js'/>"></script>
<script type="text/javascript">   
Com_IncludeFile("dialog.js|doclist.js");        
Com_IncludeFile("calendar.js");   
</script> 

<script>
var dialogObject = parent.dialogObject;
var dataObject=[];
var selectedObject=[];
Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");

<%if(StringUtil.isNull(request.getParameter("method"))){%>
window.onload = function(){
	document.getElementById("sqlContent").value=dialogObject.sqlContent;
	document.getElementById("dataFlds").value=JSON.stringify(dialogObject.flds);
	document.getElementById("selectType").value=dialogObject.selectType;
	document.getElementById("defShow").value=dialogObject.defShow;
	document.getElementById("isFirst").value="yes";

	document.getElementById("conditionsUUID").value=dialogObject.conditionsUUID;
	
	var fieldNames="";
	for(var i=0;i<dialogObject.flds.length;i++){
		if(i>0){
			fieldNames+=",";
		}
		fieldNames+=dialogObject.flds[i]["fldName"];
	}
	document.getElementById("fieldNames").value=fieldNames;
	document.getElementById("form1").submit();
}
<%}else{%>
window.onload = function(){
	setSearchDiv();
}
<%}%>

function getSelectedData(){
	var obj = document.getElementsByName("List_Selected");
	var n = 0;
	for(var i=0; i<obj.length; i++){
		if(obj[i].checked){
			selectedObject[n++] = dataObject[i];
		}
	}
	//return selectedObject;
	return swap(selectedObject);
}

function swap(fldObjects){
	var _fxMap={};
	for(var i=0;i<dialogObject.flds.length;i++){
		_fxMap[dialogObject.flds[i]["fldName"]]=dialogObject.flds[i]["xformId"];
	}
	var xformObjects=[];
	for(var i=0; i<fldObjects.length; i++){
		var swapO = {};
		for(var p in fldObjects[i]){
			swapO[_fxMap[p]]= fldObjects[i][p];
		}
		xformObjects[i] = swapO;
	}
	return xformObjects;
}

function doSelected(index){
	var obj = document.getElementsByName("List_Selected");
	obj[index].checked = true;
	//var opFrame = parent.document.getElementById("optFrame");
	//opFrame.contentWindow.Com_DialogReturnValue();
}

function doSearch(){
	var r = setCondition();
	if(!r){
		return;
	}
	document.getElementById("isFirst").value="no";
	document.getElementById("pageno").value=1;
	document.getElementById("form1").submit();
}

function setCondition(){
	var dso = JSON.parse(dialogObject.sqlContent);
	var params = dso.inputParams;
	if(!params){
		params = [];
	}
	var whereBlock="";
	var hasValue=false;
	for(var i=0;i<dialogObject.flds.length;i++){
		var fldName = dialogObject.flds[i]["fldName"];
	
		var searchFld = dialogObject.flds[i]["searchFld"];
		if(!searchFld){
			searchFld = fldName;
		}

		if(dialogObject.flds[i]["fldType"]=="Date"){
			var begind = document.getElementById("begin_"+fldName);
			if(begind.value!=""){
				var param={};
				param["name"]="begin_"+fldName;
				whereBlock+=" and "+searchFld+">=:"+param["name"];
				param["value"]=begind.value;
				param["type"]="Date";
				params[params.length]=param;
			}
			var endd = document.getElementById("end_"+fldName);
			if(endd.value!=""){
				var param={};
				param["name"]="end_"+fldName;
				whereBlock+=" and "+searchFld+"<=:"+param["name"];
				param["value"]=endd.value;
				param["type"]="Date";
				params[params.length]=param;
			}
			continue;
		}

		var fldEl = document.getElementById(fldName);
		if(dialogObject.flds[i]["required"]=="yes"){
			if(fldEl && fldEl.value==""){
				alert("请先输入查询条件'"+dialogObject.flds[i]["fldTitle"]+"'进行查找！");
				return false;
			}
		}
		var fldElChk = document.getElementById(fldName+"_chk");
		if(fldEl && fldEl.value!=""){
			var param={};
			param["name"]=fldName;
			if(fldElChk && fldElChk.checked){
				whereBlock+=" and "+searchFld+" like:"+fldName;
				param["value"]='%'+fldEl.value+'%';
			}else{
				whereBlock+=" and "+searchFld+"=:"+fldName;
				param["value"]=fldEl.value;
			}
			if(dialogObject.flds[i]["fldType"]){
				param["type"]=dialogObject.flds[i]["fldType"];
			}
			hasValue = true;
			params[params.length]=param;
		}
	}

	if(params.length>0){
		dso.inputParams=params;
	}

	if(dso.sql.toLowerCase().indexOf("where")==-1){
		if(hasValue){
			whereBlock = " where 1=1 "+whereBlock;
		}else{
			whereBlock = " where 1!=1 ";
		}
	}else{
		if(!hasValue){
			whereBlock = " and 1!=1 ";
		}
	}

	if(dso.sql.toLowerCase().indexOf("order by")!=-1){
		dso.sql = dso.sql.split("order by")[0]+whereBlock+" order by "+dso.sql.split("order by")[1];
	}else{
		dso.sql = dso.sql+whereBlock;
	}	
	//alert(JSON.stringify(dso));
	document.getElementById("sqlContent").value=JSON.stringify(dso);
	return true;
}

function getConditionHTML(){
	var html="";
	html+='<table class="tb_search">';
	html+='<tr>';

	for(var i=0;i<dialogObject.flds.length;i++){
		if("no"==dialogObject.flds[i]["search"]){
			continue;
		}
		var s = "";
		if(dialogObject.flds[i]["required"]=="yes"){
			s = "<font color='red'>*</font>"
		}
		html+='<td nowrap>'+dialogObject.flds[i]["fldTitle"]+s+':</td>';
		var fldName =dialogObject.flds[i]["fldName"];
		if(dialogObject.flds[i]["fldType"]=="Date"){
			html+='<td nowrap>';
			html+='从<input type="text" size="8" readonly="true" name="begin_'+fldName+'" id="begin_'+fldName+'">';
			html+='<a href="#" onclick="selectDate(\'begin_'+fldName+'\',null);">';
			html+='<img src="<%=request.getContextPath()%>/tic/core/cacheindb/datalist/img/calendar.gif" border="0" style="cursor:hand"></a>';
			html+='到<input type="text" size="8" readonly="true" name="end_'+fldName+'" id="end_'+fldName+'">';
			html+='<a href="#" onclick="selectDate(\'end_'+fldName+'\',null);">';
			html+='<img src="<%=request.getContextPath()%>/tic/core/cacheindb/datalist/img/calendar.gif" border="0" style="cursor:hand"></a>';
			html+='</td>';
		}else{
			html+='<td nowrap><input type="text" size="12" name="'+fldName+'" id="'+fldName+'">';
			if(!dialogObject.flds[i]["fldType"] || dialogObject.flds[i]["fldType"]=="String"){
				html+='<input type="checkbox" name="'+fldName+'_chk" id="'+fldName+'_chk" checked title="选择时进模糊查询">';
			}
			html+='</td>';
		}
	}

	html+='<td nowrap>&nbsp;<input type="button" class="btndialog"  style="width:50px" onclick="doSearch();" value="查询"></td>';
	
	html+='<td valign="top">';
	html+='<a href="#">';
	html+='<img alt="<bean:message key="button.close"/>" border="0" src="<%=request.getContextPath()%>/tic/core/cacheindb/datalist/img/x.gif" width="5" height="5" hspace="2" vspace="2" onclick="Search_Hide();">';
	html+='</a></td>';
	
	html+='</tr>';
	return html;
}

function setSearchDiv(){
		Search_Div.innerHTML = getConditionHTML();
}

function Search_Show(){
	Search_Div.innerHTML = getConditionHTML();
	var eventObj = Com_GetEventObject();
	var y1 = eventObj.clientY + 10;
	var margin_top;
	var y2;
	if(document.body.currentStyle) {
		y2 = parseInt(document.body.currentStyle.marginTop);
	}else {
		y2 = parseInt(getComputedStyle(document.body,null).marginTop);
	}
	Search_Div.style.top = Math.max(y1, y2)+document.body.scrollTop+5;
	Search_Div.style.display = "";
}

function Search_Hide(){
	Search_Div.style.display = "none";
}

</script>

<%
	String sqlContent = request.getParameter("sqlContent"); 
	String dataFlds = request.getParameter("dataFlds"); 
	
	Map fldsMap = Util.getFieldMap(dataFlds);
	String fieldNames = request.getParameter("fieldNames");  
	String selectType = request.getParameter("selectType");

	String defShow = request.getParameter("defShow");
	
%>
<form name="form1" id="form1" method="post" action="<c:url value="/tic/core/cacheindb/datalist/pageDatalist.do?method=list"/>"> 
 <textarea name="sqlContent" id="sqlContent" style="display:none"><%=sqlContent%></textarea>
 <textarea name="dataFlds" id="dataFlds" style="display:none"><%=dataFlds%></textarea>
 <input type="hidden" name="fieldNames" id="fieldNames" value="<%=fieldNames%>"  />
 <input type="hidden" name="selectType" id="selectType" value="<%=selectType==null?"checkbox":selectType%>"  />
 <input type="hidden" name="pageno" id="pageno" value="<%=request.getParameter("pageno")==null?1:request.getParameter("pageno")%>"  />
 <input type="hidden" name="rowsize" id="rowsize" value="<%=request.getParameter("rowsize")==null?10:request.getParameter("rowsize")%>"  />
 <input type="hidden" name="defShow" id="defShow" value="<%=defShow%>"  />
 <input type="hidden" name="isFirst" id="isFirst" value=""  />
 
 <%
 	String conditionsUUID = request.getParameter("conditionsUUID");
 %>
  <input type="hidden" name="conditionsUUID" id="conditionsUUID" value="<%=conditionsUUID%>"  />

 <SCRIPT language="JavaScript"> 
    document.oncontextmenu = new Function('event.returnValue=false;');
</SCRIPT>   

<!--
<div id="optBarDiv">
	<input type="button"  name="t1" value="筛选" onclick="Search_Show();">
</div>

<div id="Search_Div" style="display:none; position:absolute; top:5px; right:20px;z-index: 200">
</div>
-->
<div id="Search_Div" style="position:absolute; top:5px; right:20px;">
</div>
<br>
<br>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">   
	 
	 <%
	 String isFirst =  request.getParameter("isFirst");
	 if("yes".equals(isFirst) && "no".equals(defShow)){%>
	 	<%@ include file="/tic/core/cacheindb/datalist/list_norecord.jsp"%>
	 <%}else{
	 %>
	<table id="List_ViewTable">
		<tr>
			<td width="10pt">
			<%if("checkbox".equals(selectType)){%>
				<input type="checkbox" name="List_Tongle">
			<%}%>
			</td>
			<td width="40pt">${lfn:message('tic-core-common:ticCoreCommon.seqNum')}</td>
				<%
				String[] fs = null;
				if(StringUtil.isNotNull(fieldNames)){
					fs = fieldNames.split(",");
				}
				if(fs!=null){
					for(int j=0;j<fs.length;j++){
						if(!Util.isHidden(fs[j],fldsMap) ){
							out.print("<td style='text-align:"+Util.getAlign(fs[j],fldsMap)+";'>"+Util.getTitle(fs[j],fldsMap)+"</td>");
						}
					}
				}
				%>
		</tr>

		<%

		Page pg = (Page) request.getAttribute("queryPage");
		List<Map<String, String>> results = (List<Map<String, String>>)pg.getList();
		int i = 0;
		StringBuffer js = new StringBuffer();
		for(Map<String, String> result:results){
			js.append("var rowData={};\n");
			js.append("dataObject["+i+"]=rowData;\n");
		%>
			<tr webapps_href="" <%if("radio".equals(selectType)){out.print("onclick='doSelected("+i+");'");}%>>
				<td width="10pt"><input type="<%=selectType%>" name="List_Selected" value="<%=i%>" <%if("radio".equals(selectType)){out.print("onclick='doSelected("+i+");'");}%>></td>
				<td width="40pt" align="center"><%=++i%></td>
				<%
				if(fs!=null){
					for(int j=0;j<fs.length;j++){
						String val = Util.format(fs[j],result.get(fs[j]),fldsMap);
						js.append("rowData['"+fs[j]+"']='"+val+"';\n");
						if(!Util.isHidden(fs[j],fldsMap) ){
				%>
					<td style="text-align:<%=Util.getAlign(fs[j],fldsMap)%>;"><%=val%></td>  
				<%	
						}
				}	
			}%>  
			</tr>
		<%}%>
	</table>
	
	<script>
	<%
		out.print(js.toString());
	%>
	</script>

	<%@ include file="/tic/core/cacheindb/datalist/list_pagenav_down.jsp" %>
	<%}%>
</c:if>
</form>
<%@ include file="/resource/jsp/list_down.jsp"%>
