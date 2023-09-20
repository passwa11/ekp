<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/view.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" />
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<style>
			body{
				overflow: hidden;
			}
			.opr_type,.opr_name{
				margin: 0;
			    padding-bottom: 0;
			    padding-top: 0;
			    line-height: 30px;
			    width:94%;
			    color: #999999;
			    font-size: 14px;
			    height:30px!important;
		    }
		    .opr_name{
		    	color: #333333;
		    	border-radius: 2px;
		    }
		    .lui_custom_list_boxs{
		    	border-top: 1px solid #d5d5d5;
				position:fixed;
				bottom:0;
				width:100%;
				background-color: #fff;
				z-index:1000;
				height:63px;
		    }
		</style>
	</template:replace>
	<template:replace name="content">
		
			<script>
			Com_IncludeFile("doclist.js");
			Com_IncludeFile('data.js|formula.js|dialog.js|address.js|treeview.js');
			</script>
			<br>
			<div style="margin-top: -5px;overflow: scroll;height: 100%;padding-bottom:63px;box-sizing: border-box;">
			<center>
				<div>
					<table class="tb_simple model-view-panel-table" width=95%>
						<tr>
						<%--操作名称 --%>
							<td class="td_normal_title" width=20% style="line-height: 30px">
								${lfn:message('sys-modeling-auth:sysModelingAuth.OperationName')}
							</td>
							<td colspan="3" width=80% class="model-view-panel-table-td">
	                            <div class="inputsgl opr_name">
	                            	${role.fdOprName}
	                            </div>
							</td>
						</tr>
						<%--操作类型 --%>
						<tr>
							<td class="td_normal_title" width=20% style="line-height: 30px">							
							${lfn:message('sys-modeling-auth:sysModelingAuth.OperationType')}
							</td>
							<td colspan="3" width=80% class="model-view-panel-table-td">
		                            <div class="inputsgl opr_type">
		                            	<sunbor:enumsShow value="${role.fdType}" enumsType="sys_modeling_auth_fd_type"/>
		                            </div>
							</td>
						</tr> 
						<tr>
						<%--用户指派 --%>
							<td class="td_normal_title" width=20%>
								${lfn:message('sys-modeling-auth:sysModelingAuth.UserAssignment')}
							</td>
							<td colspan="3" width=80% class="model-view-panel-table-td">

								<script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
								<script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');</script>
								<script>
									$(document).ready(
										function(){
												Address_QuickSelection("fdOrgElementIds","fdOrgElementNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,${showValues},null,null,"");
										});
								</script>
								<div class='inputselectmul'  style="width: 95%;height:120px;">
									<input name="fdOrgElementIds" xform-name="fdOrgElementNames" value="${orgElementIds}" type="hidden" />
									<div class="textarea" style="overflow:auto;">
										<textarea style="display:none;" xform-type="newAddressHidden" xform-name="fdOrgElementNames" name="fdOrgElementNames" style="height: 60px;">${orgElementNames}</textarea>
										<textarea xform-type="newAddress" xform-name="mf_fdOrgElementNames" data-propertyId="fdOrgElementIds" data-propertyName="fdOrgElementNames" data-splitChar=";" data-orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" data-isMulti="true" ></textarea>
									</div>
									<div  onclick="Dialog_Address(true,'fdOrgElementIds','fdOrgElementNames',';',ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,null,null,null,null,null,null,null);" class="orgelement" ></div>
								</div>
								<br>
								<div style="color: #999999;">（${lfn:message('sys-modeling-auth:sysModelingAuth.EveryoneCannotOperate')}）</div>
							</td>
						</tr> 
						
						<c:if test="${showExtBiz eq 'true'}">
						<tr>
						<%--业务用户指派 --%>
							<td class="td_normal_title" width=20%>
								${lfn:message('sys-modeling-auth:sysModelingAuth.VocationalUserAssignment')}
							</td>
							<td width="80%" colspan="3"  class="model-view-panel-table-td">
								<div class="model-opt-panel-table-base model-panel-table-base">
								<table id="operationTable" class="tb_normal model-panel-child-table" width="100%">
										<tr class="tr_normal_title">
											<td width="85%"  align="center">${lfn:message('sys-modeling-auth:sysModelingAuth.VocationalUserSource')}</td>
											<td width="15%"  align="center">${lfn:message('sys-modeling-auth:sysModelingAuth.Operation')}</td>
										</tr>
										<%-- 基准行，KMSS_IsReferRow = 1 --%>
										<tr style="display:none;" KMSS_IsReferRow="1">
											<td class="model-panel-child-table-title">
											  <input type="hidden" name="listExpression[!{index}]">
											  <input type="text" name="listExpressionText[!{index}]" class="inputsgl" style="width:85%" readonly>
												<a href="#" onclick="selectBizElements('listExpression[!{index}]','listExpressionText[!{index}]')" class="highLight listOperationName label">${lfn:message('sys-modeling-auth:sysModelingAuth.Choice')}</a>
											</td>
											<td class="model-panel-child-table-item"  align="center">
												<p class="delbtn" alt="del"><a href="javascript:void(0);" onclick="DocList_DeleteRow();">${lfn:message('sys-modeling-auth:sysModelingAuth.Delete')}</a></p>
											</td>
										</tr>
										<%-- 内容行 --%>
										<c:forEach items="${fdExtendJson.expressions}" var="expression" varStatus="vstatus">
										<tr KMSS_IsContentRow="1">
											<td class="model-panel-child-table-title">
												  <input type="hidden" name="listExpression[${vstatus.index}]" value="<c:out value="${expression.value }"></c:out>">
												  <input type="text" name="listExpressionText[${vstatus.index}]" class="inputsgl" style="width:85%" readonly value="<c:out value="${expression.text }"></c:out>">
												<a href="#" onclick="selectBizElements('listExpression[${vstatus.index}]','listExpressionText[${vstatus.index}]')" class="highLight listOperationName label">${lfn:message('sys-modeling-auth:sysModelingAuth.Choice')}</a>
											</td>
											<td class="model-panel-child-table-item"  align="center">
												<p class="delbtn" alt="del"><a href="javascript:void(0);" onclick="DocList_DeleteRow();">${lfn:message('sys-modeling-auth:sysModelingAuth.Delete')}</a></p>
											</td>
										</tr>
										</c:forEach>
									</table>
								</div>
								<div class="model-data-create" onclick="DocList_AddRow('operationTable');">
									<div>${lfn:message('sys-modeling-auth:sysModelingAuth.Add')}</div>
								</div>
							</td>
						</tr>
							<c:if test="${showVocationalUserType eq 'true'}">
							<tr>
								<td class="td_normal_title" width=20%></td>
								<td width="80%" colspan="3"  class="model-view-panel-table-td">${lfn:message('sys-modeling-auth:sysModelingAuth.VocationalUserType.Title')}</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=20%></td>
								<td width="80%" colspan="3"  class="model-view-panel-table-td">
									<label>
										<input type="radio" value="1" name="vocationalUserType"  <c:if test="${fdExtendJson.type eq '1'}">checked</c:if>/>
										${lfn:message('sys-modeling-auth:sysModelingAuth.VocationalUserType.1')}
									</label>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=20%></td>
								<td width="80%" colspan="3"  class="model-view-panel-table-td">
									<label>
										<input type="radio" value="0" name="vocationalUserType" <c:if test="${fdExtendJson.type eq '0' || null == fdExtendJson.type}">checked</c:if>/>
										${lfn:message('sys-modeling-auth:sysModelingAuth.VocationalUserType.0')}
									</label>
								</td>
							</tr>
							</c:if>
						</c:if>

					</table>
					
				</div>

			</center>
			</div>
			<div class="lui_custom_list_boxs">
				<center>
					<!-- 保存 -->
					  <div class="lui_custom_list_box_content_col_btn"  style="text-align: right;width: 85%">
						<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="dialogSave()">${lfn:message('sys-modeling-auth:sysModelingAuth.Save')}</a>
						<a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="dialogClose()">${lfn:message('sys-modeling-auth:sysModelingAuth.Cancel')}</a>
					  </div>
				</center>
			</div>
		
	 	<script type="text/javascript">
			DocList_Info.push('operationTable');

			function dialogClose(){
				$dialog.hide();
			}

			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
				window.dialogSave = function(){
						var fdExtendJson = {};

						<c:if test="${showExtBiz eq 'true'}">
						var tbInfo = DocList_TableInfo["operationTable"];
						var len = tbInfo.lastIndex-1;
						var expressions = [];
						for(var i=0;i<len;i++){
							var expression = {value:document.getElementsByName("listExpression["+i+"]")[0].value,
											text:document.getElementsByName("listExpressionText["+i+"]")[0].value};
							expressions.push(expression);
						}
						fdExtendJson["expressions"] = expressions;

						var $vocationalUserType = document.getElementsByName("vocationalUserType");
						for (var i = 0; i < $vocationalUserType.length; i++) {
							if($vocationalUserType[i].checked){
								fdExtendJson["type"] = $vocationalUserType[i].value;
							 }
						}
						</c:if>

						var fdExtendStr = JSON.stringify(fdExtendJson, null, 4);

						 $.ajax({
							 url: Com_Parameter.ContextPath + "sys/modeling/auth/sys_auth_role/sysModelingSimpleAuthRole.do?method=ajaxSave&fdRoleId=${role.fdId}",
							 dataType : 'json',
							 type : 'post',
							 data:{  fdOrgElementIds : document.getElementsByName("fdOrgElementIds")[0].value,fdExtendJson:fdExtendStr ,oprLogic:"0"},
							 async : false,
							 success : function(data){
								 if(data.errcode==0){
									dialog.success(data.errmsg,null,dialogSaveSucc());
									setTimeout(function(){
										dialogClose();
									},2500);
								 }else{
									dialog.failure(data.errmsg);
								 }
							 }
						 });
						 
					}
				
				function dialogSaveSucc(){
					$dialog.config.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
				}
			}); 
			
		function selectBizElements(value,text){
			var fields = getXFormFieldList("${xformId}");
			var orgFields = [];
			for(var i=0;i<fields.length;i++){
				 if(fields[i].type.indexOf("com.landray.kmss.sys.organization.model.SysOrg")==-1){
					 if(fields[i].businessType=="placeholder"){
						orgFields.push(fields[i]);
					}
					continue;
				 }
				orgFields.push(fields[i]);
			}

            Formula_Dialog(value,text, orgFields, "Object");
		}

       function getXFormFieldList(xformId) {
            return XForm_getXFormDesignerObj(xformId);
        }

       function  XForm_getXFormDesignerObj(xformId) {
            var obj = [];

            var sysObj = _XForm_GetSysDictObj("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
		   if (sysObj != null) {
			   //通过xformId获取appmodel 从而获取有无流程标识
			   var getIsFlowUrl = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingBehavior.do?method=findModelIsFlowByXformId&xformId=" + xformId;
			   $.ajax({
				   url: getIsFlowUrl,
				   type: "get",
				   async: false,
				   success: function (data) {
					   if(!data){
						   for (var i = 0; i <sysObj.length ; i++) {
							   var sysItem  = sysObj[i];
							   if(sysItem && sysItem.name=="fdProcessEndTime"){
								   //无流程表单去掉流程结束时间
								   sysObj.splice(i,1);
							   }
						   }
					   }else{
						   //#166825
						   var fdHandlerObj = [];
						   fdHandlerObj.name="LbpmExpecterLog_fdHandler";
						   fdHandlerObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog_fdHandler')}";
						   fdHandlerObj.type="com.landray.kmss.sys.organization.model.SysOrgPerson";
						   sysObj.push(fdHandlerObj);
						   var fdNodeObj = [];
						   fdNodeObj.name="LbpmExpecterLog_fdNode";
						   fdNodeObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog.fdNode')}";
						   fdNodeObj.type="String";
						   sysObj.push(fdNodeObj);
					   }
				   }
			   });
		   }
            var extObj = null;

            extObj = _XForm_GetTempExtDictObj(xformId);

            return XForm_Util_UnitArray(obj, sysObj, extObj);
        }

        // 查询modelName的属性信息
        function _XForm_GetSysDictObj(modelName) {
            return Formula_GetVarInfoByModelName(modelName);
        }

        // 查找自定义表单的数据字典
        function _XForm_GetTempExtDictObj(tempId) {
            return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId=" + tempId).GetHashMapArray();
        }

        function XForm_Util_UnitArray(array, sysArray, extArray) {
            array = array.concat(sysArray);
            if (extArray != null) {
                array = array.concat(extArray);
            }
            return array;
        }

		</script>
	</template:replace>
</template:include>
