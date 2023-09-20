<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.ui.taglib.fn.LuiFunctions"%>
<%@page import="com.landray.kmss.util.*,com.landray.kmss.sys.notify.service.ISysNotifyCategoryService"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
JSONObject var = JSONObject.fromObject(request.getParameter("var"));
pageContext.setAttribute("luivar", var);
pageContext.setAttribute("luivarid","var_"+IDGenerator.generateID());
pageContext.setAttribute("luivarparam",StringUtil.isNotNull(var.getString("body")) ? JSONObject.fromObject(var.get("body")) : new JSONObject());
String dialogjsp = "/sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/varkind/selectThemeStyle.jsp";
String dialogrealurl = "openSelectThemeStyleTemplateDialog('!{nameField}','!{idField}','"+dialogjsp+"','选择主题');";
dialogrealurl = dialogrealurl.replace("!{nameField}", pageContext.getAttribute("luivarid").toString()+"_theme_title");
dialogrealurl = dialogrealurl.replace("!{idField}", pageContext.getAttribute("luivarid").toString()+"_theme_id");
pageContext.setAttribute("luivardialogjs", dialogrealurl);
%>
<script>
var dialogWin = dialogWin || window;
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		return $("#${luivarid}_theme_id").val();
	},
	"setter":function(val){
		if(val){
			$("#${luivarid}_theme_id").val(val);
		}
	},
	"validation":function(){
		var link = this['getter'].call();
		if(link==""){
			return "主题风格不能为空";
		}
	}
}); 

// 主题风格标题
${param['jsname']}.VarSet.push({
	"name":"themeStyleTitle",
	"getter":function(){
		return $("#${luivarid}_theme_title").val();
	},
	"setter":function(val){
		if(val){
			$("#${luivarid}_theme_title").val(val);			
		}
	}
});


	/**
	 * 打开主题风格选择框
	 * @return
	 */
	function openSelectThemeStyleTemplateDialog(nameField,idField,jsp,title){
		var _dialogWin = window.parent || dialogWin || window;
		seajs.use(['lui/dialog','lui/jquery'],function(dialog){
			dialog.iframe(jsp, title, function(val){
				if(!val){
					return;
				}
				
				$("#"+nameField).val(val.title);
                $("#"+idField).val(val.id);
			}, {width:750,height:580,"topWin":_dialogWin});
		});
	}
	
    
</script>

<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td>
	    <!-- 主题风格 -->
	    <div>
	        <input id="${luivarid}_theme_id" name="${luivarid}_theme_id" type="hidden">
	        <input class="inputsgl" readonly="readonly" id="${luivarid}_theme_title" name="${luivarid}_theme_title" type="text">
	        <a href="javascript:void(0)"  class="com_btn_link" onclick="${luivardialogjs}" >${lfn:message('sys-ui:ui.vars.select')}</a>     
	    </div>
	</td>
</tr>
