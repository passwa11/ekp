<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<script type="text/javascript">
			var hideNamesS="${hideNames}";
			
			function SaveConfig() {
				Com_Submit(document.sysFileConvertGlobalConfigForm, "saveGlobal");
			}
			
			function attConfigShow() {
				seajs.use('lui/jquery', function($) {
					var enabledAttConvert = $("input[name='attConvertEnable']").val() == "true";
					if (enabledAttConvert) {
						$("#attconfigList").show();
					} else {
						$("#attconfigList").hide();
						$("#highFidelities").hide();
					}
					var hideNames=hideNamesS.split(",");
					for(var hideName in hideNames){
						$("#"+hideNames[hideName]).hide();
					}
				});
			}

			LUI.ready(function() {
				attConfigShow();
			});

			seajs.use([ 'theme!list', 'theme!portal' ]);
			seajs.use([ 'lui/dialog', 'lui/topic' ],function(dialog, topic) {
				window.addConvertConfig = function() {
					dialog.iframe("/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=add","${ lfn:message('sys-filestore:xinzengzhuanhuanpeizhi') }",function() {
						seajs.use(['lui/base','lui/data/source'],function(base,source){
							var listView = base.byId('listView');
							listView.tableRefresh();
						});
					}, {width : 685,height : 454});
				};
				window.newRule = function() {
					dialog.confirm("${ lfn:message('sys-filestore:sure_old_to_queue') }",
							function(value) {
								if (value == true) {
									dialog.iframe("/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=newRuleToQueue","${ lfn:message('sys-filestore:new_rule_to_queue') }",function() {},{width : 800,height : 400});
								}
							});

				};
				window.delConfigs = function(fdId) {
					var values = [];
					if(typeof(fdId) =="undefined"){
						$("input[name='List_Selected']:checked").each(function() {values.push($(this).val());});
						var delType = "selection";
						if (values.length == 0) {
							dialog.alert('<bean:message key="page.noSelect"/>');
							return;
						}
					}else{
						values.push(fdId);
					}
					var confirmHintInfo = '<bean:message key="convertConfig.comfirmDelete.selection" bundle="sys-filestore"/>';
					dialog.confirm(confirmHintInfo,
						function(value) {
							if (value == true) {
								window.innerLoading = dialog.loading();
								$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=delConfigs"/>',
									$.param({"selected" : values},true),dialogCallBack,'json');
							}
						});
				};
				window.editConvertConfig = function(fdId) {
					var values = [];
					if(typeof(fdId) =="undefined"){
						$("input[name='List_Selected']:checked").each(function() {values.push($(this).val());});
						if (values.length == 0) {
							dialog.alert('<bean:message key="page.noSelect"/>');
							return;
						}
						if (values.length > 1) {
							dialog.alert("${ lfn:message('sys-filestore:config_index_hint_1') }");
							return;
						}
					}else{
						values.push(fdId);
					}
					dialog.iframe("/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=edit&fdId="+ values[0],
						"${ lfn:message('sys-filestore:config_index_hint_2') }",
						function() {
							seajs.use(['lui/base','lui/data/source'],function(base,source){
								var listView = base.byId('listView');
								listView.tableRefresh();
							});
						}, 
						{width : 685,height : 454});
				};
				window.changeConvertConfigStatus = function(btnType,fdId) {
					var values = [];
					if(typeof(fdId) =="undefined"){
						$("input[name='List_Selected']:checked").each(function() {values.push($(this).val());});
					}else{
						values.push(fdId);
					}

					if (values.length == 0&& (btnType == "enablechoose" || btnType == "disablechoose")) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var hintInfo = "";
					if (btnType == "allenable") {
						hintInfo = "${ lfn:message('sys-filestore:sure_all_enable') }";
					}
					if (btnType == "alldisable") {
						hintInfo = "${ lfn:message('sys-filestore:sure_all_disable') }";
					}
					if (btnType == "enablechoose") {
						hintInfo = "${ lfn:message('sys-filestore:sure_enable_choose') }";
					}
					if (btnType == "disablechoose") {
						hintInfo = "${ lfn:message('sys-filestore:sure_disable_choose') }";
					}
					dialog.confirm(hintInfo,function(value) {
						if (value == true) {
							window.innerLoading = dialog.loading();
							$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=changeStatus"/>',
								$.param({"List_Selected" : values,"changeType" : btnType},true),
								dialogCallBack,
								'json');
						}
					});
				};
				window.dialogCallBack = function(data) {
					if (window.innerLoading != null)
						window.innerLoading.hide();
						if (data != null&& data.status == true) {
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						} else {
							dialog.failure('<bean:message key="return.optFailure" />');
						}
				};
				
				//转换服务配置项 
				window.configConvertServer = function() {
					dialog.iframe("/sys/filestore/sys_filestore/sysFileConvertGlobalConfig.do?method=convertServerConfig","${ lfn:message('sys-filestore:sysFilestore.conversion.config.item') }",
							function() {}, {width : 800,height : 400});
				};
		});

			
		</script>
		<c:set var="sysFileConvertGlobalConfigForm"
			value="${sysFileConvertGlobalConfigForm}" scope="request" />
		<html:form
			action="/sys/filestore/sys_filestore/sysFileConvertGlobalConfig.do">
				<div style="margin-top: 6px; margin-bottom: 10px;">
					<table class="tb_normal" width="96%" id="attconvert.config">
						<tr width="100%">
						   <td class="td_normal_title" width="180px" align="center">
						    	<label>
						          	<bean:message
										key="sysFilestore.conversion.enable"
										bundle="sys-filestore" />
						          </label>
						   </td>
						   <%--enabledText="${ lfn:message('sys-filestore:attConvert.hint.info.global.enable') }"
									disabledText="${ lfn:message('sys-filestore:attConvert.hint.info.global.enable') }" --%>
							<td  colspan="2"><ui:switch id="attconvert.enabled"
									checked="${attConvertIsEnabled}" property="attConvertEnable"
									onValueChange="SaveConfig();"
									enabledText=""
									disabledText=""></ui:switch>
							</td>
						</tr>
						<tr width="100%">
						   <td class="td_normal_title" width="180px" align="center">
						    	<label>
						          	<bean:message
										key="sysFilestore.conversion.turnon"
										bundle="sys-filestore" />
						          </label>
						   </td>
						   <%--enabledText="${ lfn:message('sys-filestore:attConvert.hint.info.global.enable') }"
									disabledText="${ lfn:message('sys-filestore:attConvert.hint.info.global.enable') }" --%>
							<td  colspan="2">
								 <xform:checkbox subject="" property="converter_aspose" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
										<xform:simpleDataSource value="true">ASPOSE</xform:simpleDataSource>
								 </xform:checkbox>
								 <xform:checkbox subject="" property="converter_yozo" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
									<xform:simpleDataSource value="true"><bean:message
											key="sysFilestore.conver.server.yozo"
											bundle="sys-filestore" /></xform:simpleDataSource>
								 </xform:checkbox>
								<c:if test="${'true' eq convertWPS}">
								    <xform:checkbox subject="" property="converter_wps" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
										<xform:simpleDataSource value="true"><bean:message
											key="sysFilestore.conversion.wps.online.ofd"
											bundle="sys-filestore" /></xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${'true' eq convertWPSCenter}">
								    <xform:checkbox subject="" property="converter_wps_center" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
										<xform:simpleDataSource value="true"><bean:message
									key='sysFilestore.conversion.wps.center.ofd' bundle='sys-filestore' /></xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${'true' eq convertSuwell}">
									 <xform:checkbox subject="" property="converter_skofd" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
										<xform:simpleDataSource value="true"><bean:message
											key="sysFilestore.conversion.sk.ofd"
											bundle="sys-filestore" /></xform:simpleDataSource>
									</xform:checkbox>
					            </c:if>
								<c:if test="${'true' eq convertDianju}">
									<xform:checkbox subject="" property="converter_dianju" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
										<xform:simpleDataSource value="true"><bean:message
												key="sysFilestore.conversion.dianju.ofd"
												bundle="sys-filestore" /></xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
								<c:if test="${'true' eq convertFoxit}">
									<xform:checkbox subject="" property="converter_foxit" onValueChange="SaveConfig"  dataType="boolean" showStatus ="edit">
										<xform:simpleDataSource value="true"><bean:message
												key="sysFilestore.conver.server.foxit"
												bundle="sys-filestore" /></xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
					            <div>
					            <c:if test="${'true' eq convertSuwellTip || 'true' eq convertWPSTip
					                      ||'true' eq convertWPSCenterTip ||'true' eq convertDianjuTip
					                       ||'true' eq convertFoxitTip}">
					               <font size="0.5" color="#C0C0C0">
									<span id="convertTip">
									  <bean:message
											key="sysFilestore.conversion.tip"
											bundle="sys-filestore" />
									</span>
								</font>
								</c:if>
					            </div>
							</td>
							 
						</tr>
						<tr width="100%">
						<td class="td_normal_title" width="180px" align="center">
						    <label>
						                 <bean:message
										key="sysFilestore.conversion.config"
										bundle="sys-filestore" />
						      </label>
					   </td>
						<td colspan="2">
                           <span class="message">
					                   <a href='javascript:configConvertServer();' style='color:#66CCFF;'> <bean:message
										key="sysFilestore.conversion.config.item"
										bundle="sys-filestore" /> </a>
					            </span>
                         </td>
					</tr>
					<!-- begin -->
					
					</table>
				</div>
				<!-- end -->
			 <c:if test="${'true' eq sysFileConvertGlobalConfigForm.converter_aspose
			           || 'true' eq sysFileConvertGlobalConfigForm.converter_yozo
			           || 'true' eq sysFileConvertGlobalConfigForm.converter_wps
			           || 'true' eq sysFileConvertGlobalConfigForm.converter_wps_center
			           || 'true' eq sysFileConvertGlobalConfigForm.converter_skofd
			           || 'true' eq sysFileConvertGlobalConfigForm.converter_dianju
			           || 'true' eq sysFileConvertGlobalConfigForm.converter_foxit}">
				
				<div id="attconfigList" style="width: 96%;">
					<div class="lui_list_operation">
					<!-- 全选 -->
					<div class="lui_list_operation_order_btn">
						<list:selectall></list:selectall>
					</div>
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
							${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort"
											style="float:left" count="6">
											<list:sortgroup>
											<list:sort property="fdModelName"
												text="${ lfn:message('sys-filestore:sysFileConvertConfig.fdModelName') }"
												group="sort.list" value="up"></list:sort>
											<list:sort property="fdFileExtName"
												text="${ lfn:message('sys-filestore:sysFileConvertConfig.fdFileExtName') }"
												group="sort.list"></list:sort>
											<list:sort property="fdConverterKey"
												text="${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterKey') }"
												group="sort.list"></list:sort>
											<list:sort property="fdHighFidelity"
												text="${ lfn:message('sys-filestore:sysFileConvertConfig.fdHighFidelity') }"
												group="sort.list"></list:sort>
											<list:sort property="fdStatus"
												text="${ lfn:message('sys-filestore:sysFileConvertConfig.fdStatus') }"
												group="sort.list"></list:sort>
											</list:sortgroup>
										</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="2" id="btnToolBar">
											<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
												<ui:button text="${lfn:message('button.deleteall')}"
													onclick="delConfigs();" order="2"></ui:button>
												<ui:button text="${lfn:message('button.add')}"
													onclick="addConvertConfig();" order="2"></ui:button>
												<ui:button
													text="${lfn:message('sys-filestore:filestore.convertconfig.enablechoose')}"
													onclick="changeConvertConfigStatus('enablechoose');"
													order="3"></ui:button>
												<ui:button
													text="${lfn:message('sys-filestore:filestore.convertconfig.disablechoose')}"
													onclick="changeConvertConfigStatus('disablechoose');"
													order="3"></ui:button>
											</kmss:authShow>
										</ui:toolbar>
						</div>
					</div>
					</div>
					<ui:fixed elem=".lui_list_operation"></ui:fixed>
					    <list:listview id="listView">
						<ui:source type="AjaxJson">
										{url:'/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=data'}
								</ui:source>
						<list:colTable isDefault="false"
							layout="sys.ui.listview.columntable" name="columntable"
							onRowClick="editConvertConfig('!{fdId}')">
							<list:col-checkbox></list:col-checkbox>
							<list:col-serial></list:col-serial>
							<list:col-auto
								props="fdFileExtName;fdModelName;fdConverterKey;fdConverterType;fdHighFidelity;fdStatus;operations"></list:col-auto>
						</list:colTable>
					</list:listview>
					
					<list:paging></list:paging>
					<link href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
					<script
						src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
				</div>
				</c:if>
			<script>
				$KMSSValidation();
			</script>
		</html:form>
	</template:replace>
</template:include>