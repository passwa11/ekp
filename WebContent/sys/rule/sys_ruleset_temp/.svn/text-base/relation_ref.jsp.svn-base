<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/rule/resources/css/buttons.css">
<title>
</title>
</head>
<body style="background-color:white">
	<p class="txttitle" style="margin-top:20px">
		<bean:message key="button.lookRef" bundle="sys-rule"/>
	</p>
	<table class="tb_normal" width="90%" style="margin-top:10px">
		<tr class="tr_normal_title">
			<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.1" bundle="sys-rule"/></td>
			<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.2" bundle="sys-rule"/></td>
			<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.3" bundle="sys-rule"/></td>
			<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.4" bundle="sys-rule"/></td>
			<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.5" bundle="sys-rule"/></td>
		</tr>
		<c:forEach items="${refContents }" var="refContent" varStatus="vStatus">
			<tr>
				<td align="center">${refContent.factId }</td>
				<td align="center">${refContent.type }</td>
				<td align="center">${refContent.sysRuleSetDocName}</td>
				<td align="center" class="descTd" dataId=${vStatus.index }>${refContent.desc }</td>
				<td align="center">
					<c:if test="${refContent.maps.size() > 0 }">
						<a class="btn_txt" href="#" onclick="openMappingDialog(${vStatus.index})"><bean:message key="button.lookMap" bundle="sys-rule"/></a>
					</c:if>
					<c:if test="${refContent.maps.size() <= 0 }">
						<bean:message key="button.noMap" bundle="sys-rule"/>
					</c:if>
				</td>
			</tr>
			<tr class="mappingTable" style="display:none">
				<td colspan="7">
					<div style="max-height:300px;overflow-y:auto;">
						<table class="tb_normal" width="90%">
							<tr class="tr_normal_title">
								<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.5" bundle="sys-rule"/></td>
								<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.6" bundle="sys-rule"/></td>
								<td width="150px"><bean:message key="sysRuleSetDoc.ref.col.title.7" bundle="sys-rule"/></td>
							</tr>
							<c:forEach items="${refContent.maps }" var="map">
								<tr>
									<td align="center">${map.fieldId }</td>
									<td align="center">${map.fieldName }</td>
									<td align="center">${map.paramName }</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
		</c:forEach>
   	</table>
   	<center>
   		<div class="button-group">
			<!-- 关闭按钮 -->
			<input class="btn resultBtn" type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();">
	   	</div>
   	</center>
   	<script type="text/javascript">
   		Com_AddEventListener(window, "load", function(){
   			var tdObjs = document.getElementsByClassName("descTd");
   			for(var i=0; i<tdObjs.length; i++){
   				var desc = tdObjs[i].innerText;
   				if(desc){
   					var newText = desc;
   	   				if(desc.indexOf("id") != -1 && desc.indexOf("endNodeId") != -1){
   	   					try{
   	   						var descObj = eval('(' + desc + ')');
	   	   					newText = '分支ID：' + descObj["id"] + "<br/>";
	   	   					newText += "结束节点：" + descObj["endNodeId"];
   	   					}catch(err){
   	   					}
   	   				}
   	   				tdObjs[i].innerHTML = newText;
   				}
   			}
   		});
   	
   		function openMappingDialog(index){
   			var tableObj = document.getElementsByClassName("mappingTable")[index];
   			var value = $(tableObj).css("display");
   			if(value && value=="none"){
   				$(tableObj).css("display","");
   			}else{
   				$(tableObj).css("display","none");
   			}
   		}
   	</script>
</body>
</html>