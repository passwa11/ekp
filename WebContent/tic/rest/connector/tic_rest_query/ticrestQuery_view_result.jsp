<%@page import="com.landray.kmss.tic.rest.connector.forms.TicRestQueryForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.json-viewer.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.json-viewer.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/clipboard.min.js"></script>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js|base64.js");
</script>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<script type="text/javascript">
/************标记修正。。。************
 * 使用国际化的资源文件
 ************************/
$(function(){
	
});

$(document).ready(function(){
	var spanNode = document.createElement("span");
	spanNode.id = "Include_Custom_Validations_Span_Id";
	document.body.appendChild(spanNode);
	$("#Include_Custom_Validations_Span_Id").load(Com_Parameter.ContextPath +
		"tic/core/resource/jsp/custom_validations.jsp");
	
	var clipboard = new ClipboardJS('.btn');    
    clipboard.on('success', function(e) {        
        console.log(e);    
    });    
    clipboard.on('error', function(e) {        
        console.log(e);    
    });  
    
	});
</script>

<div id="optBarDiv">

   	<input type="button"
		value="${lfn:message('tic-core-common:ticCoreCommon.saveSearchRecord')}"
		onclick="Com_Submit(document.ticRestQueryForm, 'save');">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<html:form action="/tic/rest/connector/tic_rest_query/ticRestQuery.do">
	<input type="hidden" name="ticRestMainId" value="${ticRestQueryForm.ticRestMainId}">
	<input type="hidden" name="docSubject" value="${ticRestQueryForm.docSubjectShow}">
   <input name="fdQueryParam" id="fdQueryParam_id" type="hidden"/>
   <input name="fdJsonResult" id="fdJsonResult_id" type="hidden"/>
   <input name="fdJsonResultBase64" id="fdJsonResultBase64_id" type="hidden"/>
   <input name="docFaultInfo" id="docFaultInfo_id" type="hidden"/>
</html:form>
<center>
<p class="txttitle">${ticRestQueryForm.docSubject }</p>

<table class="tb_normal" id="Label_Tabel" width=95%>
		<tr LKS_LabelName="${lfn:message('tic-core-common:ticCoreFuncBase.fdParaIn')}">
			<td>
			<form id="jdbc_query_form" action="">
			  <table class="tb_normal" width=100% id="urlParamValueBefore">
					  	<tr>
							<td colspan="4" class="com_subject" style="font-weight: bold;">
							     ${lfn:message('tic-rest-connector:ticRestSetting.urlParam')}
						  </td>
						</tr>
			    		<tr>
						       <td class="td_normal_title" width="50%">
										${lfn:message('tic-rest-connector:ticCoreTransSett.paramName')}
								</td>
						        <td class="td_normal_title" width="50%">
										${lfn:message('tic-core-common:ticCoreCommon.writeData')}
								</td>	
						</tr>
			  </table>
		    <table class="tb_normal" width=100% id="headerParamValueBefore">
				  	<tr>
						<td colspan="4" class="com_subject" style="font-weight: bold;">
						     ${lfn:message('tic-rest-connector:ticRestSetting.headerParam')}
					  </td>
					</tr>
		    		<tr>
					       <td class="td_normal_title" width="50%">
									${lfn:message('tic-rest-connector:ticCoreTransSett.paramName')}
							</td>		
					        <td class="td_normal_title" width="50%">
									${lfn:message('tic-core-common:ticCoreCommon.writeData')}
							</td>	
					</tr>
		  </table>
		    <table class="tb_normal" width=100% id="cookieParamValueBefore">
				  	<tr>
						<td colspan="4" class="com_subject" style="font-weight: bold;">
						     ${lfn:message('tic-rest-connector:ticRestSetting.cookieParam')}
					  </td>
					</tr>
		    		<tr>
					       <td class="td_normal_title" width="50%">
									${lfn:message('tic-rest-connector:ticCoreTransSett.paramName')}
							</td>
					        <td class="td_normal_title" width="50%">
									${lfn:message('tic-core-common:ticCoreCommon.writeData')}
							</td>
					</tr>
		  </table>
		  <table class="tb_normal" width=100% >
				  	<tr>
						<td colspan="4" class="com_subject" style="font-weight: bold;">
						     ${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}
					  </td>
					</tr>
		    		<tr>
					       <td width=90%>
									<xform:textarea property="bodyQueryParam"  style="width:95%"  showStatus="edit"></xform:textarea>
							</td>
					</tr>
		  </table>
		  	</td>
	</tr>
    <tr LKS_LabelName="${lfn:message('tic-core-common:jsonView.returnParam')}">
			<td>
		  <table class="tb_normal" width=100% >
		  			<tr>
		  			<td align="center">
		  				<div id="btn" data-clipboard-text="1">
		  					<a href="javaScript:void(0)"><span style="font-size:18px">拷贝结果</span></a>
						</div>
						<script>    
						    var btn = document.getElementById('btn');    
						    var clipboard = new ClipboardJS(btn);    
						    clipboard.on('success', function(e) {        
						        console.log(e);    
						    });    
						    clipboard.on('error', function(e) {        
						        console.log(e);    
						    });
						</script>
						
		  			</td>
		  			</tr>
		    		<tr>
					       <td width=90%>
									<pre style="padding-left: 25px;" id="json_result_view" ></pre>
							</td>
					</tr>
		  </table>
		</td>
	</tr>
	    <tr LKS_LabelName="${lfn:message('tic-core-common:jsonView.errorInfo')}">
			<td>
		  <table class="tb_normal" width=100% >
		    		<tr>
					       <td width=90%>
									<xform:textarea property="errmsg"  style="width:95%"  showStatus="edit"></xform:textarea>
							</td>
					</tr>
		  </table>
		  </form>
		</td>
	</tr>
</table>
</center>
<%
TicRestQueryForm ticRestQueryForm = (TicRestQueryForm)request.getAttribute("ticRestQueryForm");
String fdQueryParam = ticRestQueryForm.getFdQueryParamBase64();
request.setAttribute("fdQueryParam", fdQueryParam);
%>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/custom_validations.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//初始化数据
	init_setInParams();
});
 function init_setInParams(){
	var fdQueryParamStr='${fdQueryParam}';
	fdQueryParamStr = Base64.decode(fdQueryParamStr);
		if(!fdQueryParamStr|| fdQueryParamStr==null){
			return;
		}
 	var fdQueryParam = $.parseJSON(fdQueryParamStr);
	var url=fdQueryParam["url"];
	if(url)
		addRow('urlParamValueBefore',url);
	var header=fdQueryParam["header"];
	if(header)
		addRow('headerParamValueBefore',header);
	var cookie=fdQueryParam["cookie"];
	if(cookie)
		addRow('cookieParamValueBefore',cookie);
	var body=fdQueryParam["body"];
	if(body&&JSON.stringify(body)!="{}")
		$("textarea[name=bodyQueryParam]").val(Base64.decode(body)); 
	$("#fdQueryParam_id").val(fdQueryParamStr);
    getRestData("${ticRestQueryForm.ticRestMainId}",fdQueryParamStr);
} 
 function addRow(tableId,json){
	 for (var item in json) {
		 var new_row="<tr><td>"+item+"</td><td>"+json[item]+"</td></tr>";
		 $("#"+tableId).append($(new_row));
	 }
}
</script>

<script>
		
	function getRestData(id,fdInputParams) {
			var dataUrl = '${KMSS_Parameter_ContextPath}tic/rest/connector/tic_rest_main/ticRestMain.do?method=getRestData';
				$.ajax({
					url: dataUrl,
					type: 'POST',
					data:{fdId:id,fdInputParams:fdInputParams},
					dataType: 'json',
					error: function(data){
						alert("error:"+data);
					},
					success: function(data){
						if(data["errcode"]=="0"){
							var str = data["result"];
							var jstr = str.substring(1,str.length-1);
							var jsonValue = eval("({" + jstr + "})");
							$("#json_result_view").jsonViewer(jsonValue.out);
							if(data["result_base64"]){
								$("#fdJsonResultBase64_id").val(data["result_base64"]);
							}else{
								$("#fdJsonResult_id").val(data["result"]);
							}
							
							$("#btn").attr('data-clipboard-text',JSON.stringify(jsonValue.out));
						}else{
							$("textarea[name=errmsg]").val("调试获取数据出错："+data["errmsg"]);
							$("#docFaultInfo_id").val("调试获取数据出错："+data["errmsg"]);
						}
					}
			   });
			}

</script>

<%@ include file="/resource/jsp/view_down.jsp"%>
