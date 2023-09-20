<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog" sidebar="auto">
	<%-- 内容 --%>
	<template:replace name="content">
		<script>
			Com_IncludeFile('calendar.js');
			Com_IncludeFile('doclist.js|jquery.js|plugin.js');
			var curProp;
			
			// 原始的字符长度
			var __fieldLength = '200';
			var __fieldType = 'java.lang.String';
			function fieldTypeChange(val) {
				if(!val) {
					if(curProp) {
						__fieldType = curProp["fdFieldType"];
						__fieldLength = curProp["fdFieldLength"];
					}
					val = __fieldType;
				}
				// 显示或隐藏“字符长度”
				$($(".fieldLength_tr").parent().children()[1]).removeAttr("colspan");
				$(".fieldLength_tr").show();
				$("#fdFieldLength").removeAttr("disabled");
				$("#fdFieldLength").val(__fieldLength);
				if("java.util.Date" == val) {
					$($(".fieldLength_tr").parent().children()[1]).attr("colspan", "3");
					$(".fieldLength_tr").hide();
					$("#fdFieldLength").attr("disabled", "disabled");
				}
				
				// 显示或隐藏“精度”
				$("#scale_span").hide();
				$("#scale_span input").attr("disabled", "disabled");
				if("java.lang.Double" == val) {
					$("#scale_span").show();
					$("#scale_span input").removeAttr("disabled");
				}

				// 显示模式
				var _mode = "displayMode_" + val.split(".")[2];
				$("#displayMode").html($("#" + _mode).html());
				var _mode_val = "text";
				// 选中已勾选的项
				if(__fieldType == val && _mode_val)
					$("#displayMode :radio[value="+_mode_val+"]").click();
				else
					$("#displayMode :radio").first().click();
				
				// “整数”和“浮点”类型时，字符长度和枚举值要修改校验
				var fieldLength = $("#fdFieldLength");
				var __validate = fieldLength.attr("validate").split(" ");
				for(var i=0; i<__validate.length; i++) {
					if(__validate[i].indexOf("max") != -1) {
						__validate.splice(i, 1);
					}
				}
				if("java.lang.Integer" == val || "java.lang.Double" == val) {
					var __max;
					if("java.lang.Integer" == val) {
						if(val == __fieldType)
							__max = __fieldLength;
						else
							__max = 10;
						__validate.push("max(10)");
					} else {
						if(val == __fieldType)
							__max = __fieldLength;
						else
							__max = 12;
						__validate.push("max(12)");
					}
					fieldLength.val(__max);
				}
				fieldLength.attr("validate", __validate.join(" "));
				updateLength(__fieldLength);
			}
			
			var showAndAbled = function(id) {
				var parentDom = $('#' + id);
				if(!parentDom)
					return;
				var childInputs = parentDom.find(':input');
				if(childInputs)
					childInputs.removeAttr('disabled');
				parentDom.show();
			};
			
			var hideAndDisabled= function(id) {
				var parentDom = $('#' + id);
				if(!parentDom)
					return;
				var childInputs = parentDom.find(':input');
				if(childInputs)
					childInputs.prop('disabled', 'disabled');
				parentDom.hide();
			};
			
			function displayTypeChange(val) {
				hideAndDisabled("fieldEnums_tr");
				// 是否需要显示枚举
				if(val == "radio" || val == "checkbox"  || val == "select" ) {
					showAndAbled("fieldEnums_tr");
					DocListFunc_Init();
					// 检查是否有枚举选项，如果没有。则自动增加一行
					if($("#fdFieldEnums tr[class!=tr_normal_title]").length < 1) {
						$("#__DocList_AddRow").click();
					}
				}
				// 预览
				$("#displayType_preview_td").html($("div[name=displayType_preview_" + val + "]").html());
				showPreview();
			}
			
			// 预览枚举类型
			function showPreview() {
				var displayType = $("input[name=fdDisplayType]:checked").val();
				if(displayType == "radio" || displayType == "checkbox"  || displayType == "select" ) {
					var datas = [];
					$.each($("#fdFieldEnums tr"), function(i, n) {
						if(i > 0) {
							var texts = $(n).find("input[name^=fdFieldEnums][name*=texts]");
							var val =  $(n).find("input[name^=fdFieldEnums][name$=value]").val();
							var text = $(texts[0]).val();
							if(val && val.length > 0 && text && text.length > 0)
								datas.push({val:val, text:text});
						}
					});
					if(datas.length > 0)
						$("#displayType_preview_td").html(buildEnumHtml(datas, displayType));
				}
			}
			
			function buildEnumHtml(datas, displayType) {
				var html = [];
				if(displayType == "select") {
					html.push('<select name="__test_preview__" class="inputsgl">');
					html.push('<option value="">==请选择==</option>');
					$.each(datas, function(i, n) {
						html.push('<option value="'+n.val+'">'+n.text+'</option>');
					});
					html.push('</select>');
				} else {
					$.each(datas, function(i, n) {
						html.push('<label><input type="'+displayType+'" name="___test_preview__" value="'+n.val+'">'+n.text+'</label>&nbsp;&nbsp;&nbsp;');
					});
				}
				return html.join("");
			}
			
			// 长度字段修改后，需要修改枚举字段的长度校验
			function updateLength(val) {
				if(!val) {
					val = $("#fdFieldLength").val();
				}
				var fieldType = $("[name=fdFieldType]").val();
				$.each($("input[name^=fdFieldEnums][name$=value]"), function(i, n) {
					var fieldEnums = $(n);
					var __validate = fieldEnums.attr("validate").split(" ");
					for(var i=0; i<__validate.length; i++) {
						if(__validate[i].indexOf("maxLength") != -1
								|| __validate[i].indexOf("digits") != -1
								|| __validate[i].indexOf("number") != -1) {
							__validate.splice(i, 1);
						}
					}
					if("java.lang.Integer" == fieldType) {
						__validate.push("digits");
					} else if("java.lang.Double" == fieldType) {
						__validate.push("number");
					}
					__validate.push("maxLength(" + val + ")");
					fieldEnums.attr("validate", __validate.join(" "));
				});
			}
			
			function getIdx() {
				var idx = "${JsParam.idx}";
				if(idx === "" || idx == null || idx == undefined) {
					return -1;
				}
				if(!isNaN(idx)) {
					return parseInt(idx);
				}
				return -1;
			}
			
			function fullFdFieldEnums(curProp, key) {
				var arr = curProp[key];
				for (var n = 0;n < arr.length;n++) {
					var fieldEnum = arr[n];
					for (key in fieldEnum) {
						if ("fdName"== key) {
							if (n > 0) {
								$("#__DocList_AddRow").click();
							}
							$("[name='fdFieldEnums[" + n + "]." + key + "']").val(fieldEnum[key]);
						} else if ("fdValue"== key) {
							$("[name='fdFieldEnums[" + n + "]." + key + "']").val(fieldEnum[key]);
						}
					}
				}
			}
			
			function fullStatusOrRequired(curProp, key) {
				var m = {"true":0, "false":1};
				var index = m[curProp[key]];
				$("[name='" + key + "']").removeAttr("checked");
				$($("[name='" + key + "']")[index]).click();
			}
			
			function fullFdDisplayType(curProp, key, fdFieldType) {
				var stringObj = {"text": 0, "textarea":1, "radio":2, "checkbox":3, "select":4};
				var integerObj = {"text":0, "radio":1, "select":2};
				var doubleObj = {"text":0};
				var dateObj = {"datetime":0, "date":1, "time":2};
				var dic = {"java.lang.String":stringObj, "java.lang.Integer":integerObj, "java.lang.Double":doubleObj, "java.util.Date":dateObj};
				var temp = dic[fdFieldType];
				var index = temp[curProp[key]];
				$("[name='" + key + "']").removeAttr("checked");
				$($("[name='" + key + "']")[index]).click();
			}
			
			LUI.ready(function() {
				DocList_Info.push("fdFieldEnums");
				DocListFunc_Init();
				
				var props = window.parent.fdDeptProps;
				if("${JsParam.prop}" == "person") {
					props = window.parent.fdPersonProps;
				}
				var idx = getIdx();
				if(idx > -1) {
					// 编辑属性
					curProp = props[idx - 1];
					if(curProp) {
						// 初始化填充数据
						for(var key in curProp) {
							if ("fdFieldEnums" == key) {
								fullFdFieldEnums(curProp, key);
								continue;
							} else if ("fdRequired" == key || "fdStatus" == key) {
								fullStatusOrRequired(curProp, key);
								continue
							} else if ("fdDisplayType" == key) {
								fieldTypeChange();
								var fdFieldType = $("[name='fdFieldType'] option:selected").val();
								fullFdDisplayType(curProp, key, fdFieldType);
								continue
							}
							$("[name='" + key + "']").val(curProp[key]);
						}
						$("[name=idx]").val(idx);
					}
				} else {
					// 新增属性
					fieldTypeChange();
				}
				
				showPreview();
				// 编辑模式
				if("${JsParam.mode}" == "edit") {
					// 下面的属性不能编辑
					var disableds = ["fdFieldName", "fdColumnName", "fdFieldType"];
					$.each(disableds, function(i, n) {
						$("form [name=" + n + "]").attr("readonly", "readonly");
						if("fdFieldType" == n)
							$("form [name=" + n + "]").attr("disabled","disabled");
					});
				}
			});
			
		</script>
		
		<form action="#" method="post" style="padding: 20px 0;">
			<p class="txttitle">${ lfn:message('sys-property:custom.field.settings.edit') }</p>
			<input type="hidden" name="idx">
			<input type="hidden" name="fdId"/>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldTexts') }(${ lfn:message('sys-property:custom.field.fieldTexts.info') })
					</td>
					<td width=35%>
						<input name="fdName" class="inputsgl" validate="required locationUniqueName" value="" onblur="genFieldName(this.value)" style="width:80%">
						<span class="txtstrong">*</span>
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.columnName') }
					</td>
					<td width=35%>
						<input name="fdColumnName" class="inputsgl" validate="required maxLength(30) normalName uniqueColumnName locationUniqueColumnName" value="${dynamicAttributeField.columnName}" style="width:90%">
						<span class="txtstrong">*</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldName') }(${ lfn:message('sys-property:custom.field.fieldName.info') })
					</td>
					<td width=35%>
						<input name="fdFieldName" class="inputsgl" validate="required normalName uniqueFieldName" value="${dynamicAttributeField.fieldName}" style="width:50%">
						<span class="txtstrong">*</span>
						<br>
						<span class="com_help">${ lfn:message('sys-property:custom.field.fieldName.desc') }</span>
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.required') }
					</td>
					<td width=35%>
						<xform:radio property="fdRequired" value="true" showStatus="edit">
						 	<xform:simpleDataSource value="true">${ lfn:message('sys-property:sysPropertyDefine.fdStatus.true') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="false">${ lfn:message('sys-property:sysPropertyDefine.fdStatus.false') }</xform:simpleDataSource>
						 </xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldType') }
					</td>
					<td>
						<xform:select property="fdFieldType" value="java.lang.String" validators="required" showStatus="edit" onValueChange="fieldTypeChange" showPleaseSelect="false">
						 	<xform:simpleDataSource value="java.lang.String">${ lfn:message('sys-property:custom.field.fieldType.string') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="java.lang.Integer">${ lfn:message('sys-property:custom.field.fieldType.integer') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="java.lang.Double">${ lfn:message('sys-property:custom.field.fieldType.double') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="java.util.Date">${ lfn:message('sys-property:custom.field.fieldType.date') }</xform:simpleDataSource>
						 </xform:select>
						 <span class="txtstrong">*</span>
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.status') }
					</td>
					<td>
						<xform:radio property="fdStatus" value="true" showStatus="edit">
						 	<xform:simpleDataSource value="true">${ lfn:message('sys-property:custom.field.status.true') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="false">${ lfn:message('sys-property:custom.field.status.false') }</xform:simpleDataSource>
						 </xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.displayType') }
					</td>
					<td colspan="3">
						<div id="displayMode" style="vertical-align: top;">
						</div>
						<hr>
						${ lfn:message('sys-property:custom.field.preview') }
						<br>
						<div id="displayType_preview_td" style="margin-top: 5px;"></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.order') }
					</td>
					<td colspan="3">
						<input name="fdOrder" class="inputsgl" validate="digits min(0)" value="${dynamicAttributeField.order}" style="width:90%">
					</td>
					<td name="fieldLength_tr" class="td_normal_title fieldLength_tr" width=15% style="display: none;">
						${ lfn:message('sys-property:custom.field.fieldLength') }
					</td>
					<td name="fieldLength_tr" class="fieldLength_tr" width=15% style="display: none;">
						<input name="fdFieldLength" id="fdFieldLength" class="inputsgl" validate="required digits min(1) changeLength" value="${dynamicAttributeField.fieldLength}" style="width:45%" onchange="updateLength(this.value);">
						<span class="txtstrong">*</span>
						<span id="scale_span" style="display: none;">
							(${ lfn:message('sys-property:custom.field.scale') }
							<input name="fdScale" class="inputsgl" validate="required digits min(0) max(8)" value="${dynamicAttributeField.scale}" style="width:30%">
							<span class="txtstrong">*</span>
							)
						</span>
					</td>
				</tr>
				<tr id="fieldEnums_tr">
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldEnums') }
					</td>
					<td colspan="3">
						<table id="fdFieldEnums" class="tb_normal" width="100%">
							<tr class="tr_normal_title">
								<td width="20px;">
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td width="50%">${ lfn:message('sys-property:custom.field.fieldEnums.texts') }</td>
								<td width="30%">${ lfn:message('sys-property:custom.field.fieldEnums.value') }</td>
								<td>
									<a id="__DocList_AddRow" href="javascript:;" class="com_btn_link" onclick="DocList_AddRow('fdFieldEnums');updateLength();">${ lfn:message('button.insert') }</a>
								</td>
							</tr>
							<%-- 模版行 --%>
							<tr style="display:none;" KMSS_IsReferRow="1">
								<td KMSS_IsRowIndex="1">
									!{index}
								</td>
								<td>
									<input name="fdFieldEnums[!{index}].fdName" class="inputsgl" validate="required maxLength(200)" value="" style="width:80%" onblur="showPreview();">
									<span class="txtstrong">*</span>
								</td>
								<td>
									<input name="fdFieldEnums[!{index}].fdValue" class="inputsgl" validate="required maxLength(200) uniqueEnumValue" value="" style="width:90%" onblur="showPreview();">
									<span class="txtstrong">*</span>
								</td>
								<td>
									<div style="text-align:center">
									<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="${KMSS_Parameter_StylePath}icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		
		<!-- 以下为模板代码 -->
		<div style="display: none;">
			<div id="displayMode_String">
				<xform:radio property="fdDisplayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="text">${ lfn:message('sys-property:custom.field.displayType.text') }</xform:simpleDataSource>
					<xform:simpleDataSource value="textarea">${ lfn:message('sys-property:custom.field.displayType.textarea') }</xform:simpleDataSource>
					<xform:simpleDataSource value="radio">${ lfn:message('sys-property:custom.field.displayType.radio') }</xform:simpleDataSource>
					<xform:simpleDataSource value="checkbox">${ lfn:message('sys-property:custom.field.displayType.checkbox') }</xform:simpleDataSource>
					<xform:simpleDataSource value="select">${ lfn:message('sys-property:custom.field.displayType.select') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_Integer">
				<xform:radio property="fdDisplayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="text">${ lfn:message('sys-property:custom.field.displayType.text') }</xform:simpleDataSource>
					<xform:simpleDataSource value="radio">${ lfn:message('sys-property:custom.field.displayType.radio') }</xform:simpleDataSource>
					<xform:simpleDataSource value="select">${ lfn:message('sys-property:custom.field.displayType.select') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_Double">
				<xform:radio property="fdDisplayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="text">${ lfn:message('sys-property:custom.field.displayType.text') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_Date">
				<xform:radio property="fdDisplayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="datetime">${ lfn:message('sys-property:custom.field.displayType.datetime') }</xform:simpleDataSource>
					<xform:simpleDataSource value="date">${ lfn:message('sys-property:custom.field.displayType.date') }</xform:simpleDataSource>
					<xform:simpleDataSource value="time">${ lfn:message('sys-property:custom.field.displayType.time') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			
			<!-- 以下为即时预览效果 -->
			<div name="displayType_preview_text">
				<input name="__test_preview__" class="inputsgl" type="text" style="width:80%">
			</div>
			<div name="displayType_preview_textarea">
				<textarea name="__test_preview__" style="width:95%"></textarea>
			</div>
			<div name="displayType_preview_radio">
				<label><input type="radio" name="___test_preview__" value="1">${ lfn:message('sys-property:custom.field.select.a') }</label>&nbsp;&nbsp;&nbsp;
				<label><input type="radio" name="___test_preview__" value="2">${ lfn:message('sys-property:custom.field.select.b') }</label>&nbsp;&nbsp;&nbsp;
			</div>
			<div name="displayType_preview_checkbox">
				<label><input type="checkbox" name="___test_preview__" value="1">${ lfn:message('sys-property:custom.field.select.a') }</label>&nbsp;&nbsp;&nbsp;
				<label><input type="checkbox" name="___test_preview__" value="2">${ lfn:message('sys-property:custom.field.select.b') }</label>&nbsp;&nbsp;&nbsp;
			</div>
			<div name="displayType_preview_select">
				<select name="__test_preview__" class="inputsgl">
					<option value="">${ lfn:message('sys-property:custom.field.select.please') }</option>
					<option value="1">${ lfn:message('sys-property:custom.field.select.a') }</option>
					<option value="2">${ lfn:message('sys-property:custom.field.select.b') }</option>
				</select>
			</div>
			<div name="displayType_preview_datetime">
				<div class='inputselectsgl' onclick="selectDateTime('__test_preview__')"  style="width:150px;"><div class="input"><input type="text" name="__test_preview__" value="" validate="__datetime" /></div><div  class="inputdatetime" ></div></div>
			</div>
			<div name="displayType_preview_date">
				<div class='inputselectsgl' onclick="selectDate('__test_preview__')"  style="width:150px;"><div class="input"><input type="text" name="__test_preview__" value="" validate="__date" /></div><div  class="inputdatetime" ></div></div>
			</div>
			<div name="displayType_preview_time">
				<div class='inputselectsgl' onclick="selectTime('__test_preview__')"  style="width:150px;"><div class="input"><input type="text" name="__test_preview__" value="" validate="__time" /></div><div  class="inputtime" ></div></div>
			</div>
		</div>
		
		<script type="text/javascript">
			var _validator = $KMSSValidation();
			
			var checkUnique = function(field, value) {
				if("${JsParam.prop}" == "person") {
					props = window.parent.fdPersonProps;
				} else {
					props = window.parent.fdDeptProps;
				}
				var idx = getIdx();
				if (idx == -1) {
					// idx = -1代表是点击新增按钮
					for (var i = 0;i < props.length;i++) {
						if (props[i][field] == value) {
							return false;
						}
					}
				} else {
					for (var i = 0;i < props.length;i++) {
						if (idx != -1 && (idx - 1) != i && props[i][field] == value) {
							return false;
						}
					}
				}
				return true;
			}
			var CustomValidators = {
					'normalName' : {
						error : "${ lfn:message('sys-property:custom.field.normalName') }",
						test : function(value) {
							var pattern = new RegExp("^[a-zA-Z_][a-zA-Z0-9_]{1,30}$");
							if (pattern.test(value)) {
								return true;
							} else {
								return false;
							}
						}
					},
					'uniqueColumnName' : {
						error : "${ lfn:message('sys-property:custom.field.uniqueName') }",
						test : function(value) {
							if("${JsParam.mode}" == "edit")
								return true;
							return checkUnique("fdColumnName", value);
					    }
					},
					'changeLength' : {
						error : "${ lfn:message('sys-property:custom.field.fieldLength.err') }",
						test : function(value) {
							// return checkUrl(value, 3);
							if ("${JsParam.mode}" == "edit") {
								var idx = getIdx();
								var props;
								if("${JsParam.prop}" == "person") {
									props = window.parent.fdPersonProps;
								} else {
									props = window.parent.fdDeptProps;
								}
								var prop = props[idx - 1];
								for (var p in prop) {
									var oldVal = parseInt(prop[p]);
									var newVal = parseInt(value);
									if (p == "fdFieldLength" && oldVal > newVal) {
										return false;
									}
								}
							}
							return true;
					    }
					},
					'uniqueFieldName' : {
						error : "${ lfn:message('sys-property:custom.field.uniqueName') }",
						test : function(value) {
							if("${JsParam.mode}" == "edit")
								return true;
							return checkUnique("fdFieldName", value);
					    }
					},
					'locationUniqueName' : {
						error : "${ lfn:message('sys-property:custom.field.uniqueName') }",
						test : function(value) {
							return checkUnique("fdName", value);
					    }
					},
					'uniqueEnumValue' : {
						error : "${ lfn:message('sys-organization:sysOrgEco.org.enum.uniqueName') }",
						test : function(value, e, o) {
							var fdValues = $('#fdFieldEnums').find('input[name$="fdValue"]');
							for (var i = 0;i < fdValues.length;i++) {
								var fdValue = $(fdValues[i]);
								if (fdValue.context.name != e.name && value == fdValue.val()) {
									return false;
								}
							}
							return true;
					    }
					}
				};
			_validator.addValidators(CustomValidators);
			
			function checkUrl(value, type) {
				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?serviceName=sysOrgElementExternalService&fdId=${JsParam.fdId}&prop=${JsParam.prop}&type="
						+ type + "&value=" + value + "&_=" + new Date().getTime());
				var xmlHttpRequest;
				if (window.XMLHttpRequest) { // Non-IE browsers
					xmlHttpRequest = new XMLHttpRequest();
				} else if (window.ActiveXObject) { // IE
					try {
						xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
					} catch (othermicrosoft) {
						try {
							xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
						} catch (failed) {
							xmlHttpRequest = false;
						}
					}
				}
				if (xmlHttpRequest) {
					xmlHttpRequest.open("GET", url, false);
					xmlHttpRequest.send();
					var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
					if (result != "") {
						return false;
					}
				}
				return true;
			}
			
			// 根据显示名称生成程序名称
			function genFieldName(value) {
				if("${JsParam.mode}" == "add") {
					// 程序属性名：根据显示的字段名来转换
					if(value && value.length > 0) {
						var data = new KMSSData();
						value = encodeURIComponent(encodeURIComponent(value));
						data.SendToBean('sysOrgElementExternalService&fdId=${JsParam.fdId}&prop=${JsParam.prop}&value='+value, function(rtnData) {
							var _data = rtnData.GetHashMapArray()[0];
							document.getElementsByName("fdFieldName")[0].value = _data.value;
						});
					}
				}
			}
			
		</script>
	</template:replace>
</template:include>