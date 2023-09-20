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
String dialogjsp = "/dbcenter/echarts/application/portlet/dbChart_portlet_category_dialog.jsp?modelName=" + request.getParameter("modelName");
String dialogrealurl = "";
if(StringUtil.isNotNull(dialogjsp)){
	if(StringUtil.isNotNull(xcode) && xcode.indexOf(SysUiConstant.SEPARATOR)>0){
		String url = xcode.substring(0,xcode.indexOf(SysUiConstant.SEPARATOR));
		url = SysUiConstant.getServerUrl(url);
		dialogjsp = url + dialogjsp;
	}
	dialogrealurl = "openTopicConfigPage('!{nameField}','!{idField}','"+dialogjsp+"','"+LuiFunctions.msg(var.get("name").toString())+"','"+ pageContext.getAttribute("luivarid") +"');";
}
dialogrealurl = dialogrealurl.replace("!{nameField}", pageContext.getAttribute("luivarid").toString()+"_name");
dialogrealurl = dialogrealurl.replace("!{idField}", pageContext.getAttribute("luivarid").toString()+"_id");
pageContext.setAttribute("luivardialogjs", dialogrealurl);
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/dbcenter/echarts/application/common/inputs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dbcenter/echarts/application/common/userInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dbcenter/echarts/application/common/DbEchartsApplication_Dialog.js"></script>
<script>
	if(typeof(DbCharts_InputsObj) == "undefined"){
		DbCharts_InputsObj = {};
	}
	
	DbCharts_InputsObj["${ luivarid }"] = new DbEchartsAppInputs($(".${ luivarid }-navTree-inputs"));
	
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
					return "${ lfn:msg(luivar['name']) }${ lfn:msg('dbcenter-echarts-application:portlet.cantNull') }";
				}
			}
		}
	});
	${param['jsname']}.VarSet.push({
		"name":"db_dynamic",
		"getter":function(){
			var val = {};
			var keyData = DbCharts_InputsObj["${ luivarid }"].getKeyData();
			val['db_dynamic_config'] = LUI.stringify(keyData);
			var dynamicVal = {};
			// 构造链接的参数	
			for(var key in keyData){
				var data = keyData[key];
				if(data.value != ''){
					dynamicVal[key] = "{" + data.value + "}";						
				}
			}
			val['db_dynamic'] = escape(LUI.stringify(dynamicVal));
			return val;
		},
		"setter":function(val){
			var db_dynamic = val['db_dynamic_config'];
			var inputs = db_dynamic == ''?{}:LUI.toJSON(db_dynamic);
			// 构造符合格式的返回值给dbEchartsAppInputs {"item":{"mainModelName":xxx},"value":模板id,"text":模板名称}
			var param = {};
			param.item = {};
			param.item.mainModelName = $("[name='${ luivarid }_modelName']").val();
			param.value = $("#${ luivarid }_id").val();
			if(param.value != ''){
				DbCharts_InputsObj["${ luivarid }"].buildInput(param);
				DbCharts_InputsObj["${ luivarid }"].setValuesAndDom(inputs);			
			}
		}
	});
	
	// 多页签，需要用id区分函数，或者把id当做参数传进去
	function openTopicConfigPage(nameField,idField,jsp,title,varid){
		var _dialogWin = dialogWin || window;
		var values = $("#"+ varid +"_id").val();
		var names =  $("#"+ varid + "_name").val();
		seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
			dialog.iframe(jsp, title, function(val){
				if(!val){
					return;
				}
				$("#"+nameField).val(val.fdName);
				$("#"+idField).val(val.fdId);
				// 构造符合格式的返回值给dbEchartsAppInputs {"item":{"mainModelName":xxx},"value":模板id,"text":模板名称}
				var rn = {};
				rn.value = val.fdId;
				rn.item = {};
				rn.item.mainModelName = $("[name='"+ varid +"_modelName']").val();
				DbCharts_InputsObj[varid].buildInput(rn);
				
			}, {width:750,height:590,"topWin":_dialogWin});
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
			out.append("<input type=\"hidden\" name=\""+ pageContext.getAttribute("luivarid") +"_modelName\" value=\""+ request.getParameter("modelName") +"\"/>");
		%>
	</td>
</tr>
<tr>
	<td>${ lfn:message("dbcenter-echarts-application:portlet.dynamicInput") }</td>
	<td>
		<div class="${ luivarid }-navTree-inputs">
		</div>
	</td>
</tr>