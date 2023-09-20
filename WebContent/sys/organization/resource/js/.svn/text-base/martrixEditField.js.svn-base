seajs.use(['lui/jquery','lui/dialog', 'lui/topic','lang!sys-organization'], function($, dialog, topic,lang) {
		var typeField = $("#matrix_field_type"), valueField = $("#matrix_field_value"), blankField = $("#matrix_field_blank"), optField = $("#matrix_field_opt");
		var conTypeTpl = $("#tpl_con_type").html(), conValueTpl = $("#tpl_con_value").html(), conOptTpl = $("#tpl_con_opt").html();
		var resTypeTpl = $("#tpl_res_type").html(), resValueTpl = $("#tpl_res_value").html(), resOptTpl = $("#tpl_res_opt").html();
		
		//设置是否向下匹配的值
		window.setValue = function(elem){
			var inputInclude = $(elem).parent().find("input[type=hidden]");
			if(elem.checked){
				inputInclude.val(true);
			}else{
				inputInclude.val(false);
			}
		}
		
		// 右移（条件）
		window.rightCon = function(elem) {
			//主数据、自定义数据新建新列时会重绘一遍表格，未选择数据时会丢失，所以未选择数据时禁止新建列
			var flag = false;
			typeField.find("td.lui_maxtrix_condition_th").each(function(index,conTh){
				
				var selectType = $(conTh).find("select")[0];
				var mainDataText = $(conTh).find("input")[1];
				if(selectType && mainDataText){
					if("sys" == selectType.value || "cust" == selectType.value){
						if(mainDataText.value == "" || typeof(mainDataText.value)=="undefined"){
							flag = true
						}
					}
				}
			
			})
			if(flag){
				dialog.alert(lang["sysOrgMatrix.column.empty.move"]);
				return false;
			}
			var idx = $(elem.parentNode).parent().prevAll().length + 1;
			if(allFields.con.length > idx) {
				saveField();
				var temp = allFields.con[idx];
				allFields.con[idx] = allFields.con[idx - 1];
				allFields.con[idx - 1] = temp;
				reDrawTable();
				reDrawViewTable();
			}
			reDrawTable();
			reDrawViewTable();
		}
		
		// 左移（条件）
		window.leftCon = function(elem) {
			//主数据、自定义数据新建新列时会重绘一遍表格，未选择数据时会丢失，所以未选择数据时禁止新建列
			var flag = false;
			typeField.find("td.lui_maxtrix_condition_th").each(function(index,conTh){
				
				var selectType = $(conTh).find("select")[0];
				var mainDataText = $(conTh).find("input")[1];
				if(selectType && mainDataText){
					if("sys" == selectType.value || "cust" == selectType.value){
						if(mainDataText.value == "" || typeof(mainDataText.value)=="undefined"){
							flag = true
						}
					}
				}
			
			})
			if(flag){
				dialog.alert(lang["sysOrgMatrix.column.empty.move"]);
				return false;
			}
			var idx = $(elem.parentNode).parent().prevAll().length + 1;
			if(idx > 1) {
				saveField();
				var temp = allFields.con[idx - 2];
				allFields.con[idx - 2] = allFields.con[idx - 1];
				allFields.con[idx - 1] = temp;
				reDrawTable();
			}
			reDrawTable();
			reDrawViewTable();
		}
		
		// 删除（条件）
		window.delCon = function(elem) {
			delColumn(elem, "con");
			reDrawTable();
			reDrawViewTable();
		}
		
		// 添加（条件）
		window.addCon = function(elem) {
			if(allFields.con.length >= 20) {
				dialog.alert(lang["sysOrgMatrix.calculation.conditional.max"]);
				return false;
			}
			//主数据、自定义数据新建新列时会重绘一遍表格，未选择数据时会丢失，所以未选择数据时禁止新建列
			var flag = false;
			typeField.find("td.lui_maxtrix_condition_th").each(function(index,conTh){
				var selectType = $(conTh).find("select")[0];
				var mainDataText = $(conTh).find("input")[1];
				if(selectType && mainDataText){
					if("sys" == selectType.value || "cust" == selectType.value){
						if(mainDataText.value == "" || typeof(mainDataText.value)=="undefined"){
							flag = true
						}
					}
				}
			})
			if(flag){
				dialog.alert(lang["sysOrgMatrix.column.empty.add"]);
				return false;
			}
			saveField();
			var idx = allFields.con.length;
			allFields.con.splice(idx, 0, {"fdType": "", "fdName": "", "fdMainDataType": "", "fdMainDataText": "","fdIncludeSubDept": "", "fdIsUnique":"", "fdValueCount": 0});
			// 增加一列
			if(allFields.con.length > 3) {
				idx = idx - 1;
				typeField.find("td:eq(" + idx + ")").after("<td class=\"lui_maxtrix_condition_th\">&nbsp;</td>");
				valueField.find("td:eq(" + idx + ")").after("<td class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
				blankField.find("td:eq(" + idx + ")").after("<td class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
				optField.find("td:eq(" + idx + ")").after("<td align=\"center\" data-del=\"false\" class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
			}

			reDrawTable();
			reDrawViewTable();
		}
		//重新渲染预览表格
		window.reDrawViewTable = function() {
			var allTab = $('.sysOrgMatrixPreviewTable_c>table>tbody>tr');
			var conVals = $('#matrix_field_value>td.lui_maxtrix_condition_td');
			var resVals = $('#matrix_field_value>td.lui_maxtrix_result_td');
			var conViewArr=[];
			var resViewArr=[];
			//获取表格数据
			$(conVals).each(function(){
				if($(this).find('input').val()){
					conViewArr.push($(this).find('input').val());
				}
			});
			$(resVals).each(function(){
				if($(this).find('input').val()){
					resViewArr.push($(this).find('input').val());
				}
			});
			//渲染表格
			$(allTab).html('');
			for(let i=0;i<conViewArr.length;i++){
				$(allTab).append("<th class='sysOrgMatrixPreviewThCon'>" + conViewArr[i] + "</th>");
			}
			for(let i=0;i<resViewArr.length;i++){
				$(allTab).append("<th class='sysOrgMatrixPreviewThRes'>" + resViewArr[i] + "</th>");
			}
		}
		//添加字段时绑定事件
		window.onload=function(){
			$('#matrix_field_value').on('input propertychange', function() {
				reDrawViewTable();
			});
			reDrawViewTable();
		}
		// 右移（结果）
		window.rightRes = function(elem) {
			var idx = $(elem.parentNode).parent().prevAll().length + 1;
			var conLen = allFields.con.length > 3 ? allFields.con.length : 3;
			idx -= conLen;
			if(allFields.res.length > idx) {
				saveField();
				var temp = allFields.res[idx];
				allFields.res[idx] = allFields.res[idx - 1];
				allFields.res[idx - 1] = temp;
				reDrawTable();
				reDrawViewTable();
			}
		}
		
		// 左移（结果）
		window.leftRes = function(elem) {
			var idx = $(elem.parentNode).parent().prevAll().length + 1;
			var conLen = allFields.con.length > 3 ? allFields.con.length : 3;
			idx -= conLen;
			if(idx > 1) {
				saveField();
				var temp = allFields.res[idx - 2];
				allFields.res[idx - 2] = allFields.res[idx - 1];
				allFields.res[idx - 1] = temp;
				reDrawTable();
				reDrawViewTable();
			}
		}
		
		// 删除（结果）
		window.delRes = function(elem) {
			delColumn(elem, "res");
			reDrawViewTable();
		}
		
		// 添加（结果）
		window.addRes = function(elem) {
			if(allFields.res.length >= 20) {
				dialog.alert(lang["sysOrgMatrix.calculation.result.max"]);
				return false;
			}
			saveField();
			var idx = allFields.res.length;
			var conLen = allFields.con.length > 3 ? allFields.con.length : 3;
			allFields.res.splice(idx, 0, {"fdType": "", "fdName": "", "fdValueCount": 0});
			// 增加一列
			if(allFields.res.length > 3) {
				idx = idx + conLen - 1;
				typeField.find("td:eq(" + idx + ")").after("<td class=\"lui_maxtrix_result_th\">&nbsp;</td>");
				valueField.find("td:eq(" + idx + ")").after("<td class=\"lui_maxtrix_result_td\">&nbsp;</td>");
				blankField.find("td:eq(" + idx + ")").after("<td class=\"lui_maxtrix_result_td\">&nbsp;</td>");
				optField.find("td:eq(" + idx + ")").after("<td align=\"center\" data-del=\"false\" class=\"lui_maxtrix_result_td\">&nbsp;</td>");
			}
			reDrawTable(); 
			reDrawViewTable();
		}
		
		// 删除列
		window.delColumn = function(elem, type) {
			saveField();
			var idx = $(elem.parentNode).parent().prevAll().length + 1;
			if("res" == type) {
				if(allFields.res.length < 2) {
					dialog.alert(lang["sysOrgMatrix.calculation.result.empty"]);
					return false;
				}
				var conLen = allFields.con.length > 3 ? allFields.con.length : 3;
				allFields.res.splice(idx - conLen - 1, 1);
			} else {
				if(allFields.con.length < 2) {
					dialog.alert(lang["sysOrgMatrix.calculation.conditional.empty"]);
					return false;
				}
				allFields.con.splice(idx - 1, 1);
			}
			// 删除表格
			idx = idx - 1;
			var isRemove = true;
			if("res" == type && allFields.res.length < 3) {
				isRemove = false;
			} else if("con" == type && allFields.con.length < 3) {
				isRemove = false;
			}
			if(isRemove) {
				// 删除表格
				typeField.find("td:eq(" + idx + ")").remove();
				valueField.find("td:eq(" + idx + ")").remove();
				blankField.find("td:eq(" + idx + ")").remove();
				optField.find("td:eq(" + idx + ")").remove();
			}
			
			reDrawTable();
			reDrawViewTable();
		}
		
		// 重置表格名称
		window.resetTable = function() {
			// 处理下拉框
			$("#matrix_table").find("select[name^='fdRelationConditionals'][name$='fdType1']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdType1");
				$(elem).find("option").removeAttr("selected");
				if(allFields.con[i]) {
					$(elem).val(allFields.con[i].fdType);
				}
			});
			// 处理隐藏域
			$("#matrix_table").find("input[name^='fdRelationConditionals'][name$='fdType']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdType");
				if(allFields.con[i]) {
					$(elem).val(allFields.con[i].fdType);
				}
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdId']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdId");
				if(allFields.con[i]) {
					$(elem).val(allFields.con[i].fdId);
				}
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdName']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdName");
				if(allFields.con[i]) {
					$(elem).val(toHtml(allFields.con[i].fdName));
					$(elem).attr("value", toHtml(allFields.con[i].fdName));
				}
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdMainDataType']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdMainDataType");
				if(allFields.con[i]) {
					$(elem).val(allFields.con[i].fdMainDataType);
					if (allFields.con[i].fdMainDataType) {
						$(elem).parent().show();
						$(elem).parent().find("input").removeAttr("disabled");
					}
				}
			});
			$("#matrix_table").find(".type_dept").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdIncludeSubDept");
				if(allFields.con[i]) {
					$(elem).val(allFields.con[i].fdIncludeSubDept ? allFields.con[i].fdIncludeSubDept : "");
					if (allFields.con[i].fdType == "dept") {
						$(elem).parent().show();
						$(elem).parent().find("input").removeAttr("disabled");
						if (allFields.con[i].fdIncludeSubDept == "true") {
							$(elem).parent().find("input[type=checkbox]").prop("checked", true);
						} else {
							$(elem).parent().find("input[type=checkbox]").prop("checked", false);
						}
					}
				}
			});
			$("#matrix_table").find(".unique_div").each(function(i, elem) {
				var input = $(elem).find("[type='hidden']"),
					checkbox = $(elem).find("[type='checkbox']");
				input.attr("name", "fdRelationConditionals[" + i + "].fdIsUnique");
				if(allFields.con[i]) {
					input.val(allFields.con[i].fdIsUnique ? allFields.con[i].fdIsUnique : "");
					if (allFields.con[i].fdIsUnique == "true") {
						checkbox.prop("checked", true);
					} else {
						checkbox.prop("checked", false);
					}
				}
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdMainDataText']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationConditionals[" + i + "].fdMainDataText");
				if(allFields.con[i]) {
					$(elem).val(allFields.con[i].fdMainDataText);
				}
			});
			$("#matrix_table").find("select[name^='fdRelationResults'][name$='fdType']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationResults[" + i + "].fdType");
				if(allFields.res[i]) {
					$(elem).val(allFields.res[i].fdType);
				}
			});
			$("#matrix_table").find("[name^='fdRelationResults'][name$='fdId']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationResults[" + i + "].fdId");
				if(allFields.res[i]) {
					$(elem).val(allFields.res[i].fdId);
				}
			});
			$("#matrix_table").find("input[name^='fdRelationResults'][name$='fdType']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationResults[" + i + "].fdType");
				if(allFields.res[i]) {
					$(elem).val(allFields.res[i].fdType);
				}
			});
			$("#matrix_table").find("[name^='fdRelationResults'][name$='fdName']").each(function(i, elem) {
				$(elem).attr("name", "fdRelationResults[" + i + "].fdName");
				if(allFields.res[i]) {
					$(elem).val(toHtml(allFields.res[i].fdName));
					$(elem).attr("value", toHtml(allFields.res[i].fdName));
				}
			});
			resizeTable();
		}
		
		window.toHtml = function(value) {
			if(value == null || value == "")
				return "";
	        value = value.replace(/&lt;/g, "<");
	        value = value.replace(/&gt;/g, ">");
	        value = value.replace(/&nbsp;/g, " ");
	        value = value.replace(/&quot/g, "\"");
	        value = value.replace(/&#034;/g, "\"");
	        value = value.replace(/&#039;/g, "'");
	        value = value.replace(/&amp;/g, "&");
	        return value;
		}
		
		// 表格高度重置
		window.resizeTable = function() {
			var trs1 = $("#matrix_type_table tbody").children(), trs2 = $("#matrix_table tbody").children("[id]");
			// #172689 保证重置高度前先异步隐藏完提示块
			setTimeout(function () {
				trs2.each(function(i, tr) {
					$(trs1[i]).height($(tr).height());
				});
			},100)
		}
		
		// 保存表单数据
		window.saveField = function() {
			var tempCon = allFields.con, tempRes = allFields.res;
			// 重置数据，新增页面时，清空所有数据
			allFields.con = [], allFields.res = [];
			// 加载已经生成的表格数据
			// {"fdRelationConditionals": ["fdId", "fdType", "fdName", "fdMainDataType", "fdMainDataText"], "fdRelationResults": ["fdId", "fdType", "fdName"]};
			$("#matrix_table").find("select[name^='fdRelationConditionals'][name$='fdType1']").each(function(i, elem) {
				allFields.con.push({"fdId": "", "fdType": "", "fdName": "", "fdMainDataType": "", "fdMainDataText": "","fdIncludeSubDept":"","fdIsUnique":"","fdValueCount": tempCon[i] ? tempCon[i].fdValueCount : 0});
				allFields.con[i].fdType = $(elem).val();
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdName']").each(function(i, elem) {
				allFields.con[i].fdName = $(elem).val();
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdId']").each(function(i, elem) {
				allFields.con[i].fdId = $(elem).val();
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdMainDataType']").each(function(i, elem) {
				allFields.con[i].fdMainDataType = $(elem).val();
			});
			$("#matrix_table").find(".type_dept").each(function(i, elem) {
				allFields.con[i].fdIncludeSubDept = $(elem).val();
			});
			$("#matrix_table").find(".unique_div").each(function(i, elem) {
				allFields.con[i].fdIsUnique = $(elem).find("input[type=hidden]").val();
			});
			$("#matrix_table").find("[name^='fdRelationConditionals'][name$='fdMainDataText']").each(function(i, elem) {
				allFields.con[i].fdMainDataText = $(elem).val();
			});
			$("#matrix_table").find("select[name^='fdRelationResults'][name$='fdType']").each(function(i, elem) {
				allFields.res.push({"fdId": "", "fdType": "", "fdName": "", "fdValueCount": tempRes[i] ? tempRes[i].fdValueCount : 0});
				allFields.res[i].fdType = $(elem).val();
			});
			$("#matrix_table").find("[name^='fdRelationResults'][name$='fdName']").each(function(i, elem) {
				allFields.res[i].fdName = $(elem).val();
			});
			$("#matrix_table").find("[name^='fdRelationResults'][name$='fdId']").each(function(i, elem) {
				allFields.res[i].fdId = $(elem).val();
			});
		}
		
		// 重新渲染表格，当表格内容有“新增，删除，左移，右移”时，需要重新渲染一次表格
		window.reDrawTable = function() {
			var con = allFields.con, res = allFields.res;
			typeField.find("td").empty();
			valueField.find("td").empty();
			blankField.find("td").empty();
			optField.find("td").empty();
			// 条件
			for(var i=0; i<con.length; i++) {
				var __tpl = conTypeTpl;
				if(con[i].fdType.indexOf("Range") > -1) {
					var __p = $("<div/>").append(conTypeTpl);
					// 区间类型隐藏唯一校验
					__p.find("div.unique_div").hide();
					__tpl = __p.html();
				}
				var typeTd = typeField.find("td:eq(" + i + ")");
				typeTd.html(__tpl);
				valueField.find("td:eq(" + i + ")").html(conValueTpl);
				blankField.find("td:eq(" + i + ")").html("&nbsp;");
				var optTd = optField.find("td:eq(" + i + ")");
				optTd.html(conOptTpl);
				if(con[i].fdValueCount > 0) {
					optTd.attr("data-del", true);
					typeTd.attr("data-readonly", true);
				} else {
					optTd.removeAttr("data-del");
					typeTd.removeAttr("data-readonly");
				}
			}
			// 结果
			var conLen = con.length > 3 ? con.length : 3;
			for(var i=0; i<res.length; i++) {
				var idx = i + conLen;
				var typeTd = typeField.find("td:eq(" + idx + ")");
				typeTd.html(resTypeTpl);
				valueField.find("td:eq(" + idx + ")").html(resValueTpl);
				blankField.find("td:eq(" + idx + ")").html("&nbsp;");
				var optTd = optField.find("td:eq(" + idx + ")");
				optTd.html(resOptTpl);
				if(res[i].fdValueCount > 0) {
					optTd.attr("data-del", true);
					typeTd.attr("data-readonly", true);
				} else {
					optTd.removeAttr("data-del");
					typeTd.removeAttr("data-readonly");
				}
			}
			// 处理左移，右移
			resetOpt();
			resetTable();
			setTimeout(function(){resizeTable();}, 300);
		}
		
		// 初始化表格
		window.initTable = function() {
			var con = allFields.con, res = allFields.res;
			var conLen = con.length > 3 ? con.length : 3, resLen = res.length > 3 ? res.length : 3;
			// 删除第2列以后元素
			typeField.find("td").remove();
			valueField.find("td").remove();
			blankField.find("td").remove();
			optField.find("td").remove();
			// 条件
			for(var i=0; i<conLen; i++) {
				if(con.length > i) {
					var isDel = con[i].fdValueCount > 0;
					var __tpl = conTypeTpl;
					if(con[i].fdType.indexOf("Range") > -1) {
						var __p = $("<div/>").append(conTypeTpl);
						// 区间类型隐藏唯一校验
						__p.find("div.unique_div").hide();
						__tpl = __p.html();
					}
					typeField.append("<td class=\"lui_maxtrix_condition_th\" data-readonly=\"" + isDel + "\">" + __tpl + "</td>");
					valueField.append("<td class=\"lui_maxtrix_condition_td\">" + conValueTpl + "</td>");
					blankField.append("<td class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
					optField.append("<td align=\"center\" data-del=\"" + isDel + "\" class=\"lui_maxtrix_condition_td\">" + conOptTpl + "</td>");
				} else {
					typeField.append("<td class=\"lui_maxtrix_condition_th\">&nbsp;</td>");
					valueField.append("<td class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
					blankField.append("<td class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
					optField.append("<td align=\"center\" class=\"lui_maxtrix_condition_td\">&nbsp;</td>");
				}
			}
			// 结果
			for(var i=0; i<resLen; i++) {
				if(res.length > i) {
					var isDel = res[i].fdValueCount > 0;
					typeField.append("<td class=\"lui_maxtrix_result_th\" data-readonly=\"" + isDel + "\">" + resTypeTpl + "</td>");
					valueField.append("<td class=\"lui_maxtrix_result_td\">" + resValueTpl + "</td>");
					blankField.append("<td class=\"lui_maxtrix_result_td\">&nbsp;</td>");
					optField.append("<td align=\"center\" data-del=\"" + isDel + "\" class=\"lui_maxtrix_result_td\">" + resOptTpl + "</td>");
				} else {
					typeField.append("<td class=\"lui_maxtrix_result_th\">&nbsp;</td>");
					valueField.append("<td class=\"lui_maxtrix_result_td\">&nbsp;</td>");
					blankField.append("<td class=\"lui_maxtrix_result_td\">&nbsp;</td>");
					optField.append("<td align=\"center\" class=\"lui_maxtrix_result_td\">&nbsp;</td>");
				}
			}
			resetTable();
			// 处理左移，右移
			resetOpt();
			setTimeout(function(){resizeTable();}, 300);
		}
		
		// 重置操作行
		window.resetOpt = function() {
			var con = allFields.con, res = allFields.res;
			var conLen = con.length > 3 ? con.length : 3;
			for(var i=0; i<con.length; i++) {
				if(i == 0) {
					// 不能左移
					optField.find("td:eq(" + i + ")").find("[name=left]").remove();
				}
				if(i == con.length - 1) {
					// 不能右移
					optField.find("td:eq(" + i + ")").find("[name=right]").remove();
				}
			}
			// 结果
			for(var i=0; i<res.length; i++) {
				if(i == 0) {
					// 不能左移
					optField.find("td:eq(" + (i + conLen) + ")").find("[name=left]").remove();
				}
				if(i == res.length - 1) {
					// 不能右移
					optField.find("td:eq(" + (i + conLen) + ")").find("[name=right]").remove();
				}
			}
			// 处理删除（已有数据不能删除）
			optField.find("[data-del=true]").each(function(i, n) {
				$(n).find("[name=del]").remove();
				
			});
			typeField.find("[data-readonly=true]").each(function(i, n) {
				$(n).find("select").attr("disabled", true);
				var _type = $(n).find("select");
				$(n).find("a").removeAttr("onclick");
				// 禁用后，编辑时无法提交该属性，需要重写一个隐藏域
				$(n).append("<input type='hidden' name='" + _type.attr("name") + "' value='" + _type.val() + "' />");
			});
			// 插入分隔符
			optField.find("td").each(function(i, n) {
				var sub = $(n).find("a");
				var content = [];
				content.push("<span class=\"lui_matrix_opt_bar\">");
				sub.each(function(j, m) {
					content.push($(m).prop("outerHTML"));
					if(j < sub.length - 1) {
						content.push(" <i class=\"lui_matrix_split\"></i>");
					}
				});
				content.push("</span>");
				$(n).html(content.join(""));
			});
		}
		
		$(function() {
			// 表格高度变化需要同步到另一个表格
			$("#matrix_table").on("DOMNodeInserted", function() {
				resizeTable();
			});
			initTable();
		});
		
		if(window.addEventListener){
			window.addEventListener('load',function(){
				resizeTable();
			})
		}
	});