<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/view.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" />
<template:include ref="config.profile.edit" sidebar="no" showTop="no">
	<template:replace name="head">
		<style>
			html{
				overflow-y: clip;
			}
			body{
				overflow: hidden;
			}
			.opr_name{
				margin: 0;
			    padding-bottom: 0;
			    padding-top: 0;
			    line-height: 30px;
			    width:94%;
			    color: #333333;
			    font-size: 14px;
			    height:30px;
			    color: #333333;
		    	border-radius: 2px;
		    	overflow:hidden;
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
			</script>
			<br>
			<div style="margin-top:-5px;overflow: scroll;height: 100%;padding-bottom:63px;box-sizing: border-box;">
			<center>
				<div>
					<table class="tb_simple model-view-panel-table" width=95%>
						<tr>
							<%--多个操作名称 --%>
							<td class="td_normal_title" width=20% style="line-height: 30px">
								<nobr>${lfn:message('sys-modeling-auth:sysModelingAuth.MultipleOperationNames')}</nobr>
							</td>
							
							<%-- <td colspan="3" width=85% class="model-view-panel-table-td">
								${names}
							</td> --%>
							<td colspan="3" width=80% class="model-view-panel-table-td">
								<div class="inputsgl opr_name show_info">
	                            	${names}
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=20% style="line-height: 20px">
								<nobr>${lfn:message('sys-modeling-auth:sysModelingAuth.OperationLogic')}</nobr>
							</td>
							<td colspan="3" width=80% class="model-view-panel-table-td">
							<%--重置 --%>
							<label class="lui-lbpm-radio">
								<input type="radio" checked=""  name = "oprlogic" value="0" style="ime-mode:disabled" subject="${lfn:message('sys-modeling-auth:sysModelingAuth.reset')}"/>
								<span class="radio-label">${lfn:message('sys-modeling-auth:sysModelingAuth.reset')} </span>
							</label>
							<%--追加 --%>
							<label>
								<input type="radio"  value="1" name = "oprlogic" style="ime-mode:disabled" subject="${lfn:message('sys-modeling-auth:sysModelingAuth.append')}" />
								<span class="radio-label">${lfn:message('sys-modeling-auth:sysModelingAuth.append')} </span>
							</label>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=20% style="line-height: 30px">
								${lfn:message('sys-modeling-auth:sysModelingAuth.UserAssignment')} 
							</td>
							<td colspan="3" width=80% class="model-view-panel-table-td">

								<script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
								<script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');</script>
								<script>
									$(document).ready(
										function(){
												Address_QuickSelection("fdOrgElementIds","fdOrgElementNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,[],null,null,"");
										});
								</script>
								<div class='inputselectmul'  style="width: 95%;height:120px;">
									<input name="fdOrgElementIds" xform-name="fdOrgElementNames" value="" type="hidden" />
									<div class="textarea" style="overflow:auto;">
										<textarea style="display:none;" xform-type="newAddressHidden" xform-name="fdOrgElementNames" name="fdOrgElementNames" style="height: 80px;"></textarea>
										<textarea xform-type="newAddress" xform-name="mf_fdOrgElementNames" data-propertyId="fdOrgElementIds" data-propertyName="fdOrgElementNames" data-splitChar=";" data-orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" data-isMulti="true" ></textarea>
									</div>
									<div  onclick="Dialog_Address(true,'fdOrgElementIds','fdOrgElementNames',';',ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,null,null,null,null,null,null,null);" class="orgelement" ></div>
								</div>
								<br>
								<div style="color: #999999;">${lfn:message('sys-modeling-auth:sysModelingAuth.EveryoneCannotOperate')} </div>
							</td>
						</tr> 
					</table>
					
				</div>

			</center>
			</div>
			
			<div class="lui_custom_list_boxs">
				<center>
					<!-- 保存 -->
					  <div class="lui_custom_list_box_content_col_btn"  style="text-align: right;width:85%">
						<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="dialogSave()">${lfn:message('sys-modeling-auth:sysModelingAuth.Save')}</a>
						<a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="dialogClose()">${lfn:message('sys-modeling-auth:sysModelingAuth.Cancel')}</a>
					  </div>
				</center>
			</div>
		
	 	<script type="text/javascript">

			function dialogClose(){
				$dialog.hide();
			}

			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
				window.dialogSave = function(){
						 $.ajax({
							 url: Com_Parameter.ContextPath + "sys/modeling/auth/sys_auth_role/sysModelingSimpleAuthRole.do?method=ajaxSave",
							 dataType : 'json',
							 type : 'post',
							 data:{  fdRoleId:"${ids}",fdOrgElementIds : document.getElementsByName("fdOrgElementIds")[0].value ,oprLogic:$('input:radio:checked').val()},
							 async : false,
							 success : function(data){
								 if(data.errcode==0){
									dialog.success(data.errmsg,null,dialogSaveCallBack());
							 }else{
									dialog.failure(data.errmsg);
								 }
							 }
						 });
					}
				
				function dialogSaveCallBack(data){
					$dialog.config.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
					setTimeout(function(){
						$dialog.hide();			
					},2500);	
				}
			}); 
			
		</script>
	</template:replace>
</template:include>
