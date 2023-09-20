<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgRelation"%>
<%@page import="com.landray.kmss.sys.relation.web.RelationEntry"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
	<script type="text/javascript" >
		Com_IncludeFile("validator.jsp|validation.jsp|validation.js|plugin.js|jquery.js", null, "js");
		Com_IncludeFile("rela_text.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
	</script>
	<script type="text/javascript">
		var _param={"tempId":'<%=com.landray.kmss.util.IDGenerator.generateID()%>',
				"varName":"rela_opt",
				'fdModuleName.isNull':"<bean:message bundle="sys-relation" key="sysRelationMain.fdModuleName.isNull"/>",
				'preview.title':'<bean:message key="sys-relation:button.setting.preview"/>',
				'button.cancel':'<bean:message key="button.close"/>'
			};
		
		new RelationTextSetting(_param);
	</script>
	<div class="rela_config_subset" style="height:248px;overflow:auto;">
		<table class="tb_simple" style="width:100%">
			<tr><td width="20%" style="text-align:center"><bean:message key="sys-relation:sysRelationEntry.text.subject"/>
			</td><td>
				<xform:text property="fdModuleName" required="true" validators="maxLength(200)" value="${sysRelationMain.relation.fdModelName}" 
					showStatus="edit" subject="${lfn:message('sys-relation:sysRelationEntry.text.subject')}" style="width:75%"></xform:text>
			</td></tr>
			<tr><td width="20%" style="text-align:center"><bean:message key="sys-relation:sysRelationEntry.text.des"/>
			</td><td>
					<textarea style="width:75%;height:150px"  name="fdDesContent" class="rela_des_textarea"></textarea>
			</td></tr>
			<tr><td colspan="5" align="center" class="rela_scope_button">
					<ui:button text="${lfn:message('button.ok')}" id="rela_config_save"></ui:button>
					&nbsp;&nbsp;&nbsp;
					<ui:button styleClass="lui_toolbar_btn_gray"  text="${lfn:message('button.cancel')}" id="rela_config_close"></ui:button>
					&nbsp;&nbsp;&nbsp;
					
				</td></tr>
		</table>
	</div>
	
	</template:replace>
</template:include>