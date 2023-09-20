<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
   <template:replace name="content">
   		
   		<script language="JavaScript">
			Com_IncludeFile("doclist.js|dialog.js|validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js");
			var deleteGroups=new Array();//待删除标签列表
			seajs.use(['lui/jquery','lui/dialog'], function($,dialog) {
				//保存(包含新增、更新、删除操作)
				var lastIndex = "${fn:length(kmCalendarUserShareGroupForm.kmCalendarShareGroupFormList)}";
				window.saveUserKmCalendarShareGroup=function(formObj,method){
					if(!validation.validate()){
						return false;
					}
					if(deleteGroups.length>0){
				    	var confirmMsg="是否确认删除以下分组：";
				    	var values = [];
					    for(var i=0;i<deleteGroups.length;i++){
						    confirmMsg+=" "+deleteGroups[i].name+" ";
						    values.push(deleteGroups[i].id);
						}
			    		dialog.confirm(confirmMsg,function(value){
			    			if(value==true){
								$("[name='deleteIds']").val(values);
								ajaxform();
							}
				    	});
					}else{
						ajaxform();
					}
				};

				//提交
				var ajaxform=function(){
					$.ajax({
						url: "${LUI_ContextPath}/km/calendar/km_calendar_share_group/kmCalendarUserShareGroup.do?method=update",
						type: 'POST',
						dataType: 'json',
						data: $("#userShareGroupform").serialize(),
						success: function(data, textStatus, xhr) {//操作成功
							if (data && data['status'] === true) {
								window.$dialog.hide("true");
							}
						},
						error:function(xhr, textStatus, errorThrown){//操作失败
							window.$dialog.hide("false");
						}
					});
				};

				window.DocList_AddRow2 = function(optTB, content){
					if(optTB==null)
						optTB = DocListFunc_GetParentByTagName("TABLE");
					else if(typeof(optTB)=="string")
						optTB = document.getElementById(optTB);
					if(content==null)
						content = new Array;
					var tbInfo = DocList_TableInfo[optTB.id];
					var index = tbInfo.lastIndex - tbInfo.firstIndex;
					var htmlCode, newCell;
					var newRow = optTB.insertRow(tbInfo.lastIndex);
					tbInfo.lastIndex++;
					lastIndex++;
					newRow.className = tbInfo.className;
					for(var i=0; i<tbInfo.cells.length; i++){
						newCell = newRow.insertCell(-1);
						newCell.className = tbInfo.cells[i].className;
						newCell.align = tbInfo.cells[i].align ? tbInfo.cells[i].align : '';
						newCell.vAlign = tbInfo.cells[i].vAlign ? tbInfo.cells[i].vAlign : '';
						if(tbInfo.cells[i].isIndex)
							htmlCode = index + 1;
						else
							htmlCode = DocListFunc_ReplaceIndex(content[i]==null?tbInfo.cells[i].innerHTML:content[i], lastIndex-1);
						newCell.innerHTML = htmlCode;
						Address_QuickSelectionDetail(htmlCode);
					}
					DocList_RemoveDeleteAllFlag(optTB);
					return newRow;
				};

				// 重写move方法，move的时候修改群组的排序号
				window.DocList_MoveRow2 = function(direct, optTR){
				    var firstIndex = 1;
			    	if(optTR==null)
			    		optTR = DocListFunc_GetParentByTagName("TR");
			    	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
			    	var tbInfo = DocList_TableInfo[optTB.id];
			    	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
			    	var tagIndex = rowIndex + direct;
			    	//alert(tagIndex);
			    	if(direct==1){
			    		if(tagIndex>lastIndex)
			    			return;
			    		var tagIndexOrderValue=$(optTB.rows[tagIndex]).find(":input")[2].value;
			    		var rowIndexOrderValue=$(optTB.rows[rowIndex]).find(":input")[2].value;
			    		$(optTB.rows[tagIndex]).find(":input")[2].value = rowIndexOrderValue;
			    		$(optTB.rows[rowIndex]).find(":input")[2].value = tagIndexOrderValue;
			    		optTB.rows[rowIndex].parentNode.insertBefore(optTB.rows[tagIndex], optTB.rows[rowIndex]);
			    	}else{
			    		if(tagIndex<firstIndex)
			    			return;
			    		var tagIndexOrderValue=$(optTB.rows[tagIndex]).find(":input")[2].value;
			    		var rowIndexOrderValue=$(optTB.rows[rowIndex]).find(":input")[2].value;
			    		$(optTB.rows[tagIndex]).find(":input")[2].value = rowIndexOrderValue;
			    		$(optTB.rows[rowIndex]).find(":input")[2].value = tagIndexOrderValue;
			    		optTB.rows[rowIndex].parentNode.insertBefore(optTB.rows[rowIndex], optTB.rows[tagIndex]);
			    	}
			    	DocListFunc_RefreshIndex(tbInfo, rowIndex);
			    	DocListFunc_RefreshIndex(tbInfo, tagIndex);
			    };

				window.moveUp=function(_this){
					DocList_MoveRow2(-1);
				};

				window.moveDown=function(_this){
					DocList_MoveRow2(1);
				};
				
				//移除群组(显示上移除,后台未移除)
				window.removeGroup=function(obj){
					var index = $(obj).attr('data-removeGroup-index');
					var idObject = "kmCalendarShareGroupFormList["+index+"].fdId";
				    var nameObject = "kmCalendarShareGroupFormList["+index+"].fdName";
				    var idValue = $("[name='"+idObject+"']").val();
				    if(idValue!=null && idValue!=''){
				    	deleteGroups.push({"id":idValue,"name":$("[name='"+nameObject+"']").val()});//放入待删除标签列表
				    }
				    DocList_DeleteRow();//删除行
				    //刷新其他行序列
				    var removeGroups = $('[data-removeGroup-index]');
				    for(var i = 0; i < removeGroups.length;i++){
				    	removeGroups.eq(i).attr('data-removeGroup-index',i);
				    }
				};
   	   		});
			
		</script>
       <html:form action="/km/calendar/km_calendar_share_group/kmCalendarUserShareGroup.do" styleId="userShareGroupform">
       <input type="hidden" name="deleteIds" value="">
       <input style="display:none" mce_style="display:none" /> 
      	<br/><p class="txttitle"><bean:message  bundle="km-calendar" key="kmCalendarShareGroup.fdGroup"/></p><br/>       
       		<table class="tb_normal" width=98% id="TABLE_DocList" align="center">
       			<%--标题--%>
       			<tr>
       				<%--群组名--%> 
					<td class="td_normal_title" style="width: 18%">
						<bean:message  bundle="km-calendar" key="kmCalendarShareGroup.fdName"/>
					</td>
					<%--群组人员--%> 
					<td class="td_normal_title" style="width: 35%">
						<bean:message  bundle="km-calendar" key="kmCalendarShareGroup.fdGroupMemberNames"/>
					</td>
					<td class="td_normal_title" style="width: 35%">
						<bean:message  bundle="km-calendar" key="kmCalendarShareGroup.fdDescription"/>
					</td>
					<td width="12%" align="center">
						<a onclick="DocList_AddRow2(TABLE_DocList);" style="cursor: pointer;"><div style="width: 16px;height: 16px;" class="lui_icon_s_icon_add_green" ></div></a>
					</td>
       			</tr>
				<tr KMSS_IsReferRow="1" style="display: none">
					<%--群组名--%> 					
					<td width="18%">
						<input type="hidden" name="kmCalendarShareGroupFormList[!{index}].fdId"/>
						<xform:text required="true" showStatus="edit" property="kmCalendarShareGroupFormList[!{index}].fdName" validators="required maxLength(100)" style="width:90%"></xform:text>
						<input type="hidden" name="kmCalendarShareGroupFormList[!{index}].fdOrder" value="!{index}">
					</td>
					<%--群组人员--%> 
					<td width="35%">
						<input type="hidden" name="kmCalendarShareGroupFormList[!{index}].fdGroupMemberIds" />
						<xform:text required="true" property="kmCalendarShareGroupFormList[!{index}].fdGroupMemberNames" style="width:80%" showStatus="edit"></xform:text>
						<a href="#" onclick="Dialog_List(true,'kmCalendarShareGroupFormList[!{index}].fdGroupMemberIds','kmCalendarShareGroupFormList[!{index}].fdGroupMemberNames',';','kmCalendarUserShareGroupDataBean',null,null,null,null,null,'${lfn:message('km-calendar:sysCalendarShareGroup.winRemark') }');"><bean:message key="button.select"/></a>
					</td>
					<%-- 备注 --%>
					<td width="35%">
						<xform:text showStatus="edit" property="kmCalendarShareGroupFormList[!{index}].fdDescription" validators="maxLength(1500)" style="width: 95%"></xform:text>
					</td>
					<td width="12%" align="center">
						<div onclick='moveUp(this)' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_arrow_up_blue'></div>
						<div onclick='moveDown(this)' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_arrow_down_blue'></div>
						<div data-removeGroup-index="!{index}" onclick='removeGroup(this);' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_close_red' ></div>
					</td>
				</tr>
				<c:forEach items="${kmCalendarUserShareGroupForm.kmCalendarShareGroupFormList}" var="kmCalendarShareGroupForm" varStatus="vstatus">
				<tr KMSS_IsContentRow="1" height="20px;">
					<td width="18%">
						<input type="hidden" name="kmCalendarShareGroupFormList[${vstatus.index}].fdId" value="${kmCalendarShareGroupForm.fdId}"> 
						<xform:text required="true" showStatus="edit" property="kmCalendarShareGroupFormList[${vstatus.index}].fdName" validators="required maxLength(100)" value="${kmCalendarShareGroupForm.fdName}" style="width: 90%"></xform:text>
						<input type="hidden" name="kmCalendarShareGroupFormList[${vstatus.index}].fdOrder" value="${vstatus.index}"> 
					</td>
					<td width="35%">
						<input type="hidden" name="kmCalendarShareGroupFormList[${vstatus.index}].fdGroupMemberIds" value="${kmCalendarShareGroupForm.fdGroupMemberIds}" />
						<xform:text required="true" property="kmCalendarShareGroupFormList[${vstatus.index}].fdGroupMemberNames" value="${kmCalendarShareGroupForm.fdGroupMemberNames}" style="width:80%" showStatus="edit"></xform:text>
						<a href="#" onclick="Dialog_List(true,'kmCalendarShareGroupFormList[${vstatus.index}].fdGroupMemberIds','kmCalendarShareGroupFormList[${vstatus.index}].fdGroupMemberNames',';','kmCalendarUserShareGroupDataBean',null,null,null,null,null,'${lfn:message('km-calendar:sysCalendarShareGroup.winRemark') }');"><bean:message key="button.select"/></a>
					</td>
					<td width="35%">
						<xform:text showStatus="edit" property="kmCalendarShareGroupFormList[${vstatus.index}].fdDescription" validators="maxLength(1500)" value="${kmCalendarShareGroupForm.fdDescription}" style="width:95%;" ></xform:text>
					</td>
					<td width="12%" align="center">
						<div onclick='moveUp(this)' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_arrow_up_blue'></div>
						<div onclick='moveDown(this)' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_arrow_down_blue'></div>
						<div data-removeGroup-index="${vstatus.index}" onclick='removeGroup(this);' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_close_red' ></div>
						</td>
				</tr>
				</c:forEach>
       		</table>
       		<div style="text-align: center;padding-top: 10px;">
		   		<ui:button  text="${lfn:message('button.save')}"  onclick="saveUserKmCalendarShareGroup(document.kmCalendarUserShareGroupForm, 'update');" style="width:70px;"/>
		   	</div>
       </html:form>
   </template:replace>
</template:include>
<script language="JavaScript">
	var validation=$KMSSValidation();//加载校验框架
	var form = document.getElementById('userShareGroupform');
	form.onkeydown = function(e){
		var e = e || window.event,
			keyCode = e.which || e.keyCode;
		var srcObj = e.srcElement ? e.srcElement : e.target;
		if(srcObj && srcObj.name && srcObj.name.indexOf('fdGroupMemberNames')>-1){
			setTimeout(function(){
				srcObj.value='';
			},1);
			return;
		}
		return keyCode == 13 ? false : true;
	};
</script>