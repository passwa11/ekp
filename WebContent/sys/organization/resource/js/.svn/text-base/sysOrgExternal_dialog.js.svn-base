/* 扩展属性逻辑 */
seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
	dialogProp = function(tableId, prop, mode, idx) {
		if(!mode) {
			mode = "edit";
		}
		dialog.iframe("/sys/organization/sys_org_element_external/sysOrgExternal_dialog.jsp?idx=" + idx + "&prop=" + prop + "&mode=" + mode, "添加属性", function(data) {
			if(data) {
				if(!data["fdShowList"]) {
					data["fdShowList"] = false;
				}
				var idx = 0, displayType = "文本", required = "否";
				if(data["fdRequired"] == "true") {
					required = "是";
				}
				if(data["fdDisplayType"] == "textarea") {
					displayType = "文本域";
				} else if(data["fdDisplayType"] == "radio") {
					displayType = "单选";
				} else if(data["fdDisplayType"] == "checkbox") {
					displayType = "多选";
				} else if(data["fdDisplayType"] == "select") {
					displayType = "下拉框";
				} else if(data["fdDisplayType"] == "datetime") {
					displayType = "日期时间";
				} else if(data["fdDisplayType"] == "date") {
					displayType = "日期";
				} else if(data["fdDisplayType"] == "time") {
					displayType = "时间";
				}
				// 取到数据，生成表格
				if(data["idx"]) {
					var index = data["idx"] - 1;
					var tds;
					if(prop == "dept") {
						fdDeptProps.splice(index, 1, data);
						tds = $($("#detpTaple [name=propTr]")[index]).children();
					} else {
						fdPersonProps.splice(index, 1, data);
						tds = $($("#personTaple [name=propTr]")[index]).children();
						
					}
					
					tds[0].innerText = data["fdName"];
					tds[1].innerText = displayType;
					tds[2].innerText = data["fdRequired"] == "true" ? "是" : "否";
					
					if (data["fdStatus"] != '') {
						var operations =$(tds[4]).children();
						if (operations.length > 2) {
							var op = operations[1];
							if (data["fdStatus"] == 'true') {
								op.innerText = "禁用";
							} else {
								op.innerText = "启用";
							}
						}
					}
					
				} else {
					if(prop == "dept") {
						idx = fdDeptProps.length;
						fdDeptProps.push(data);
					} else {
						idx = fdPersonProps.length;
						fdPersonProps.push(data);
					}
					
					var trs = [];
					trs.push('<tr name="propTr" class="add_prop" style="text-align: center;">');
					trs.push('<td>' + data["fdName"] + '</td>');
					trs.push('<td>' + displayType + '</td>');
					trs.push('<td>' + required + '</td>');
					trs.push('<td><input type="checkbox" name="fdShowList"></td>');
					trs.push('<td>');
					trs.push('	<a href="javascript:;" class="com_btn_link" onclick="editProp(\'' + tableId + '\', \'' + prop + '\', \'' + mode + '\', this);">编辑</a>');
					// trs.push('	<a href="javascript:;" class="com_btn_link" onclick="disableProp(\'' + tableId + '\', \'' + prop + '\', \'' + mode + '\', this);">禁用</a>');
					trs.push('	<a href="javascript:;" class="com_btn_link" onclick="delProp(\'' + tableId + '\', \'' + prop + '\', \'' + mode + '\', this);">删除</a>');
					trs.push('</td>');
					trs.push('</tr>');
					$("#" + tableId).append(trs.join(""));
				}
			}
		}, {
			width : 1000,
			height : 500,
			buttons : [{
				name : '确定',
				focus : true,
				fn : function(value, dialog) {
					var obj = {};
					if(dialog.frame && dialog.frame.length > 0) {
						var frame = $(dialog.frame[0]).find("iframe")[0]
						var contentDoc = frame.contentDocument;
						var contentWindow = frame.contentWindow;
						// 校验表单
						if(!contentWindow._validator.validate()) {
							return false;
						}
						// 如果不是枚举类型，需要删除枚举值
						var fdDisplayType = $(contentDoc).find("input[name=fdDisplayType]:checked").val();
						if(fdDisplayType != "radio" && fdDisplayType != "checkbox"  && fdDisplayType != "select" ) {
							$.each($(contentDoc).find("#fdFieldEnums tr"), function(i, n) {
								if(i > 0){
									$(n).remove();
								}
							});
						}
						// 处理表格内容
						var idx = $(contentDoc).find("input[name=idx]").val();
						var fdId = $(contentDoc).find("input[name=fdId]").val();
						var fdName = $(contentDoc).find("input[name=fdName]").val();
						var fdColumnName = $(contentDoc).find("input[name=fdColumnName]").val();
						var fdFieldName = $(contentDoc).find("input[name=fdFieldName]").val();
						var fdRequired = $(contentDoc).find("input[name=fdRequired]:checked").val();
						var fdFieldType = $(contentDoc).find("select[name=fdFieldType] option:selected").val();
						var fdStatus = $(contentDoc).find("input[name=fdStatus]:checked").val();
						var fdOrder = $(contentDoc).find("input[name=fdOrder]").val();
						var fdFieldLength = $(contentDoc).find("input[name=fdFieldLength]").val();
						var fdScale = $(contentDoc).find("input[name=fdScale]").val();
						
						obj["idx"] = idx || "";
						obj["fdId"] = fdId || "";
						obj["fdName"] = fdName || "";
						obj["fdColumnName"] = fdColumnName || "";
						obj["fdFieldName"] = fdFieldName || "";
						obj["fdRequired"] = fdRequired || "";
						obj["fdFieldType"] = fdFieldType || "";
						obj["fdStatus"] = fdStatus || "";
						obj["fdOrder"] = fdOrder || "";
						obj["fdFieldLength"] = fdFieldLength || "";
						obj["fdScale"] = fdScale || "";
						obj["fdDisplayType"] = fdDisplayType || "";
						obj["fdFieldEnums"] = [];
						$.each($(contentDoc).find("#fdFieldEnums tr"), function(i, n) {
							var _fdName = $(n).find("input[name$=fdName]").val();
							var _fdValue = $(n).find("input[name$=fdValue]").val();
							if(_fdName && _fdValue) {
								obj["fdFieldEnums"].push({"fdName": _fdName, "fdValue": _fdValue});
							}
						});
					}
					setTimeout(function() {
						dialog.hide(obj);
					}, 200);
				}
			}, {
				name : '取消',
				styleClass : 'lui_toolbar_btn_gray',
				fn : function(value, dialog) {
					dialog.hide();
				}
			}]
		});
	}
	
	editProp = function(tableId, prop, mode, elem) {
		var idx = getRowNum(elem);
		dialogProp(tableId, prop, mode, idx);
	}
	
	changePropStatus = function(tableId, prop, mode, elem, fdId) {
		var idx = getRowNum(elem);
		var prop;
		if (prop == 'dept') {
			prop = fdDeptProps[idx -1];
		} else {
			prop = fdPersonProps[idx -1];
		}
		if ('true' == prop['fdStatus']) {
			changeStatus(prop, "false", elem);
		} else {
			changeStatus(prop, "true", elem);
		}
	}
	
	delProp = function(tableId, prop, mode, elem) {
		var idx = getRowNum(elem);
		var obj = prop == "dept" ? window.fdDeptProps[idx - 1] : window.fdPersonProps[idx - 1];
		dialog.confirm('您确定要删除此属性吗？', function(value) {
			if(value == true) {
				// 如果后台没有数据，可以直接删除
				if(obj.fdId) {
					window.del_load = dialog.loading();
					$.ajax({
						url : Com_Parameter.ContextPath + 'sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=deleteProp',
						type : 'POST',
						data : $.param({"propId" : obj.fdId}, true),
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							if(data.responseJSON.message && data.responseJSON.message.length > 0)
								dialog.alert(data.responseJSON.message[0].msg);
						},
						success: function(data) {
							if(window.del_load != null) {
								window.del_load.hide();
							}
							if(data.status) {
								delPropTab(tableId, obj, idx);
							}
							dialog.result(data);
						}
				   });
				} else {
					delPropTab(tableId, obj, idx);
				}
			}
		});
	}
	
	// 删除页面表格
	delPropTab = function(tableId, obj, idx) {
		$("#" + tableId + " tr").eq(idx).remove();
		var props = window.fdDeptProps;
		if("personTaple" == tableId) {
			props = window.fdPersonProps;
		}
		for(var i=0; i<props.length; i++) {
			if(obj.fdColumnName == props[i].fdColumnName) {
				props.splice(i, 1);
				break;
			}
		}
	}
	
	/**
	 * 获取行号
	 */
	getRowNum = function(elem) {
		var tr = $(elem).parents("tr[name=propTr]");
		var trSeq = $(tr).parent().find("tr").index(tr[0]);
		return trSeq;
	}
	
	//提交表单前处理扩展属性
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
		if(window.fdDeptProps.length > 0) {
			// 处理部门属性
			var detpTaple = $("#detpTaple");
			for(var i=0; i<window.fdDeptProps.length; i++) {
				var input = [];
				var prop = window.fdDeptProps[i];
				input = buildProps("fdDeptProps", prop, i);
				// 是否列表展示(取当前表格的值)
				var fdShowList = detpTaple.find("tr.add_prop:eq("+i+") input[name=fdShowList]").is(':checked');
				input.push('<input type="hidden" name="fdDeptProps['+i+'].fdShowList" value="'+fdShowList+'">');
				detpTaple.append(input.join("\n"));
			}
		}
		if(window.fdPersonProps.length > 0) {
			// 处理人员属性
			var personTaple = $("#personTaple");
			for(var i=0; i<window.fdPersonProps.length; i++) {
				var input = [];
				var prop = window.fdPersonProps[i];
				input = buildProps("fdPersonProps", prop, i);
				// 是否列表展示(取当前表格的值)
				var fdShowList = personTaple.find("tr.add_prop:eq("+i+") input[name=fdShowList]").is(':checked');
				input.push('<input type="hidden" name="fdPersonProps['+i+'].fdShowList" value="'+fdShowList+'">');
				personTaple.append(input.join("\n"));
				
			}
		}
		return true;
	};
	
	buildProps = function(type, prop, idx) {
		var input = [];
		// 显示名称
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdName" value="'+prop['fdName']+'">');
		// 排序号
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdOrder" value="'+prop['fdOrder']+'">');
		// 属性名称
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdFieldName" value="'+prop['fdFieldName']+'">');
		// 字段名称
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdColumnName" value="'+prop['fdColumnName']+'">');
		// 对应数据类型
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdFieldType" value="'+prop['fdFieldType']+'">');
		// 字段长度
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdFieldLength" value="'+prop['fdFieldLength']+'">');
		// 精度
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdScale" value="'+prop['fdScale']+'">');
		// 是否必填
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdRequired" value="'+prop['fdRequired']+'">');
		// 是否启用
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdStatus" value="'+prop['fdStatus']+'">');
		// 显示的模式
		input.push('<input type="hidden" name="'+type+'['+idx+'].fdDisplayType" value="'+prop['fdDisplayType']+'">');
		// 枚举集合
		if(prop['fdFieldEnums'] && prop['fdFieldEnums'].length > 0) {
			for(var j=0; j<prop['fdFieldEnums'].length; j++) {
				var enums = prop['fdFieldEnums'][j];
				// 枚举排序号
				input.push('<input type="hidden" name="'+type+'['+idx+'].fdFieldEnums['+j+'].fdOrder" value="'+(j+1)+'">');
				// 枚举名称
				input.push('<input type="hidden" name="'+type+'['+idx+'].fdFieldEnums['+j+'].fdName" value="'+enums['fdName']+'">');
				// 枚举值
				input.push('<input type="hidden" name="'+type+'['+idx+'].fdFieldEnums['+j+'].fdValue" value="'+enums['fdValue']+'">');
			}
		}
		return input;
	}
	
	// 改变属性状态
 	window.changeStatus = function(prop, status, elem) {
 		var url  = Com_Parameter.ContextPath + 'sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=changePropStatus';
 		var message = '你确定要禁用此属性吗？';
 		if ("true" == status) {
 			message = '你确定要开启此属性吗？';
 		}
 		dialog.confirm(message, function(value) {
 			if(value == true) {
 				window.del_load = dialog.loading();
 				$.ajax({
					url : url,
					type : 'POST',
					data : $.param({"fdId":prop['fdId'], "status":status}, true),
					dataType : 'json',
					error : function(data) {
						if(window.del_load != null) {
							window.del_load.hide(); 
						}
						if(data.responseJSON.message && data.responseJSON.message.length > 0)
							dialog.alert(data.responseJSON.message[0].msg);
					},
					success: function(data) {
						if(window.del_load != null){
							window.del_load.hide(); 
							if ("true" == status) {
								elem.innerText = '禁用';
								prop['fdStatus'] = 'true';
							} else {
								elem.innerText = '启用';
								prop['fdStatus'] = 'false';
							}
						}
						dialog.result(data);
					}
			   });
 			}
 		});
 	};
	
});