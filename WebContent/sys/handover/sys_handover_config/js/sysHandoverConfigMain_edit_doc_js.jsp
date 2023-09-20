<!--文档类js-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var _validator = $KMSSValidation();
	_validator.addValidator('handoverNameSame',"<bean:message key='sysHandoverConfigMain.notEq' bundle='sys-handover' />",function(v, e, o) {
		var fdFromId = $("input[name='fdFromId']").val();
		var fdToId = $("input[name='fdToId']").val();
		if(fdFromId != "" && fdToId != "" && fdToId == fdFromId)
			return false;
		else
			return true;
	});
	
	seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		// 初始化树
		window.initTree = function() {
			if(jstree) {
				jstree.open_all();
				jstree.check_all();
				$.each(jstree.get_node("handler_root_node").children_d, function(i, n) {
					jstree.hide_node(n + "-hide1");
					jstree.hide_node(n + "-hide2");
					var a = $("#" + n.replace(/\./g, "\\\.") + " a");
					var id_data = a.attr("a_id_data");
					if (id_data) {
						$(a.parent()[0]).removeClass("jstree-closed jstree-open").addClass("jstree-leaf");
						$("#valueDiv").append("<input type='hidden' name='" + n + "'/>");
						$(a[0]).after("<span class='a_doc' onclick='dialogDetail(\"" + n + "\");'>${ lfn:message('sys-handover:sysHandoverConfigMain.detail') }</span>");
					}
				});
			}
		};

		// 构建模块树
		window.buildTreeModel = function(data) {
			var node = {};
			node.id = data.module;
			node.text = data.moduleMessageKey;
			node.text += ' <span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.total") }<span class="number_total" id="' + node.id + '_number_total">' + data.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }';
			node.text += '<span class="split"></span>${ lfn:message("sys-handover:sysHandoverConfigMain.total.select") }<span class="number" id="' + node.id + '_number">' + data.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }';
			node.text += ')</span>';

			var items = data.item;
			if (items instanceof Array) {
				// 三级
				node.children = [];
				$.each(items, function(i, n) {
					if (n.total > 0)
						node.children.push(buildNodeItem(n));
				});
			}

			return node;
		};

		// 构建模块交接项
		window.buildNodeItem = function(data) {
			var node = {};
			node.id = data.module + "-" + data.item;
			node.text = data.itemMessageKey;
			node.text += ' <span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.total") }<span class="number_total" id="' + node.id + '_number_total">' + data.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }';
			node.text += '<span class="split"></span>${ lfn:message("sys-handover:sysHandoverConfigMain.total.select") }<span class="number" id="' + node.id + '_number">' + data.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }';
			node.text += ')</span>';
			node.a_attr = {
				a_id_data : node.id
			};
			// 增加2条无意义的数据，目的是在选择文档明细时，可以设置交接项目的树状态
			node.children = [ {
				text : "hide1",
				id : data.module + "-" + data.item + "-hide1"
			}, {
				text : "hide2",
				id : data.module + "-" + data.item + "-hide2"
			} ];
			return node;
		};

		// 文档类交接显示明细表
		window.dialogDetail = function(itemMark) {
			var url = "/sys/handover/sys_handover_doc/sysHandoverDoc_detail.jsp";
			var fdFromId = $("input[name='fdFromId']")[0].value;
			var moduleName = itemMark.split("-")[0];
			var item = itemMark.split("-")[1];
			var _itemMark = itemMark.replace(/\./g, "\\\.");

			window.selectedIds = $("#valueDiv input[name=" + _itemMark + "]").val(); // 已经选中的记录

			var title = '<bean:message bundle="sys-handover" key="sysHandoverConfigMain.detail"/>';
			switch (item) {
			case ("handlerIds"): {
				title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.handlerIds"/>)';
				break;
			}
			case ("optHandlerIds"): {
				title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.optHandlerIds"/>)';
				break;
			}
			case ("handlerIds_later"): {
				title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.handlerIds_later"/>)';
				break;
			}
			case ("privilegerIds"): {
				title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.privilegerIds"/>)';
				break;
			}
			case ("otherCanViewCurNodeIds"): {
				title += '(<bean:message bundle="sys-handover-support-config-doc" key="sysHandoverConfigHandler.otherCanViewCurNodeIds"/>)';
				break;
			}
			default: {
				var itemText = $("a[a_id_data='" + itemMark + "']").text();
				if (itemText && itemText.indexOf("\(")) {
					title += "(" + itemText.substring(0,itemText.indexOf("\(")).replace(/(^\s*)|(\s*$)/g, "") + ")";
				}
			}
			}

			url += "?type=${param.type}&fdFromId=" + fdFromId + "&moduleName=" + moduleName + "&item=" + item;
			dialog.build({
				config : {
					width : 900,
					height : 500,
					lock : true,
					cache : false,
					title : title,
					content : {
						id : 'dialog_iframe',
						scroll : true,
						type : "iframe",
						url : url
					}
				},
				callback : function(ids, dialog) {
					if (null != ids && undefined != ids) {
						//存储明细
						$("#valueDiv input[name=" + _itemMark + "]").val(ids);
						//更新已选条数
						var selectCount = ids.length;
						var oldItemNum = parseInt($("#" + _itemMark + "_number").text());
						//交接项修改
						itemChange(_itemMark, selectCount);
					}
				}
			}).show();
		};

		// 交接项修改
		window.itemChange = function(_itemMark, selectCount) {
			// 修改交接项的数量
			$("#" + _itemMark + "_number").text(selectCount);
			// 修改交接项的样式
			var itemNum = parseInt($("#" + _itemMark + "_number_total").text());
			var _node = _itemMark.replace(/\\/g, "");
			if (selectCount == itemNum) { // 全选
				jstree.check_node(_node + "-hide1");
				jstree.check_node(_node + "-hide2");
			} else if (selectCount == 0) { // 全不选
				jstree.uncheck_node(_node + "-hide1");
				jstree.uncheck_node(_node + "-hide2");
			} else { // 半选
				jstree.uncheck_node(_node + "-hide1");
				jstree.check_node(_node + "-hide2");
			}

			// 修改交接模块的数量
			var _item = _itemMark.split("-")[0];
			var numbers = $("#" + _item + " span.number");
			var moduleNum = 0;
			for ( var i = 1; i < numbers.length; i++) {
				moduleNum += parseInt($(numbers[i]).text());
			}
			$(numbers[0]).text(moduleNum);

			changeSubmitNum();
		};

		// 修改提交按钮的数量
		window.changeSubmitNum = function() {
			var total = 0;
			// 计算模块的选中数量
			$.each(jstree.get_node("handler_root_node").children, function(i, n) {
				var moduleNum = parseInt($("#" + n.replace(/\./g, "\\\.") + "_number").text());
				total += moduleNum;
			});
			putCountIntoBtn($("#submit .lui_widget_btn_txt"), total);
		};

		// 模块或交接修改
		window.moduleChange = function(id, checked) {
			if ("handler_root_node" == id) { // 全选
				$.each($("#valueDiv input"), function(i, n) {
					$(n).val("");
				});

				$.each(jstree.get_node("handler_root_node").children, function(i, n) {
					// 计算模块
					var moduleNum = 0;
					if (checked) {
						moduleNum = parseInt($("#" + n.replace(/\./g, "\\\.") + "_number_total").text());
					}
					$("#" + n.replace(/\./g, "\\\.") + "_number").text(moduleNum);

					// 计算交接项
					$.each(jstree.get_node(n).children, function(i, n) {
						var itemNum = 0;
						if (checked) {
							itemNum = parseInt($("#" + n.replace(/\./g, "\\\.") + "_number_total").text());
						}
						$("#" + n.replace(/\./g, "\\\.") + "_number").text(itemNum);
					});
				});
			} else if (id.indexOf("-") == -1) { // 修改模块
				// 计算模块
				var moduleNum = 0;
				if (checked) {
					moduleNum = parseInt($("#" + id.replace(/\./g, "\\\.") + "_number_total").text());
				}
				$("#" + id.replace(/\./g, "\\\.") + "_number").text(moduleNum);

				// 计算交接项
				$.each(jstree.get_node(id).children, function(i, n) {
					$("#valueDiv input[name=" + n.replace(/\./g, "\\\.") + "]").val("");
					var itemNum = 0;
					if (checked) {
						itemNum = parseInt($("#" + n.replace(/\./g, "\\\.") + "_number_total").text());
					}
					$("#" + n.replace(/\./g, "\\\.") + "_number").text(itemNum);
				});
			} else if (id.length > 1) { // 修改交接项
				$("#valueDiv input[name=" + id.replace(/\./g, "\\\.") + "]").val("");
				// 计算交接项
				var itemNum = 0;
				var itemTotal = parseInt($("#" + id.replace(/\./g, "\\\.") + "_number_total").text());
				if (checked) {
					itemNum = itemTotal;
				}
				$("#" + id.replace(/\./g, "\\\.") + "_number").text(itemNum);

				// 计算模块
				var parent = jstree.get_node(id).parent;
				itemNum = 0;
				$.each(jstree.get_node(parent).children, function(i, n) {
					itemNum += parseInt($("#" + n.replace(/\./g, "\\\.") + "_number").text());
				});
				$("#" + parent.replace(/\./g, "\\\.") + "_number").text(itemNum);
			}
			// 计算提交按钮
			changeSubmitNum();
		};
		
		// 提交前检验
		form_validator = function() {
			if(!_validator.validate()){
				return false;
			}
			return true;
		}

		//提交任务
		window.submitOperation = function() {
			if(!form_validator()) {
				return;
			}
			var fdToId = $("input[name='fdToId']")[0].value;
			//接收人为空确认
			if (fdToId == null || fdToId == "") {
				var hasHander = false;
				// 如果有当前处理人，并且接收人为空，则弹出提示，不允许提交
				$.each(jstree.get_node("handler_root_node").children, function(i, n) {
					var number = $("#" + n.replace(/\./g, "\\\.") + "-handlerIds_number");
					var count = parseInt(number.text());
					if (count > 0) {
						hasHander = true;
						return false;
					}
				});

				if (hasHander) {
					dialog.alert('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.fdToNameNotNull"/>');
					return;
				} else {
					dialog.confirm('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.fdToNameNotNullConfirm"/>', window.submitOperation2);
				}
			} else {
				window.submitOperation2(true);
			}
		};

		window.submitOperation2 = function(val) {
			if (!val)
				return;

			// 加载图标
			window.del_load2 = dialog.loading();
			
			var total = 0;
			var submitData = {};
			var moduleDataArr = new Array();
			$.each(jstree.get_node("handler_root_node").children, function(i, n) {
				var moduleData = {};
				var itemDataArr = new Array();
				var number = $("#" + n.replace(/\./g, "\\\.") + "_number");
				var moduleNum = parseInt(number.text()); //累加总数
					total += moduleNum;

					$.each(jstree.get_node(n).children, function(i, n) {
						var itemData = {};
						itemData.item = n.split("-")[1];
						itemData.itemNumber = parseInt($("#" + n.replace(/\./g, "\\\.") + "_number").text());
						if(itemData.itemNumber == 0) {
							return true;
						}

						var ids = $("#valueDiv input[name=" + n.replace(/\./g, "\\\.") + "]").val();
						if (ids.length > 0) {
							itemData.ids = ids;
						} else {
							itemData.isAll = true;
						}

						itemDataArr.push(itemData);
					});

					moduleData.module = n; //构建
					moduleData.total = moduleNum;
					moduleData.items = itemDataArr;
					moduleDataArr.push(moduleData);
				});

			//构建提交数据
			submitData.total = total;
			if (submitData.total == 0) {
				// 关闭加载图标
				if(window.del_load2 != null) {
					window.del_load2.hide();
				}
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			submitData.modules = moduleDataArr;

			// 执行时间类型
			var execType = $("input[name='execType']:checked").val();
			// 执行交接方式
			var execMode = $("input[name='execMode']:checked").val();
			var fdId = $("input[name='fdId']")[0].value;
			
			var fdFromId = $("input[name='fdFromId']").val();
			var fdToId = $("input[name='fdToId']").val();

			//提交job
			var url = Com_SetUrlParameter(location.href, "method", "submit");
			var data = {
				fdId : fdId,
				type : "${param.type}",
				execType : execType,
				execMode : execMode,
				fdFromId : fdFromId,
				fdToId : fdToId,
				fdContent : JSON.stringify(submitData)
			};

			LUI.$.ajax( {
				url : url,
				type : 'post',
				dataType : 'text',
				data : data,
				success : function(data, textStatus, xhr) {
					// 关闭加载图标
					if(window.del_load2 != null) {
						window.del_load2.hide();
					}
					if (data != "") {
						if("true" == data) {
							dialog.success('<bean:message key="return.optSuccess" />');
						} else {
							dialog.alert(data,function(){
								location.reload();
							});
						}
					} else {
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				}
			});
		};

		// 一键展开、折叠
		window.oneKeyShow = function(isShow) {
		};
	});
</script>