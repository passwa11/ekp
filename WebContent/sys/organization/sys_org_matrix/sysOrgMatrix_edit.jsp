<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('home.help')}" order="1" onclick="Com_OpenWindow('sysOrgMatrix_edit_help.jsp');"></ui:button>
			<c:choose>
				<c:when test="${ sysOrgMatrixForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.save')}" order="3" onclick="saveMatrix();" />
				</c:when>
				<c:when test="${ sysOrgMatrixForm.method_GET == 'edit' }">
					<ui:button text="${lfn:message('button.save')}" order="3" onclick="updateMatrix();" />
				</c:when>
			</c:choose>
			<ui:button id="sys_ui_step_pre" text="${lfn:message('sys-ui:ui.step.pre')}" 
				order="4" onclick="clickSysUiStep('pre')" style="display:none">
			</ui:button>
			<ui:button id="sys_ui_step_next" text="${lfn:message('sys-ui:ui.step.next')}" order="5" onclick="clickSysUiStep('next')">
			</ui:button>
	    	<ui:button text="${lfn:message('button.close') }" order="2" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
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
				margin: 20px 0 0 0;
			}
		</style>
		<script type="text/javascript">Com_IncludeFile("dialog.js");</script>
		<script type="text/javascript">Com_IncludeFile('doclist.js');</script>
		<script type="text/javascript">DocList_Info.push("TABLE_DocList_Cates");</script>
		<script language="JavaScript">
			LUI.ready(function() {
	        	//不加载导出机制的按钮
	        	var toolbar = LUI('toolbar');
	        	toolbar.dataInit = false;
	        });
			var allFields = {"con": [{"fdType": "constant", "fdName": "常量", "fdMainDataType": "", "fdMainDataText": "", "fdIncludeSubDept":"", "fdIsUnique":"", "fdValueCount": 0}], "res": [{"fdType": "person", "fdName": "人员", "fdValueCount": 0}]};
			<c:if test="${ sysOrgMatrixForm.method_GET == 'edit' }">
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
			</c:if>
			window.reBuildVersion = [];
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				// 矩阵数据分组
				window._DocList_DeleteRow = function(elem) {
					var trs = $("#TABLE_DocList_Cates").find("tr");
					if(trs.length > 2) {
						dialog.confirm('<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.del.node"/>', function(value) {
							if(value == true) {
								var tr = $(elem).parents("tr");
								DocList_DeleteRow(tr[0]);
							}
						});
					} else {
						dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.noEmpty"/>');
					}
				}
				// 增加行，需要获取一个分组ID
				window._DocList_AddRow = function(tab) {
					$.post('${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getId', function(id) {
						var def = {"fdDataCates[!{index}].fdId": id};
						var cateTab = $("#TABLE_DocList_Cates");
						if(cateTab.find("tr").length < 2) {
							def['fdDataCates[!{index}].fdName'] = "${defCate}";
						}
						DocList_AddRow(tab, null, def);
					});
				}
				
				window.enabledCate = function(val) {
					var tab = $("#docList_Cates_container");
					if(val === 'true' || val === true) {
						tab.show();
						// 移除所有禁用标识
						tab.find("input").removeAttr("disabled");
						// 如果没有分组，增加一个默认
						if(tab.find("tr").length < 2) {
							tab.find(".sysOrgMatrixAppendCate").click();
						}
					} else {
						tab.hide();
						// 禁用所有输入框
						tab.find("tr").each(function(i, n) {
							if(!$(n).attr("style") || $(n).attr("style").indexOf("display") == -1) {
								$(n).find("input").attr("disabled", "disabled");
							}
						});
					}
				}
				
				$(function() {
					enabledCate("${sysOrgMatrixForm.fdIsEnabledCate}");
				});
			});
		</script>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
	</template:replace>
	<template:replace name="content">
		<div style="width: 100%; margin: 10px auto;">
		<p class="txttitle">
			<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
			<c:if test="${ sysOrgMatrixForm.method_GET == 'edit' }">
			(<c:out value="${sysOrgMatrixForm.fdName}"/>)
			</c:if>
			<bean:message key="button.edit"/>
		</p>
		<html:form action="/sys/organization/sys_org_matrix/sysOrgMatrix.do">
		<html:hidden property="fdId"/>
		<input type="hidden" name="toData" value="false"/>
		<ui:step id="__step" onSubmit="commitMethod('save','false');" layout="sys.ui.step.fixed" cfg-submitText="${lfn:message('sys-organization:sysOrgMatrix.saveFieldToData')}">
			<ui:content title="${lfn:message('sys-organization:sysOrgMatrix.edit.base')}" toggle="true">
				<table class="tb_normal sysOrgMatrixTable" width=100%>
					<!-- 矩阵名称 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.fdName"/>
						</td>
						<td width=85% colspan="5" class="sysOrgMatrixTableInput">
						    <xform:text property="fdName" style="width:90%"></xform:text>
						</td>
					</tr>
					<!-- 矩阵分类 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.fdCategory"/>
						</td><td width=35%>
							<html:hidden property="fdCategoryId"/>
							<html:text style="width:90%" property="fdCategoryName" readonly="true" styleClass="inputsgl"/>
							<a href="#" onclick="Dialog_Tree(
								false,
								'fdCategoryId',
								'fdCategoryName',
								null,
								Tree_GetBeanNameFromService('sysOrgMatrixCateService', 'hbmParent', 'fdName:fdId'),
								'<bean:message bundle="sys-organization" key="table.sysOrgMatrixCate"/>');">
								<bean:message key="dialog.selectOrg"/>
							</a>
						</td>
						<!-- 排序号 -->
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.fdOrder"/>
						</td>
						<td width=15%>
						    <xform:text property="fdOrder" style="width:90%"></xform:text>
						</td>
						<!-- 是否禁用 -->
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.fdIsAvailable"/>
						</td>
						<td width=15%>
						    <c:if test="${sysOrgMatrixForm.matrixType=='1'}">
						    	<sunbor:enumsShow value="${sysOrgMatrixForm.fdIsAvailable}" enumsType="sys_org_available" />
							</c:if>
							<c:if test="${sysOrgMatrixForm.matrixType!='1'}">
								<sunbor:enums property="fdIsAvailable" value="${sysOrgMatrixForm.fdIsAvailable}" enumsType="sys_org_available" elementType="select"/>
							</c:if>
						</td>
					</tr>

					<!-- 矩阵描述 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.fdDesc"/>
						</td><td width=90% colspan="5">
							<xform:textarea property="fdDesc" style="width:90%"></xform:textarea>
						</td>
					</tr>

					<!-- 数据分组 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate"/>
						</td>
                        <td width=90% colspan="5" style="padding:0 0 0 8px;">
							<ui:switch property="fdIsEnabledCate" checked="false" onValueChange="enabledCate(this.checked);"
                          		enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"/>
                            <div id="docList_Cates_container">
    							<table class="tb_normal" width=90% id="TABLE_DocList_Cates" align="center">
    								<tr>
    									<td align="center" class="td_normal_title" style="width:40%">
    										<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.name"/>
    									</td>
    									<td align="center" class="td_normal_title" style="width:40%">
    										<bean:message bundle="sys-organization" key="sysOrgMatrix.dataCate.manager"/>
    									</td>
    									<td align="center" class="td_normal_title" style="width:20%">
    										<span class="cate_add">
                                             <bean:message key="title.option"/>
                                            </span>
    									</td>
    								</tr>
    								<!-- 基准行 -->
    								<tr KMSS_IsReferRow="1" style="display: none">
    									<td>
    										<input type="hidden" name="fdDataCates[!{index}].fdId" value="">
    										<input type="hidden" name="fdDataCates[!{index}].fdOrder" value="!{index}">
    										<input type="text" name="fdDataCates[!{index}].fdName" validate="required" style="width:94%" class="inputsgl cateInput">
    										<span class="txtstrong">*</span>
    									</td>
    									<td>
    										<xform:address style="width:94%" propertyId="fdDataCates[!{index}].fdElementId" propertyName="fdDataCates[!{index}].fdElementName" orgType='ORG_TYPE_POSTORPERSON' className="input" isExternal="false"></xform:address>
    									</td>
    									<td align="center">
    										<a class="btn_txt cate_del" onclick="_DocList_DeleteRow(this);"><bean:message key="button.delete"/></a>
    									</td>
    								</tr>
    								<c:forEach items="${sysOrgMatrixForm.fdDataCates}" var="item" varStatus="vstatus">
    								<tr KMSS_IsContentRow="1">
    									<td align="center">
    										<input type="hidden" name="fdDataCates[${vstatus.index}].fdId" value="${item.fdId}">
    										<input type="hidden" name="fdDataCates[${vstatus.index}].fdOrder" value="${vstatus.index}">
    										<input type="text" name="fdDataCates[${vstatus.index}].fdName" style="width:94%" class="inputsgl cateInput" validate="required" value="<c:out value="${item.fdName}"/>">
    										<span class="txtstrong">*</span>
    									</td>
    									<td align="center">
    										<xform:address style="width:94%" propertyId="fdDataCates[${vstatus.index}].fdElementId" propertyName="fdDataCates[${vstatus.index}].fdElementName" orgType='ORG_TYPE_POSTORPERSON' className="input"></xform:address>
    									</td>
    									<td align="center">
    										<c:if test="${item.count < 1}">
    										<a class="btn_txt cate_del" onclick="_DocList_DeleteRow(this);"><bean:message key="button.delete"/></a>
    										</c:if>
    									</td>
    								</tr>
    								</c:forEach> 
    							</table>
           	                    <div class="sysOrgMatrixAppendCate" onclick="_DocList_AddRow('TABLE_DocList_Cates')">
                                   <span></span>
                                   <bean:message key="title.option"/>
                                </div>
                          </div>
						</td>
					</tr>
					<!-- 可使用者 -->
					<tr>
						<td class="td_normal_title" width=10%><bean:message bundle="sys-organization" key="sysOrgMatrix.authReaders"/></td>
						<td  width=90% colspan="5" style="font-size:0;"><html:hidden property="authReaderIds" /> <html:textarea
							property="authReaderNames" style="width:84%" readonly="true" /> <a
							href="#"  class="sysOrgMatrixSelectOther" 
							onclick="Dialog_Address(true, 'authReaderIds','authReaderNames', ';', null, null, null, null, null, null, null, null, null, false);"></a><br>
							<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
							        <!-- （为空则本组织人员可使用） -->
							        <bean:message bundle="sys-organization" key="sysOrgMatrix.authReaders.describe.outter"/>
							    <% } else { %>
							        <!-- （为空则所有内部人员可使用） -->
							        <bean:message bundle="sys-organization" key="sysOrgMatrix.authReaders.describe.inner"/>
							    <% } %>
							<% } else { %>
							    <!-- （为空则所有人可使用） -->
							    <bean:message bundle="sys-organization" key="sysOrgMatrix.authReaders.describe"/>
							<% } %>
					   </td>
					</tr>
					<!-- 可维护者 -->
					<tr>
						<td class="td_normal_title" width=10%><bean:message bundle="sys-organization" key="sysOrgMatrix.authEditors"/></td>
						<td width=90% colspan="5" style="font-size:0;"><html:hidden property="authEditorIds" /> <html:textarea
							property="authEditorNames" style="width:84%" readonly="true" /> <a
							href="#"  class="sysOrgMatrixSelectOther" 
							onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';', null, null, null, null, null, null, null, null, null, false);"></a><br>
							<bean:message bundle="sys-organization" key="sysOrgMatrix.authEditors.describe"/>
						</td>
					</tr>
				</table>
				</ui:content>
				<ui:content title="${lfn:message('sys-organization:sysOrgMatrix.edit.field')}" toggle="true">
					<c:import url="/sys/organization/sys_org_matrix/sysOrgMatrix_edit_field.jsp" charEncoding="UTF-8" />
				</ui:content>
			</ui:step>
			</html:form>
		</div>

		<script language="JavaScript">
			Com_IncludeFile("relation_common.js", '<c:url value="/sys/xform/designer/relation/" />', null, true);
			window.validator = $KMSSValidation(document.forms['sysOrgMatrixForm']);
			window.STEP = {"cur": parseInt("${JsParam.step}")};
			seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
				// 跳到对应的步骤
				window.toStep = function(step) {
					if(!step) return;
					if($('[data-lui-mark="step.title"] li').length > 0) {
						LUI("__step").fireJump(step);
					} else {
						setTimeout(function(){toStep(step);}, 100);
					}
				}
				
				$(function() {
					toStep('${JsParam.step}');
				});
				
				// 下一步/上一步
				window.clickSysUiStep = function(stepFlag) {
					$("input[data-lui-mark='step." + stepFlag + "']").click();
				};

				// 选择条件
				window.conditionalChange = function(value, elem) {
					var name = $(elem).attr("name");
					var div = $(elem).parent().find("div.main_data_div");
					var deptDiv = $(elem).parent().find("div.org_dept_div");
					$(elem).parent().find("div.unique_div").show();
					// 设置隐藏属性
					$("[name='" + name.replace("fdType1", "fdType") + "']").val(value);
					if(value == "sys" || value == "cust") {
						deptDiv.hide();
						div.show();
						div.find("input").removeAttr("disabled");
						var mainDataType = $("[name='" + name.replace("fdType1", "fdMainDataType") + "']");
						var mainDataText = $("[name='" + name.replace("fdType1", "fdMainDataText") + "']");
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
						if(value.indexOf('Range') > -1) {
							// 区间类型不需要唯一校验
							$(elem).parent().find("div.unique_div").hide();
						}
					}
					// 表格高度重置
					resizeTable();
				}
				
				window.selectMainData = function(elem) {
					var type = $(elem).parent().parent().find("[name$='fdType1']").val();
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
				
				// 保存数据
				window.saveMatrix = function(toData) {
					if(toData) {
						// 保存并跳到数据页面
						$("[name=toData]").val("true");
					}
					// 保存矩阵基本信息
					saveMatrixField();
				}
				
				// 更新数据
				window.updateMatrix = function(toData) {
					if(toData) {
						// 保存并跳到数据页面
						$("[name=toData]").val("true");
					}
					// 保存矩阵基本信息
					saveMatrixField();
				}

				window.saveMatrixField = function() {
					// 表单校验
					if(!validator.validate()) {
						return false;
					}
					Com_Submit(document.sysOrgMatrixForm, 'saveMatrixField');
				}

				window.commitMethod = function() {
					<c:choose>
						<c:when test="${ sysOrgMatrixForm.method_GET == 'add' }">
						saveMatrix(true);
						</c:when>
						<c:when test="${ sysOrgMatrixForm.method_GET == 'edit' }">
						updateMatrix(true);
						</c:when>
					</c:choose>
				}

				// 监听STEP的变化
				topic.subscribe('JUMP.STEP', function(evt) {
					window.STEP = evt;
					setTimeout(function(){resizeTable();}, 100);
					var nameInput = $("input[type=text][name=fdName]");
					var cateInput = $("input[type=text].cateInput");
					//#105937 全局的validate不包含fdName。。手动校验
					if(evt && evt.cur == 1 && evt.last == 0){
						if(nameInput[0] && (nameInput[0].value == '' || typeof(nameInput[0].value) == 'undefine')) {
							//触发校验器。。。
							nameInput[0].focus();
							nameInput[0].blur();
							nameInput[0].focus();
							evt.cancel = true;
							return false;
						}
						if(cateInput){
							$(cateInput).each(function(index,_input){
								//未开启分组
								if(!$(_input).prop('disabled')&&$(_input).val()==''){
									$(_input).focus();
									$(_input).blur();
									$(_input).focus();
									evt.cancel = true;
									return false;
								}
							});
						}
					}
				});
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

			});
		</script>
	</template:replace>
</template:include>
