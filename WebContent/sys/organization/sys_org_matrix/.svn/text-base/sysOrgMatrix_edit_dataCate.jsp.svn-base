<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('home.help')}" order="1" onclick="Com_OpenWindow('sysOrgMatrix_edit_help.jsp');"></ui:button>
			<ui:button text="${lfn:message('button.save')}" order="2" onclick="updateMatrix();" />
	    	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
		<style type="text/css">
			.main_data_div {
				padding-top: 10px;
			}
			.item_tips {
				float: right;
			    margin-top: 5px;
			    margin-right: 20px;
			}
			#TABLE_DocList_Cates {
				margin: 20px 0;
				display: none;
			}
		</style>
		<script type="text/javascript">Com_IncludeFile("dialog.js");</script>
		<script>Com_IncludeFile('doclist.js');</script>
		<script>DocList_Info.push("TABLE_DocList_Cates");</script>
		<script language="JavaScript">
			Com_IncludeFile("dialog.js");
			var allFields = {"con": [{"fdType": "", "fdName": "", "fdMainDataType": "", "fdMainDataText": "", "fdIncludeSubDept":"", "fdIsUnique":"", "fdValueCount": 0}], "res": [{"fdType": "", "fdName": "", "fdValueCount": 0}]};
			var con = [], res = [], fdRelationConditionals = [], fdRelationResults = [], fdDataCates = [];
			<c:forEach items="${sysOrgMatrixForm.fdRelationConditionals}" var="conditional">
			con.push({"fdType": "${conditional.fdType}", "fdName": "<c:out value="${conditional.fdName}" escapeXml="true"/>", "fdMainDataType": "${conditional.fdMainDataType}", "fdMainDataText": "<c:out value="${conditional.fdMainDataText}" escapeXml="true"/>", "fdIncludeSubDept": "${conditional.fdIncludeSubDept}", "fdIsUnique": "${conditional.fdIsUnique}", "fdValueCount": parseInt("${conditional.fdValueCount}"),"fdId": "${conditional.fdId}"});
			fdRelationConditionals.push({"fdId": "${conditional.fdId}","fdName": "<c:out value="${conditional.fdName}" escapeXml="true"/>","fdType": "${conditional.fdType}","fdFieldName": "${conditional.fdFieldName}","fdMainDataType": "${conditional.fdMainDataType}","fdMainDataText": "<c:out value="${conditional.fdMainDataText}" escapeXml="true"/>","fdResultValueIds": "${conditional.fdResultValueIds}","fdIncludeSubDept": "${conditional.fdIncludeSubDept}","fdIsUnique": "${conditional.fdIsUnique}","fdConditionalId": "${conditional.fdConditionalId}","fdConditionalValue": "${conditional.fdConditionalValue}","fdValueCount": parseInt("${conditional.fdValueCount}"),"fdId": "${conditional.fdId}"});
			</c:forEach>
			<c:forEach items="${sysOrgMatrixForm.fdRelationResults}" var="result">
			res.push({"fdType": "${result.fdType}", "fdName": "<c:out value="${result.fdName}" escapeXml="true"/>", "fdValueCount": parseInt("${result.fdValueCount}"),"fdId": "${result.fdId}"});
			fdRelationResults.push({"fdId": "${result.fdId}","fdName": "<c:out value="${result.fdName}" escapeXml="true"/>","fdType": "${result.fdType}","fdFieldName": "${result.fdFieldName}","fdResultValueIds": "${result.fdResultValueIds}","fdResultValueNames": "${result.fdResultValueNames}","fdValueCount": parseInt("${result.fdValueCount}"),"fdId": "${result.fdId}"});
			</c:forEach>
			<c:forEach items="${sysOrgMatrixForm.fdDataCates}" var="cate">
			fdDataCates.push({"fdId": "${cate.fdId}","fdName": "<c:out value="${cate.fdName}" escapeXml="true"/>"});
			</c:forEach>
			allFields.con = con;
			allFields.res = res;
			window.MatrixResult = {"fdId": "${sysOrgMatrixForm.fdId}"};
			window.MatrixResult.fdRelationConditionals = fdRelationConditionals;
			window.MatrixResult.fdRelationResults = fdRelationResults;
			window.MatrixResult.fdDataCates = fdDataCates;
			window.reBuildVersion = [];
		</script>
	</template:replace>
	<template:replace name="content">
		<div style="width: 100%; margin: 10px auto;">
			<html:form action="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveMatrixDataByCate">
				<input type="hidden" name="matrixId" value="${sysOrgMatrixForm.fdId}">
				<c:import url="/sys/organization/sys_org_matrix/sysOrgMatrix_edit_data.jsp" charEncoding="UTF-8" />
			</html:form>
		</div>
		
		<!-- 模板下载 -->
		<form id="downloadTemplateForm" method="post"></form>
		<!-- 矩阵数据下载 -->
		<form id="downloadMatrixDataForm" method="post"></form>
		<!-- 矩阵数据提交 -->
		<form id="saveMatrixDataForm" action="${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveAllData" method="post">
			<input type="hidden" name="fdMatrixId">
			<input type="hidden" name="matrixData">
		</form>
		<!-- 临时数据，不需要提交，只限于本地临时使用 -->
		<form action="#" onsubmit="return false;">
			<input type="hidden" name="__idField">
			<input type="hidden" name="__nameField">
		</form>
		<script language="JavaScript">
			Com_IncludeFile("relation_common.js", '<c:url value="/sys/xform/designer/relation/" />', null, true);
			validator = $KMSSValidation(document.forms['sysOrgMatrixForm']);
			window.downloadTemplateFormAction = "${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=downloadTemplate&fdId=";
			window.downloadMatrixDataFormAction = "${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=exportMatrixData&fdId=";
			seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {

				// 选择条件
				window.conditionalChange = function(value, elem) {
					var name = $(elem).attr("name");
					var div = $(elem).parent().find("div.main_data_div");
					var deptDiv = $(elem).parent().find("div.org_dept_div");
					if(value == "sys" || value == "cust") {
						deptDiv.hide();
						div.show();
						div.find("input").removeAttr("disabled");
						var mainDataType = $("[name='" + name.replace("fdType", "fdMainDataType") + "']");
						var mainDataText = $("[name='" + name.replace("fdType", "fdMainDataText") + "']");
						$(mainDataText).attr("validate","required")
						//readOnly和require冲突，手动使其失焦
						$(mainDataText).focus(function(){
							$(this).blur();
						});
						dialogMainData(value, mainDataType, mainDataText);
					}else if(value == "org"){
						deptDiv.hide();
						div.hide();
					}else if(value == "dept"){
						deptDiv.show();
						deptDiv.find("input").removeAttr("disabled");
						div.hide();
					} else {
						div.hide();
						deptDiv.hide();
						div.find("input").attr("disabled", "disabled");
					}
					// 表格高度重置
					resizeTable();
				}
				
				window.selectMainData = function(elem) {
					var type = $(elem).parent().parent().find("[name$='fdType']").val();
					var mainDataType = $(elem).parent().find("[name$='fdMainDataType']");
					var mainDataText = $(elem).parent().find("[name$='fdMainDataText']");
					dialogMainData(type, mainDataType, mainDataText);
				}
				window.dialogMainData = function(type, mainDataType, mainDataText) {
					if(type == "sys") {
						type = "MAINDATAINSYSTEM";
					} else if(type == "cust") {
						type = "MAINDATACUSTOM";
					}
					var rtnVal = {
							"_source" : type,
							"_key" : mainDataType.val(),
							"_keyName" : mainDataText.val()
						};
					var url = '<c:url value="/sys/xform/maindata/dialog/dialog.jsp?springBean=sysFormMainDataInsystemControlTreeBean&infoBean=sysFormMainDataInsystemControlTreeInfo&url=sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do&range=fdRangeMatrix" />';
					if(type == "MAINDATACUSTOM") {
						url = '<c:url value="/sys/xform/maindata/dialog/dialog.jsp?springBean=sysFormMainDataCustomControlTreeBean&infoBean=sysFormMainDataCustomControlTreeInfo&url=sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do" />';
					}
					new ModelDialog_Show(url, rtnVal, function(rtnVal) {
						if(rtnVal && rtnVal._key) {
							mainDataType.val(rtnVal._key.substr(type.length + 1));
							mainDataText.val(rtnVal._keyName);
						}
				    }).show();
				}
				
				// 更新数据
				window.updateMatrix = function() {
					// 保存矩阵数据
					var cateid = $("#lui_matrix_panel_content_" + window.curVersion).find(".lui_maxtrix_cate_item_dis").data("cateid");
					saveCateData(window.curVersion, cateid, function(res) {
						if(res.status) {
							Com_OpenWindow("${LUI_ContextPath}/resource/jsp/success.jsp", "_self");
						} else {
							dialog.result(res);
						}
					});
				}
				
				// 保存矩阵数据
				window.saveMatrixData = function() {
					var __data = {};
					$("table[name^='matrix_data_table_']").each(function(idx, table) {
						var datas = [], version = $(table).data('version');
						$("#matrix_seq_table_" + version + " tbody").find("tr:gt(0)").each(function(i, n) {
							var obj = {};
							var fdId = $(n).find("[type=checkbox]").val();
							if(fdId.length > 0 && fdId != 'on' && fdId.indexOf("new_") == -1) {
								obj['fdId'] = fdId;
							}
							var hasVal = false;
							$(table).find("tbody tr:eq(" + (i + 1) + ")").find("[data-type=fieldId]").each(function(j, m) {
								var name = $(m).attr("name").replace(/\[[^\]]+\]/g, '');
								var value = $(m).val();
								if(value.length > 0) {
									hasVal = true;
									obj[name] = value;
								}
							});
							if(hasVal) {
								datas.push(obj);
							}
						});
						__data[version] = datas;
					});
					// 提交数据
					var downloadForm = $("#saveMatrixDataForm");
					downloadForm.find("[name=fdMatrixId]").val(window.MatrixResult.fdId);
					downloadForm.find("[name=matrixData]").val(JSON.stringify(__data));
					downloadForm.submit();
				}
				
				// 多语言资源信息
				window.Msg_Info = {
						sysOrgMatrix_result_maxLen: '<bean:message bundle="sys-organization" key="sysOrgMatrix.result.maxLen"/>',
						errors_unknown: '<bean:message key="errors.unknown"/>',
						button_select: '<bean:message key="button.select"/>',
						button_delete: '<bean:message key="button.delete"/>',
						page_comfirmDelete: '<bean:message key="page.comfirmDelete"/>',
						button_ok: '<bean:message key="button.ok" />',
						button_cancel: '<bean:message key="button.cancel" />',
						button_clear: '<bean:message key="button.clear" />',
						delete_version: '<bean:message bundle="sys-organization" key="sysOrgMatrix.version.comfirmDelete"/>',
						select_notice: '<bean:message bundle="sys-organization" key="sysOrgMatrix.data.delete.notice"/>',
						button_replace: '<bean:message bundle="sys-organization" key="sysOrgMatrix.edit.batchReplace.button"/>',
						replace_note: '<bean:message bundle="sys-organization" key="sysOrgMatrix.edit.batchReplace.note"/>'
				};
				
				/* 生成分组标签 */
				window.generateCateData = function (dataCates) {
					if(dataCates && dataCates.length > 0) {
						// 为每个版本增加分组信息
						_generateCateData(dataCates);
					} else {
						// 如果是编辑状态，可能会存在分组按钮，需要删除
						$(".matrix_data_cate").empty();
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
								for(var i=0; i<dataCates.length; i++) {
									var data = dataCates[i];
									cates.push('<a class="com_bgcolor_d" href="javascript:;" data-cateid="' + data.fdId + '" onclick="switchCateData(this, \'' + data.fdId + '\');"><pre>' + Com_HtmlEscape(data.fdName) + '</pre></a>');
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
			});
		</script>
	</template:replace>
</template:include>
