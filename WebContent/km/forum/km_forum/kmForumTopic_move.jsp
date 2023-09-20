<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<script type="text/javascript">
		seajs.use(['lui/dialog'],function(dialog) {
			//提交验证新闻类别不可为空
			window.validateForm=function (){
					var fdForumName = document.getElementsByName("fdForumName")[0].value;
						if(fdForumName==null||fdForumName==""){
							dialog.alert("${lfn:message('km-forum:kmForumCategory.chooseCategory')}");
						return false;
						}
						return true;
			   };

			window.submitForum=function(){
				if(!validateForm()){
                       return;
				 }
				var url ="${LUI_ContextPath}/km/forum/km_forum/kmForumTopic.do?method=move";
				var fdId = '${JsParam.fdId}';
				var fdForumId = '${JsParam.fdForumId}';
				var type = '${JsParam.type}'
				var fdTargetId = document.getElementsByName("fdForumId")[0].value;
				var data={fdId:fdId,fdForumId:fdForumId,fdTargetId:fdTargetId,type:type};
				LUI.$.ajax({
					url: url,
					type: 'post',
					dataType: 'text',
					async: false,
					data: data,
					success: function(data, textStatus, xhr) {
						if(data!=""){
							dialog.success('<bean:message key="return.optSuccess" />');
							setTimeout(function (){
								 $dialog.hide(data);
							}, 1500);
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					}
				  });

			   };
			 
			});
				
			Com_IncludeFile("common.js|doclist.js|dialog.js");
			function openCategoryWindow(){
				dialog_Category_Tree(null, 'fdForumId', 'fdForumName', ',', 'kmForumCategoryTeeService&categoryId=!{value}', '<bean:message key="dialog.tree.title" bundle="km-forum"/>', null, null, '${JsParam.fdId}', null, null, '<bean:message key="dialog.title" bundle="km-forum"/>');
			}
			function dialog_Category_Tree(mulSelect, idField, nameField, splitStr, treeBean, treeTitle, treeAutoSelChilde, action, exceptValue, isMulField, notNull, winTitle){
				var dialog = new KMSSDialog(mulSelect, false);
				var node = dialog.CreateTree(treeTitle);
				node.AppendBeanData(treeBean, null, null, null, exceptValue);
				dialog.tree.isAutoSelectChildren = treeAutoSelChilde;
				dialog.winTitle = winTitle==null?treeTitle:winTitle;
				dialog.BindingField(idField, nameField, splitStr);
				dialog.SetAfterShow(action);
				dialog.Show();
			}
		</script>
		<style>body{padding:20px;}</style>
		<html:form action="/km/forum/km_forum/kmForumTopic.do" onsubmit="return validateForm(this);">
			<center>
			  <table class="tb_normal" width=95%>
					<html:hidden property="fdId"/>
				<tr>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="km-forum" key="kmForumTopic.to.title"/>
					</td>
					<td>
						<html:hidden property="fdForumId" />
				        <div class="inputselectsgl"  style="width:90%">
						   <div class="input">
						     	<html:text property="fdForumName" readonly="true" style="width:80%;" value="" onclick="openCategoryWindow();" styleClass="inputsgl" />	
							</div>
							 <div class="selectitem" id="tag_selectItem" onclick="openCategoryWindow();">
							</div>
					    </div>		
						<span class="txtstrong">*</span>	
					</td>
				</tr>
			 </table>
		<div style="padding-top: 20px">
			<ui:button style="width:60px" text="${lfn:message('button.update')}" onclick="submitForum();"></ui:button>
			<ui:button style="padding-left:10px;width:60px" text="${lfn:message('button.close')}" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"></ui:button>
		</div>
			</center>
		 </html:form>
		</template:replace>
</template:include>