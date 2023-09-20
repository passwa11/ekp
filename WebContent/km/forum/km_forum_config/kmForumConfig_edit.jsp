<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="head">
		<script type="text/javascript">
			seajs.use(['sys/ui/js/dialog'], function(dialog) {
				window.dialog = dialog;
			});
		</script>
		<script type="text/javascript">
		   function submitForm(){
			   var hotReplyCount = document.getElementsByName("hotReplyCount")[0].value;
			   var replyTimeInterval = document.getElementsByName("replyTimeInterval")[0].value;			   
			   if(hotReplyCount==null||hotReplyCount==""){
				    dialog.alert('<bean:message bundle="km-forum" key="kmForumConfig.tips.notNull"/>');
				    return;
				   }
			   var re = /^[0-9]*[1-9][0-9]*$/ ; 
			   if(!re.test(hotReplyCount)){
				   dialog.alert('<bean:message bundle="km-forum" key="kmForumConfig.tips.mum"/>');
				   return;
			   }
			   if(!re.test(replyTimeInterval)){
				   if(replyTimeInterval != "0"){
					   dialog.alert('<bean:message bundle="km-forum" key="kmForumConfig.tips.replyTimeInterval"/>');
					   return;
				   }
			   }
			   Com_Submit(document.kmForumConfigForm, 'update');
			}

		   seajs.use(['lui/dialog', 'lui/jquery', 'lui/util/str'], function(dialog, $, strutil) {
	           window.selectCategory = function() {
				     dialog.simpleCategoryForNewFile({modelName:"com.landray.kmss.km.forum.model.KmForumCategory",
	                    									url:"/km/forum/km_forum_cate/simple-category.jsp",
	                    									action:function(rtn){
				                 if(rtn == null || rtn==false) {
				                     return;
				                 }
				                 $("input[name='webServiceDefForumId']").val(rtn.id);
				                 $("input[name='webServiceDefForumName']").val(decodeURIComponent(rtn.name)).attr('title',decodeURIComponent(rtn.name));
						}});
				};
		   });
		</script>
		<script type="text/javascript" language="Javascript1.1"> 
			<!-- Begin 
			 function validateKmForumConfigForm() {                                                                   
				var level = document.getElementsByName("level")[0];
				if(level.value=="")return true;
				var ls = level.value.split("\n");
				for(var i=0;i<ls.length;i++){
					var ks = ls[i].split(":");
					if(parseInt(ks[1])!=ks[1]|| ks[0]==""){
						dialog.alert("<bean:message  bundle="km-forum" key="kmForumConfig.level"/>:'"+ls[i]+"'<bean:message  bundle="km-forum" key="kmForum.error"/>");
						level.focus();
						return false;
					}
					if(-2147483648>parseInt(ks[1])||parseInt(ks[1])>2147483647){
						dialog.alert("<bean:message  bundle="km-forum" key="kmForumConfig.level"/>:'"+ls[i]+"'<bean:message  bundle="km-forum" key="kmForum.number.error"/>");
						level.focus();
						return false;
					}
				}
				return true;
			 } 
			//End --> 
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/km/forum/km_forum_config/kmForumConfig.do" onsubmit="return validateKmForumConfigForm(this);">
			<div style="margin-top:25px">
				<p class="configtitle">
				<bean:message bundle="km-forum" key="menu.kmForum.config"/>
				</p>
			<center>
			<table class="tb_normal" width=90%>
			
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.anonymous"/>
					</td><td colspan=3>
						<ui:switch property="anonymous" checked="${kmForumConfigForm.anonymous}" enabledText="${ lfn:message('km-forum:kmForumConfig.anonymous.yes') }" disabledText="${ lfn:message('km-forum:kmForumConfig.anonymous.no') }"></ui:switch>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" colspan="4">
						<bean:message  bundle="km-forum" key="kmForumConfig.anonymous.msg"/>
					</td>
				</tr>
			    <tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.hotReplyCount"/>
					</td><td colspan=3>
						 	<bean:message  bundle="km-forum" key="kmForumConfig.tips"/><xform:text property="hotReplyCount" style="width:8.5%;"/>
				            <bean:message  bundle="km-forum" key="kmForumConfig.hot"/>
					</td>
				</tr> 
			
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.replyTimeInterval"/>
					</td><td colspan=3>
						 <xform:text property="replyTimeInterval" style="width:8.5%;"/>
				         <bean:message  bundle="km-forum" key="kmForumConfig.seconds"/>
				         (<bean:message  bundle="km-forum" key="kmForumConfig.replyTimeInterval.tips"/>)
					</td>
				</tr>
				<!--<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.canModifyRight"/>
					</td><td colspan=3>
						<sunbor:enums property="canModifyRight"  enumsType="forumConfig_canModifyRight_yesno" elementType="radio" />
					</td>
				</tr>
			
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.canModifyNickname"/>
					</td><td colspan=3>
						<sunbor:enums property="canModifyNickname" enumsType="forumConfig_canModifyNickname_yesno" elementType="radio" />
					</td>
				</tr>
			
				--><tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.level"/>
					</td><td colspan=3>
						<html:textarea property="level" style="width:95%;" rows="5"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" colspan="4">
						<bean:message  bundle="km-forum" key="kmForumConfig.level.msg"/>
					</td>
				</tr>
				<%--敏感词检测配置--%>
				<%-- <tr>
					<td class="td_normal_title" width=15%>
					   <bean:message  bundle="km-forum" key="kmForumConfig.word"/>
					</td><td colspan=3>
						<ui:switch property="isWordCheck" checked="${kmForumConfigForm.isWordCheck}" enabledText="${ lfn:message('km-forum:kmForumConfig.word.yes') }" disabledText="${ lfn:message('km-forum:kmForumConfig.word.no') }"></ui:switch>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="km-forum" key="kmForumConfig.word.set"/>
					</td><td colspan=3>
						<html:textarea property="words" style="width:95%;height:70px"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" colspan="4">
						<bean:message  bundle="km-forum" key="kmForumConfig.word.tips"/>
					</td>
				</tr> --%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="kmForumConfig.webservice.title" bundle="km-forum"/>
					</td><td colspan=3>
						<html:hidden property="webServiceDefForumId" />
						<xform:text showStatus="readOnly" property="webServiceDefForumName" style="width:20%;"/>
						<a href="#" onclick="selectCategory();"><bean:message key="kmForumConfig.webservice.select" bundle="km-forum"/></a>
					</td>
				</tr>
			</table>
			<html:hidden property="method_GET"/>
			<div style="margin-bottom: 10px;margin-top:25px">
				   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="submitForm();" order="1" ></ui:button>
			</div>
			</center>
		</div>
		</html:form>
		
		<%@ include file="/km/forum/km_forum_ui/kmForumPost_edit_script.jsp"%>
	</template:replace>
</template:include>	

<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>
