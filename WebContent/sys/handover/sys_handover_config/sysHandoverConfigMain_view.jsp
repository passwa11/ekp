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
			 		window.toggleOperation = function(obj){
			 			var id = $(obj).attr("name");
						id = id.replace(/\./g, "\\\.");
						if ($("#" + id + "_list").is(":hidden")) {
							$("#" + id + "_list").slideDown(1, function() {
								$(obj).text("${ lfn:message('sys-handover:sysHandoverConfigMain.hide') }");
								$(obj).removeClass("a_spead").addClass("a_retract");
							});
						} else {
							$("#" + id + "_list").slideUp(1, function() {
								$(obj).text("${ lfn:message('sys-handover:sysHandoverConfigMain.show') }");
								$(obj).removeClass("a_retract").addClass("a_spead");
							});
						}
			 		};
			 		 //一键展开折叠
					window.oneKeyShow = function(isShow){
						if(isShow == true){
							 $("#a_spead_onekeyButton").hide();
							 $("#a_retract_onekeyButton").show();
							 $(".a_spead").each( function() {
								toggleOperation(this);
							 });
						}else{
							 $("#a_retract_onekeyButton").hide();
							 $("#a_spead_onekeyButton").show();
							 $(".a_retract").each( function() {
								toggleOperation(this);
							 });
					    }
					};

					// 显示文档日志列表
					window.dialogDetail = function(obj, state) {
						var url = "/sys/handover/sys_handover_log/sysHandoverLog_detail.jsp";
						var fdMainId = "${param.fdId}";
						var itemMark = $(obj).attr("name");
						var moduleName = itemMark.split("-")[0];
						var item = itemMark.split("-")[1];
						url += "?type=doc&fdMainId=" + fdMainId + "&moduleName=" + moduleName + "&item=" + item + "&state=" + state;
						
						var title = "${ lfn:message('sys-handover:table.sysHandoverConfigLogDetail') }";
						switch (item) {
						case ("handlerIds"): {
							title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.handlerIds"/>)';
							break;
						}
						case ("optHandlerIds"): {
							title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.optHandlerIds"/>)';
							break;
						}
						case ("handlerIds_later"): {
							title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.handlerIds_later"/>)';
							break;
						}
						case ("privilegerIds"): {
							title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.privilegerIds"/>)';
							break;
						}
						case ("otherCanViewCurNodeIds"): {
							title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.otherCanViewCurNodeIds"/>)';
							break;
						}
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

					/* 展现明细表：数据集，页面容器ID，日志状态（1：显示处理成功的日志；2：显示忽略处理的日志） */
					window.showData = function(data, contentId, state) {
						var moduleDate = data;
						var moduleDateArr = moduleDate.item;
						var module_pre = moduleDate.module + "-";
						/* 兼容doc交接 */
						var type = moduleDate.type;
						/* 三层 */
						if (moduleDateArr instanceof Array) {
							/*需要再加一个table*/
							var dataTable = $('<table class="tb_simple table_module" name="moduleTable" id="' + moduleDate.module + '_table"/>');
							var dataTr = $('<tr class="module_title" index="' + 1 + '"/>');
							var dataTd = $('<td colspan="2"/>');
							/*记录数*/
							dataTd.append(getTableTitleMessage(moduleDate.module, moduleDate, "module", false, type));
							dataTd.appendTo(dataTr);
							dataTr.appendTo(dataTable);
							/* 无记录不显示二级表格 */
							if (moduleDate.total > 0) {
								for ( var j = 0; j < moduleDateArr.length; j++) {
									moduleDate = moduleDateArr[j];
									dataTr = $('<tr class="list"/>');
									$('<td class="td_left" />').appendTo(dataTr);
									dataTd = $('<td/>');
									dataTd.append(getModuleTable(module_pre, moduleDate, type, state));
									dataTd.appendTo(dataTr);
									dataTr.appendTo(dataTable);
								}
							}
							$("#" + contentId).append(dataTable);
						} else {
							var dataTable = getModuleTable(module_pre, moduleDate);
							$("#" + contentId).append(dataTable);
						}
					};

					// 获取表头 
					window.getTableTitleMessage = function(labelFor, moduleDate, type, showToggle, handlerType, state) {
						var recodeMessage = '<label for="' + labelFor + '">';
						var spanId = "";
						if (moduleDate.total == 0) {
							recodeMessage += '<span class="no_recode_message">';
						}
						if (type == "module") {
							recodeMessage += moduleDate.moduleMessageKey;
							spanId = moduleDate.module;
						} else if (type = "item") {
							recodeMessage += moduleDate.itemMessageKey;
							spanId = moduleDate.module + "-" + moduleDate.item;
						}
						if (moduleDate.total == 0) {
							recodeMessage += '<span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.noRecodes") })</span></span>';
						} else {
							recodeMessage += '<span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.total") }<span class="number_total" id="' + labelFor + '_number_total">' + moduleDate.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }'
							recodeMessage += ')</span>';
						}
						recodeMessage += '</label>';
						if (handlerType == "doc") {
							/*是文档形交接*/
							if (moduleDate.total != 0 && showToggle != false) {
								recodeMessage += '<span class="a_doc" name="' + spanId + '" onclick="dialogDetail(this, ' + state + ');">${ lfn:message("sys-handover:sysHandoverConfigMain.detail") }</span>';
							}
						} else {
							if (moduleDate.total != 0 && showToggle != false) {
								recodeMessage += '<span class="a_spead" name="' + spanId + '" onclick="toggleOperation(this);">${ lfn:message("sys-handover:sysHandoverConfigMain.show") }</span>';
							}
						}
						return recodeMessage;
					};

					// 显示二级表格
					window.getModuleTable = function(module_pre, moduleDate, handlerType, state) {
						var dataTable;
						var checkIdValue = "";
						if (moduleDate.item == "") {
							dataTable = $('<table class="tb_simple table_module" name="moduleTable" id="' + moduleDate.module + '_table"/>');
							checkIdValue = moduleDate.module;
						} else {
							dataTable = $('<table class="tb_simple table_module" name="itemTable" id="' + module_pre + moduleDate.item + '_table"/>');
							checkIdValue = module_pre + moduleDate.item;
						}
						var dataTr = $('<tr class="tab_data" index="' + 1 + '"/>');
						var dataTd = $('<td colspan="3"/>');
						if (moduleDate.item == "") {
							dataTd.html(dataTd.html() + getTableTitleMessage(checkIdValue, moduleDate, "module", null, handlerType, state));
						} else {
							dataTd.html(dataTd.html() + getTableTitleMessage(checkIdValue, moduleDate, "item", null, handlerType, state));
						}
						dataTd.appendTo(dataTr);
						dataTr.appendTo(dataTable);
					if (moduleDate.total > 0) {
						var dataListTr;
						if (moduleDate.item == "") {
							dataListTr = $('<tr class="list" style="display:none" id="' + moduleDate.module + '_list"/>');
						} else {
							/*item列表默认隐藏*/
							dataListTr = $('<tr class="list" style="display:none" id="' + moduleDate.module + '-' + moduleDate.item + '_list"/>');
						}
						$('<td class="td_left" />').appendTo(dataListTr);
						dataTd = $('<td colspan="2"/>');
						dataTd.append(getListTable(moduleDate.handoverRecords));
						dataTd.appendTo(dataListTr);
						dataListTr.appendTo(dataTable);
					}
					return dataTable;
				};

				// 显示明细表 
				window.getListTable = function(handoverRecords) {
					var dataTable = $('<table class="tb_simple table_list" name="listTable"/>');
					var dataTr = $('<tr class="tab_data" index="' + 1 + '"/>');
					dataTr.appendTo(dataTable);
					for ( var i = 0; i < handoverRecords.length; i++) {
						var dataListTr = $('<tr class="tab_data" index="' + 1 + '"/>');
						var dataTd1 = $('<td class="td_left" />');
						var checkBoxName = "";
						var index = i + 1;
						$('<span>' + index + '</span>').appendTo(dataTd1);
						dataTd1.appendTo(dataListTr);
						var dataTd2 = $('<td colspan="3"><a href="javascript:openUrl(\'' + handoverRecords[i].url + '\');">' + _str.encodeHTML(handoverRecords[i].datas[0]) + '</a></td>');
						dataTd2.appendTo(dataListTr);

						dataListTr.appendTo(dataTable);
					}
					return dataTable;
				};

				LUI.ready( function() {
					var logDataJson = JSON.parse('${lfn:escapeJs(logData)}');
					for (var key in logDataJson){
						showData(logDataJson[key],"resultContent",1);
					}
					<c:if test="${configMain.handoverType == 2}">
					var logIgnoreDataJson = JSON.parse('${ignoreData}');
					for (var key in logIgnoreDataJson){
						showData(logIgnoreDataJson[key],"ignoreContent",2);
					}
					</c:if>
				});

				});
		  </script>
	      <div name="searchContent" class="searchContent" style="padding: 10px">		
	       	  <div class="lui_handover_header">
                <span>${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.executeLog') }</span>
                <c:if test="${configMain.handoverType == null || configMain.handoverType == 1}">
                <%-- 一键展开/折叠--%> 
                <ui:button styleClass="a_spead_onekeyButton" text="${ lfn:message('sys-handover:sysHandoverConfigMain.onekeySpred') }" id="a_spead_onekeyButton" onclick="oneKeyShow(true);"/>
                <ui:button styleClass="a_retract_onekeyButton" style="display:none" text="${ lfn:message('sys-handover:sysHandoverConfigMain.onekeyRetract') }" id="a_retract_onekeyButton" onclick="oneKeyShow(false);"/>
                </c:if>
              </div>   
		     <table class="tb_normal lui_handover_headTb lui_sheet_c_table" >  
			      <tr>
					  <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }</td>
					  <td width="35%"><c:out value="${configMain.fdFromName}" /></td>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }</td>
					  <td width="35%"><c:out value="${configMain.fdToName}" /></td>
				  </tr><!--	
				  <tr>
				      <td width="20%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.executeWay') }</td>
					  <td><c:out value="${ lfn:message('sys-handover:sysHandover.executeWay.replace') }" /></td>
				  </tr>	
			      -->
			      <tr>
			 	      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.executeList') }</td>
			 	      <%-- 交接记录 --%>
					   <td width="85%" colspan="3">
					       <div name="resultContent" class="resultContent" id="resultContent"></div>
					  </td>
				  </tr>	
				  <c:if test="${configMain.handoverType == 2}">
				  <tr>
			 	      <td width="15%" class="td_normal_title">
			 	      	${ lfn:message('sys-handover:sysHandoverConfigLog.fdMain.ignoreList') }
			 	      	<div class="lui_icon_s lui_icon_s_icon_help" style="cursor: pointer;" onclick="_help();"></div>
			 	      	
			 	      	<script>
							seajs.use(['lui/dialog'], function(dialog) {
								_help = function() {
									dialog.confirm('<div align="left" style="font-size: 12pt;">${lfn:message("sys-handover:sysHandoverConfigLog.fdMain.ignoreHelp")}</div>', function(value) {
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
				  </c:if>
				  <tr>
				      <td width="15%" class="td_normal_title">${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType') }</td>
				      <td width="35%">
				      	<c:choose>
							<c:when test="${configMain.handoverType == 2}">
								${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.doc') }
							</c:when>
							<c:when test="${configMain.handoverType == 3}">
								${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.auth') }
							</c:when>
							<c:otherwise>
								${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.config') }
							</c:otherwise>
						</c:choose>
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
