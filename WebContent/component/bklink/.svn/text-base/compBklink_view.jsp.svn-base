<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<%--内容 --%>
	<template:replace name="body">
		<script type="text/javascript">
		Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
		</script>
		<script>
		Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");
		Com_IncludeFile("msg.js", "style/"+Com_Parameter.Style+"/msg/");
		var quoteSize = "${compBklinkForm.quoteSize}";
		function showRelation(){
			var table_relation = document.getElementById("relation_id");
			if(table_relation.style.display =='none'){
			 Com_OpenWindow('${KMSS_Parameter_ContextPath}component/bklink/compBklink.do?method=findQuotes&deleteModelIds=${ids}&parentIsNotNull=${parentIsNotNull}&modelName=${modelName}&_fdMainModelName=${_fdMainModelName}&searchCondition=${searchCondition}&httpURL=${httpURL}&mainModelName=${JsParam.mainModelName}&style=display:','_self');
			}
		}
		//页面数据过多，显示加载中
		function load(){
			var loadOver = document.getElementsByName("loadOver")[0].value;
			if(loadOver.length==0){
				document.getElementById("loading").innerHTML = "<bean:message bundle="component-bklink" key="compBklink.loading"/>";
				setTimeout("load()",10);
			}else{
				document.getElementById("loading").innerHTML = "";
			}
		}
		function my_submit(){
			//没有选择引用关系，则提示
			var fdShow = document.getElementById("fdShow").value;
			if(fdShow.length==0){
				alert("<bean:message bundle="component-bklink" key="compBklink.please.checked"/>");
				return false;
			}
			var deletelObjectSize = document.getElementsByName("deletelObjectSize")[0].value;
			if(parseInt(deletelObjectSize)<=1){
				alert("<bean:message bundle='component-bklink' key='compBklink.delete.object.less'/>");
				return false;
			}
			for(var i = 0; i < quoteSize; i++){
				if(document.getElementsByName("modelquoteList["+i+"].fdRemoveToModelName").length<=0){
					continue;
				}
				var value = document.getElementsByName("modelquoteList["+i+"].fdRemoveToModelName")[0].value;
				if(value == null || value == '' || value == undefined){
					alert("<bean:message bundle='component-bklink' key='compBklink.fdRemoveToModelName.message'/>");
					return false;
				}
			}	
			return true;
		}
		
		var quoteParam={};
		var historyIframe = false;
		function openQuoteModelList(indexValue){
			historyIframe = true;
			var url = "${KMSS_Parameter_ContextPath}component/bklink/compBklink.do?method=viewQuote&indexValue="+indexValue;
		    ${modelQuoteScript};
			var quoteObjList;
			if(modelQuoteJson != null && modelQuoteJson != ''){
				quoteObjList = eval((modelQuoteJson));
			}	
			
			var style = "width=500,height=500,top=150,left=400, status=0,scrollbars =1, resizable=1";
			quoteParam.quoteObjList = quoteObjList;
			//window.open("${KMSS_Parameter_ContextPath}component/bklink/compBklink_quote_view.jsp?indexValue="+indexValue,"",style);
			var viewUrl = "/component/bklink/compBklink_quote_view.jsp?indexValue="+indexValue;
		  	seajs.use("lui/dialog" , function(dialog) {
		  		dialog.iframe(viewUrl, " ", null, {width:600, height:450, topWin : window, close: true});
		  	});
			//window.showModelessDialog("${KMSS_Parameter_ContextPath}component/bklink/compBklink_quote_view.jsp?indexValue="+indexValue,quoteObjList);
		}
		//返回跳转
		function historyTo(){
			var i = historyIframe ? -3 : -2;
			window.history.go(i);
			/*
			var fdShow = document.getElementById("fdShow").value;
			var table_relation = document.getElementById("relation_id");
			if(fdShow.length==0||table_relation.style.display =='none'){
				window.history.go(-1);
			}else{
				window.history.go(-2);
			}*/
		}
		</script>
		<center>
		<html:form action="/component/bklink/compBklink.do?method=saveRemoveQuoteAndDelete" onsubmit="return my_submit()">
		<table  width="95%" width=400 border=0 cellspacing=1 cellpadding=0>
			<tr cellspacing="0" cellpadding="0">
				<td align="center" colspan="3" height="20"  class="barmsg"><bean:message bundle="component-bklink" key="compBklink.delete.confirm"/></td>
			</tr>
			<tr cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
				<td colspan="3" height="40">
						<center>
							<bean:message bundle="component-bklink" key="compBklink.delete.confirm.message"/>
							<c:if test="${isRecycle}">
								<bean:message bundle="component-bklink" key="compBklink.delete.confirm.message.recycle"/>
							</c:if>
						</center>
						<br>
				</td>
			</tr>
		</table>
		<table  width="95%" width=400 border=0 cellspacing=1 cellpadding=0>
			<htm:text property="fdId"/>
			<input name="fdShow" id="fdShow" type="hidden"/>
			<input name="httpURL" id="httpURL" type="hidden" value="${httpURL}"/>
			<input name="modelName" id="modelName" type="hidden" value="${modelName}"/>
			<tr cellspacing="0" cellpadding="0">
				<td colspan="3">
					   <center><a href="javascript:void(0)" onclick="showRelation()"><bean:message bundle="component-bklink" key="compBklink.show.quote.infomation"/></a></center>
				</td>
			</tr>
			<tr id = "relation_id" cellspacing="0" cellpadding="0"  style="${HtmlParam.style}"  >
				<td colspan="3" bgcolor="#FFFFFF" align="center">
				<center><span id="loading" style="color: red;"></span></center>
					<table width="100%" class="tb_normal">
						<tr align="center" class="td_normal_title">				
						  <td width="30%"><bean:message bundle="component-bklink" key="compBklink.deleteModel"/></td>
						  <td width="25%"><bean:message bundle="component-bklink" key="compBklink.quoteObjList"/></td>
						  <td width="45%">
						  <input type="hidden" name="fdRemoveToAllModelId"/>
						  <input type="hidden" name="fdRemoveToAllModelName"/>
						  <a href="#"
								onclick="Dialog_Tree(false, 
						 'fdRemoveToAllModelId', 
						 'fdRemoveToAllModelName', 
						 ',', 
						 'compBklinkTreeService&_fdMainModelName=${_fdMainModelName}&fdModelName=${modelName}&fdModelIds=${ids}&fdSelectModelId=!{value}', 
						 '<bean:message key="compBklink.quote.document.list" bundle="component-bklink"/>',
						  null, getRemoveToModelIds,
						   null, null, null,
						   '<bean:message key="compBklink.quote.document.list" bundle="component-bklink"/>')"><bean:message bundle="component-bklink" key="compBklink.allRemoveToObj"/></a></td>						
						</tr>
						<c:forEach items = "${compBklinkForm.modelquoteList}" var="compBklinkContext" varStatus="vstatus" >
						<input type="hidden" name="modelquoteList[${vstatus.index}].fdId"   value="${compBklinkContext.fdId}" />
						<input type="hidden" name="modelquoteList[${vstatus.index}].fdModelName"   value="${compBklinkContext.fdModelName}" />
						<input type="hidden" name="loadOver" value="${compBklinkContext.fdSize}" />
						<input type="hidden" name="deletelObjectSize" value="${contextListSize}" />
						<tr align="center">					
						  <td width="30%">
						  	<c:forEach items = "${compBklinkContext.quoteObjList}" var="compBklinkQuoteContext" varStatus="vstatus_">
						  		<input type="hidden" name="modelquoteList[${vstatus.index}].quoteObjList[${vstatus_.index}].fdId"  value="${compBklinkQuoteContext.fdId}" />
						  		<input type="hidden" name="modelquoteList[${vstatus.index}].quoteObjList[${vstatus_.index}].fdModelName"  value="${compBklinkQuoteContext.fdModelName}" />
						  		<input type="hidden" name="fdMainModelName"  value="${mainModelName}" />
						  		<input type="hidden" name="modelquoteList[${vstatus.index}].quoteObjList[${vstatus_.index}].fdProperty"  value="${compBklinkQuoteContext.fdProperty}" />
						  	</c:forEach>
						  	<c:out value="${compBklinkContext.fdName}"/>				  
						  </td>
						  <td width="25%">
						  <c:if test="${compBklinkContext.fdSize>0}">
						  	<a href ="#" onclick="openQuoteModelList(${vstatus.index});">
						  </c:if>
						  		<c:out value="${compBklinkContext.fdSize}"/>
						  <c:if test="${compBklinkContext.fdSize>0}">
						  	</a>
						  </c:if>
						  </td>
						  <td width="45%">
						  <c:if test="${compBklinkContext.fdSize>0}">
						   <input type="hidden" name="modelquoteList[${vstatus.index}].fdRemoveToModelId"  value="" />
						  <input type="text" name="modelquoteList[${vstatus.index}].fdRemoveToModelName"  class="inputsgl" readonly="true" value="" />
						  <a href="#"
								onclick="Dialog_Tree(false, 
						 'modelquoteList[${vstatus.index}].fdRemoveToModelId', 
						 'modelquoteList[${vstatus.index}].fdRemoveToModelName', 
						 ',', 
						 'compBklinkTreeService&_fdMainModelName=${_fdMainModelName}&fdModelName=${modelName}&fdModelIds=${ids}&parentIsNotNull=${JsParam.parentIsNotNull}&fdSelectModelId=!{value}', 
						 '<bean:message key="compBklink.quote.document.list" bundle="component-bklink"/>',
						  null, null,
						   null, null, null,
						   '<bean:message key="compBklink.quote.document.list" bundle="component-bklink"/>')"><bean:message bundle="component-bklink" key="compBklink.removeToObj"/></a><span class="txtstrong">*</span>
						  </c:if>
						  </td>						
						</tr>
								
						</c:forEach>				
					</table>
					<br></br>	
				</td>
			</tr>	
		</table>
		<br>
			<div align=center id = "btnDiv" style="${HtmlParam.style}">
				<input type="submit" class="btnmsg"  value="<bean:message bundle='component-bklink' key='compBklink.removeAndDelete'/>" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btnmsg" value="<bean:message key='button.back'/>" onclick="historyTo();"/>									
			</div>
		</html:form>
		<script type="text/javascript">
			function getRemoveToModelId(obj){	
				document.getElementsByName("modelquoteList["+obj+"].fdRemoveToModelName")[0].value;
				var rtnValue = window.showModalDialog("${KMSS_Parameter_ContextPath}component/bklink/compBklink.do?method=editRemoveToModel&modelName=${modelName}&searchCondition=${searchCondition}");
				if(rtnValue != null || rtnValue != undefined){
					if(rtnValue[0]!=undefined){
						document.getElementsByName("modelquoteList["+obj+"].fdRemoveToModelId")[0].value = rtnValue[0];
					}
					if(rtnValue[1]!=undefined){
						document.getElementsByName("modelquoteList["+obj+"].fdRemoveToModelName")[0].value = rtnValue[1];
					}		 			
				}
			}
			function getRemoveToModelIds(){	
				var fdRemoveToAllModelId = document.getElementsByName("fdRemoveToAllModelId")[0].value ;	
				var fdRemoveToAllModelName = document.getElementsByName("fdRemoveToAllModelName")[0].value ;	
					for(var j = 0; j < quoteSize; j++){	
						$("input[name='modelquoteList["+j+"].fdRemoveToModelId']").val(fdRemoveToAllModelId);
						$("input[name='modelquoteList["+j+"].fdRemoveToModelName']").val(fdRemoveToAllModelName);
					}
			}
		</script>
		<script type="text/javascript">
			var style = "${JsParam.style}";
			if(style == '' || style == undefined){
				document.getElementById("relation_id").style.display = 'none';
				document.getElementById("btnDiv").style.display = 'none';
			}else{
					setTimeout("load()",10);
					document.getElementById("fdShow").value = "123456" ;
			}
		</script>
		</center>
	</template:replace>
</template:include>
