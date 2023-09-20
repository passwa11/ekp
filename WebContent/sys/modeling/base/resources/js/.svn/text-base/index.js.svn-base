
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic','lang!','lang!sys-modeling-main'], function($, dialog, topic,lang,mainLang) {
	// 监听新建更新等成功后刷新
	topic.subscribe('successReloadPage', function() {
		topic.publish("list.refresh");
	});

	//删除文档
	delDoc = function () {
		var values = [];
		$("input[name='List_Selected']:checked").each(function () {
			values.push($(this).val());
		});
		if (values.length === 0) {
			dialog.alert(mainLang['modeling.select.data.deleted']);
			return;
		}
		var actionUrl = 'sys/modeling/main/modelingAppSimpleMain.do';
		if (listOption.isFlow === 'true') {
			actionUrl = 'sys/modeling/main/modelingAppModelMain.do';
		}
		var config = {
			url: Com_Parameter.ContextPath + actionUrl + '?method=deleteall', // 删除数据的URL
			data: $.param({"List_Selected": values}, true) // 要删除的数据
		};
		// 通用删除方法
		Com_Delete(config, delCallback);
	}

	//删除回调
	function delCallback(data) {
		if (window.del_load != null)
			window.del_load.hide();
		if (data != null && data.status == true) {
			topic.publish("list.refresh");
			dialog.result(data);
		} else {
			//#129895、 #130038
			var msg = "";
			if(data.responseJSON){
				var messageArr = data.responseJSON.message;
				if(messageArr){
					for(var i = 0;i < messageArr.length;i++){
						if(!messageArr[i].isOk){
							msg = messageArr[i].msg;
							break;
						}
					}
				}
			}
			if(msg){
				var message = "";
				if(msg.indexOf(data.responseJSON.title) > -1){
					message = msg;
				}else{
					message = data.responseJSON.title + msg;
				}
				dialog.result({"status" : data.responseJSON.status, "title" : message});
			}else{
				dialog.result(data);
			}
		}
	}

	//新建文档
	function addDoc() {
		var fdAppModelId = listOption.fdAppModelId;
		if (listOption.isFlow === 'true') {
			var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + fdAppModelId;
			$.ajax({
				url: url,
				method: 'GET',
				async: false
			}).success(function (ret) {
				if (ret && ret.status) {
					if(ret.status == "success"){
						if (ret.data && ret.data.length === 1) {
							var id = ret.data[0].id;
							Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=" + fdAppModelId + "&fdAppFlowId=" + id, "_blank");
						} else {
							var url = "/sys/modeling/main/flow_dialog.jsp?fdAppModelId=" + fdAppModelId;
							dialog.iframe(url, mainLang['modeling.select.process'], function (param) {
								if(param) {
									var data = $.parseJSON(param);
									if (data.url){
										Com_OpenWindow(Com_Parameter.ContextPath +data.url, "_blank");
									}
								}
							}, {width: 750, height: 500})
						}
					}else{
						dialog.alert(mainLang['modeling.findFlow.error']);
					}
				} else {
					//#169527 该处需要兼容未登录状态，目前除了登录过滤器以及建模发布状态过滤器这两个，没有考虑到其他情况会走向该分支
					if(ret.indexOf("module.main.444.flag")>-1){
						window.parent.document.write(ret)
					}
				}
			});
		} else {
			Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppSimpleMain.do?method=add&fdAppModelId=" + fdAppModelId, "_blank");
		}
	}

	// 导入文档
	function importDoc() {
		var fdAppModelId = listOption.fdAppModelId;
		var id = "";
		var changeFlow = false;
		if (listOption.isFlow === 'true') {
			var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + fdAppModelId;
			$.ajax({
				url: url,
				method: 'GET',
				async: false
			}).success(function (ret) {
				if (ret && ret.data) {
					if (ret.data.length === 1) {
						id = ret.data[0].id;
					} else {
						changeFlow = true;
					}
				} else {
					alert(ret);
				}
			});
		}
		
		var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingImportConfig.do?method=findConfigs&fdAppModelId=" + fdAppModelId;
		$.ajax({
			url: url,
			method: 'GET',
			async: false
		}).success(function (ret) {
			if (ret && ret.data) {
				//#143273
				if(ret.data.length==0){
					dialog.alert(mainLang['modeling.import.template.no.found.contact.administrator']);
					return;
				}
				//导入机制优化
				var url = "/sys/modeling/main/transport/import_config_dialog.jsp?fdAppModelId=" + fdAppModelId+"&enableFlow="+ret.data[0].enableFlow + "&fdAppFlowId=" + id+"&changeFlow="+changeFlow;
				dialog.iframe(url, mainLang['modeling.data.import'], null, {width: 750, height: 560})

				// if (ret.data.length === 1) {
				// 	var id = ret.data[0].id;
				// 	//Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingImportConfig.do?method=importDoc&type=upload&fdId=" + id + "&fdAppModelId=" + fdAppModelId, "_blank");
				// 	dialog.iframe( "/sys/modeling/main/modelingImportConfig.do?method=importDoc&type=upload&fdId=" + id + "&fdAppModelId=" + fdAppModelId, "数据导入", null, {width: 750, height: 500})
				// } else {
				// 	var url = "/sys/modeling/base/transport/import_config_dialog.jsp?fdAppModelId=" + fdAppModelId;
				// 	dialog.iframe(url, "选择导入配置", null, {width: 750, height: 500})
				// }
			} else {
				alert(ret);
			}
		});
	}
	
	//批量打印
	function batchPrint(){
		var values = [];
		$("input[name='List_Selected']:checked").each(function(){
			values.push($(this).val());
		});
		if(values.length==0){
			dialog.alert(lang['page.noSelect']);
			return;
		}
		if(values.length>50){
			dialog.alert(mainLang['main.print.hint']);
			return;
		}
		var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppSimpleMain.do?method=printBatch&fdIds=" + values;
		if (listOption.isFlow === 'true') {
			url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppModelMain.do?method=printBatch&fdIds=" + values;
		}
		url += "&fdAppModelId="+listOption.fdAppModelId;
		Com_OpenWindow(url);
	};
	
	window.delCallback = delCallback;
	window.addDoc = addDoc;
	window.importDoc = importDoc;
	window.batchPrint = batchPrint;
});