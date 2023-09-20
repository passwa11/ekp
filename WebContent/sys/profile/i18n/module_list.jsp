<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.util.SysProfileI18nConfigUtil"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" href="${LUI_ContextPath}/sys/profile/i18n/dialog.css?s_cache=${LUI_Cache}">
<%
boolean isReset = "1".equals(request.getParameter("isReset"));
List<Map<String, String>> moduleList = SysProfileI18nConfigUtil.getModuleList(isReset);
%>
<script>
	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event){
		if (event && event.keyCode == '13') {
			dialog_moduleSelect();
		}
	}

	//模块搜索
	function dialog_moduleSelect(){
		var moduleList = <%=JSONArray.fromObject(moduleList)%>;
		var $selectInput = $(".main_data_dialog_select_input");
		var selectValue = $selectInput.val();
		var $table = $("#main_data_dialog_table");
		// 索引  只有1到4
		var indexFlag = 1;
		// 设置到table里面的HTML
		var html = "";
		for(var i=0; i<moduleList.length; i++){
			var value = moduleList[i].name;
			var urlPrefix = moduleList[i].urlPrefix;
			//模糊匹配
			if(value.toUpperCase().indexOf(selectValue.toUpperCase()) > -1){
				if(indexFlag == 1){
					html += "<tr>";
				}
				//构建td
				html += "<td width='25%'><label class='main_data_dialog_td_label'><input type='radio' name='urlPrefix' value='"+urlPrefix+"' data-text='" + value + "'/>&nbsp;"+value+"</label></td>";
				indexFlag++;
				if(indexFlag == 5){
					indexFlag = 1;
					html += "</tr>";
				}
			}
		}
		if(html.length < 1) {
			html = "<tr><td colspan='4' style='text-align: center;'><bean:message key="message.noRecord"/></td></tr>";
		}
		$table.html(html);
	}
</script>

<div class="main_data_dialog_contentWrap">
	<div class="main_data_dialog_selectModule">
		<!-- 搜索 -->
		<div class="main_data_dialog_selectBlock">
			<span>${ lfn:message("sys-profile:sys.profile.i18n.seach.module.title") }:</span>
			<div class="main_data_dialog_select">
				<input type='text' class='main_data_dialog_select_input' title='${ lfn:message("button.search") }' onkeyup='enterTrigleSelect(event);' placeholder='${ lfn:message("sys-profile:sys.profile.i18n.seach.module.desc") }'></input>
				<a href="javascript:dialog_moduleSelect();">${ lfn:message("button.search") }</a>
			</div>
		</div>
		<!-- 数据列表 -->
		<div class="main_data_dialog_content" style="padding:8px 0px;">
			<table id="main_data_dialog_table" class="tb_normal" width="90%">
				<% 
					int i=1;
					if(moduleList.isEmpty()) {
				%>
					<tr><td colspan='4' style='text-align: center;'><bean:message key="message.noRecord"/></td></tr>
				<%		
					} else if (isReset) {
				%>
						 <tr><th colspan='4' style="padding:6px;">
						 	<label class="main_data_dialog_td_label">
						 		<input type="radio" name="urlPrefix" value="__all__"/>&nbsp;<bean:message key="sys-profile:sys.profile.i18n.resetAll"/>
						 	</label>
						 </th></tr>
				<%	
					}
					for (Map<String, String> moduleMap : moduleList) {
						String module = moduleMap.get("name");
						String urlPrefix = moduleMap.get("urlPrefix");
						if("/third/wx/".equals(urlPrefix))
							continue;
				%>
				<% 
				if(i==1){
				%>
					<tr>			
				<%		
					}
				%>
				<td width="25%">
					<label class="main_data_dialog_td_label">
						<input type="radio" name="urlPrefix" value="<%=urlPrefix%>" data-text="<%=module %>"/>&nbsp;<%=module%>
						<% i++; %>
					</label>
					<%
					if (isReset) {
						%>
						&nbsp;&nbsp;&nbsp;
						<span style="cursor:pointer;color:red" onclick="showDetails('<%=urlPrefix%>')"><bean:message key="sys.profile.i18n.checker.details" bundle="sys-profile" /></span>
						<%
					}
					%>
				</td>
				<% 
				if(i==5){
					i=1;
				%>
					</tr>			
				<%		
					}
				%>
				<%
				} 
				%>
			</table>
		</div>
	</div>
</div>

<%
if (isReset) {
	// 重置模块时显示变更详情
%>
<script>
	function showDetails(urlPrefix) {
		window.open("${LUI_ContextPath}/sys/profile/i18n/reset_details.jsp?urlPrefix=" + urlPrefix, "_blank")
	}
</script>
<%
}
%>
