window.resultList = [];

seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic','lang!sys-organization'], function($, dialog, topic, lang){
	/* 复选框值变化时，清空输入框的值 */
	window.clearInput = function(dom){
		// 向上查找TR jquery 对象
		var $trObj = $(dom).closest("tr.matrix_add_tr");
		$trObj.find("input[type='hidden'][data-type='fieldId']").val("");
		$trObj.find("input[type='text'][data-type='fieldText']").val("");
	}
	/* 提交批量增加的矩阵数据 */
	window.sumitAllData = function(){
		//无值标记 true时传空值，避免生成空行
		var flag = false;
		//切割符
		var _splitStr = "|||||";
		//表单数据处理成初始数组
		var inputData = [];
		//最大长度那行形成的数组
		var maxRowData = [];
		//生成的数据行数
		var rowCount = 1;
		//第一遍循环，获取生成的行数
		$(".matrix_add_tr").each(function(i,_tr){
			$(_tr).find("input[data-type='fieldId']").each(function(j,_id){
				//input框的name,也就是条件或者结果对象的id
				var name = $(_id).attr("name").replace(/\[[^\]]+\]/g, '');
				//input框的值
				var value = $(_id).val();
				if(value != ''){
					flag = true;
				}
				var mulFlag = false;
				//判断是否按值拆分确定行数 常量类型
				if($(_tr).find("input[type='checkbox']").length > 0)
					mulFlag = $(_tr).find("input[type='checkbox']").get(0).checked;
				var isMulselect = value.indexOf(";") > -1 && ($(_id).hasClass("isConstant") || mulFlag);
				//其他类型
				if(isMulselect){
					var arr = value.split(";");
					rowCount = arr.length > rowCount ? arr.length : rowCount;
				}
			})
		});
		//第二遍循环，做表单的数据处理
		var resIndex = 0;
		$(".matrix_add_tr").each(function(i,_tr){
			//person_post类型处理
			if($(_tr).hasClass("matrix_res_person_post")){
				var _id = $(_tr).find("input.person_post");
				var name = $(_id).attr("name").replace(/\[[^\]]+\]/g, '');
				var value = $(_id).val();
				var objArr = [];
				var personId = $(_id).parent().find("input[type=hidden][name$=person]").val();
				var personName = $(_id).parent().find("input[type=text][name$=person]").val();
				var postId = $(_id).parent().find("input[type=hidden][name$=post]").val();
				var postName = $(_id).parent().find("input[type=text][name$=post]").val();
				//按值拆分
				if(checkMulselect("res_checkbox_"+resIndex)){
					//按值拆分 人员和岗位都是多值情况下
					if(personId.indexOf(";") > -1 && postId.indexOf(";") > -1){
						var personIdArr = personId.split(";");
						var personNameArr = personName.split(";");
						var postIds = postId.split(";");
						var postNameArr = postName.split(";");
						//数目可能不对等
						if(personIdArr.length > postIds.length){
							for(var k = 0;k < personIdArr.length;k++){
								var obj = {}
								postIds[k] =  postIds[k] ? postIds[k] : '';
								postNameArr[k] = postNameArr[k] ? postNameArr[k] : '';
								obj["col"] = name;
								obj["value"] = personIdArr[k] + ";" + postIds[k] + _splitStr + personNameArr[k] + ";" + postNameArr[k] + _splitStr + "8;4;";
								objArr.push(obj);
							}
							//放置空对象
							for(var m = 0;m < rowCount - personIdArr.length;m++){
								var obj = {}
								obj["col"] = "";
								obj["value"] = "";
								objArr.push(obj);
							}
						
						}else{
							for(var k = 0;k < postIds.length;k++){
								var obj = {}
								personIdArr[k] = personIdArr[k] ? personIdArr[k] : '';
								personNameArr[k] =  personNameArr[k] ? personNameArr[k] : '';
								obj["col"] = name;
								obj["value"] = personIdArr[k] + ";" + postIds[k] + _splitStr + personNameArr[k] + ";" + postNameArr[k] + _splitStr + "8;4;";
								objArr.push(obj);
							}
							//放置空对象
							for(var m = 0;m < rowCount - postIds.length;m++){
								var obj = {}
								obj["col"] = "";
								obj["value"] = "";
								objArr.push(obj);
							}
						}
						
					}else if(personId.indexOf(";") > -1 ){//人员为多值，岗位为单值或无值
						var personIdArr = personId.split(";");
						var personNameArr = personName.split(";");
						for(var k = 0;k < personIdArr.length;k++){
							var obj = {}
							obj["col"] = name;
							obj["value"] = personIdArr[k] + ";" + postId + _splitStr + personNameArr[k] + ";" + postName + _splitStr + "8;4;";
							objArr.push(obj);
						}
						//放置空对象
						for(var m = 0;m < rowCount - personIdArr.length;m++){
							var obj = {}
							obj["col"] = "";
							obj["value"] = "";
							objArr.push(obj);
						}
					}else if(postId.indexOf(";") > -1){//岗位为多值，人员为单值或无值
						var postIds = postId.split(";");
						var postNameArr = postName.split(";");
						for(var k = 0;k < postIds.length;k++){
							var obj = {}
							obj["col"] = name;
							obj["value"] = personId + ";" + postIds[k] + _splitStr + personName + ";" + postNameArr[k] + _splitStr + "8;4;";
							objArr.push(obj);
						}
						//放置空对象
						for(var m = 0;m < rowCount - postIds.length;m++){
							var obj = {}
							obj["col"] = "";
							obj["value"] = "";
							objArr.push(obj);
						}
					}else{//都为单值或无值
						var obj = {}
						obj["col"] = name;
						obj["value"] = personId + ";" + postId + _splitStr + personName + ";" + postName + _splitStr + "8;4;";
						objArr.push(obj);
					}
				}else{//未按值拆分，填充至行数
					var obj = {}
					obj["col"] = name;
					obj["value"] = personId + ";" + postId + _splitStr + personName + ";" + postName + _splitStr + "8;4;";
					for(var m = 0;m < rowCount;m++){
						objArr.push(obj);
					}
				}
				
				inputData.push(objArr);
			}else{
				$(_tr).find("input[data-type='fieldId']").each(function(j,_id){
					//input框的name,也就是条件或者结果对象的id
					var textInput = $(_id).next()[0];
					var name = $(_id).attr("name").replace(/\[[^\]]+\]/g, '');
					//区分是否常量
					var nameFieldValue = $(_id).parent().find("input[data-type='fieldText']").val() || $(_id).val();
					//input框的值
					var value = $(_id).val();
					var objArr = [];
					//数据格式{col:name,value:value+|||||+nameVal}
					//判断是否按值拆分 有分号说明不是单值且已勾选按值拆分
					
					//处理条件
					if($(_tr).hasClass("matrix_con_tr")){
						//多值
						if(value.indexOf(";") > -1){
							var arr = value.split(";");
							var nameArr = nameFieldValue.split(";");
							if(arr.length == rowCount){
								for(var k = 0;k < arr.length;k++){
									var obj = {}
									obj["col"] = name;
									obj["value"] = arr[k] + _splitStr + nameArr[k];
									objArr.push(obj);
								}
							}else{
								for(var k = 0;k < arr.length;k++){
									var obj = {}
									obj["col"] = name;
									obj["value"] = arr[k] + _splitStr + nameArr[k];
									objArr.push(obj);
								}
								//放置空对象
								for(var j = 0;j < rowCount - arr.length;j++){
									var obj = {}
									obj["col"] = '';
									obj["value"] = '';
									objArr.push(obj);
								}
							}
						}else if($(_tr).hasClass("matrix_con_tr") && !checkMulselect("checkbox_"+i)){//单值情况下没勾选按值拆分  条件
							//未按值拆分是填充值至行数
							var obj = {}
							obj["col"] = name;
							obj["value"] = value + _splitStr + nameFieldValue;
							for(var m = 0;m < rowCount;m++){
								objArr.push(obj);
							}
						}else{//按值拆分，只有一条数据
							var obj = {}
							obj["col"] = name;
							obj["value"] = value + _splitStr + nameFieldValue;
							objArr.push(obj);
							for(var m = 0;m < rowCount - 1;m++){
								var o = {}
								o["col"] = '';
								o["value"] = '';
								objArr.push(o);
							}
						}
					}else{//处理结果
						if(checkMulselect("res_checkbox_"+resIndex)){//勾选了按值拆分
							if(value.indexOf(";") > -1){
								var arr = value.split(";");
								var nameArr = nameFieldValue.split(";");
								if(arr.length == rowCount){
									for(var k = 0;k < arr.length;k++){
										var obj = {}
										obj["col"] = name;
										obj["value"] = arr[k] + _splitStr + nameArr[k];
										objArr.push(obj);
									}
								}else{
									for(var k = 0;k < arr.length;k++){
										var obj = {}
										obj["col"] = name;
										obj["value"] = arr[k] + _splitStr + nameArr[k];
										objArr.push(obj);
									}
									//放置空对象
									for(var j = 0;j < rowCount - arr.length;j++){
										var obj = {}
										obj["col"] = '';
										obj["value"] = '';
										objArr.push(obj);
									}
								}
							}else{
								var obj = {}
								obj["col"] = name;
								obj["value"] = value + _splitStr + nameFieldValue;
								objArr.push(obj);
								for(var m = 0;m < rowCount - 1;m++){
									var o = {}
									o["col"] = '';
									o["value"] = '';
									objArr.push(o);
								}
							}
						}else{
							//未按值拆分是填充值至行数
							var obj = {}
							obj["col"] = name;
							obj["value"] = value + _splitStr + nameFieldValue;
							for(var m = 0;m < rowCount;m++){
								objArr.push(obj);
							}
						}
					}
					inputData.push(objArr);
				})
			}
			if($(_tr).hasClass("matrix_res_tr")){
				resIndex++;
			}
		});
		var finalArr = [];
		for (i= 0;i<inputData[0].length ;i++ )
		{
		   finalArr[i]=[];

		}
		for ( i=0;i<inputData.length ;i++ )
		{
		  for ( j=0 ;j<inputData[0].length ;j++ )
		  {
		    finalArr[j][i] = inputData[i][j];
		  }

		}
		window.resultList = !flag ? [] : finalArr;
	}

	/* 检查是否多选,勾选按值拆分后为多选 */
	 window.checkMulselect = function(mulSelect){
			var enabledCheck = document.getElementById(mulSelect);
			var flag = false;
			if(enabledCheck){
				flag = enabledCheck.checked?true:false;
			}
			return flag;
	}
	
	/* 系统主数据 */
	window.Dialog_MainData = function(fieldId, fieldName, title, checkBoxId) {
		var curArea = $("#"+checkBoxId).closest("tr.matrix_add_tr");
		var selected = curArea.find("input[name='" + fieldId + "']").val();
		var fdNameTd = curArea.find("td.fdName");
	 	var text = fdNameTd[0].innerText;
		// fieldName过滤[X]字符
		var _fieldName = fieldName.replace(/\[[^\]]+\]/g, '');
		dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrixData_mainData.jsp?matrixId="+window.parent.MatrixResult.fdId+"&fieldName=" + _fieldName + "&selected=" + fieldId +"&mulSelect=" +checkMulselect(checkBoxId),
				title, function(data) {
			if(data) {
				if(data == "clear") {
					curArea.find("input[name='" + fieldId + "']").val("");
					curArea.find("input[name='" + fieldName + "']").val("");
				} else {
					if(data.id && data.id.indexOf(";") > -1){
						var arr = data.id.split(";");
						var msg = lang['sysOrgMatrix.result.maxLen100'].replace(/\{0\}/g,text);
						if(arr.length > 100)
							// 弹出提示信息
							dialog.alert(msg);
					}
					curArea.find("input[name='" + fieldId + "']").val(data.id);
					curArea.find("input[name='" + fieldName + "']").val(data.name);
				}
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
						var value = {}
						var ids = [], names = [], err = [];
						$(contentDoc).find("input[name='List_Selected']:checked").each(function(i, n) {
							var _id = $(n).val(), _name = $(n).parent().parent().find("td.mainData_title").text();
							if(checkUnique(fieldName, _id)) {
								ids.push(_id);
								names.push(_name);
							} else {
								err.push(_name);
							}
						});
						if(err.length > 0) {
							uniqueError(err.join(";"));
						}
						value.id = ids.join(";");
						value.name = names.join(";");
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
	 
	/* 地址本 */
	window.Dialog_Address_Cust = function(mulSelect, idField, nameField, splitStr, selectType, action) {
		var orgType = selectType == 'org' ? ORG_TYPE_ORG : selectType == 'dept' ? ORG_TYPE_DEPT : selectType == 'post' ? ORG_TYPE_POST : selectType == 'person' ? ORG_TYPE_PERSON : selectType == 'group' ? ORG_TYPE_GROUP : '';
		var curArea = $("#"+mulSelect).closest("tr.matrix_add_tr");
		var _idField = curArea.find("input[name='" + idField + "']");
		var _nameField = curArea.find("input[name='" + nameField + "']");
		var mulFlag = false;
		//人员加岗位回填只有单值,根据是否勾选判断是否按值拆分
		if(mulSelect.indexOf("res_checkbox") > -1 && !curArea.hasClass("matrix_res_person_post")){
			//结果字段不按值拆分也能多选
			mulFlag = true;
		}else{
			mulFlag = checkMulselect(mulSelect);
		}
		
		Dialog_Address(mulFlag, idField, nameField, splitStr, orgType,function(result) {
			if(action) {
				action(result, idField, nameField,mulSelect);
			} else {
			 	if(result.data.length > 0) {
					var ids = [], names = [], err = [];
					for(var i=0; i<result.data.length; i++) {
						var _id = result.data[i].id, _name = result.data[i].name;
						if(checkUnique(nameField, _id)) {
							ids.push(_id);
							names.push(_name);
						} else {
							err.push(_name);
						}
					}
					if(err.length > 0) {
						uniqueError(err.join(";"));
					}
					_idField.val(ids.join(";"));
					_nameField.val(names.join(";"));
				} else {
					_idField.val("");
					_nameField.val("");
				} 
			}
			// 清除临时表单填充数据
			$("input[name=__idField]").val("");
			$("input[name=__nameField]").val("");
		}, null, null, null, null, null, null, null);
	}
	/* 检查结果集数量 */
	window.resultCheck = function(rtnVal, idField, nameField, mulSelect) {
		var checkBox = $("#"+mulSelect);
	 	var curArea = $(checkBox).closest("tr.matrix_add_tr");
	 	var fdNameTd = curArea.find("td.fdName");
	 	var text = fdNameTd[0].innerText;
		__idField = curArea.find("input[name='" + idField + "']"),
		__nameField = curArea.find("input[name='" + nameField + "']");
		if(rtnVal && rtnVal.data) {
			//如果是结果字段未按值拆分，则值取前27条
			if(mulSelect.indexOf("res_checkbox") > -1 && !checkMulselect(mulSelect)){
				// 大于27组数据，强制裁剪
				if(rtnVal.data.length > 27) {
					rtnVal.data = rtnVal.data.slice(0, 27);
					var msg = lang['sysOrgMatrix.result.maxLen27'].replace(/\{0\}/g,text);
					// 弹出提示信息
					dialog.alert(msg);
				}
				var ids = [], names = [], err = [];
				for(var i=0; i<rtnVal.data.length; i++) {
					var _id = rtnVal.data[i].id, _name = rtnVal.data[i].name;
					if(checkUnique(nameField, _id)) {
						ids.push(_id);
						names.push(_name);
					} else {
						err.push(_name);
					}
				}
				if(err.length > 0) {
					uniqueError(err.join(";"));
				}
				__idField.val(ids.join(";"));
				__nameField.val(names.join(";"));
			}else{
				// 大于100组数据
				if(rtnVal.data.length > 100) {
					var msg = lang['sysOrgMatrix.result.maxLen100'].replace(/\{0\}/g,text);
					// 弹出提示信息
					dialog.alert(msg);
				}
				var ids = [], names = [], err = [];
				for(var i=0; i<rtnVal.data.length; i++) {
					var _id = rtnVal.data[i].id, _name = rtnVal.data[i].name;
					if(checkUnique(nameField, _id)) {
						ids.push(_id);
						names.push(_name);
					} else {
						err.push(_name);
					}
				}
				if(err.length > 0) {
					uniqueError(err.join(";"));
				}
				__idField.val(ids.join(";"));
				__nameField.val(names.join(";"));
			}
		} else {
			__idField.val("");
			__nameField.val("");
		}
	}
	/* 主要处理人+岗位的数据 */
	window.resultCheck2 = function(rtnVal, idField, nameField,mulSelect) {
	 	var curArea = $("#"+mulSelect).closest("tr.matrix_add_tr");
	 	var fdNameTd = curArea.find("td.fdName");
	 	var text = fdNameTd[0].innerText;
		var split = idField.split("_"),
			id = split[0],
			type = split[1],
			field = curArea.find("input[name='" + id + "']"),
			__idField = curArea.find("input[name='" + idField + "']"),
			__nameField = curArea.find("input[name='" + nameField + "']"),
			value = field.val() || "{}",
			json = JSON.parse(value);
		var __postId, __postName;
		// 如果选择的是人员，需要把岗位带出来
		if(idField.indexOf("_person") > 0) {
			var __post = $(curArea).find("div[name='" + idField + "']"),
				__postId = __post.find("input[name='" + idField.replace(/person/g, "post") + "']"),
				__postName = __post.find("input[name='" + nameField.replace(/person/g, "post") + "']");
		}
		if(rtnVal && rtnVal.data && rtnVal.data.length > 0) {
			if(mulSelect.indexOf("res_checkbox") > -1 && !checkMulselect(mulSelect)){
				// 大于27组数据，强制裁剪
				if(rtnVal.data.length > 27) {
					rtnVal.data = rtnVal.data.slice(0, 27);
					var msg = lang['sysOrgMatrix.result.maxLen27'].replace(/\{0\}/g,text);
					// 弹出提示信息
					dialog.alert(msg);
				}
			}
			// 超过100条弹出提示信息
			if(rtnVal.data.length > 100) {
				var msg = lang['sysOrgMatrix.result.maxLen100'].replace(/\{0\}/g,text);
				// 弹出提示信息
				dialog.alert(msg);
			}
			var ids = '';
			var names = '';
			var index = 0;
			for(var i = 0;i < rtnVal.data.length;i++){
				if(index > 0){
					ids += ';';
					names += ';';
				}
				ids += rtnVal.data[i].id;
				names += rtnVal.data[i].name;
				index++;
			}
			// 增加或替换
			json[type] = rtnVal.data[0].id;
			__idField.val(ids);
			__nameField.val(names);
			// 如果选择的是人员，需要把岗位带出来
			if(__postId) {
				// 根据人员获取该人员的岗位信息
				var data = new KMSSData();
				data.UseCache = false;
				data.AddBeanData("sysOrgMatrixService&type=get_post&persons=" + ids);
				var rtn = data.GetHashMapArray();
				if(rtn.length > 0) {
					var postId = [];
					var postName = [];
					for(var k=0; k<rtn.length; k++) {
						postId.push(rtn[k].postId);
						postName.push(rtn[k].postName);
					}
					// 岗位信息填充到页面
					json['post'] = rtn[0].postId;
					__postId.val(postId.join(";"));
					__postName.val(postName.join(";"));
				} else {
					delete json['post'];
					__postId.val("");
					__postName.val("");
				}
			}
		} else {
			// 删除
			delete json[type];
			__idField.val("");
			__nameField.val("");
			if(__postId) {
				delete json['post'];
				__postId.val("");
				__postName.val("");
			}
		}
		field.val(JSON.stringify(json)); 
	}
	
	/* 自定义数据 */
	window.Dialog_CustData = function(mulSelect, idField, nameField, splitStr, treeBean, treeTitle) {
		var curArea = $("#"+mulSelect).closest("tr.matrix_add_tr");
		var _idField = curArea.find("input[name='" + idField + "']");
		var _nameField = curArea.find("input[name='" + nameField + "']");
		var fdNameTd = curArea.find("td.fdName");
	 	var text = fdNameTd[0].innerText;
		// 往临时表单填充数据
		$("input[name='_idField']").val(_idField.val());
		$("input[name='_nameField']").val(_nameField.val());
		Dialog_Tree(checkMulselect(mulSelect), idField, idField, splitStr, treeBean, treeTitle, null, function(result) {
			if(result.data.length > 0) {
				var ids = [], names = [], err = [];
				for(var i=0; i<result.data.length; i++) {
					var _id = result.data[i].id, _name = result.data[i].name;
					if(checkUnique(nameField, _id)) {
						ids.push(_id);
						names.push(_name);
					} else {
						err.push(_name);
					}
				}
				if(err.length > 0) {
					uniqueError(err.join(";"));
				}
				_idField.val(ids.join(";"));
				_nameField.val(names.join(";"));
				if(result.data.length > 100){
					var msg = lang['sysOrgMatrix.result.maxLen100'].replace(/\{0\}/g,text);
					dialog.alert(msg);
				}
			} else {
				_idField.val("");
				_nameField.val("");
			}
			// 清除临时表单填充数据
			$("input[name=_idField]").val("");
			$("input[name=_nameField]").val("");
		});
	}
	
	/* 常量唯一性校验 */
	window.checkconstant = function(elem) {
		var val = $(elem).val(),
			field = $(elem).attr("name"),
			values = val.split(";"),
			error = [],
			succ = [];
		for(var i=0; i<values.length; i++) {
			var value = values[i];
			if(checkUnique(field, value)) {
				succ.push(value);
			} else {
				error.push(value);
			}
		}
		$(elem).val(succ.join(";"));
		if(error.length > 0) {
			uniqueError(error.join(";"));
		}
	}

	/* 数值区间唯一性校验 */
	window.checknumRange = function(elem) {
		var val = $(elem).val(),
			field = $(elem).parents("tr").find("td:eq(0)").text(),
			values = val.split(";"),
			error = [],
			succ = [];
		for(var i=0; i<values.length; i++) {
			var value = values[i];
			if(/^[\d.~～;-]*$/.test(value)) {
				succ.push(value);
			} else {
				error.push(value);
			}
		}
		$(elem).val(succ.join(";"));
		if(error.length > 0) {
			checkError(field, error.join(";"));
		}
	}

	/* 日期区间唯一性校验 */
	window.checkdateRange = function(elem) {
		var val = $(elem).val(),
			field = $(elem).parents("tr").find("td:eq(0)").text(),
			values = val.split(";"),
			error = [],
			succ = [];
		for(var i=0; i<values.length; i++) {
			var value = values[i];
			console.error("未处理 --- checkdateRange:", value);
			if(/^[\d.~～;-]*$/.test(value)) {
				succ.push(value);
			} else {
				error.push(value);
			}
		}
		$(elem).val(succ.join(";"));
		if(error.length > 0) {
			checkError(field, error.join(";"));
		}
	}

	/* 唯一性校验 */
	window.checkUnique = function(field, value) {
		var checked = true;
		value = value.replace(/(^\s*)|(\s*$)/g, "");
		if(value.length == 0) {
			// 空数据不校验
			return checked;
		}
		if(window.fdRelationConditionals) {
			for(var i=0; i<window.fdRelationConditionals.length; i++) {
				var con = window.fdRelationConditionals[i];
				if(field == con.fdFieldName && "true" == con.fdIsUnique) {
					// 检查后台数据
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("sysOrgMatrixService&type=unique&matrixId=" + window.matrixId + "&field=" + con.fdId + "&version=" + window.curVersion + "&id=&value=" + window.encodeURIComponent(value));
					var rtn = data.GetHashMapArray();
					if(rtn.length > 0) {
						checked = false;
					}
					if(checked) {
						// 如果后台数据没有重复，还需要检查页面数据
						var tab = $(parent.document).find("#matrix_data_table_" + window.curVersion),
							th = tab.find("th[data-field='" + con.fdFieldName + "']"),
							idx = th.prevAll().length;
						tab.find("tr").each(function(i, n) {
							var val = $(n).find("td:eq(" + idx + ")").find("[name^="+con.fdId+"]").val();
							if(value == val) {
								checked = false;
								return false;
							}
						});
					}
					break;
				}
			}
		}
		return checked;
	}
});