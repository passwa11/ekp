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
			.flow_name{
				margin: 0;
			    padding-bottom: 0;
			    padding-top: 0;
			    line-height: 25px;
			    width:94%;
			    color: #333333;
			    font-size: 14px;
			    height:30px!important;
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
		<form name="f1">
			<script>
			Com_IncludeFile("doclist.js");
			</script>
			<br>
			<div style="overflow: scroll;height: 100%;padding-bottom:63px;box-sizing: border-box;">
			<center>
				<div>
					<table class="tb_simple model-view-panel-table" width=95%>
						<tr>
							<td class="td_normal_title" width=15% style="line-height: 30px">
							<%--流程名称 --%>
								${lfn:message('sys-modeling-auth:sysModelingAuth.ProcessName')}
							</td>
							<%-- <td colspan="3" width=85% class="model-view-panel-table-td">
								${appFlow.fdName}
							</td> --%>
							<td colspan="3" width=85% class="model-view-panel-table-td">
								<div class="inputsgl flow_name">
	                            	${appFlow.fdName}
	                            </div>
							</td>
						</tr>
						<tr style="display:none;">
							<td class="td_normal_title" width=15%>
							<%--可维护者 --%>
								${lfn:message('sys-modeling-auth:sysModelingAuth.Maintainer')}
							</td>
							<td colspan="3" width=85% class="model-view-panel-table-td">

								<script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
								<script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');</script>
								<script>
									$(document).ready(
										function(){
												Address_QuickSelection("authEditorIds","authEditorNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,${showAuthEditorValues},null,null,"");
										});
								</script>
								<div class='inputselectmul'  style="width:95%;height:120px;">
									<input name="authEditorIds" xform-name="authEditorNames" value="${authEditorIds}" type="hidden" />
									<div class="textarea" style="overflow:auto;">
										<textarea style="display:none;" xform-type="newAddressHidden" xform-name="authEditorNames" name="authEditorNames" style="height: 80px;">${authEditorNames}</textarea>
										<textarea xform-type="newAddress" xform-name="mf_authEditorNames" data-propertyId="authEditorIds" data-propertyName="authEditorNames" data-splitChar=";" data-orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" data-isMulti="true" ></textarea>
									</div>
									<div  onclick="Dialog_Address(true,'authEditorIds','authEditorNames',';',ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,null,null,null,null,null,null,null);" class="orgelement" ></div>
								</div>
								<br>
								<div style="color: #999999;">${lfn:message('sys-modeling-auth:sysModelingAuth.MaintainerinStructions')}</div>
							</td>
						</tr> 

						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-auth:sysModelingAuth.Users')}
							</td>
							<td colspan="3" width=85% class="model-view-panel-table-td">
							<input type="checkbox" name="authNotReaderFlag" value="false" ${authNotReaderFlag} onclick="Cate_CheckNotReaderFlag(this);">${lfn:message('sys-modeling-auth:sysModelingAuth.EveryOneUseNot')}
								<script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
								<script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');</script>
								<script>
									$(document).ready(
										function(){
												Address_QuickSelection("authReaderIds","authReaderNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,${showAuthReaderValues},null,null,"");
										});
								</script>
								<div id="Cate_AllUserId">
								<div class='inputselectmul'  style="width:95%;height:120px;" >
									<input name="authReaderIds" xform-name="authReaderNames" value="${authReaderIds}" type="hidden" />
									<div class="textarea" style="overflow:auto;">
										<textarea style="display:none;" xform-type="newAddressHidden" xform-name="authReaderNames" name="authReaderNames" style="height: 80px;">${authReaderNames}</textarea>
										<textarea xform-type="newAddress" xform-name="mf_authReaderNames" data-propertyId="authReaderIds" data-propertyName="authReaderNames" data-splitChar=";" data-orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" data-isMulti="true" ></textarea>
									</div>
									<div  onclick="Dialog_Address(true,'authReaderIds','authReaderNames',';',ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,null,null,null,null,null,null,null);" class="orgelement" ></div>
								</div>
								</div>
								<div id="Cate_AllUserNote">
								<div style="color: #999999;">${lfn:message('sys-modeling-auth:sysModelingAuth.UsersStructions')}</div>
								</div>
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
		</form>
	 	<script type="text/javascript">

			function dialogClose(){
				$dialog.hide();
			}

			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
				window.dialogSave = function(){
						var authEditorIds = document.getElementsByName("authEditorIds")[0].value;
						var authReaderIds = document.getElementsByName("authReaderIds")[0].value;
						var authNotReaderFlag = document.getElementsByName("authNotReaderFlag")[0].checked;
						 $.ajax({
							 url: Com_Parameter.ContextPath + "sys/modeling/auth/flow_auth/sysModelingFlowAuth.do?method=ajaxSave",
							 dataType : 'json',
							 type : 'post',
							 data:{  fdAppFlowId:"${param.fdAppFlowId}",authEditorIds : authEditorIds,authReaderIds : authReaderIds,authNotReaderFlag:authNotReaderFlag },
							 async : false,
							 success : function(data){
								 if(data.errcode==0){
									 dialog.success(data.errmsg);
									 setTimeout(function(){
										 dialogClose();
									 },2500);
							 	 }else{
									 dialog.failure(data.errmsg);
								 }
							 }
						 });
					}
			}); 
			
			function Cate_CheckNotReaderFlag(el){
				document.getElementById("Cate_AllUserId").style.display=el.checked?"none":"";
				document.getElementById("Cate_AllUserNote").style.display=el.checked?"none":"";
				el.value=el.checked;
			}
			
			function Cate_Win_Onload(){
				Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
			}

			Com_AddEventListener(window, "load", Cate_Win_Onload);

		</script>
	</template:replace>
</template:include>
