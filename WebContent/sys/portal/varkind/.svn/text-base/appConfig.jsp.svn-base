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
	String dialogjsp = "/sys/portal/varkind/selectAppNav.jsp";
	String dialogrealurl = "openSelectAppNavDialog('!{nameField}','!{idField}','"+dialogjsp+"','"+LuiFunctions.msg(var.get("name").toString())+"');";
	dialogrealurl = dialogrealurl.replace("!{nameField}", pageContext.getAttribute("luivarid").toString()+"_appName");
	dialogrealurl = dialogrealurl.replace("!{idField}", pageContext.getAttribute("luivarid").toString()+"_appId");
	pageContext.setAttribute("luivardialogjs", dialogrealurl);
%>
<script>
	var dialogWin = dialogWin || window;
	${param['jsname']}.VarSet.push({
		"name":"${ luivar['key'] }",
		"getter":function(){
			var isShowApp = "false";
			var showAppDoms = $("input[name='${ luivarid }']:checked");
			if(showAppDoms && showAppDoms.length>0){
				isShowApp = "true";
			}
			var luivarAppId = $("#${ luivarid }_appId").val();
			var luivarAppName = $("#${ luivarid }_appName").val();
			return {
				showApp:isShowApp,
				showAppId:luivarAppId,
				showAppName:luivarAppName,
				context:{
					id:luivarAppId,
					sourceId:"sys.portal.sysnav.source",
				}
			};
		},
		"setter":function(val){
			$("#${ luivarid }_appId").val(val.showAppId || '');
			$("#${ luivarid }_appName").val(val.showAppName || '');
			$("input[name='${ luivarid }']").each(function(){
				if(val.showApp == 'true'){
					this.checked = true;
				}else{
					this.checked = false;
				}
			});

		},
		"validation":function(){
			var val = this['getter'].call();
			if(val.showApp=='true' && !val.showAppId){
				return "${ lfn:message('sys-portal:portlet.theader.item.app.tip') }";
			}
		}
	});

	$("input[name=${luivarid}]").change(function(){
		var $this = $(this);
		if(!$this.checked&&!$("#${ luivarid }_appId").val()){
			${luivardialogjs}
		}
	})
	function openSelectAppNavDialog(nameField,idField,jsp,title){
		var _dialogWin = window.parent || dialogWin || window;
		seajs.use(['lui/dialog','lui/jquery'],function(dialog){
			dialog.iframe(jsp, title, function(val){
				if(!val){
					return;
				}
				$("#"+nameField).val(val.fdName);
				$("#"+idField).val(val.fdId);
				$("input[name='${ luivarid }']").prop('checked',true);
			}, {width:750,height:550,"topWin":_dialogWin});
		});
	}
	function clearAppNavInfo(){
		$("#${ luivarid }_appId").val('');
		$("#${ luivarid }_appName").val('');
		$("input[name='${ luivarid }']").prop('checked',false);
	}
</script>

<tr>
	<td>${ lfn:msg(luivar['name']) }</td>
	<td>
		<label>
			<input type="checkbox" name="${ luivarid }" value="true">
			${ lfn:message('sys-portal:portlet.header.var.yes') }
		</label>
		<input type="hidden" id="${ luivarid }_appId" name="${ luivarid }_appId">
		<input class="inputsgl" readonly="readonly" id="${ luivarid }_appName" name="${ luivarid }_appName" type="text">
		<a href="javascript:void(0)"  class="com_btn_link" onclick="${luivardialogjs}" >${ lfn:message('sys-ui:ui.vars.select') }</a>
		<a href="javascript:void(0)"  class="com_btn_link" onclick="clearAppNavInfo()" >${ lfn:message('sys-ui:ui.vars.clear') }</a>
		<span class="com_help">${ lfn:message('sys-portal:portlet.theader.var.isSwitchApp') }</span></td>
</tr>
