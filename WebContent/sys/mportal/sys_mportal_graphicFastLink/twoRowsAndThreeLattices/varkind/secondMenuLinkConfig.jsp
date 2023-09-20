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
String dialogjsp = "/sys/mportal/sys_mportal_menu/sysMportalMenu_dialog.jsp?selection_type=single";
String dialogrealurl = "openSelectSystemLinkDialog('!{nameField}','!{linkField}','"+dialogjsp+"','选择链接');";
dialogrealurl = dialogrealurl.replace("!{nameField}", pageContext.getAttribute("luivarid").toString()+"_title");
dialogrealurl = dialogrealurl.replace("!{linkField}", pageContext.getAttribute("luivarid").toString()+"_link");
pageContext.setAttribute("luivardialogjs", dialogrealurl);
String iconDialogJsp = "/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange=1";
String iconDialogUrl = "openSelectIconDialog('!{iconField}','"+iconDialogJsp+"','选择图标');";
iconDialogUrl = iconDialogUrl.replace("!{iconField}", pageContext.getAttribute("luivarid").toString()+"_icon");
pageContext.setAttribute("iconDialogUrl", iconDialogUrl);
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/js/menuLinkConfig.js"></script>
<script type="text/javascript">
var dialogWin = dialogWin || window;
${param['jsname']}.VarSet.push({
	"name":"${ luivar['key'] }",
	"getter":function(){
		return $("#${luivarid}_link").val();
	},
	"setter":function(val){
		if(val){
			$("#${luivarid}_link").val(val);
		}
	},
	"validation":function(){
		var link = this['getter'].call();
		if(link==""){
			return "第二个快捷链接的“系统链接”不能为空";
		}
	}
}); 

// 标题
${param['jsname']}.VarSet.push({
	"name":"secondMenuTitle",
	"getter":function(){
		return $("#${luivarid}_title").val();
	},
	"setter":function(val){
		if(val){
			$("#${luivarid}_title").val(val);			
		}
	},
	"validation":function(){
		var title = this['getter'].call();
		if(title==""){
			return "第二个快捷链接的“标题”不能为空";
		}
	}
});

// 图标
${param['jsname']}.VarSet.push({
	"name":"secondMenuIcon",
	"getter":function(){
		return $("#${luivarid}_icon").val();
	},
	"setter":function(val){
		if(val){
			setIconContent("${luivarid}_icon",val);		
		}
	}
});
</script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_menu/css/iconList.css"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/css/menuLinkConfig.css"></link>
<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td>
	
		<table class="configTable" >
	      <!-- 系统链接  -->
	      <tr> 
	         <td><label>系统链接</label></td>
	         <td>
	            <input class="inputsgl" id="${luivarid}_link" name="${luivarid}_link" type="text">
	            <a href="javascript:void(0)"  class="com_btn_link" onclick="${luivardialogjs}" >${lfn:message('sys-ui:ui.vars.select')}</a>  
	         </td>
	      </tr>
	      <!-- 链接标题 -->
	      <tr> 
	         <td><label>标题</label></td>
	         <td>
                 <input class="inputsgl" id="${luivarid}_title" name="${luivarid}_title" type="text">
	         </td>
	      </tr>
	      <!-- 图标  -->
	      <tr> 
	         <td><label>图标</label></td>
	         <td>
		        <div class="icon" style="display: inline-block;vertical-align: top;"><div id="${luivarid}_icon_panel" class="mui configIcon mui-quick-entry-01-icon" claz="mui-quick-entry-01-icon"></div></div>
		        <div style="display: inline-block;vertical-align: top; margin-top: 16px;">
		        	<input id="${luivarid}_icon" name="${luivarid}_icon" type="hidden" value="mui-quick-entry-01-icon">
		            <a href="javascript:void(0)"  class="com_btn_link" onclick="${iconDialogUrl}" >${lfn:message('sys-ui:ui.vars.select')}</a> 
		        </div>
	         </td>
	      </tr>	 	      	      
	    </table>
	
	</td>
</tr>
