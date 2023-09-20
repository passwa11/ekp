<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
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
	"name":"scoretype",
	"getter":function(){
		var showType = "checkbox";
		if(showType==""||showType=="select"){
			return $("#${luivarid}").val();		
		}else if(showType=="radio"){
			return $("input[name='${luivarid}']:checked").val();
		}else if(showType=="checkbox"){
			var tempVal = [];
			$("input[name='${luivarid}']:checked").each(function(){
				tempVal.push($(this).val());
			});
			return tempVal.join(";");
		}
	},
	"setter":function(val){
		var showType = "checkbox";
		if(showType==""||showType=="select"){
			$("#${luivarid}").val(val);		
		}else if(showType=="radio"){
			$("input[name='${luivarid}']").each(function(){
				if($(this).val() == val){
					$(this).attr("checked","checked");
				}
			});
		}else if(showType=="checkbox"){
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
		
		if($.trim(val).split(";").length>1){
			return "${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.score.view.max') }";
		}
		
		var requ = false;
		if(requ){
			if($.trim(val)==""){
				return "${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.score.view.notNull') }";
			}
		}
	}
}); 
</script>
<tr>
	<td>${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.score.view')}</td>
	<td><label><input checked="true" type="checkbox" name="${luivarid}" value="fdScore">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.score')}</label>&nbsp;&nbsp;<label><input type="checkbox" name="${luivarid}" value="fdRich">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.rich')}</label>&nbsp;&nbsp;<span class="com_help"></span></td>
</tr>