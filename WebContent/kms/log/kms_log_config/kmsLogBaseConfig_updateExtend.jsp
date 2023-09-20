<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="logExtend.edit" width="90%" sidebar="no">
	<template:replace name="title">
		${lfn:message('kms-log:kmsLogConfig.extend.add.setting')}
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
		<ui:button text="${ lfn:message('button.submit') }" onclick="submitContent()"></ui:button>	
		<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content" >
		<html:form action="/kms/log/kms_log_extend/kmsLogExtendMain.do">
		<div class="lui_form_content_frame"  style="min-height: 400px;padding-top:10px; ">
			<div style="width: 100%">
				<ui:button text="${lfn:message('kms-log:kmsLogExtend.module.add')}" onclick="addModule();"></ui:button>	
			</div>
			<div id="contentInfoDiv">
				<c:forEach var="kmsLogExtendForm" items="${kmsLogExtendMainForm.extendList}" varStatus="varStatus">
					<table class="itemTable" id="${varStatus.index}">
						<tr>
							<td style="text-align:center;width: 15%">${lfn:message('kms-log:kmsLogExtend.fdModuleName')}:</td><td style="width: 35%"><xform:text property="extendList[${varStatus.index}].fdModuleName"></xform:text></td>
							<td style="text-align:center;width: 15%">${lfn:message('kms-log:kmsLogExtend.fdModuleKey')}:</td><td style="width: 35%"><xform:text property="extendList[${varStatus.index}].fdModuleKey"></xform:text></td>
						</tr>
						<tr>
							<td colspan="4">
							<table class="operateTable">
								<tr>
									<td colspan='1'>
										<div class='btnDiv' onclick='addItem(this)'>${lfn:message('kms-log:kmsLogExtend.item.add')}</div> 
									</td>
									<td colspan='3'>
										<div class='btnDiv' onclick='deleteModile(this)'>${lfn:message('kms-log:kmsLogExtend.module.del')}</div> 
									</td>
								</tr>
								<c:forEach var="kmsLogExtendItemForm" items="${kmsLogExtendForm.itemList}" varStatus="vStatus">
								<tr>
								<td class="td1" >${lfn:message('kms-log:kmsLogExtend.operateName')}：</td><td class="td2" ><xform:text property="extendList[${varStatus.index}].itemList[${vStatus.index}].fdOperateName"></xform:text></td>
								<td class="td1" >${lfn:message('kms-log:kmsLogExtend.operateValue')}：</td><td class="td2" ><xform:text property="extendList[${varStatus.index}].itemList[${vStatus.index}].fdOperateValue"></xform:text></td>
								<html:hidden property="extendList[${varStatus.index}].itemList[${vStatus.index}].fdId"/>
								<html:hidden property="extendList[${varStatus.index}].itemList[${vStatus.index}].isSelected"/>
								<td style="width:10% "><a onclick="deleteItem(this)" class="delIcon">${lfn:message('kms-log:kmsLogExtend.module.remove')}</a></td>
								</tr>
								</c:forEach>
							</table>
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
		</div>
		</html:form>
		<script>
			function addModule(){
					var fdContent=$("#contentInfoDiv");
					var fdNum=fdContent.children().length;
					
					var fdTable=$("<table class='itemTable'></table>");
						fdTable.attr("id",fdNum);
					var fdTr=$("<tr></tr>");
					var fdTd=$("<td></td>");
					var fdInput=$("<input class='inputsgl'></input>");

					fdTd.attr("style","width:15%;text-align:center;");
					fdTd.text("<bean:message bundle='kms-log' key='kmsLogExtend.fdModuleName'/>：");
					fdTr.append(fdTd);

					fdTd=$("<td></td>");
					fdTd.attr("style","width:35%;");
					fdInput.attr("name","extendList["+fdNum+"].fdModuleName");
					fdTd.append(fdInput);
					fdTr.append(fdTd);

					fdTd=$("<td></td>");
					fdTd.attr("style","width:15%;text-align:center;");
					fdTd.text("<bean:message bundle='kms-log' key='kmsLogExtend.fdModuleKey'/>：");
					fdTr.append(fdTd);

					fdTd=$("<td></td>");
					fdTd.attr("style","width:35%;");
					fdInput=$("<input class='inputsgl' ></input>");
					fdInput.attr("name","extendList["+fdNum+"].fdModuleKey");
					fdTd.append(fdInput);
					fdTr.append(fdTd);
					fdTable.append(fdTr);

					fdTr=$("<tr></tr>");
					fdTd=$("<td colspan='4'></td>");
					var operateTable=$("<table class='operateTable'><tr><td colspan='1' style='width:110px;'><div class='btnDiv' onclick='addItem(this)'><bean:message bundle='kms-log' key='kmsLogExtend.item.add'/></div></td><td colspan='3'><div class='btnDiv' onclick='deleteModile(this)'>${lfn:message('kms-log:kmsLogExtend.module.del')}</div> </td></tr></table>");
					fdTd.append(operateTable);
					fdTr.append(fdTd);
					fdTable.append(fdTr);
					
					fdContent.append(fdTable);
				}

			function addItem(obj){
				var fdContent=obj.parentNode.parentNode.parentNode;
				var fdNum=fdContent.parentNode.parentNode.parentNode.parentNode.parentNode.id;
				var fdItemNum=$(fdContent).children().length-1;
				var fdTr=$("<tr></tr>");
				var fdTd=$("<td></td>");
				var fdInput=$("<input class='inputsgl' ></input>");
				
				fdTd.attr("class","td1");
				fdTd.text("<bean:message bundle='kms-log' key='kmsLogExtend.operateName'/>：");
				fdTr.append(fdTd);

				fdTd=$("<td></td>");
				fdTd.attr("class","td2");
				fdInput.attr("name","extendList["+fdNum+"].itemList["+fdItemNum+"].fdOperateName");
				fdTd.append(fdInput);
				fdTr.append(fdTd);

				fdTd=$("<td></td>");
				fdTd.attr("class","td1");
				fdTd.text("<bean:message bundle='kms-log' key='kmsLogExtend.operateValue'/>：");
				fdTr.append(fdTd);

				fdTd=$("<td></td>");
				fdTd.attr("class","td2");
				fdInput=$("<input class='inputsgl'></input>");
				fdInput.attr("name","extendList["+fdNum+"].itemList["+fdItemNum+"].fdOperateValue");
				fdTd.append(fdInput);
				fdTr.append(fdTd);

				fdTd=$("<td style='width:10% '><a onclick='deleteItem(this)' class='delIcon'>${lfn:message('kms-log:kmsLogExtend.module.remove')}</a></td>");
				fdTr.append(fdTd);
				$(fdContent).append(fdTr);
				}

			function submitContent(){
				var fdInputList=document.getElementsByTagName("input");
				seajs.use(['lui/dialog'],function(dialog){
						if(fdInputList.length>0){
							var fdInput;
							var fdSubmit=true;
							for(var i=0;i<fdInputList.length;i++){
								fdInput=fdInputList[i];
								if(null==fdInput.value||""==fdInput.value){
									fdSubmit=false;
									break;
									}
								}
							if(fdSubmit){
							    Com_SubmitForm(document.kmsLogExtendMainForm, 'updateExtendInfo');
								}else{
									dialog.alert("<bean:message bundle='kms-log' key='kmsLogExtend.worning.finishInfo'/>");
									}
						}else{
						    Com_SubmitForm(document.kmsLogExtendMainForm, 'updateExtendInfo');
							}
					});
				}

			function deleteModile(obj){
				var fdTableContent=obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
					fdTableContent.parentNode.removeChild(fdTableContent);
				var fdContent=document.getElementById("contentInfoDiv");
				var childrenList=fdContent.childNodes;
				var childNode;
				var fdInput;
				var fdInputName;
				var num=0;
				for(var i=0;i<childrenList.length;i++){
					childNode=childrenList[i];
					if("itemTable"==childNode.className){
						childNode.id=num;
						num=num+1;
						}
					}
				for(var j=0;j<num;j++){
					var fdTable=document.getElementById(j.toString());
					var fdNum=parseInt(fdTable.id);
					var inputList=fdTable.getElementsByTagName("input");
					for(var m=0;m<inputList.length;m++){
						fdInput=inputList[m];
						fdInputName=fdInput.name;
						if(fdInputName!=null&&fdInputName.indexOf("extendList")>-1){
							fdInput.setAttribute("name",fdInputName.replace(/extendList\[\d+\]/g, "extendList["+fdNum+"]"));
							}
						}
					}
				
				}

			function deleteItem(obj){
					var fdTr=obj.parentNode.parentNode;
					var fdTbody=obj.parentNode.parentNode.parentNode;
						fdTbody.removeChild(fdTr);
					var fdTrList= fdTbody.getElementsByTagName("tr");

					var fdItem;
					var fdInput;
					var fdInputName;
					var fdNum;
					for(var i=0;i<fdTrList.length;i++){
						fdNum=i-1;
						fdItem=fdTrList[i];
						var inputList=fdItem.getElementsByTagName("input");
						for(var m=0;m<inputList.length;m++){
							fdInput=inputList[m];
							fdInputName=fdInput.name;
							if(fdInputName!=null&&fdInputName.indexOf("itemList")>-1){
								fdInput.setAttribute("name",fdInputName.replace(/itemList\[\d+\]/g, "itemList["+fdNum+"]"));
								}
							}
					}
				}
		</script>
		<style>
		 .itemTable{
		 	margin-top:10px;
		 	width: 95%;
		 	border: 1px solid grey;
		 }
		 
		 .itemTable td{
		 	padding: 10px 0px;
		 }
		 
		 .btnDiv{
		 	width:90px;
		 	color:white;
		 	text-align: center;
		 	padding: 5px;
		 	background-color: #47b5e6;
		 	cursor: pointer;
		 	margin-left: 10px;
		 }
		 .operateTable{
			 width:100%;
		 }
		 .delIcon{
		 	text-decoration: underline;
		 	color: #47b5e6;
		 	cursor: pointer;
		 }
		 .td1{
		 	text-align: center;
		 	width: 12%;
		 }
		 .td2{
			 width: 32%;
		 }
		</style>
	</template:replace>
</template:include>

