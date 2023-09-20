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
	"name":"numtype",
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
		
		if($.trim(val).split(";").length>3){
			return "${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.num.view.max') }";
		}
		
		var requ = true;
		if(requ){
			if($.trim(val)==""){
				return "${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.num.view.notNull') }";
			}
		}
	}
}); 
</script>
<tr>
	<td>${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.num.view')}</td>
	<td>
		<label><input checked="true" type="checkbox" name="${luivarid}" value="fdPublicDay">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.fdDay')}</label><br/>
		<label><input type="checkbox" name="${luivarid}" value="fdPublicWeek">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.fdWeek')}</label><br/>
		<label><input type="checkbox" name="${luivarid}" value="fdPublicMonth">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.fdMonth')}</label><br/>
		<label><input type="checkbox" name="${luivarid}" value="fdReaderMonth">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.reader.fdMonth')}</label><br/>
		<label><input type="checkbox" name="${luivarid}" value="fdCommentMonth">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.comment.fdMonth')}</label><br/>
		<label><input type="checkbox" name="${luivarid}" value="fdDownloadMonth">&nbsp;${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.download.fdMonth')}</label><br/>
		<span style="color:red;">*<span><span class="com_help"></span></span></span>
	</td>
</tr>
