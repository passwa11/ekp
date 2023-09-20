<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.portal.xml.model.SysPortalFooter"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@page import="java.util.Date,com.landray.kmss.util.DateUtil,com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.simple">
	<template:replace name="title">配置页眉</template:replace>
	<template:replace name="body">
		<ui:toolbar layout="sys.ui.toolbar.float" count="10" var-navwidth="100%">
			<ui:button onclick="onEnter()" text="${lfn:message('button.ok')}"></ui:button>
		</ui:toolbar>
		<script>
		seajs.use(['theme!form']);
		</script>
		<style type="text/css">
			#portal_page_footer_select {
				position: fixed;
				top: 6px;
				z-index: 20;
				width: 200px;
				left: 20px;
			}
		</style>
		<script src="${LUI_ContextPath}/sys/ui/js/var.js"></script>
		<script>
		String.prototype.startWith=function(str){     
			  var reg=new RegExp("^"+str);     
			  return reg.test(this);        
		}  

		String.prototype.endWith=function(str){     
		  var reg=new RegExp(str+"$");     
		  return reg.test(this);        
		}

		lodingImg = "<img src='${LUI_ContextPath}/sys/ui/js/ajax.gif'/>"
		function onEnter(){
			var val = {};
			val.fdFooter = $("#portal_page_footer_select").val();
			val.fdFooterName = $("#portal_page_footer_select option:selected").text();
			
			var footerVars = {};
			if(window['portal_page_footer_opts_var']!=null){
				footerVars = window['portal_page_footer_opts_var'].getValue();
			}
			
			// 默认页脚可自定义页脚文字
			if(LUI.$("#portal_page_footer_custom").html().length > 0) {
				// 需要过滤掉特殊符号，则否页面会报错
				var info1 = LUI.$("#portal_page_footer_custom input[name=info1]").val().replace(/\"/g, "").replace(/\'/g, "");
				var info2 = LUI.$("#portal_page_footer_custom input[name=info2]").val().replace(/\"/g, "").replace(/\'/g, "");
				if(info1.length > 0) {
					footerVars.info1 = info1;
				}
				if(info2.length > 0) {
					footerVars.info2 = info2;
				}
			}

			val.fdFooterVars = escape(LUI.stringify(footerVars));
			window.$dialog.hide(val);
		}
		function fdFooterSelectChange(val){
			LUI.$("#portal_page_footer_custom").empty().hide();
			var sindex = document.getElementById("portal_page_footer_select").selectedIndex;
			if( sindex > 0){
				var opt = document.getElementById("portal_page_footer_select").options[sindex];
				var footerId = opt.value;
				//存在页脚配置则展示默认参数配置
				if(!!footerId){
					LUI.$("#fdFooter").val(footerId);
					LUI.$("#portal_page_footer_img").attr("src","${LUI_ContextPath}"+opt.getAttribute("img")).show();
					LUI.$("#portal_page_footer_opts").html(lodingImg).show().attr("jsname","portal_page_footer_opts_var").load("${LUI_ContextPath}/sys/portal/designer/jsp/vars/footer.jsp?x="+(new Date().getTime()),{"fdId":footerId,"jsname":"portal_page_footer_opts_var"},function(){
						 if(val != null){
							 window['portal_page_footer_opts_var'].setValue(val);
						 }
					});
				}
				//只有默认页脚和匿名默认页脚才展示默认的页脚参数设置
			    if(footerId == "footer.default" || footerId == "footer.anonymous.default"){
					customFooter();
				}
			}else{
				LUI.$("#fdFooter").val("");
				LUI.$("#portal_page_footer_opts").empty().hide();
				window['portal_page_header_opts_var']=null;
				LUI.$("#portal_page_footer_img").hide();
			}
		}
		function onReady(){
			if(window.$dialog == null){
				window.setTimeout(onReady, 100);
				return
			}
			window.$ = LUI.$;
			var value = window.$dialog.dialogParameter;
			if(value != null){
				// 获取之前保存的页脚信息
				fdFooterVars = unescape(value.fdFooterVars);
				if($.trim(value.fdFooter) != "") {
					LUI.$("#portal_page_footer_select").val(value.fdFooter);
					var val = null;
					if($.trim(value.fdFooterVars)!=""){
						val = LUI.toJSON(unescape(value.fdFooterVars));
						fdFooterSelectChange(val);
					}else{
						fdFooterSelectChange();
					}
				}
			}else{
				$("#portal_page_footer_select option").each(function(){
					if($(this).val().endWith(".default")){
						$(this).attr("selected","true");
					}
				});
				fdFooterSelectChange();
			}
		}
		var fdFooterVars = undefined;
		LUI.ready(onReady);

		// 自定义页脚
		function customFooter() {
			<%
	    		// 版权信息的年份根据服务器时间自动获取
	    		String s_year = DateUtil.convertDateToString(new Date(), "yyyy");
				String footerInfo = ResourceUtil.getString("sys.portal.footer.info2", "sys-portal", null, s_year);
	    	%>
			var info1 = '<bean:message bundle="sys-portal" key="sys.portal.footer.info1" />';
			var info2 = '<%=footerInfo%>';
			if(fdFooterVars && fdFooterVars.length > 0) {
				var infoObj = LUI.toJSON(fdFooterVars);
				if(infoObj.info1) {
					info1 = infoObj.info1;
				}
				if(infoObj.info2) {
					info2 = infoObj.info2;
				}
			}
			var html = '<table class="tb_normal" width="100%">'
					+ '<tr>'
					+ '<td width="10%"><bean:message bundle="sys-portal" key="sys.portal.footer.custom.label1"/></td>'
					+ '<td><input class="inputsgl" type="text" name="info1" value="'+info1+'" style="width:98%;"></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td width="10%"><bean:message bundle="sys-portal" key="sys.portal.footer.custom.label2"/></td>'
					+ '<td><input class="inputsgl" type="text" name="info2" value="'+info2+'" style="width:98%;"></td>'
					+ '</tr>'
					+ '</table>';
					
			LUI.$("#portal_page_footer_custom").html(html).show();
		}
		</script>
	
		<select id="portal_page_footer_select" onchange="fdFooterSelectChange()">
		<option value="">${lfn:message('sys-portal:portlet.header.nofooter')}</option>		  					
		<% 
		List fs = PortalUtil.getPortalFooters(request);
		for(int i=0;i<fs.size();i++){
			SysPortalFooter f = (SysPortalFooter)fs.get(i);
			out.println("<option value='"+f.getFdId()+"' img='"+f.getFdThumb()+"'>" +f.getFdName()+"</option>");
		}
		%>	
		</select>		
		<div style="height: 5px;"></div>
		<table style="width:670px;padding: 0px;border: 0px;margin: 0 auto;">
			<tr>
				<td><img id="portal_page_footer_img" src="" style="max-width: 670px;"></td>
			</tr>
			<tr>
				<td><div id="portal_page_footer_opts"></div></td>
			</tr>
			<tr>
				<td><div id="portal_page_footer_custom"></div></td>
			</tr>
		</table>
		
	</template:replace>
</template:include>