<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
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
		<html:form action="/sys/modeling/base/modelingAppFlow.do">
			<html:hidden property="fdId" />
			<input type="hidden" name="moduleModelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppFlow"/>
			<div id='contentContainer' style="overflow: auto;">
				<center>
					<div>
						<table class="tb_simple model-view-panel-table" width=95%>
							<tr>
								<td class="td_normal_title" width=15% style="line-height: 30px">
									${lfn:message('sys-modeling-auth:sysModelingAuth.ProcessName')}
								</td>
								<td width=85% class="model-view-panel-table-td">
									<div class="inputsgl flow_name">
		                            	${modelingAppFlowForm.fdName}
		                            </div>
								</td>
							</tr>
							<!-- 权限 -->
							<tr>
								<td colspan=2>
									<xform:config showStatus="edit">
										<table class="tb_simple model-view-panel-table" width=100%>
											<c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
												<c:param name="formName" value="modelingAppFlowForm" />
												<c:param name="orgType" value="ORG_TYPE_ALL" />
												<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppFlow" />
												<c:param name="modelingFlag" value="true" />
											</c:import>
										</table>
									</xform:config>
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
		</html:form>
	 	<script type="text/javascript">

	 	function dialogClose(){
			$dialog.hide();
		}

		seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
			window.dialogSave = function(){
				Com_Submit(document.modelingAppFlowForm,"updateDefaultAuth");
			}
		}); 
	    
		
		Com_AddEventListener(window,"load",function(){
			var parentDoc = window.parent.document;
			$("#contentContainer").height($(parentDoc).find(".lui_dialog_content").eq(0).height() - 73);
		})

		</script>
	</template:replace>
</template:include>
