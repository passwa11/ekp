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
%>
<script>
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
			return "第一个快捷链接的“系统链接”不能为空";
		}
	}
}); 

// 标题
${param['jsname']}.VarSet.push({
	"name":"firstMenuTitle",
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
			return "第一个快捷链接的“标题”不能为空";
		}
	}
});

// 副标题
${param['jsname']}.VarSet.push({
	"name":"firstMenuSubtitle",
	"getter":function(){
		return $("#${luivarid}_subtitle").val();
	},
	"setter":function(val){
		if(val){
			$("#${luivarid}_subtitle").val(val);			
		}
	},
	"validation":function(){
		var _subtitle = this['getter'].call();
		if(_subtitle==""){
			return "第一个快捷链接的“副标题”不能为空";
		}
	}
});


	/**
	 * 打开系统链接选择框
	 * @return
	 */
	function openSelectSystemLinkDialog(nameField,linkField,jsp,title){
		var _dialogWin = window.parent || dialogWin || window;
		seajs.use(['lui/dialog','lui/jquery'],function(dialog){
			dialog.iframe(jsp, title, function(val){
				if(!val){
					return;
				}
				var title = "";
				var link = "";
				if(val.length>0){
					title = val[0].name;
					link = val[0].link;
				}
				$("#"+nameField).val(title);
				$("#"+linkField).val(link);
			}, {width:750,height:580,"topWin":_dialogWin});
		});
	}
	
    
</script>
<style>
	.configTable{
	  width: 100%;
	}
	.configTable tr{
	  height: 40px;
	}
	.configTable tr td:first-child{
	  width: 14%;
	}
</style>
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
	      <!-- 链接副标题 -->
	      <tr> 
	         <td><label>副标题</label></td>
	         <td>
                 <input class="inputsgl" id="${luivarid}_subtitle" name="${luivarid}_subtitle" type="text">
	         </td>
	      </tr>	 	      	      
	    </table>
	    
	</td>
</tr>
