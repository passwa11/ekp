<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.base.service.spring.SysFormTemplateControlUtils"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="auto">
<template:replace name="title">
		<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.info" />
	</template:replace>
	<template:replace name="head">
		<style>
			.com_qrcode {
				display:none!important;
			}
		</style>
		<script>
			<%@ include file="/sys/xform/designer/lang.jsp" %>
		</script>
		<script>
			Com_IncludeFile("jquery.js");
		</script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/dtree/dtree.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/builder.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/panel.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/control.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/dash.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/mobile/js/config_mobile.js""></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/mobile/js/control_ext.js""></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/config_ext.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/config.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/attachment.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/jspcontrol.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/buttons.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/toolbar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/effect.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/treepanel.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/attrpanel.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/shortcuts.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/cache.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/rightmenu.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/relation/relation.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/hidden.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/right.js"></script>
		<link href="<%=request.getContextPath() %>/sys/xform/designer/mobile/css/mobileDesigner.css" type="text/css" rel="stylesheet" />
		
		<%
			pageContext.setAttribute("jsFiles", SysFormTemplateControlUtils.getAllControlJsFiles(request.getParameter("fdModelName")));
		%>
		<c:forEach items="${jsFiles}" var="jsFile">
		<script type="text/javascript" src="<c:url value="${jsFile}" />"></script>
		</c:forEach>
		<%
			// 单独的js嵌入
			pageContext.setAttribute("jsFiles", SysFormTemplateControlUtils.getDesignJsFiltes());
		%>
		<c:forEach items="${jsFiles}" var="jsFile">
		<script type="text/javascript" src="<c:url value="${jsFile}" />"></script>
		</c:forEach>
		<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/designer.js"></script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="1" var-navwidth="90%">
			 <ui:button order="1" onclick="top.close();" text="${ lfn:message('button.close') }"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle">
			<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.info" />
		</p>
		<center>
			<table class="tb_normal" width=98%>
				<html:hidden name="sysFormModifiedLogForm" property="fdId"/>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.fdTemplateName" />
					</td>
					<td width=85% colspan=3>
						<a href="javascript:void(0);" style="color: #47b5ea;border-bottom:1px solid #47b5ea;" src="${ sysFormModifiedLogForm.fdUrl }" onclick="openTemplate(this);">
							<bean:write name="sysFormModifiedLogForm" property="fdName"/>
						</a>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.fdModule" />
					</td>
					<td width=35%>
						<bean:write name="sysFormModifiedLogForm" property="fdModuleName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.fdFormVersion" />
					</td>
					<td width=35%>
						v<bean:write name="sysFormModifiedLogForm" property="fdFormVersion"/>
						<c:if test="${'true' eq sysFormModifiedLogForm.isLatestVersion}">
							(当前版本)
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.docCreator" />
					</td>
					<td width=35%>
						<bean:write name="sysFormModifiedLogForm" property="docCreatorName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.docCreateTime" />
					</td>
					<td width=35%>
						<bean:write name="sysFormModifiedLogForm" property="docCreateTime"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.fdAlterorDept" />
					</td>
					<td width=35%>
						<bean:write name="sysFormModifiedLogForm" property="fdDeptName"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.fdIp" />
					</td>
					<td width=35%>
						<bean:write name="sysFormModifiedLogForm" property="fdIp"/>
					</td>
				</tr>
			</table>
			<div style="text-align: left;margin-left: 10px;margin-top:20px;margin-bottom:10px;">
				<span>
					<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.fdLogDetail" />
				</span>
			</div>
			<table class="tb_normal"  width=98% style="text-align:center;">
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.serial" />
					</td>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.modifyControl" />
					</td>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.modifyControlType" />
					</td>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.modifyControlId" />
					</td>
					<td class="td_normal_title" width="35%">
						<bean:message bundle="sys-xform-base" key="sysFormModifiedLog.modifyContent" />
						<div class="lui_prompt_tooltip" style="margin-left:0px;">
					    	<label class="lui_prompt_tooltip_drop">
					    		<img src="${KMSS_Parameter_ContextPath}sys/xform/designer/style/img/promptControl.png">
			    			</label>
					    	<div class="lui_dropdown_tooltip_menu" style="display: none;"><bean:message bundle="sys-xform-base" key="sysFormModifiedLog.modifyContentTip"/></div>
					    </div>
					</td>
				</tr>
				<c:forEach items="${sysFormModifiedLogForm.listField_Form}" var="listField_Form"
                                       varStatus="vstatus">
	                <tr>
	                	<td>${ vstatus.index + 1 }</td>
						<td class="model-change-list-line">
					        <c:choose>
								<c:when test="${empty listField_Form.fdFieldLabel}">
									<i>该控件无名称配置</i>
								</c:when>
								<c:otherwise>
									<p>${listField_Form.fdFieldLabel}</p>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<input
					            name="fdBussinessType" type="hidden"
					            value="<c:out value='${listField_Form.fdBusinessType}'/>"/>
	                		<p class="controlType"><c:out value='${listField_Form.fdBusinessType}'/></p>
	                	</td>
						<td>
	                		<p><c:out value='${listField_Form.fdFieldId}'/></p>
	                	</td>
						<td style="text-align:left;" mapping-log-mark="fdChangeLog">
						     <input class="model-change-list-desc-hidden"
						            type="hidden"
						            value="<c:out value='${listField_Form.fdChangeLog}'/>"/>
						     <c:if test="${'2'.equals(listField_Form.fdModifiedType)}">
						         <p><bean:message bundle="sys-xform-base" key="sysFormModifiedLog.controlTypeBydelete" /></p>
						     </c:if>
						     <c:if test="${'0'.equals(listField_Form.fdModifiedType)}">
						         <p><bean:message bundle="sys-xform-base" key="sysFormModifiedLog.addControl" /></p>
						     </c:if>
						 </td>
	                  </tr>
	            </c:forEach>
			</table>
		</center>
		<script>
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
			
			seajs.use(["lui/jquery", "sys/ui/js/dialog", "lui/topic", "sys/xform/base/resource/js/logRender"], function ($, dialog, topic, logRender) {
	               function init() {
	                   var cfg = {
	                       formlogId:"${sysFormModifiedLogForm.fdId}",
	                   };
	                   window.logRenderInst = new logRender.LogRender(cfg);
	                   logRenderInst.startup();
	               }
	               init();
	               $(function(){
	            	   var $tip = $(".lui_prompt_tooltip");
	            	   var $menu = $(".lui_dropdown_tooltip_menu");
					   $tip.mouseover(function(){
						   $menu.show();
					   });
					   $tip.mouseout(function(){
						   $menu.hide();
					   });
	               });
	               
	           });
		</script>
		
	</template:replace>
</template:include>