<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="auto">
<template:replace name="title">
		<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.info" />
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/sys/lbpmservice/changelog/resource/css/lbpmTemplateChangelog.css">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="1" var-navwidth="90%">
			 <ui:button order="1" onclick="top.close();" text="${ lfn:message('button.close') }"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle">
			<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.info" />
		</p>
		<center>
			<table class="tb_normal" width=98%>
				<html:hidden name="lbpmTemplateChangeLogForm" property="fdId" />
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.fdTemplateName" />
					</td>
					<td width=85% colspan=3>
						<a href="javascript:void(0);" style="color: #47b5ea;border-bottom:1px solid #47b5ea;" src="${ lbpmTemplateChangeLogForm.fdUrl }" onclick="openTemplate(this);">
							<bean:write name="lbpmTemplateChangeLogForm" property="fdName"/>
						</a>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.fdModule" />
					</td>
					<td width=35%>
						<bean:write name="lbpmTemplateChangeLogForm" property="fdModuleName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.fdVersion" />
					</td>
					<td width=35%>
						v<bean:write name="lbpmTemplateChangeLogForm" property="fdVersion"/>
						<c:if test="${'true' eq lbpmTemplateChangeLogForm.isLatestVersion}">
							(当前版本)
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.docCreator" />
					</td>
					<td width=35%>
						<bean:write name="lbpmTemplateChangeLogForm" property="docCreatorName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.docCreateTime" />
					</td>
					<td width=35%>
						<bean:write name="lbpmTemplateChangeLogForm" property="docCreateTime"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.fdAlterorDept" />
					</td>
					<td width=35%>
						<bean:write name="lbpmTemplateChangeLogForm" property="fdDeptName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.fdIp" />
					</td>
					<td width=35%>
						<bean:write name="lbpmTemplateChangeLogForm" property="fdIp"/>
					</td>
				</tr>
			</table>
			<!-- 流程详情 -->
			<div id="processChangeWrap" style="display:none;">
				<div style="text-align: left;margin-left: 10px;margin-top:20px;margin-bottom:10px;">
					<span>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.Template.fdLogDetail" />
					</span>
				</div>
				<table id="processChangeTb" class="tb_normal"  width=98% style="text-align:center;">
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.serial" />
						</td>
						<td class="td_normal_title" width="15%">
							名称
						</td>
						<td class="td_normal_title" width="75%">
							<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.changeContent" />
						</td>
					</tr>
					<c:forEach items="${lbpmTemplateChangeLogForm.changeLogDetails}" var="changeLogDetail"
	                                       varStatus="vstatus">
		               <c:if test="${'process' eq changeLogDetail.fdNodeId }">
		               		<input name="process_change_desc" type="hidden"
							            value="<c:out value='${changeLogDetail.fdChangeLogDesc}'/>"/>
		               </c:if>
		            </c:forEach>
				</table>
			</div>
			<!-- 节点详情 -->
			<div id="nodeChangeWrap" style="display:none;">
				<div style="text-align: left;margin-left: 10px;margin-top:20px;margin-bottom:10px;">
					<span>
						<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.Node.fdLogDetail" />
					</span>
				</div>
				<table class="tb_normal" id="nodeChangeTb"  width=98% style="text-align:center;">
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.serial" />
						</td>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.changeNodeName" />
						</td>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.changeNodeId" />
						</td>
						<td class="td_normal_title" width="55%">
							<bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.changeContent" />
							<div class="lui_prompt_tooltip" style="margin-left:0px;">
						    	<label class="lui_prompt_tooltip_drop">
						    		<img src="${KMSS_Parameter_ContextPath}sys/lbpmservice/changelog/resource/image/promptControl.png">
				    			</label>
						    	<div class="lui_dropdown_tooltip_menu" style="display: none;"><bean:message bundle="sys-lbpmservice-changelog" key="lbpmTemplateChangeLog.tip"/></div>
						    </div>
						</td>
					</tr>
					<c:set var="index" value="0" />
					<c:forEach items="${lbpmTemplateChangeLogForm.changeLogDetails}" var="changeLogDetail"
	                                       varStatus="vstatus">
		                <c:if test="${'process' ne changeLogDetail.fdNodeId }">
			                <c:set var="index" value="${index + 1}" />
			                <tr change-content="true">
			                	<td>${index}</td>
								<td>
							        <p><c:out value='${changeLogDetail.fdNodeName}'/></p>
								</td>
								<td>
			                		<p><c:out value='${changeLogDetail.fdNodeId}'/></p>
			                	</td>
								<td mark="fdChangeLog" style="text-align:left;">
								    <input class="change-desc-hidden" type="hidden"
								            value="<c:out value='${changeLogDetail.fdChangeLogDesc}'/>"/>
								 </td>
			                  </tr>
		                  </c:if>
		            </c:forEach>
				</table>
			</div>
		</center>
		<script>
		    window.onload = function(){ 
		　　　       $("#top").css("display","none");
		　     　}
			function openTemplate(src) {
				var src = $(src).attr("src");
				console.log(src);
				if (src) {
					if (src.startsWith("/")) {
						src = src.replace("/","");
					}
					Com_OpenWindow(Com_Parameter.ContextPath + src);
				}
			}
			
			seajs.use(["lui/jquery"],function($){
				LUI.ready(function(){
				   var $tip = $(".lui_prompt_tooltip");
            	   var $menu = $(".lui_dropdown_tooltip_menu");
				   $tip.mouseover(function(){
					   $menu.show();
				   });
				   $tip.mouseout(function(){
					   $menu.hide();
				   });
				   if ($("#nodeChangeTb").find("tr[change-content]").length > 0) {
						$("#nodeChangeWrap").show();
						$("[mark='fdChangeLog']").each(function (idx, ele) {
			                var changeLog = $(ele).find(".change-desc-hidden").val();
			                var obj = JSON.parse(changeLog);
			                for (var i = 0; i < obj.length; i++) {
			                	 if (obj[i].desc) {
			                		 $(ele).append("<p>" + Com_HtmlEscape(obj[i].desc) + "</p>");
			                	 }
			                }
			            });
				   }
					//流程
					var processChangeLogs = $("[name='process_change_desc']").val();
					if (!processChangeLogs) return;
					processChangeLogs = JSON.parse(processChangeLogs);
					var groupChangeLog = {};
					for (var i = 0; i < processChangeLogs.length; i++) {
						var processChange = processChangeLogs[i];
						var group = processChange.group;
						var desc = processChange.desc;
						if (!groupChangeLog[group]) {
							groupChangeLog[group] = [];
						}
						groupChangeLog[group].push(processChange);
					}
					console.log(groupChangeLog);
					var html = [];
					var index = 1;
					for (var group in groupChangeLog) {
						var groupDescs = groupChangeLog[group];
						html.push("<tr>");
						html.push("<td>" + index++ + "</td>");
						html.push("<td>" + group + "</td>");
						html.push("<td style='text-align:left;'>");
						for (var i = 0; i < groupDescs.length; i++) {
							html.push("<p>" + Com_HtmlEscape(groupDescs[i].desc) + "</p>");
						}
						html.push("</td>");
						html.push("</tr>");
					}
					$("#processChangeTb").append(html.join(""));
					$("#processChangeWrap").show();
				});
			});
		</script>
	</template:replace>
</template:include>