<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no" width="980px">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-handover:module.sys.handover') }"></c:out>
	</template:replace>
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
		    <kmss:authShow roles="ROLE_SYSHANDOVER_MAINTAIN">
				<ui:button text="${lfn:message('button.delete') }" order="1" onclick="deleteDoc('sysHandoverConfigMain.do?method=delete&fdId=${JsParam.fdId}');">
				</ui:button>
			</kmss:authShow>
				<ui:button text="${lfn:message('button.close') }" order="2" onclick="closeDoc();">
				</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
			<ui:menu layout="sys.ui.menu.nav">
				<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self">
				</ui:menu-item>
				<ui:menu-item text="${lfn:message('sys-handover:module.sys.handover')}" href="/sys/profile/index.jsp#org/handover" target="_self">
			   </ui:menu-item> 
		</ui:menu>
	</template:replace>	
	<template:replace name="content"> 
		   <script>
				seajs.use("${LUI_ContextPath}/sys/handover/resource/css/handover.css");
				seajs.use(['lui/dialog', 'lui/util/str'], function(dialog, _str) {

					window.openUrl = function(url){
						if(url==''){
							return;
					    }
			 		    window.open("${LUI_ContextPath}"+url,"_blank");
			 		};
			 		window.deleteDoc = function(delUrl){
						dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
							if(isOk){
								Com_OpenWindow(delUrl,'_self');
							}	
						});
						return;
					};
					window.closeDoc = function(){
				 		window.close();
			 		};

					/* 展现明细表：数据集，页面容器ID */
					window.showData = function(data, contentId, state) {
						var dataTable = $('<table class="tb_simple table_module"/>');
						var dataTr = $('<tr class="module_title" index="' + 1 + '"/>');
						var dataTd = $('<td colspan="2"/>');
						
						dataTd.append(getTableTitleMessage(data));
						dataTd.appendTo(dataTr);
						dataTr.appendTo(dataTable);
						
						for (var i = 0; i < data.modules.length; i++) {
							var module = data.modules[i];
							dataTr = $('<tr class="list"/>');
							$('<td class="td_left index"/>').html(" ").appendTo(dataTr);
							dataTd = $('<td/>');
							dataTd.append(getModuleTable(module, state));
							dataTd.appendTo(dataTr);
							dataTr.appendTo(dataTable);
						}
						
						$("#" + contentId).append(dataTable);
					};

					// 获取表头 
					window.getTableTitleMessage = function(data) {
						var recodeMessage = '<label>';
						if (data.total == 0) {
							recodeMessage += '<span class="no_recode_message">';
						}
						recodeMessage += data.text;
						
						if (data.total == 0) {
							recodeMessage += '<span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.noRecodes") })</span></span>';
						} else {
							recodeMessage += '<span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.total") }<span class="number_total">' + data.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }'
							recodeMessage += ')</span>';
						}
						recodeMessage += '</label>';
						
						
						return recodeMessage;
					};

					// 显示二级表格
					window.getModuleTable = function(module, state) {
						var dataTable = $('<table class="tb_simple table_module"/>');
						var dataTr = $('<tr class="tab_data"/>');
						var dataTd = $('<td colspan="2" style="width:50%"/>');
						dataTd.html(dataTd.html() + getTableTitleMessage(module));
						dataTd.appendTo(dataTr);
						
						var dataTd2 = $('<td/>');
						dataTd2.html('<span class="a_doc" name="' + module.name + '" onclick="dialogDetail(this, ' + state + ');">${ lfn:message("sys-handover:sysHandoverConfigMain.detail") }</span>');
						dataTd2.appendTo(dataTr);
						
						dataTr.appendTo(dataTable);
						return dataTable;
					};
					
					// 显示文档日志列表
					window.dialogDetail = function(obj, state) {
						var url = "/sys/handover/sys_handover_log/sysHandoverLog_detail.jsp";
						var itemMark = $(obj).attr("name");
						var item = itemMark.split("-")[0];
						var moduleName = itemMark.split("-")[1];
						url += "?type=item&fdMainId=${JsParam.fdId}&moduleName=" + moduleName + "&item=" + item + "&state=" + state;
						
						var title = '<bean:message bundle="sys-handover" key="sysHandoverConfigMain.detail"/>';
						
						
						var itemText = $(obj).prev().text();
						if (itemText && itemText.indexOf("\(")) {
							title += "(" + itemText.substring(0,itemText.indexOf("\(")).replace(/(^\s*)|(\s*$)/g, "") + ")";
						}
						
						dialog.build({
							config : {
								width : 800,
								height : 500,
								lock : true,
								cache : false,
								title : title,
								content : {
									id : 'dialog_iframe',
									scroll : true,
									type : "iframe",
									url : url
								}
							}
						}).show();
					};

					LUI.ready( function() {
						var logDataJson = JSON.parse('${logData}');
						for (var key in logDataJson){
							showData(logDataJson[key], "resultContent", 1);
						}
						var logIgnoreDataJson = JSON.parse('${ignoreData}');
						for (var key in logIgnoreDataJson){
							showData(logIgnoreDataJson[key],"ignoreContent", 2);
						}
					});
				});
		  </script>
	      <div name="searchContent" class="searchContent" style="padding: 10px">		
	       	  <div class="lui_handover_header">
                <span>${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.executeLog') }</span>
              </div>   
		     <table class="tb_normal lui_handover_headTb lui_sheet_c_table" >  
			      <tr>
					  <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }</td>
					  <td width="35%"><c:out value="${configMain.fdFromName}" /></td>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }</td>
					  <td width="35%"><c:out value="${configMain.fdToName}" /></td>
				  </tr>
			      <tr>
			 	      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.executeList') }</td>
			 	      <%-- 交接记录 --%>
					   <td width="85%" colspan="3">
					       <div name="resultContent" class="resultContent" id="resultContent"></div>
					  </td>
				  </tr>	
				  <tr>
			 	      <td width="15%" class="td_normal_title">
			 	      	${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.ignoreList') }
			 	      	<div class="lui_icon_s lui_icon_s_icon_help" style="cursor: pointer;" onclick="_help();"></div>
			 	      	
			 	      	<script>
							seajs.use(['lui/dialog'], function(dialog) {
								_help = function() {
									dialog.confirm('<div align="left" style="font-size: 12pt;">${lfn:message("sys-handover:sysHandoverConfigLog.fdMain.item.ignoreHelp")}</div>', function(value) {
									}, null, [{
										name : "${lfn:message('button.ok')}",
										fn : function(value, dialog) {
											dialog.hide(value);
										}
									}]
								);
								}
							});
						</script>
			 	      </td>
			 	      <%-- 忽略记录 --%>
					   <td width="85%" colspan="3">
					       <div name="ignoreContent" class="ignoreContent" id="ignoreContent"></div>
					  </td>
				  </tr>
				  <tr>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigLog.fdCount') }</td>
				      <td width="35%"><c:out value="${succTotal}"/></td>
					  <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigLog.fdIgnoreCount') }  </td>
					  <td width="35%"><c:out value="${ignoreTotal}"/></td>
				  </tr>	
				  <tr>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType') }</td>
				      <td width="35%">
				      	${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.item') }
				      </td>
					  <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.total') }  </td>
					  <td width="35%"><c:out value="${configMain.total}"/></td>
				  </tr>	
				  <tr>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.status') }</td>
				      <td colspan="3" width="85%">
				      	<sunbor:enumsShow value="${configMain.fdState}"	enumsType="sys_handover_state" />
				      </td>
				  </tr>		
				  <tr>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }</td>
				      <td width="35%"><c:out value="${configMain.docCreator.fdName}"/></td>
					  <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }  </td>
					  <td width="35%"><kmss:showDate type="datetime" value="${configMain.docCreateTime}" /></td>
				  </tr>	  
		  	</table>
		 </div>	 
	</template:replace>
</template:include>
