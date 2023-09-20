<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>  
<template:include ref="default.edit">
    
    <template:replace name="head">
    	<script type="text/javascript">
    		Com_IncludeFile("doclist.js|dialog.js|validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js");
    		var validation=$KMSSValidation();//加载校验框架
    		var deleteGroups=new Array();//待删除标签列表
			seajs.use(['lui/jquery','lui/dialog'], function($,dialog) {
				//保存(包含新增、更新、删除操作)
				var lastIndex = "${fn:length(hrRatifyUserStaffConcernConfigForm.hrRatifyStaffConcernConfigFormList)}";
				window.saveUserKmCalendarShareGroup=function(formObj,method){
					if(!validation.validate()){
						return false;
					}
					if(deleteGroups.length>0){
				    	var values = [];
					    for(var i=0;i<deleteGroups.length;i++){
						    values.push(deleteGroups[i].id);
						}
					    $("[name='deleteIds']").val(values);
					}
					Com_Submit(formObj,method);
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
					var idObject = "hrRatifyStaffConcernConfigFormList["+index+"].fdId";
				    var idValue = $("[name='"+idObject+"']").val();
				    if(idValue!=null && idValue!=''){
				    	deleteGroups.push({"id":idValue,"name":$("[name='"+index+"']").val()});//放入待删除行列表
				    }
				    DocList_DeleteRow();//删除行
				    //刷新其他行序列
				    var removeGroups = $('[data-removeGroup-index]');
				    for(var i = 0; i < removeGroups.length;i++){
				    	removeGroups.eq(i).attr('data-removeGroup-index',i);
				    }
				};
				
				$(".weui_switch :checkbox").on("click", function() {
					var status = $(this).is(':checked');
					$(this).prev().val(status);
				});
				window.changeValue = function(obj){
					var status = $(obj).is(':checked');
					$(obj).parent().next().val(status);
				};
   	   		});
    	</script>
    	<script src="./resource/weui_switch.js"></script>
    </template:replace>
    
    <template:replace name="content">
        <html:form action="/hr/ratify/hr_ratify_staff_concern_config/hrRatifyUserStaffConcernConfig.do">
			<input type="hidden" name="deleteIds" value="">
	       	<input style="display:none" mce_style="display:none" /> 
	      	<br/><p class="txttitle"><bean:message  bundle="hr-ratify" key="table.hrRatifyStaffConcernConfig"/></p><br/>       
	       		<table class="tb_normal" width=98% id="TABLE_DocList" align="center">
	       			<tr>
	       				<%--功能--%> 
						<td class="td_normal_title" style="width: 18%">
							<bean:message  bundle="hr-ratify" key="hrRatifyStaffConcernConfig.fdGongNeng"/>
						</td>
						<%--管理员--%> 
						<td class="td_normal_title" style="width: 35%">
							<bean:message  bundle="hr-ratify" key="hrRatifyStaffConcernConfig.fdManagers"/>
						</td>
						<td width="12%" align="center">
							<a href="javascript:void(0)" onclick="DocList_AddRow2(TABLE_DocList);" style="cursor: pointer;"><div style="width: 16px;height: 16px;" class="lui_icon_s_icon_add_green" ></div></a>
						</td>
	       			</tr>
					<tr KMSS_IsReferRow="1" style="display: none">
						<%--功能--%> 					
						<td width="18%">
							<input type="hidden" name="hrRatifyStaffConcernConfigFormList[!{index}].fdId"/> 
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdEntry') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" onclick="changeValue(this);" />
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[!{index}].fdEntry" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdLeave') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" onclick="changeValue(this);" />
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[!{index}].fdLeave" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdPositive') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" onclick="changeValue(this);" />
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[!{index}].fdPositive" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdTransfer') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" onclick="changeValue(this);" />
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[!{index}].fdTransfer" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdContract') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" onclick="changeValue(this);" />
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[!{index}].fdContract" />
							</label>
						</td>
						<%--管理员--%> 
						<td width="35%">
							<xform:address propertyName="hrRatifyStaffConcernConfigFormList[!{index}].fdManagerNames" propertyId="hrRatifyStaffConcernConfigFormList[!{index}].fdManagerIds" showStatus="edit" mulSelect="true" required="true" subject="${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdManagers') }"></xform:address>
						</td>
						<td width="12%" align="center">
							<div onclick='moveUp(this)' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_arrow_up_blue'></div>
							<div onclick='moveDown(this)' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_arrow_down_blue'></div>
							<div data-removeGroup-index="!{index}" onclick='removeGroup(this);' style='cursor:pointer;display:inline-block;width: 16px;height: 16px;' class='lui_icon_s_icon_close_red' ></div>
						</td>
					</tr>
					<c:forEach items="${hrRatifyUserStaffConcernConfigForm.hrRatifyStaffConcernConfigFormList}" var="hrRatifyStaffConcernConfigForm" varStatus="vstatus">
					<tr KMSS_IsContentRow="1" height="20px;">
						<td width="18%">
							<input type="hidden" name="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdId" value="${hrRatifyStaffConcernConfigForm.fdId}"> 
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdEntry') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" ${'true' eq hrRatifyStaffConcernConfigForm.fdEntry ? 'checked' : '' } onclick="changeValue(this);"/>
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdEntry" value="${hrRatifyStaffConcernConfigForm.fdEntry }" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdLeave') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" ${'true' eq hrRatifyStaffConcernConfigForm.fdLeave ? 'checked' : '' } onclick="changeValue(this);"/>
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdLeave" value="${hrRatifyStaffConcernConfigForm.fdLeave }" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdPositive') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" ${'true' eq hrRatifyStaffConcernConfigForm.fdPositive ? 'checked' : '' } onclick="changeValue(this);"/>
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdPositive" value="${hrRatifyStaffConcernConfigForm.fdPositive }" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdTransfer') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" ${'true' eq hrRatifyStaffConcernConfigForm.fdTransfer ? 'checked' : '' } onclick="changeValue(this);"/>
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdTransfer" value="${hrRatifyStaffConcernConfigForm.fdTransfer }" />
							</label>
							${lfn:message('hr-ratify:hrRatifyStaffConcernConfig.fdContract') }
							<label class="weui_switch">
								<span class="weui_switch_bd">
									<input type="checkbox" ${'true' eq hrRatifyStaffConcernConfigForm.fdContract ? 'checked' : '' } onclick="changeValue(this);"/>
									<span></span>
									<small></small>
								</span>
								<input type="hidden" name="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdContract" value="${hrRatifyStaffConcernConfigForm.fdContract }" />
							</label> 
						</td>
						<td width="35%">
							<xform:address propertyName="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdManagerNames" propertyId="hrRatifyStaffConcernConfigFormList[${vstatus.index}].fdManagerIds" showStatus="edit" mulSelect="true" required="true" nameValue="${hrRatifyStaffConcernConfigForm.fdManagerNames }" idValue="${hrRatifyStaffConcernConfigForm.fdManagerIds }"></xform:address>
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
			   		<ui:button  text="${lfn:message('button.save')}"  onclick="saveUserKmCalendarShareGroup(document.hrRatifyUserStaffConcernConfigForm, 'update');" style="width:70px;"/>
			   	</div>
        </html:form>
    </template:replace>
</template:include>