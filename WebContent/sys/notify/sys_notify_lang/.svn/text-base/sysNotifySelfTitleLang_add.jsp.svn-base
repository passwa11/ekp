<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,net.sf.json.JSONObject,net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%

	JSONObject langTitles = (JSONObject)request.getAttribute("langTitles");
	JSONObject oriLangTitles = (JSONObject)request.getAttribute("oriLangTitles");
	JSONObject langOffical = (JSONObject)request.getAttribute("langOffical");
	JSONArray langSupport = (JSONArray)request.getAttribute("langSupport");

	String langJson = (String)request.getAttribute("langJson");
	Boolean isLangSuportEnabled = (Boolean)request.getAttribute("isLangSuportEnabled");
%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<center>
		<html:form action="/sys/notify/sys_notify_lang/sysNotifyLang.do" >
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<html:hidden property="has" value="true"/>
				<tr>
					<td width="18%" class="td_normal_title">
						${ lfn:message('sys-notify:sysNotifySelfTitleSetting.fdBundle') }
					</td>
					<td colspan="3">
						<xform:text property="fdBundle" style="width:75%;" showStatus="edit" required="true"  validators="maxLength(200)"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
							<ui:button text="${ lfn:message('sys-notify:sysNotifySelfTitleSetting.load.default') }" onclick="loadBundle();" height="20" width="80" ></ui:button></span>
						<br>
						${ lfn:message('sys-notify:sysNotifySelfTitleSetting.fdBundle.format') }
					</td>
				</tr>			
				<tr>
					<td colspan="2">
						<span id="expression_span" name="expression_span">
							<textarea name="expression" id="expression_<%=langOffical.getString("value") %>" style="width:100%;height:40px" subject="<%=langOffical.getString("text") %>"></textarea>
							<span name="expression_messageSpan_<%=langOffical.getString("value") %>"><%=langOffical.getString("text") %>${ lfn:message('sys-notify:sysNotifySelfTitleSetting.offical') } </span>
							<% if(langSupport.size()>0){ %>
								<br><br>
							<% } %>	
							<% 
							  for(int i=0;i<langSupport.size();i++){
								    JSONObject lng = langSupport.getJSONObject(i);
								    if(langOffical.getString("value").equals(lng.getString("value")))
								    	continue;
								    
							 %>
							<textarea name="expression" id="expression_<%=lng.getString("value") %>" style="width:100%;height:40px" subject="<%=lng.getString("text") %>"></textarea>
							<span name="expression_messageSpan_<%=lng.getString("value") %>"><%=lng.getString("text") %></span>
							<% if(i!=langSupport.size()-1) %>
									<br><br>
							<% 
								} 
							%>
						</span>						
						
					</td>
				</tr>		
					
			</table>
		</html:form>

			<ui:button text="${lfn:message('button.save')}" onclick="_submit();" height="35" width="60" ></ui:button>
			<ui:button text="${lfn:message('button.clear')}" onclick="_clear();" height="35" width="60" ></ui:button>
			<ui:button text="${lfn:message('button.cancel')}" onclick="_cancel();" height="35" width="60" ></ui:button>


<script type="text/javascript">
	// 表单校验
	var _validation = $KMSSValidation();
	
	//自定义校验器:校验会议历时不能大于会议实际时差
/* 	_validation.addValidator('validateFileName','${lfn:message("sys-attachment:sysAttMain.illegal.fileName")}',function(v,e,o){
		var fdFileName = document.getElementsByName('expression').value,
			result = true;
		
		var reg = new RegExp('[\\/:*?"<>|\r\n]+');
		if(reg.test(fdFileName)){
		    //文件名含有非法字符()
			result = false; 
		}
		
		return result;
	}); */

	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog, dateUtil) {
			var isLangSuportEnabled = <%=isLangSuportEnabled%>;
			var langJson = <%=langJson%>;

			window.loadBundle = function() {
				var fdBundle = document.getElementsByName("fdBundle")[0].value;
				fdBundle = fdBundle.replace(/(^\s*)|(\s*$)/g, "");
				if(fdBundle==""){
					alert('<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.fdBundle.null.load"/>');
					return;
				}else{
					$.ajax({
						url:"${KMSS_Parameter_ContextPath}sys/notify/sys_notify_lang/sysNotifyLang.do?method=checkBundle&fdBundle="+fdBundle,
						type: 'POST',
						dataType: 'json',
						async:true, 
						success: function(data){
							if(true==data["has"]){
								var offLang = langJson["official"]["value"];
								document.getElementById("expression_"+offLang).value = data[offLang];
								
								for(var j=0;j<langJson["support"].length;j++){
									var lang = langJson["support"][j]["value"];
									
									if(offLang==lang)
										continue;
									
									document.getElementById("expression_"+lang).value = data[lang];

								}	
							}else{
								alert('<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.fdBundle.null.or.setted"/>')
							}
						}
				   }); 					
				}
			};
			
			// 确认提交
			window._submit = function() {
				if ($KMSSValidation().validate()) {
					var o = {};
					
					var fdBundle = document.getElementsByName("fdBundle")[0].value;
					fdBundle = fdBundle.replace(/(^\s*)|(\s*$)/g, "");
					if(fdBundle==""){
						alert('<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.fdBundle.null.submit"/>');
						return;
					}else
						o["fdBundle"] = fdBundle;
					
					var offLang = langJson["official"]["value"];
					var offValue = document.getElementById("expression_"+offLang).value;
					o[offLang] = offValue;
					
					for(var j=0;j<langJson["support"].length;j++){
						var lang = langJson["support"][j]["value"];
						
						if(offLang==lang)
							continue;
						
						var value = document.getElementById("expression_"+lang).value;
						o[lang] = value;
					}	
					window.$dialog.hide(o);
				}
			};

			// 取消
			window._cancel = function() {
				window.$dialog.hide();
			};
			
			window._clear = function() {
				document.getElementsByName("fdBundle")[0].value = "";
				var offLang = langJson["official"]["value"];
				document.getElementById("expression_"+offLang).value = "";
				
				for(var j=0;j<langJson["support"].length;j++){
					var lang = langJson["support"][j]["value"];
					document.getElementById("expression_"+lang).value ="";
				}	
			};
		});
</script>
		</center>
	</template:replace>
</template:include>
