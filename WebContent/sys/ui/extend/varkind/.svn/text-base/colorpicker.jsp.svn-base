<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
JSONObject var = JSONObject.fromObject(request.getParameter("var"));
pageContext.setAttribute("luivar",var);
pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
//out.print(request.getParameter("var")); 
%>
<style>
	.vakind-colorpick input[type="color"]{
		border:none;
		border-bottom: 1px solid #eee;
	}
</style>
<script>
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		return $("#${ luivarid }").val();
	},
	"setter":function(val){
		var ck = $("#ck-${ luivarid }").prop("checked");
		if(ck){
			val = 'transparent';
		} 
		$("#${ luivarid }").val(val);
	},
	"validation":function(){
		var val = this['getter'].call();
		var requ = ${ luivar['require'] ? "true" : "false" };
		if(requ){
			if($.trim(val)==""){
				return "${ lfn:msg(luivar['name']) }不能为空";
			}
		}
	}
});
</script>
<tr>
	<td><span>${ luivar['require'] ? "<i class='lui_varkind_require'>*</i>" : "" }${ lfn:msg(luivar['name']) }</span></td>
	<td class="vakind-colorpick">
		<input class="inputsgl" type="text" style="width: 150px;" id="${ luivarid }" name="${ luivar['key'] }" placeholder="#4285F4" value="${luivar['default']}">
		<span class="com_help">${ lfn:msg(luivarparam['help']) }</span>
	</td>
</tr>