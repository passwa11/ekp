<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.landray.kmss.framework.util.PluginConfigLocationsUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	JSONObject var = JSONObject.fromObject(request.getParameter("var"));
	pageContext.setAttribute("luivar", var);
	pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
	pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
%>
<script>
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		var showType = "checkbox";
		if(showType=="checkbox"){
			var tempVal = [];
			$("input[name='${luivarid}']:checked").each(function(){
				tempVal.push($(this).val());
			});
			return tempVal.join(";");
		}
	},
	"setter":function(val){
		var showType = "checkbox";
		if(showType=="checkbox"){
			$("input[name='${luivarid}']").each(function(){
				this.checked = false; 
			});
			$("input[name='${luivarid}']").each(function(){
				var vals = $.trim(val).split(";");
				for(var i=0;i<vals.length;i++){
					if(vals[i] == $.trim($(this).val())){
						//$(this).attr("checked","checked");
						this.checked = true; 
					}
				}
			});
		}
	},
	"validation":function(){
		var val = this['getter'].call();
		if($.trim(val)==""){
			return "显示内容不能为空";
		}
	}
}); 
</script>
<tr>
	<td>${lfn:message('kms-common:kmsKnowledge.porlet.show.content')}</td>
	<td>
	<kmss:ifModuleExist path="/kms/knowledge">	<label><input checked="true" class='module' type='checkbox'  name='${luivarid}' value='knowledge_doc'/>&nbsp;${lfn:message('kms-common:kmsKnowledge.porlet.knowledge.warehouse.static')}</label><br/></kmss:ifModuleExist>
	<kmss:ifModuleExist path="/kms/kmtopic">	<label><input class='module' type='checkbox'  name='${luivarid}' value='kmtopic'/>&nbsp;${lfn:message('kms-common:kmsKnowledge.porlet.knowledge.album.static')}</label><br/></kmss:ifModuleExist>
	<kmss:ifModuleExist path="/kms/knowledge">	<label><input class='module' type='checkbox'  name='${luivarid}' value='knowledge_att'/>&nbsp;${lfn:message('kms-common:kmsKnowledge.porlet.file.download.static')}</label><br/></kmss:ifModuleExist>
	<kmss:ifModuleExist path="/kms/kmaps">	<label><input class='module' type='checkbox'  name='${luivarid}' value='kmaps'/>&nbsp;${lfn:message('kms-common:kmsKnowledge.porlet.knowledge.lmap.static')}</label><br/></kmss:ifModuleExist>
	<kmss:ifModuleExist path="/kms/kem">	<label><input class='module' type='checkbox'  name='${luivarid}' value='kem'/>&nbsp;${lfn:message('kms-common:kmsKnowledge.porlet.atomic.knowledge.static')}</label><br/></kmss:ifModuleExist>
	</td>
</tr>

