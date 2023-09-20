<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.organization.transfer.SysOrgMatrixVersionChecker" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
	</template:replace>
	<template:replace name="head">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
		<script>
			function confirm_invalidated(){
				var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
				return msg;
			}
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('sys-organization:sysOrgRoleConf.simulator')}" order="1" onclick="simulator();" />
			<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=edit&fdId=${sysOrgMatrixForm.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" order="2" onclick="Com_OpenWindow('sysOrgMatrix.do?method=edit&fdId=${sysOrgMatrixForm.fdId}','_self');" />
			</kmss:auth>
			<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=editMatrixData&fdId=${sysOrgMatrixForm.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('sys-organization:sysOrgMatrix.edit.data')}" order="2" onclick="Com_OpenWindow('sysOrgMatrix.do?method=editMatrixData&fdId=${sysOrgMatrixForm.fdId}','_self');" />
			</kmss:auth>
			<c:if test="${sysOrgMatrixForm.fdIsAvailable}">
			<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=invalidated&fdId=${sysOrgMatrixForm.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('sys-organization:organization.invalidated')}" order="3" onclick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgMatrix.do?method=invalidated&fdId=${sysOrgMatrixForm.fdId}','_self');" />
			</kmss:auth>
			</c:if>
    	 	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div style="width: 95%; margin: 10px auto;">
		<p class="txttitle">
			<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/> - <c:out value="${sysOrgMatrixForm.fdName}"/>
		</p>
		
		<ui:tabpanel id="MatrixTab">
			<ui:content title="${lfn:message('sys-organization:sysOrgMatrix.base')}">
				<table class="tb_normal" width=100%>
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-organization" key="sysOrgMatrix.fdName"/>
						</td><td width=35% colspan="3">
							<bean:write name="sysOrgMatrixForm" property="fdName"/>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-organization" key="sysOrgMatrix.fdCategory"/>
						</td><td width=35% colspan="3">
							<bean:write name="sysOrgMatrixForm" property="fdCategoryName"/>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-organization" key="sysOrgMatrix.fdOrder"/>
						</td><td width=35% colspan="3">
							<bean:write name="sysOrgMatrixForm" property="fdOrder"/>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-organization" key="sysOrgMatrix.fdDesc"/>
						</td><td width=35% colspan="3">
							<bean:write name="sysOrgMatrixForm" property="fdDesc"/>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-organization" key="sysOrgMatrix.fdIsAvailable"/>
						</td><td width=35% colspan="3">
							<sunbor:enumsShow value="${sysOrgMatrixForm.fdIsAvailable}" enumsType="sys_org_available_result" />
						</td>
					</tr>
					<!-- 数据分组 -->
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate"/>
						</td><td width=85% colspan="3">
							<c:if test="${sysOrgMatrixForm.fdIsEnabledCate eq 'true'}">
								<table class="tb_normal" width=100% id="TABLE_DocList_Cates" align="center">
								<tr>
									<td align="center" class="td_normal_title" style="width:50%">
										<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.name"/>
									</td>
									<td align="center" class="td_normal_title" style="width:50%">
										<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.manager"/>
									</td>
								</tr>
								<c:forEach items="${sysOrgMatrixForm.fdDataCates}" var="item" varStatus="vstatus">
								<tr>
									<td align="center">
										<c:out value="${item.fdName}"/>
									</td>
									<td align="center">
										<c:out value="${item.fdElementName}"/>
									</td>
								</tr>
								</c:forEach> 
							</table>
							</c:if>
							<c:if test="${sysOrgMatrixForm.fdIsEnabledCate eq 'false'}">
								<sunbor:enumsShow value="false" enumsType="sys_org_available_result" />
							</c:if>
						</td>
					</tr>
					<!-- 可使用者 -->
					<tr>
						<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgMatrix.authReaders"/></td>
						<td  width=85% colspan="3">
						  <kmss:showText value="${sysOrgMatrixForm.authReaderNames}"/>
					   </td>
					</tr>
					<!-- 可维护者 -->
					<tr>
						<td class="td_normal_title" width=15%><bean:message bundle="sys-organization" key="sysOrgMatrix.authEditors"/></td>
						<td width=85% colspan="3">
						  <kmss:showText value="${sysOrgMatrixForm.authEditorNames}"/>
						</td>
					</tr>
					<!-- 判断是否需要进行数据迁移，如果返回false，则提示用户进行数据迁移 -->
					<%
						Boolean flag = new SysOrgMatrixVersionChecker().isRuned();
						if(!flag){
					%>
					<tr id="transfer_warm">
						<td>
							<p>提示</p>
						</td>
						<td width="100%">
							<p class="txttitle" style="color: red;">当前数据不兼容无法正常使用，需要前往管理员工具箱执行兼容性检测操作！</p>
						</td>
					</tr>
					<%	
						}
					%>
				</table>
			</ui:content>
			<ui:content title="${lfn:message('sys-organization:sysOrgMatrix.field')}">
				<div class="lui_matrix_div_wrap">
				   <!-- 矩阵卡片 - 左右移动 Starts  -->
				   <div class="lui_matrix_field_tb_wrap">
				       <!-- 类型 -->
				       <div class="lui_matrix_field_tb_item lui_matrix_field_tb_item_type">
				           <table id="matrix_type_table" class="lui_matrix_tb_normal" style="width: 96px;">
				               <tr>
				                   <td class="lui_matrix_td_normal_title"><bean:message key="sys.common.viewInfo.type"/></td>
				               </tr>
				               <tr>
				                   <td class="lui_matrix_td_normal_title"><bean:message bundle="sys-organization" key="sysOrgMatrixRelation.fdFieldName"/></td>
				               </tr>
				           </table>
				       </div>
				       <!-- 条件数据 -->
				       <div class="lui_matrix_field_tb_item lui_matrix_field_tb_item_condition">
				           <table id="matrix_table" class="lui_matrix_tb_normal lui_matrix_tb_view">
								<!-- 表头行 -->
								<tr id="matrix_field_type" align="center">
								</tr>
								<!-- 内容行 -->
				              	<tr id="matrix_field_value">
								</tr>
				           </table>
				       </div>
				   </div>
					<kmss:auth requestURL="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=check&matrixId=${sysOrgMatrixForm.fdId}" requestMethod="GET">
					<c:if test="${not empty delCountDesc}">
					<div style="padding-top: 20px;">
						<span style="color: red;">
							<c:out value="${delCountDesc}"/>
							<a href="javascript:;" onclick="fieldCheck();">${lfn:message('sys-organization:sysOrgMatrix.field.check')}</a>
						</span>
					</div>
					</c:if>
					</kmss:auth>
				</div>
				<!-- 最小列宽 -->
				<div id="sysOrgMatrixPreviewContent">
					<div class="sysOrgMatrixPreviewTitle">
						<bean:message bundle="sys-organization" key="sysOrgMatrix.min.width"/>
						<xform:text property="width" style="width:93%"
									subject="${lfn:message('sys-organization:sysOrgMatrix.min.width') }" required="true"
									validators="maxLength(200)"/>px
						<label>
							<div class="item_tips lui_icon_s lui_icon_s_cue4"
								 title="<bean:message bundle='sys-organization' key='sysOrgMatrix.min.width.desc'/>"></div>
						</label>
					</div>
				</div>
			</ui:content>
			<ui:content title="${lfn:message('sys-organization:sysOrgMatrix.data')}">
				<div class="lui_matrix_div_wrap">
					<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=importData&fdId=${sysOrgMatrixForm.fdId}">
					<!-- 操作按钮 -->
					<div class="lui_maxtrix_toolbar">
						<div class="lui_maxtrix_toolbar_r">
							<a class="com_bgcolor_d" href="javascript:;" onclick="importData();"><bean:message key="button.import"/></a>
							<a class="com_bgcolor_d" href="javascript:;" onclick="downloadMatrixRelation();"><bean:message key="button.export"/></a>
							<a class="com_bgcolor_d" href="javascript:;" onclick="deleteMatrixRelation();"><bean:message key="button.deleteall"/></a>
						</div>
					</div>
					<script>
						window.canDel = true;
					</script>
					</kmss:auth>

				   <c:choose>
				   		<c:when test="${fn:length(allVersions) > 0}">
				   			<ui:tabpanel id="lui_matrix_panel" layout="sys.ui.tabpanel.sucktop" var-average='false' var-useMaxWidth='true'>
				   				<c:forEach items="${ allVersions }" var="version">
									<ui:content id="lui_matrix_panel_content_${ version.fdName }" title="${ version.fdName }" titleicon="${version.fdIsEnable ? '' : 'matrix_nonactivated'}">
										<!-- 增加数据类别按钮  start-->
										<div class="lui_maxtrix_toolbar">
											<div class="lui_maxtrix_toolbar_r matrix_data_cate" style="float: left;margin-top: 10px;">

											</div>
										</div>
										<!-- 增加数据类别按钮  end-->
										<div id="lui_matrix_data_tb_wrap_nodata_${ version.fdName }" class="lui_matrix_data_tb_wrap_nodata" style="display: none;">
											<bean:message bundle="sys-organization" key="sysOrgMatrix.cate.nodata"/>
										</div>
										<!-- 矩阵数据表格开始 -->
										<div id="lui_matrix_data_tb_wrap_${ version.fdName }" class="lui_matrix_data_tb_wrap" style="display: none;">
									       <!-- 类型 -->
									       <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_l">
									           <table id="matrix_seq_table_${ version.fdName }" class="lui_matrix_tb_normal">
									               <tr style="height: 40px;">
									                   <th class="lui_matrix_td_normal_title"><input id="matrix_seq_checkbox_${ version.fdName }" type="checkbox"></th>
									                   <th class="lui_matrix_td_normal_title"><bean:message key="page.serial"/></th>
									               </tr>
									           </table>
									       </div>
									       <!-- 条件数据 -->
									       <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_c">
									           <table id="matrix_data_table_${ version.fdName }" class="lui_matrix_tb_normal">
									               <tr style="height: 40px;">
									               </tr>
									           </table>
									       </div>
									       <!-- 操作数据 -->
									       <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_r">
									           <table id="matrix_opt_table_${ version.fdName }" class="lui_matrix_tb_normal">
									               <tr style="height: 40px;">
									                   <th><bean:message key="list.operation"/></th>
									               </tr>
									           </table>
									       </div>
									   </div>
										<list:listview id="listview_${ version.fdName }" channel="${ version.fdName }">
											<ui:source type="AjaxJson">
												{url:'/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=findMatrixPage&fdId=${sysOrgMatrixForm.fdId}&fdVersion=${version.fdName}'}
											</ui:source>
											<!-- 矩阵表格 -->
											<div data-lui-type="sys/organization/resource/js/matrixDataView!MatrixDataView" style="display:none;">
												<div data-lui-type="lui/listview/template!Template" style="display:none;"></div>
											</div>
										</list:listview>
										<list:paging id="matrix_data_table_${ version.fdName }_page" channel="${ version.fdName }"></list:paging>
									</ui:content>
								</c:forEach>
							</ui:tabpanel>
				   		</c:when>
				   		<c:otherwise>
				   			<div style="text-align: center;">
				   				${lfn:message('sys-organization:sysOrgMatrix.version.empty')}
				   			</div>
				   		</c:otherwise>
				   </c:choose>

					<%-- 矩阵与流程模板关系 --%>
					<c:import url="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.jsp" charEncoding="UTF-8">
						<c:param name="matrixId" value="${sysOrgMatrixForm.fdId}"></c:param>
						<c:param name="type" value="view"></c:param>
					</c:import>
				</div>

			</ui:content>
		</ui:tabpanel>
		
		<!-- 矩阵数据下载 -->
		<form id="downloadMatrixDataForm" action="${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=exportMatrixData&fdId=${sysOrgMatrixForm.fdId}" method="post"></form>
		<script language="JavaScript">
			// 当前版本
			window.curVersion = undefined;
			window.fdDataCateId = undefined;
			// 页签集合，保存所有页签对象，可以通过版本名称获取
			window.matrixPanelArray = {};
			var con = [], res = [], fdDataCates = [], hasTitle = {}, dataType = {};
			<c:forEach items="${sysOrgMatrixForm.fdRelationConditionals}" var="conditional">
			con.push({"fdType": "${conditional.fdType}", "fdName": "<c:out value="${conditional.fdName}" escapeXml="true"/>", "fdMainDataType": "${conditional.fdMainDataType}", "fdMainDataText": "${conditional.fdMainDataText}", "fdValueCount": parseInt("${conditional.fdValueCount}")});
			</c:forEach>
			<c:forEach items="${sysOrgMatrixForm.fdRelationResults}" var="result">
			res.push({"fdType": "${result.fdType}", "fdName": "<c:out value="${result.fdName}" escapeXml="true"/>", "fdValueCount": parseInt("${result.fdValueCount}")});
			</c:forEach>
			<c:forEach items="${sysOrgMatrixForm.fdDataCates}" var="cate">
			fdDataCates.push({"fdId": "${cate.fdId}","fdName": "<c:out value="${cate.fdName}" escapeXml="true"/>"});
			</c:forEach>
			seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.channel(window.curVersion).publish("list.refresh");
				});
				
				<c:if test="${fn:length(allVersions) > 0}">
				LUI.ready(function() {
					<c:forEach items="${ allVersions }" var="version">
					window.matrixPanelArray["${version.fdName}"] = {
						"seq": "matrix_seq_table_${version.fdName} tbody",
						"data": "matrix_data_table_${version.fdName} tbody",
						"opt": "matrix_opt_table_${version.fdName} tbody",
						"checkbox": "matrix_seq_checkbox_${version.fdName}",
					}
					// 全选
					$("#matrix_seq_checkbox_${version.fdName}").click(function() {
						$("#matrix_seq_table_${version.fdName} [name=List_Selected]:checkbox").prop("checked", this.checked);
					});
					$("#matrix_seq_table_${version.fdName}").on("change", "[name=List_Selected]:checkbox", function() {
						var isAll = true;
						$("#matrix_seq_table_${version.fdName} [name=List_Selected]:checkbox").each(function(i, n) {
							if(!n.checked) {
								isAll = false;
								return false;
							}
						});
						$("#matrix_seq_checkbox_${version.fdName}").prop("checked", isAll);
					});
					topic.channel("${version.fdName}").subscribe("list.changed", showTable);
					</c:forEach>
					
					/* 生成分组标签 */
					window.generateCateData = function (dataCates) {
						if(dataCates && dataCates.length > 0) {
							// 为每个版本增加分组信息
							_generateCateData(dataCates);
						}
					};
					
					/* 生成分组标签 */
					var __to__;
					window._generateCateData = function(dataCates) {
						var panel_content = $("#lui_matrix_panel [data-lui-mark='panel.content']");
						if(panel_content.length > 0) {
							if(__to__) {
								clearTimeout(__to__);
							}
							panel_content.each(function(i, n) {
								var dataCate = $(n).find(".matrix_data_cate");
								if(dataCate.length > 0) {
									var cates = [];
									// 如果有多个分组，增加一个“全部”的分组，方便查看
									if(dataCates.length > 1) {
										cates.push('<a class="com_bgcolor_d" href="javascript:;" style="color: #333;" data-cateid="all" onclick="switchCateData(this, \'all\');"><bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.all"/></a>');
									}
									for(var i=0; i<dataCates.length; i++) {
										var data = dataCates[i];
										cates.push('<a class="com_bgcolor_d" href="javascript:;" style="color: #333;" data-cateid="' + data.fdId + '" onclick="switchCateData(this, \'' + data.fdId + '\');"><pre>' + data.fdName + '</pre></a>');
									}
									dataCate.html(cates.join(""));
									// 默认点击第一个
									dataCate.find("a:eq(0)").click();
								}
							});
						} else {
							__to__ = setTimeout(function() {_generateCateData(dataCates);}, 100);
						}
					};
					
					/* 分组数据切换 */
					window.switchCateData = function(elem, dataCateId) {
						var parent = $(elem).parent();
						// 禁用本按钮，启用其它按钮
						if($(elem).hasClass("lui_maxtrix_cate_item_dis")) {
							return false;
						}
						parent.find("a").each(function(i, n) {
							$(n).removeClass("lui_maxtrix_cate_item_dis");
						});
						$(elem).addClass("lui_maxtrix_cate_item_dis");
						if(dataCateId == "all") {
							dataCateId = "";
						}
						// 重新加载分组数据
						$.ajax({
							url : Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=findMatrixPage&fdId=${sysOrgMatrixForm.fdId}&fdVersion=' + window.curVersion + '&fdDataCateId=' + dataCateId,
							type: 'POST',
							dataType: 'json',
							success: function(res) {
								showTable(res);
							},
							error: function() {
								dialog.failure(Msg_Info.errors_unknown);
							}
						});
						window.fdDataCateId = dataCateId;
						// 列表URL需要替换分组条件
						var _listview = LUI("listview_" + window.curVersion);
						if(_listview && _listview.sourceURL) {
							_listview.sourceURL = Com_SetUrlParameter(_listview.sourceURL, "fdDataCateId", dataCateId);
							_listview.source.url = _listview.sourceURL;
							_listview.source._url = _listview.sourceURL;
							_listview.source.ajaxConfig.url = _listview.sourceURL;
						}
					}

					LUI("lui_matrix_panel").on("layoutFinished", function() {
						// 增加版本状态
						this.navs.forEach(function(n) {
							if($(n.navTitle).find("i").hasClass("matrix_nonactivated")) {
								$(n.navTitle).append('<span class="matrix_nonactivated"><bean:message bundle="sys-organization" key="sysOrgMatrix.version.nonactivated"/></span>');
							}
						});
					}).on("indexChanged", function(evt) {
						// 获取当前点击的页签
						var cur = evt.panel.contents[evt.index.after];
						var title = cur.config.title;
						if(window.curVersion == title) {
							// 取消切换，防止复制渲染
							evt.cancel = true;
							return false;
						} else {
							window.curVersion = title;
						}
						// 根据分组创建矩阵数据
						generateCateData(fdDataCates);
					});
				});
				</c:if>

				// 判断字符串是否以某字符结尾
				window.endWith = function(str, target) {
					var start = str.length-target.length;
					var arr = str.substr(start,target.length);
					if(arr == target){
						return true;
					}
					return false;
				}
				
				// 生成表格
				window.showTable = function(rtnData) {
					var width = <%=request.getAttribute("width")%>;
					var panel = window.matrixPanelArray[window.curVersion];
					var seqTab = $("#" + panel.seq), dataTab = $("#" + panel.data), optTab = $("#" + panel.opt);
					// 删除内容数据
					seqTab.find("tr:gt(0)").remove();
					dataTab.find("tr:gt(0)").remove();
					optTab.find("tr:gt(0)").remove();
					// 处理分页
					showPage(rtnData.page.currentPage, rtnData.page.pageSize, rtnData.page.totalSize);
					// 无数据
					if(rtnData.datas.length == 0) {
						$("#lui_matrix_data_tb_wrap_" + window.curVersion).hide();
						$("#listview_" + window.curVersion).show();
						$("#listview_" + window.curVersion).find(".prompt_container").remove();
						$("#lui_matrix_data_tb_wrap_nodata_" + window.curVersion).show();
						return;
					}
					// 显示表格
					$("#lui_matrix_data_tb_wrap_" + window.curVersion).show();
					$("#listview_" + window.curVersion).hide();
					$("#lui_matrix_data_tb_wrap_nodata_" + window.curVersion).hide();
					// 处理表头
					if(!hasTitle[window.curVersion]) {
						var cols = {};
						for(var i=0; i<rtnData.columns.length; i++) {
							var obj = rtnData.columns[i];
							if(obj.property) {
								cols[obj.property] = obj;
							}
						}
						for(var i=0; i<rtnData.columns.length; i++) {
							var obj = rtnData.columns[i];
							var tr = dataTab.find("tr:eq(0)");
							if(obj.headerClass == "conditional") {
								dataType[obj.property] = "conditional";
								// 判断是否有区间条件
								var temp = obj.property + "_2", colspan = "";
								if(cols[temp]) {
									// 找到了，确定是区间条件
									colspan = "colspan=\"2\"";
								}
								if(!obj.title) {
									continue;
								}
								tr.append("<th class=\"lui_matrix_td_normal_title lui_maxtrix_condition_th\" style=\"min-width:" + width + "px !important\" "+colspan+"><xmp>"+obj.title+"</xmp></th>");							} else if(obj.headerClass == "result") {
								dataType[obj.property] = "result";
								tr.append("<th class=\"lui_matrix_td_normal_title lui_maxtrix_result_th\" style=\"min-width:" + width + "px !important\"><xmp>"+obj.title+"</xmp></th>");							}
						}
						<c:if test="${!empty sysOrgMatrixForm.fdDataCates}">
						tr.prepend("<th style=\"width:70px;\"><bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.note"/></th>");
		               	</c:if>
						hasTitle[window.curVersion] = true;
					}
					// 处理数据
					for(var i=0; i<rtnData.datas.length; i++) {
						var datas = rtnData.datas[i];
						var seqTr = [], dataTr = [], optTr = [], dataTmp = [];
						seqTr.push("<tr style=\"height: 40px;\">");
						dataTr.push("<tr style=\"height: 40px;\">");
						
						for(var j=0; j<datas.length; j++) {
							var data = datas[j];
							if(data.col == "fdId") {
								// 处理ID和序号
					            seqTr.push("<td><input type=\"checkbox\" name=\"List_Selected\" value=\"" + data.value + "\"></td>");
					            seqTr.push("<td name=\"matrix_data_seq\">" + (i + 1) + "</td>");
							} else if(data.col == 'cate') {
								cate = data.value;
								if(cate) {
									dataTr.push("<td style=\"text-align: center;\">");
									dataTr.push(cate);
									dataTr.push("</td>");
								}
							} else if(endWith(data.col, "_2")) {
								// 处理内容
								dataTmp.push("<td class=\"lui_maxtrix_condition_td\"><xmp>");
								dataTmp.push(data.value);
								dataTmp.push("</xmp></td>");
							} else {
								var temp = data.value.split("|||||");
								var val = temp.length > 1 ? temp[1] : "";
								// 处理内容
								var cls = dataType[data.col] == "conditional" ? "lui_maxtrix_condition_td" : "lui_maxtrix_result_td";
								dataTmp.push("<td class=\"" + cls + "\"><xmp>");
								dataTmp.push(val);
								dataTmp.push("</xmp></td>");
							}
						}
						seqTr.push("</tr>");
						seqTab.append(seqTr.join(""));
						dataTr.push(dataTmp.join(""));
						dataTr.push("</tr>");
						dataTab.append(dataTr.join(""));
						// 处理操作
						optTr.push("<tr style=\"height: 40px;\">");
						optTr.push("<td>");
						if(window.canDel) {
							optTr.push("<span class=\"lui_text_primary\"><a href=\"javascript:;\" onclick=\"delData(this);\">${lfn:message('button.delete')}</a></span>");
						}
						optTr.push("</td>");
						optTr.push("</tr>");
						optTab.append(optTr.join(""));
					}
					
					// 调整高度
					var seqTr = seqTab.find("tr"), dataTr = dataTab.find("tr"), optTr = optTab.find("tr");
					dataTr.each(function(i, n) {
						var height = $(n).height();
						$(seqTr[i]).height(height);
						$(optTr[i]).height(height);
					});
				}
				
				window.showPage = function(page, pageSize, totalSize) {
					window.dataTablePage = LUI("matrix_data_table_" + window.curVersion + "_page");
					if(window.dataTablePage) {
						dataTablePage.config.totalSize = totalSize;
						dataTablePage.config.currentPage = page;
						dataTablePage.config.pageSize = pageSize;
						dataTablePage.totalSize = parseInt(totalSize);
						dataTablePage.currentPage = parseInt(page);
						dataTablePage.pageSize = parseInt(pageSize);
						dataTablePage.draw();
					} else {
						setTimeout(function() {showPage(page, pageSize, totalSize);}, 100);
					}
				}

				// 模拟器
				window.simulator = function() {
					var matrixName = encodeURIComponent("<c:out value="${sysOrgMatrixForm.fdName}"/>");
					window.open("${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix_simulator.jsp?matrixId=${sysOrgMatrixForm.fdId}&matrixName=" + matrixName, "_blank");
				}
				
				// 数据下载
				window.downloadMatrixRelation = function() {
					$("#downloadMatrixDataForm").submit();
				}
				
				// 导入数据
				window.importData = function() {
					window.open("${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=importData&fdId=${sysOrgMatrixForm.fdId}", "_blank");
				}
				
				// 删除数据
				window.delData = function(elem) {
					var panel = window.matrixPanelArray[window.curVersion];
					var seqTab = $("#" + panel.seq), dataTab = $("#" +  + panel.data), optTab = $("#" +  + panel.opt);
					var idx = $(elem.parentNode).parent().parent().prevAll().length;
					var tr = seqTab.find("tr:eq(" + idx + ")");
					var id = tr.find("[type=checkbox]").val();
					deleteMatrixRelation(id);
				}
				
				// 删除数据
				window.deleteMatrixRelation = function(id) {
					var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("#matrix_seq_table_" + window.curVersion + " input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteData"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"fdId": "${sysOrgMatrixForm.fdId}", "List_Selected": values}, true),
								dataType : 'json',
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.channel(window.curVersion).publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
					// 取消全选
					$("#matrix_seq_checkbox_" + window.curVersion).prop("checked", false);
				}
				
				// 显示字段信息
				window.showField = function() {
					var typeTr = $("#matrix_field_type"), valuetTr = $("#matrix_field_value");
					for(var i=0; i<con.length; i++) {
						var type = con[i].fdType == 'constant' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.constant"/>' :
								    con[i].fdType == 'numRange' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.numRange"/>' :
									con[i].fdType == 'org' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.org"/>' : 
									con[i].fdType == 'dept' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.dept"/>' : 
									con[i].fdType == 'post' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.post"/>' : 
									con[i].fdType == 'person' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.person"/>' : 
									con[i].fdType == 'group' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.group"/>' : 
									con[i].fdType == 'sys' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.sys"/>' : 
									con[i].fdType == 'cust' ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.cust"/>' : '';
						type = "<select class=\"inputsgl\" disabled><option>" + type + "</option></select>";
						if(con[i].fdType == "sys" || con[i].fdType == "cust") {
							type += "<br><input type=\"text\" class=\"inputsgl\" value=\"" + con[i].fdMainDataText + "\" disabled>";
						}
						typeTr.append("<td class=\"lui_maxtrix_condition_th\">" + type + "</td>");
						valuetTr.append("<td class=\"lui_maxtrix_condition_td\">" + con[i].fdName + "</td>");
					}
					for(var i=0; i<res.length; i++) {
						var type = '<bean:message bundle="sys-organization" key="sysOrgMatrix.result.type.person_post"/>';
						if(res[i].fdType == 'post') {
							type = '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.post"/>';
						} else if(res[i].fdType == 'person') {
							type = '<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.type.person"/>';
						}
						typeTr.append("<td class=\"lui_maxtrix_result_th\"><select class=\"inputsgl\" disabled><option>" + type + "</option></select></td>");
						valuetTr.append("<td class=\"lui_maxtrix_result_td\">" + res[i].fdName + "</td>");
					}
				}
				
				// 重置表格高度
				window.resetHeight = function() {
					var typeTr = $("#matrix_type_table").find("tr"), valueTr = $("#matrix_table").find("tr");
					//重置表格左间距
					var bortab = $(".lui_matrix_field_tb_item_type").width();
					$(".lui_matrix_field_tb_item_condition").css('padding-left',bortab-1);
					valueTr.each(function(i, tr) {
						$(typeTr[i]).height($(tr).height());
					});
				}

				// 字段检测
				window.fieldCheck = function() {
					Com_OpenWindow('<c:url value="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=check&matrixId=${sysOrgMatrixForm.fdId}"/>');
				}

				LUI.ready(function() {
					showField();
					LUI("MatrixTab").on('indexChanged', function(evt) {
						if(evt.index.after == 1) {
							setTimeout(function(){resetHeight();}, 100);
						}
					});
				});
				
			});
		</script>
	</template:replace>
</template:include>
