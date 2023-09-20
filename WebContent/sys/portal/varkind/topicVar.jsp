<%@page import="com.landray.kmss.sys.ui.util.SysUiConstant"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%> 
<%
String xcode = request.getParameter("code");
JSONObject var = JSONObject.fromObject(request.getParameter("var"));
pageContext.setAttribute("luivar",var);
pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());

JSONObject jsonbody = (JSONObject)pageContext.getAttribute("luivarparam");
String dialogjs = (String)jsonbody.get("js");
String dialogjsp = "/sys/portal/sys_portal_topic/portlet/sysPortalTopicPortlet.jsp";
String dialogrealurl = "";
if(StringUtil.isNotNull(dialogjsp)){
	if(StringUtil.isNotNull(xcode) && xcode.indexOf(SysUiConstant.SEPARATOR)>0){
		String url = xcode.substring(0,xcode.indexOf(SysUiConstant.SEPARATOR));
		url = SysUiConstant.getServerUrl(url);
		dialogjsp = url + dialogjsp;
	}
	dialogrealurl = "openTopicConfigPage('!{nameField}','!{idField}','"+dialogjsp+"','"+LuiFunctions.msg(var.get("name").toString())+"');";
}
dialogrealurl = dialogrealurl.replace("!{nameField}", pageContext.getAttribute("luivarid").toString()+"_name");
dialogrealurl = dialogrealurl.replace("!{idField}", pageContext.getAttribute("luivarid").toString()+"_id");
pageContext.setAttribute("luivardialogjs", dialogrealurl);
%>
<script>
	//Com_IncludeFile("dialog.js");
	${param['jsname']}.VarSet.push({
		"name":"${ luivar['key'] }",
		"getter":function(){
			var val = {};
			val.${ luivar['key'] } = $("#${ luivarid }_id").val();
			val.${ luivar['key'] }_name = $("#${ luivarid }_name").val();
			return val;
		},
		"setter":function(val){
			$("#${ luivarid }_id").val(val.${ luivar['key'] });
			$("#${ luivarid }_name").val(val.${ luivar['key'] }_name);
		},
	 	"validation":function(){
			var val = this['getter'].call();
			var requ = ${ luivar['require'] ? "true" : "false" };
			if(requ){
				if(val ==null || val.${ luivar['key'] } == null || $.trim(val.${ luivar['key'] }) == ""){
					return "${ lfn:msg(luivar['name']) }不能为空";
				}
			}
		}
	});
	function openTopicConfigPage(nameField,idField,jsp,title){
		var _dialogWin = dialogWin || window;
		var values = $("#${ luivarid }_id").val();
		var names =  $("#${ luivarid }_name").val();
		jsp = jsp + "?fdIds=" + values + "&fdNames="+encodeURIComponent(names);
		seajs.use(['lui/dialog','lui/jquery'],function(dialog){
			dialog.iframe(jsp, title, function(val){
				if(!val){
					return;
				}
				$("#"+nameField).val(val.fdName);
				$("#"+idField).val(val.fdId);
			}, {width:750,height:550,"topWin":_dialogWin});
		});
	}
</script>
<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td>
		<input id="${ luivarid }_id" name="${ luivarid }_id" type="hidden">
		<input class="inputsgl" readonly="readonly" id="${ luivarid }_name" name="${ luivarid }_name" type="text">${ luivar['require'] ? "<span style='color:red;'>*<span>" : "" }
		<a href="javascript:void(0)"  class="com_btn_link" onclick="${luivardialogjs}" >${ lfn:message('sys-ui:ui.vars.select') }</a>
		<%
			if(var.get("require")!=null && var.getBoolean("require")){
				
			}else{
				out.append("<a href='javascript:void(0)' class='com_btn_link' onclick=\"document.getElementById('"+pageContext.getAttribute("luivarid")+"_id').value='';document.getElementById('"+pageContext.getAttribute("luivarid")+"_name').value='';\">"+LuiFunctions.message("sys-ui:ui.vars.clear")+"</a>");
			}
		%>
		<span class="com_help">${ lfn:msg(luivarparam['help']) }</span>
	</td>
</tr>