<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js|base64.js");
</script>
<link href="../../../core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="../../../core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<script type="text/javascript">
/************标记修正。。。************
 * 使用国际化的资源文件
 ************************/
$(function(){
	var spanNode = document.createElement("span");
	spanNode.id = "Include_Custom_Validations_Span_Id";
	document.body.appendChild(spanNode);
	$("#Include_Custom_Validations_Span_Id").load(Com_Parameter.ContextPath +
		"tic/core/resource/jsp/custom_validations.jsp");
});
</script>

<div id="optBarDiv">

   	<input type="button"
		value="${lfn:message('button.list')}"
		onclick="query_submit();">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<center>
<p class="txttitle">${ticRestMainName}</p>

<html:form action="/tic/rest/connector/tic_rest_query/ticRestQuery.do">
	<input type="hidden" name="ticRestMainId" value="${ticRestQueryForm.ticRestMainId}">
  <table class="tb_normal" width=81%>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-rest-connector:ticRestQuery.docSubject')}
		</td><td colspan="3" width="85%">
			<xform:text property="docSubject"  showStatus="edit" style="width:85%" />
		</td>
   </tr>
   </table>
  <br>
  <input name="fdQueryParam" id="fdQueryParam_id" type="hidden"/>
</html:form>
<form id="jdbc_query_form" action="">
  <table class="tb_normal" width=81% id="urlParamValueBefore">
		  	<tr>
				<td colspan="4" class="com_subject" style="font-weight: bold;">
				     ${lfn:message('tic-rest-connector:ticRestSetting.urlParam')}
			  </td>
			</tr>
    		<tr>
			       <td class="td_normal_title" width="20%">
							${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
					</td>
					<td class="td_normal_title" width="30%">
							${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
					</td>	
					<td class="td_normal_title" width="20%">
							${lfn:message('tic-core-common:ticCoreTransSett.paramType')}
					</td>	
			        <td class="td_normal_title" width="30%">
							${lfn:message('tic-core-common:ticCoreCommon.writeData')}
					</td>	
			</tr>
  </table>
    <table class="tb_normal" width=81% id="headerParamValueBefore">
		  	<tr>
				<td colspan="4" class="com_subject" style="font-weight: bold;">
				     ${lfn:message('tic-rest-connector:ticRestSetting.headerParam')}
			  </td>
			</tr>
    		<tr>
			       <td class="td_normal_title" width="40%">
							${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
					</td>		
			        <td class="td_normal_title" width="50%">
							${lfn:message('tic-core-common:ticCoreCommon.writeData')}
					</td>	
					<td class="td_normal_title" width="10%" align='center'>
							<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow('headerParamValueBefore');"/>
					</td>	
			</tr>
  </table>
    <table class="tb_normal" width=81% id="cookieParamValueBefore">
		  	<tr>
				<td colspan="4" class="com_subject" style="font-weight: bold;">
				     ${lfn:message('tic-rest-connector:ticRestSetting.cookieParam')}
			  </td>
			</tr>
    		<tr>
			       <td class="td_normal_title" width="40%">
							${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
					</td>
			        <td class="td_normal_title" width="50%">
							${lfn:message('tic-core-common:ticCoreCommon.writeData')}
					</td>
					<td class="td_normal_title" width="10%" align='center'>
							<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow('cookieParamValueBefore');"/>
					</td>		
			</tr>
  </table>
  <table class="tb_normal" width=81% >
		  	<tr>
				<td colspan="4" class="com_subject" style="font-weight: bold;">
				     ${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}
			  </td>
			</tr>
    		<tr>
			       <td width=40% style="vertical-align: top;">
							<xform:textarea property="bodyQueryParam"  style="width:95%"  showStatus="edit"></xform:textarea>
					</td>
					<td width=60% >
							 <table  class="tb_normal" width=100%  id="bodyParamValueBefore">
							      <tr>
									  <td class="td_normal_title" width="35%">
											${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
											</td>
					            	   <td class="td_normal_title" width="35%">
											${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
											</td>
										<td class="td_normal_title" width="20%">
											${lfn:message('tic-core-common:ticCoreTransSett.paramType')}
										</td>
					            	   <td class="td_normal_title" width="10%">
											${lfn:message('tic-core-common:ticCoreCommon.writeData')}
									   </td>
							       </tr>
							   </table>
					</td>
			</tr>
  </table>
</form>
</center>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/custom_validations.js" type="text/javascript"></script>
<script type="text/javascript">
$KMSSValidation();
$(function(){
	// 验证标题
	FUN_AddValidates("docSubject:required");
	//初始化数据
	init_setInParams();
});
function init_setInParams(){
	var reqParam = $.parseJSON('${reqParam}'); 
	var queryStr='${fdQueryParam}';
	if(!reqParam|| reqParam==null){
		return;
	}
	var bodyParam=reqParam["body"];
	if(bodyParam){
    	createTableBodyElementHTML(bodyParam);
	}
	var headerFlag=true;
	var cookieFlag=true;
	var urlFlag=true;
	if(queryStr){
		var fdQueryParam = $.parseJSON('${fdQueryParam}'); 
		var urlQueryParam= fdQueryParam["url"];
		$("#urlParamValueBefore tr:not(:first)").each(function(){
			var name=$(this).find("td:first-child").text();
			$(this).find("input[name=value]").val(urlQueryParam[name]);
		});
		var headerQueryParam= fdQueryParam["header"];
		 for (var name in headerQueryParam) {
			 var html =  $("<tr><td>"+name+"</td><td><input class='inputsgl' type='text' name='value' value='"+headerQueryParam[name]+"'></td><td align='center'><center><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></center></td></tr>");
			$("#headerParamValueBefore").append(html);
			headerFlag=false;
			if(headerFlag){
				headerFlag=false;
			}
		}
		var cookieQueryParam= fdQueryParam["cookie"];
		 for (var name in cookieQueryParam) {
			 var html =  $("<tr><td>"+name+"</td><td><input class='inputsgl' type='text' name='value' value='"+cookieQueryParam[name]+"'></td><td align='center'><center><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></center></td></tr>");
			$("#cookieParamValueBefore").append(html);	
			if(cookieFlag){
				cookieFlag=false;
			}
		}
		var bodyQueryParam= fdQueryParam["body"];
		//var fdBody = Base64.decode(JSON.stringify(bodyQueryParam));
		$("textarea[name=bodyQueryParam]").text(JSON.stringify(bodyQueryParam));
	}
	 var urlParam=reqParam["url"];
	 if(urlFlag && urlParam){
	 	for (var len = urlParam.length, i = len - 1; i >= 0 ; i--) {
			var html =  $("<tr><td>"+urlParam[i].name+"</td><td>"+urlParam[i].title+"</td><td>"+urlParam[i].type+"</td><td><input class='inputsgl' type='text' name='value''></td></tr>");
			$("#urlParamValueBefore").append(html);	
			if(urlFlag){
				urlFlag=false;
			}
		}
	} 
	var headerParam=reqParam["header"];
	if(headerFlag && headerParam){
	 	for (var len = headerParam.length, i = len - 1; i >= 0 ; i--) {
			var html =  $("<tr><td>"+headerParam[i].name+"</td><td><input class='inputsgl' type='text' name='value' value='"+headerParam[i].value+"'></td><td align='center'><center><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></center></td></tr>");
			$("#headerParamValueBefore").append(html);	
		}
	} 
	var cookieParam=reqParam["cookie"];
	if(cookieFlag && cookieParam){
	 	for (var len = cookieParam.length, i = len - 1; i >= 0 ; i--) {
			var html =  $("<tr><td>"+cookieParam[i].name+"</td><td><input class='inputsgl' type='text' name='value' value='"+cookieParam[i].value+"'></td><td align='center'><center><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></center></td></tr>");
			$("#cookieParamValueBefore").append(html);	
		}
	}	 
}
function deleteRow(obj){
	//debugger;
	$(obj).parent().parent().parent().remove();
}
function addRow(tableId,type){
	 var new_row="<tr><td><input  type='text' name='name' style='width:85%' validate='required' class='inputsgl'></input></td>";
	 new_row+="<td><input  type='text' name='value' style='width:85%' validate='required' class='inputsgl'></input></td>";
	 new_row+="<td><center><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></center></td></tr>";
	 $("#"+tableId).append($(new_row));
}
 function createTableBodyElementHTML(infoObj){
	trs= new Array();
	$.each(infoObj, function(idx, obj) {
		genParaInHtml(obj,"");
	});
	for(i=0;i<trs.length;i++){
		var tr = trs[i];
		var tr_new;
		if(tr.child)//判断如果有子节点不显示填值框
		{
			tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td>"+tr.type+"</td><td></td></tr>");
		}else
		{
			tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td>"+tr.type+"</td><td><input class='inputsgl' type='text' name='value'></td></tr>");
		} 
		 if(tr.parentId){
			tr_new.addClass("child-of-"+tr.parentId);
		}
		$("#bodyParamValueBefore").append(tr_new);	
	}
	$("#bodyParamValueBefore").treeTable({ initialState: true,indent:15 }).expandAll();
}
function genParaInHtml( obj,  parentId){
	var name = obj.name;
	var title = obj.title;
	var type= obj.type;
	var id = name;
	if(parentId){
		id = parentId+"-"+name;
	}
	var tr = {};
	//判断是否有children,做个标识用于页面展示去除填值信息显示
	if(obj.children)
	{
		tr.child = 1;
	}
	tr.id = id.replace(":","_");
	tr.name = name;
	tr.title = title;
	tr. type= type;
	tr.parentId = parentId;
	trs.push(tr);
	var children = obj.children;
	if(children){
		$.each(children, function(idx2, obj2) {
			genParaInHtml(obj2,tr.id);
		});
	}
}

function query_submit(){
	var fdQueryParam={};
	 var fdUrl={};
	 $('#urlParamValueBefore tr:not(:first):not(:first)').each(function(){
			var key=$(this).find("td:first").text();
			fdUrl[key]=$(this).find("input[name=value]").val();
	});
	 fdQueryParam["url"]=fdUrl;
	 var fdHeader={};
	 $('#headerParamValueBefore tr:not(:first):not(:first)').each(function(){
			var key=$(this).find("td:first").text()||$(this).find("input[name=name]").val();
			fdHeader[key]=$(this).find("input[name=value]").val();
	});
	 fdQueryParam["header"]=fdHeader;
	 var fdCookie={};
	 $('#cookieParamValueBefore tr:not(:first):not(:first)').each(function(){
			var key=$(this).find("td:first").text()||$(this).find("input[name=name]").val();
			fdCookie[key]=$(this).find("input[name=value]").val();
	});
	 fdQueryParam["cookie"]=fdCookie;
	 var bodyQueryParam= $("textarea[name=bodyQueryParam]").val();
	 var transType = false;
	 var fdBody= null;
	 if(bodyQueryParam){
		 try{
			 //var fdBody=JSON.parse(bodyQueryParam);
			 fdBody=Base64.encode(bodyQueryParam);
			 
		 }catch(err){
			 alert("请检查json格式是否正确！");
		 }
	 }else{
		 fdBody={};
		 $('#bodyParamValueBefore tr:not(:first)').each(function(){
			    var isRootNode = ($(this)[0].className.search("child-of") == -1);
			    if(isRootNode) {
			    	var name =$(this).find("td:first").text();
			    	var value = $(this).find("input[name='value']").val();	
			    	if($(this).hasClass("parent")){;
			    		fdBody[name]=buildPara($(this));
			    	}else{
			    		fdBody[name]=value;
			    	}
			    }
		}); 
		 fdBody = Base64.encode(JSON.stringify(fdBody));
		 transType = true;
	 }
	fdQueryParam["body"]=fdBody;
	fdQueryParam["transType"]=transType;
	$("#fdQueryParam_id").val( JSON.stringify(fdQueryParam));
	 Com_Submit(document.ticRestQueryForm, "getResult");
}
 function buildPara(node,object){
       var childNodes = getChildrenOf(node);
       object=[];
	   var obj={};
	   var type = $(node).find("td:eq(2)").text();
       childNodes.each(function() {
    	   var name =$(this).find("td:first").text();
    	   var value = $(this).find("input[name='value']").val();
    	   if(typeof(value) != "undefined")
    	      obj[name] = value;
    	   else{
    		   return obj[name] =buildPara($(this),name);
    	   }
     });
       if(type&&type.indexOf("array")>-1){
    	   object.push(obj);
    	   return object;
       }else{
    	     return obj;
       }
}
function getChildrenOf(node) {
    return $(node).siblings("tr.child-of-" + node.attr("id"));
  };
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
