<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="auto">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/> - <bean:message key="global.init.export.data"/>
	</template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
		<link href="${LUI_ContextPath}/resource/style/default/doc/document.css" rel="stylesheet" type="text/css" />
		<script>
			Com_IncludeFile("dialog.js");
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<script id="upload_result" type="text/config">
			${result}
		</script>
		<script language="JavaScript">
			// 当前选择的版本
			window.curVersion = undefined;
			// 页签集合，保存所有页签对象，可以通过版本名称获取
			window.matrixPanelArray = {};
			// 多语言资源信息
			window.Msg_Info = {
					errors_unknown: '<bean:message key="errors.unknown"/>',
					button_select: '<bean:message key="button.select"/>',
					button_delete: '<bean:message key="button.delete"/>',
					button_ok: '<bean:message key="button.ok" />',
					button_cancel: '<bean:message key="button.cancel" />',
					button_clear: '<bean:message key="button.clear" />'
			};
			// 初始化参数信息
			window.matrixId = undefined;
			window.conditionalTitle = undefined;
			window.resultTitle = undefined;
			try {
				var result = JSON.parse($("#upload_result").text());
				if(window.console) {
					console.log("导入失败：", result);
				}
				window.matrixId = result.matrixId;
				window.conditionalTitle = result.conditionalTitle;
				window.cateTitle =  result.cateTitle;
				window.resultTitle = result.resultTitle;
			} catch(err) {}
		</script>
	</template:replace>
	<template:replace name="body">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
    	 	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
		
		<div style="width: 95%; margin: 20px auto;">
			<!-- 矩阵导入成功 Starts  -->
	        <div class="lui_maxtrix_fail_wrap">
	            <!-- 图标 -->
	            <span class="lui_maxtrix_icon icon_error"></span>
	            <p class="lui_maxtrix_desc">
	            	<c:if test="${!empty error}">
	            		${ error }
	            		<a class="lui_text_primary" href="javascript:back();"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.back"/></a>
	            	</c:if>
	            	<c:if test="${empty error}">
		            	<bean:message bundle="sys-organization" key="sysOrgMatrix.import.error.note1"/>${sucCount}
		            	<bean:message bundle="sys-organization" key="sysOrgMatrix.import.error.note2"/><em>${failCount}</em>
		            	<bean:message bundle="sys-organization" key="sysOrgMatrix.import.error.note3"/>
		            	<a class="lui_text_primary" href="javascript:back();"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.back"/></a>
	                </c:if>
	            </p>
	        </div>
	        <!-- 矩阵导入成功 Ends  -->
	        <c:if test="${empty error}">
	        <!-- 表格展示区域 Starts -->
	        <div class="lui_maxtrix_table_wrap">
	            <!-- 操作区域 -->
	            <div class="lui_maxtrix_toolbar">
	                <div class="lui_maxtrix_toolbar_l">
	                    <p class="lui_maxtrix_txt_error">
	                       	<bean:message bundle="sys-organization" key="sysOrgMatrix.import.error.title"/>
	                    </p>
	                </div>
	                <div class="lui_maxtrix_toolbar_r">
	                    <a class="com_bgcolor_d" href="javascript:downloadErrorData();"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.downloadError"/></a>
	                    <a class="com_bgcolor_d" href="javascript:reupload();"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.reupload"/></a>
	                </div>
	            </div>
	        </div>
	        <!-- 表格展示区域 Ends -->
			<html:form action="/sys/organization/sys_org_matrix/sysOrgMatrix.do">
	        <ui:tabpanel id="lui_matrix_panel" layout="sys.ui.tabpanel.sucktop" var-average='false' var-useMaxWidth='true'>
				<c:forEach items="${ result.datas }" var="data">
					<c:if test="${fn:length(data.value.datas) > 0}">
						<ui:content id="lui_matrix_panel_content_${ data.key }" title="${ data.key }">
							<!-- 矩阵卡片 - 左右移动 Starts  -->
						   <div class="lui_matrix_data_tb_wrap">
						       <!-- 类型 -->
						       <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_l">
						           <table id="matrix_seq_table_${ data.key }" class="lui_matrix_tb_normal">
						               <tr style="height: 40px;">
						                   <th class="lui_matrix_td_normal_title"><input id="matrix_seq_checkbox_${ data.key }" type="checkbox"></th>
						                   <th class="lui_matrix_td_normal_title"><bean:message bundle="sys-organization" key="sysOrgMatrix.reimport.line.number"/></th>
						               </tr>
						           </table>
						       </div>
						       <!-- 条件数据 -->
						       <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_c">
						           <table id="matrix_data_table_${ data.key }" class="lui_matrix_tb_normal">
						               <tr style="height: 40px;">
						               </tr>
						           </table>
						       </div>
						       <!-- 操作数据 -->
						       <div class="lui_matrix_data_tb_item lui_matrix_data_tb_item_r">
						           <table id="matrix_opt_table_${ data.key }" class="lui_matrix_tb_normal">
						               <tr style="height: 40px;">
						                   <th class="lui_matrix_td_normal_title"><bean:message key="list.operation"/></th>
						               </tr>
						           </table>
						       </div>
						   </div>
							<div class="matrix_page"></div>
							<div id="upload_result_${data.key}" style="display: none;">
						      ${ data.value }
						   </div>
						   <!-- 矩阵卡片 Ends  -->
						   <script language="JavaScript">
							   seajs.use(['lui/jquery','lui/dialog', 'lui/topic', 'sys/organization/resource/js/matrixErrorPanel'], function($, dialog, topic, matrixErrorPanel) {
									var upload_result = $("#upload_result_${data.key}").text();
									console.log(upload_result);
							   	    var matrixPanel = new matrixErrorPanel.MatrixPanel({'version': '${data.key}', 'result': JSON.parse(upload_result)});
									matrixPanelArray['${data.key}'] = matrixPanel;
									matrixPanel.render();
									matrixPanel.showTitle();
									matrixPanel.showData(matrixPanel.page);
								});
						   </script>
						</ui:content>
					</c:if>
				</c:forEach>
			</ui:tabpanel>
			</html:form>
		    </c:if>
		</div>
		<!-- 下载错误数据 -->
		<form id="downloadErrorForm" action="${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=exportMatrixData" method="post">
			<input type="hidden" name="fdId">
			<input type="hidden" name="type" value="error">
			<input type="hidden" name="datas">
		</form>
		<!-- 重新导入数据 -->
		<form id="saveMatrixDataForm" action="${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveMatrixData" method="post">
			<input type="hidden" name="fdId">
			<input type="hidden" name="type" value="compensate">
			<input type="hidden" name="datas">
		</form>
		<!-- 临时数据，不需要提交，只限于本地临时使用 -->
		<form action="#" onsubmit="return false;">
			<input type="hidden" name="__idField">
			<input type="hidden" name="__nameField">
		</form>
		<script language="JavaScript">
		validator = $KMSSValidation(document.forms['sysOrgMatrixForm']);
		seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
			// 数值区间校验
			validator.addValidator('numRange', "<bean:message key='sysOrgMatrix.range.form.error' bundle='sys-organization' />", function(v, e, o) {
				var name = e.name, name2;
				if(name.indexOf("_2") > -1) {
					name2 = name;
					name = name.substr(0, name.length -2);
				} else {
					name2 = name + "_2";
				}
				var val = $("[name='" + name + "']").val(),
						val2 = $("[name='" + name2 + "']").val(),
						flag = false;
				if(val == "" || val2 == "") {
					flag = true;
				} else {
					flag = parseFloat(val) <= parseFloat(val2);
				}
				if(flag) {
					// 校验通过，修改数据
					matrixPanelArray[window.curVersion].updateData($(e), val2, val);
				}
				return flag;
			});

			window.constantChange = function(e) {
				// 常量修改
				matrixPanelArray[window.curVersion].updateData($(e), e.value, e.value);
			}

			// 模板下载
			window.downloadTemplate = function() {
				$("#downloadTemplateForm").submit();
			};
			
			// 返回导入页
			window.back = function() {
				window.location.href = "${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=importData&fdId=" + result.matrixId;
			}
			
			// 表单提交
			window.__submitForm = function(formId) {
				// 表单校验
				if(!validator.validate()) {
					return false;
				}
				// 数据合并，只能在提交前调用，检查一下原来有错误的数据
				var formData = {};
				for(var i in matrixPanelArray) {
					var version = matrixPanelArray[i].version;
					var _data = matrixPanelArray[i].result;
					for(var i=0; i<_data.datas.length; i++) {
						var dataErr = _data.datas[i].dataErr;
						var dataSuc = _data.datas[i].dataSuc;
						for(var j=0; j<dataErr.length; j++) {
							var err = dataErr[j], fix = false;
							for(var k=0; k<dataSuc.length; k++) {
								var suc = dataSuc[k];
								if(err.index == suc.index) {
									fix = true;
									break;
								}
							}
							if(!fix) {
								dataSuc.push(err);
							}
						}
					}
					formData[version] = _data.datas;
				}
				var __form = $("#" + formId);
				$(__form).find("[name=fdId]").val(window.matrixId);
				$(__form).find("[name=datas]").val(JSON.stringify(formData));
				__form.submit();
			}
			
			// 下载错误数据
			window.downloadErrorData = function() {
				__submitForm("downloadErrorForm");
			}
			
			// 重新导入
			window.reupload = function() {
				__submitForm("saveMatrixDataForm");
			}
			/* 删除一行 */
			window.delData = function(elem) {
				window.matrixPanelArray[window.curVersion].delData(elem);
			}
			// 回调
			window.dialogCallback = function(rtnVal, idField, nameField) {
				var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
				var _idField = curArea.find("input[name='" + idField + "']");
				var _nameField = curArea.find("input[name='" + nameField + "']");
				var id = [], name = [];
				for(var i=0; i<rtnVal.data.length; i++) {
					id.push(rtnVal.data[i]["id"]);
					name.push(rtnVal.data[i]["name"]);
				}
				var td = $(_idField).parents("td");
				matrixPanelArray[window.curVersion].updateData(td, id.join(";"), name.join(";"));
			}
			
			/* 地址本 */
			window.Dialog_Address_Cust = function(mulSelect, idField, nameField, splitStr, selectType, action) {
				var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
				var _idField = curArea.find("input[name='" + idField + "']");
				var _nameField = curArea.find("input[name='" + nameField + "']");
				// 往临时表单填充数据
				$("input[name='__idField']").val(_idField.val());
				$("input[name='__nameField']").val(_nameField.val());
				Dialog_Address(mulSelect, "__idField", "__nameField", splitStr, selectType, function(result) {
					if(result.data.length > 0) {
						var ids = [], names = [];
						for(var i=0; i<result.data.length; i++) {
							ids.push(result.data[i].id);
							names.push(result.data[i].name);
						}
						_idField.val(ids.join(";"));
						_nameField.val(names.join(";"));
					} else {
						_idField.val("");
						_nameField.val("");
					}
					if(action) {
						action(result, idField, nameField);
					}
					// 清除临时表单填充数据
					$("input[name=__idField]").val("");
					$("input[name=__nameField]").val("");
				});
			}

			/* 主要处理人+岗位的数据 */
			window.resultCheck2 = function(rtnVal, idField, nameField) {
				var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
				var _idField = curArea.find("input[name='" + idField + "']");
				var _nameField = curArea.find("input[name='" + nameField + "']");
				var split = idField.split("_"),
					id = split[0],
					type = split[1],
					field = curArea.find("input[name='" + id + "']"),
					value = field.val() || "{}",
					json = JSON.parse(value),
					_ids = [],
					_names = [];
				if(rtnVal && rtnVal.data && rtnVal.data.length > 0) {
					// 增加或替换
					var ___id = rtnVal.data[0].id,
					___name = rtnVal.data[0].name;
					json[type] = {'id':___id, 'name': ___name};
				} else {
					// 删除
					delete json[type];
				}
				field.val(JSON.stringify(json));
				for(var t in json) {
					var ___json = json[t];
					_ids.push(___json.id);
					_names.push(___json.name);
				}
				var td = field.parents("td");
				matrixPanelArray[window.curVersion].updateData(td, _ids.join(";"), _names.join(";"));
			}
			
			/* 自定义数据 */
			window.Dialog_CustData = function(mulSelect, idField, nameField, splitStr, treeBean, treeTitle) {
				var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
				var _idField = curArea.find("input[name='" + idField + "']");
				var _nameField = curArea.find("input[name='" + nameField + "']");
				// 往临时表单填充数据
				$("input[name='__idField']").val(_idField.val());
				$("input[name='__nameField']").val(_nameField.val());
				Dialog_Tree(mulSelect, "__idField", "__nameField", splitStr, treeBean, treeTitle, null, function(result) {
					var id = "", name = "";
					if(result.data.length > 0) {
						id = result.data[0].id;
						name = result.data[0].name;
					}
					_idField.val(id);
					_nameField.val(name);
					// 处理提示标识
					var td = _idField.parents("td");
					matrixPanelArray[window.curVersion].updateData(td, id, name);
					// 清除临时表单填充数据
					$("input[name=__idField]").val("");
					$("input[name=__nameField]").val("");
				});
			}
			
			window.updateCate = function(sel) {
				var td = $(sel).parents("td");
					opt = $(sel).find("option:selected");
				matrixPanelArray[window.curVersion].updateData(td, opt.val(), opt.text());
			}

			// 系统主数据
			window.Dialog_MainData = function(fieldId, fieldName, title) {
				var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
				var selected = curArea.find("input[name='" + fieldId + "']").val();
				// fieldName过滤[X]字符
				var _fieldName = fieldName.replace(/\[[^\]]+\]/g, '');
				dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrixData_mainData.jsp?matrixId=" + result.matrixId + "&fieldName=" + _fieldName + "&selected=" + selected,
						title, function(data) {
					if(data) {
						if(data == "clear") {
							curArea.find("input[name='" + fieldId + "']").val("");
							curArea.find("input[name='" + fieldName + "']").val("");
						} else {
							curArea.find("input[name='" + fieldId + "']").val(data.id);
							curArea.find("input[name='" + fieldName + "']").val(data.name);
						}
						// 处理提示标识
						var td = curArea.find("input[name='" + fieldId + "']").parents("td");
						matrixPanelArray[window.curVersion].updateData(td, data.id, data.name);
					}
				}, {
					width : 1200,
					height : 600,
					buttons : [{
						name : Msg_Info.button_ok,
						focus : true,
						fn : function(value, dialog) {
							if(dialog.frame && dialog.frame.length > 0) {
								var frame = dialog.frame[0];
								var contentDoc = $(frame).find("iframe")[0].contentDocument;
								$(contentDoc).find("input[name='List_Selected']:checked").each(function(i, n) {
									value = {};
									value.id = $(n).val();
									value.name = $(n).parent().parent().find("td.mainData_title:first").text();
									return true;
								});
							}
							setTimeout(function() {
								dialog.hide(value);
							}, 200);
						}
					}, {
						name : Msg_Info.button_cancel,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide();
						}
					}, {
						name : Msg_Info.button_clear,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide("clear");
						}
					}]
				});
			}
			
			function refreshNotify() {
				try{
					if(window.opener!=null) {
						try {
							if (window.opener.LUI) {
								window.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
								return;
							}
						} catch(e) {}
						if (window.LUI) {
							LUI.fire({ type: "topic", name: "successReloadPage" }, window.opener);
						}
						var hrefUrl= window.opener.location.href;
						var localUrl = location.href;
						if(hrefUrl.indexOf("/sys/notify/")>-1 && localUrl.indexOf("/sys/notify/")==-1)
							window.opener.location.reload();
					} else if(window.frameElement && window.frameElement.tagName=="IFRAME" && window.parent){
						if (window.parent.LUI) {
							window.parent.LUI.fire({ type: "topic", name: "successReloadPage" });
						}
					}
				}catch(e){}
			}
			Com_AddEventListener(window,"load",refreshNotify);
		});
		</script>
		<script language="JavaScript">
			seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
				LUI.ready(function() {
					LUI("lui_matrix_panel").on("indexChanged", function(evt) {
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
					});
				});
			});
		</script>
	</template:replace>
</template:include>
