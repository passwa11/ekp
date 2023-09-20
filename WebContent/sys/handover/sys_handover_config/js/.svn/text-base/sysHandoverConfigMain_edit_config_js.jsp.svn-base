<!--配置类js-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		window.resourceState = [];
		window.moduleChange = function(id, checked, docId) {
			if ("handler_root_node" == id) { // 全选
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
			} else if (docId) { // 修改交接文档
				// 计算交接项
				var itemNum = parseInt($("#" + id.replace(/\./g, "\\\.") + "_number").text());
				if (checked) {
					itemNum++;
				} else {
					itemNum--;
				}
				$("#" + id.replace(/\./g, "\\\.") + "_number").text(itemNum);
	
				// 计算模块
				var parent = jstree.get_node(id).parent;
				var moduleNum = parseInt($("#" + parent.replace(/\./g, "\\\.") + "_number").text());
				if (checked) {
					moduleNum++;
				} else {
					moduleNum--;
				}
				$("#" + parent.replace(/\./g, "\\\.") + "_number").text(moduleNum);
			} else if (id.indexOf("-") != -1) { // 修改交接项
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
			} else { // 修改模块
				// 计算模块
				var moduleNum = 0;
				if (checked) {
					moduleNum = parseInt($("#" + id.replace(/\./g, "\\\.") + "_number_total").text());
				}
				$("#" + id.replace(/\./g, "\\\.") + "_number").text(moduleNum);
	
				// 计算交接项
				$.each(jstree.get_node(id).children, function(i, n) {
					var itemNum = 0;
					if (checked) {
						itemNum = parseInt($("#" + n.replace(/\./g, "\\\.") + "_number_total").text());
					}
					$("#" + n.replace(/\./g, "\\\.") + "_number").text(itemNum);
				});
			}
			// 计算提交按钮
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
			putCountIntoBtn($("#execute .lui_widget_btn_txt"), total);
		};

		// 初始化树状态，只显示到模块项，隐藏文档列表
		window.initTree = function(checkAll) {
			if(jstree) {
				jstree.close_all();
				jstree.open_node("handler_root_node");
				$.each(jstree.get_node("handler_root_node").children, function(i, n){
					if('handOverSysOrganization' != n) {
						jstree.open_node(n);
					}
				});
				if(checkAll == undefined || checkAll == null || checkAll) {
					try {
						jstree.check_all();
					} catch(e){}
				}
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
			if(items instanceof Array) {
				// 三级
				node.children = [];
				$.each(items, function(i, n) {
					if(n.total > 0)
						node.children.push(buildNodeItem(n));
				});
			} else {
				// 二级
				items = data.handoverRecords
				node.children = [];
				$.each(items, function(i, n) {
					node.children.push(buildNodeRecords(n));
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
			
			if(data.handoverRecords.length > 0) {
				node.children = [];
				$.each(data.handoverRecords, function(i, n) {
					node.children.push(buildNodeRecords(n));
				});
			}
			return node;
		};

		// 构建交接明细（模板类）
		window.buildNodeRecords = function(data) {
			var text = data.datas[0].replace(/<br/g, "< br");
			var node = {};
			node.text = autoAddEllipsis(text, 80);
			node.name = data.id;
			node.a_attr = { href : "${LUI_ContextPath}" + data.url, a_id_data : data.id, title: text };
			return node;
		};

		// 一键展开、折叠
		window.oneKeyShow = function(isShow) {
			if (isShow == true) {
				$(".a_spead_onekey").hide();
				$(".a_retract_onekey").show();
				if(jstree)
					jstree.open_all();
			} else {
				$(".a_retract_onekey").hide();
				$(".a_spead_onekey").show();
				initTree(false);
			}
		};

		// 处理操作
		window.executeOperation = function() {
			var fdToId = $("input[name='fdToId']")[0].value;
			// 接收人为空确认
			if (fdToId == null || fdToId == "") {
				dialog.confirm('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.fdToNameNotNullConfirm"/>', function(value) {
					if (value == true) {
						var isOk = true;
						// 执行前需要检查是否包含“组织机构”中的“角色线配置”，角色线配置交接时，接收人不能为空
						if (jstree.get_node("handOverSysOrganization").children_d) {
							$.each(jstree.get_node("handOverSysOrganization").children_d, function(i, n) {
								var node = jstree.get_node(n);
								if(jstree.is_checked(n) && node.a_attr.a_id_data.indexOf("LINE")!=-1) {
									isOk = false;
									dialog.alert('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.fdToName.line.nonull"/>');
									return false;
								}
							});
						}
						if(isOk)
							initExecute();
					}
				});
			} else {
				initExecute(fdToId);
			}
		};

		// 初始化处理内容
		window.initExecute = function(fdToId) {
			// 获取交接内容
			var checkRecoIds = false;
			var moduleRecoIds = {};
			var moduleArr = new Array();

			// 判断是否有交接内容（根据“处理”按钮的数量来判断）
			var num = $("#execute .lui_widget_btn_txt").text().replace(/[^0-9]+/g, '');
			if(num < 1) {
				dialog.alert('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.executeContentNotNull"/>');
				return false;
			}

			// 判断交接人和接收人是否相同
			var fdFromId = $("input[name='fdFromId']")[0].value;
			if (fdFromId == fdToId) {
				dialog.alert('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.notEq"/>');
				return false;
			}

			// 加载图标
			window.del_load2 = dialog.loading();
			
			// 获取所有模块
			var modules = [];
			$.each(jstree.get_node("handler_root_node").children, function(i, n) {
				modules.push(n);
			});

			window.resourceState = [];
			var mainId = $("input[name='fdId']").val();
			execute(mainId, fdFromId, fdToId, modules, 0);
		};

		// 处理数据
		window.execute = function(mainId, fdFromId, fdToId, modules, index) {
			if (index >= modules.length) {
				// 关闭加载图标
				if(window.del_load2 != null) {
					window.del_load2.hide();
				}
			
				$("#execute").hide();
				$("#operationArea").hide();
				$("#operationCompletion").show();
				
				// 展开所有树型结构
				jstree.open_all();
				// 禁用所有节点
				jstree.disable_node("handler_root_node");
				jstree.disable_node(jstree.get_node("handler_root_node").children_d);

				// 标记交接状态
				$.each(window.resourceState, function(i, n) {
					$("#" + n.id).append(n.state);
				});

				window.resourceState = [];
				return;
			}

			var item = modules[index];
			// 处理模块数据
			var url = Com_SetUrlParameter(location.href, "method", "perform");
				
			var recordIds = "";
			$.each(jstree.get_node(item).children_d, function(i, n) {
				var node = jstree.get_node(n);
				if(node.a_attr && node.a_attr.href && node.a_attr.href !== '#' && jstree.is_checked(n)) {
					recordIds += node.a_attr.a_id_data + ",";
				}
			});
			if(recordIds.length < 1) {
				index++;
				execute(mainId, fdFromId, fdToId, modules, index);
				return;
			}
			
			// 每个模块请求一次
			var data = {
				ids : recordIds,
				fdFromId : fdFromId,
				fdToId : fdToId,
				mainId : mainId
			};

			LUI.$.ajax( {
				url : url,
				type : 'post',
				dataType : 'json',
				data : data,
				success : function(data, textStatus, xhr) {
					index++;
					if (data != "") {
                        if(data.hasOwnProperty("errorKey")) {
                            var desc = data["errorKey"];
                            dialog.failure(desc);
                        }else{
                            afterExecute(item, data);
                        }
					} else {
						dialog.failure('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.executeFailture"/>');
					}
					execute(mainId, fdFromId, fdToId, modules, index);
				}
			});
		};

		// 显示处理状态
		window.afterExecute = function(module, resultJson) {
			$.each(jstree.get_node(module).children_d, function(i, n) {
				var node = jstree.get_node(n);
				if(node.a_attr && node.a_attr.href && node.a_attr.href !== '#' && jstree.is_checked(n)) {
					var name = node.a_attr.a_id_data;
					var span = "";
					
					if (resultJson.info != "") { // 警告 或 成功
						var desc = resultJson.info[name];
						if (desc != null) { // 警告 
							span = '<span class="span_info" title="' + desc + '"></span>';
						} else { // 成功
							span = '<span class="span_success"></span>';
						}
					} else if (resultJson.err != "") { // 错误 或 成功
						var desc = resultJson.err[name];
						if (desc != null) { // 警告 
							span = '<span class="span_err" title="' + desc + '"></span>';
						} else { // 成功
							span = '<span class="span_success"></span>';
						}
					} else { // 成功
						span = '<span class="span_success"></span>';
					}
					window.resourceState.push({id: n, state: span});
				}
			});
		};

		/** ==============================截取字符串长期========================================*/
		// 处理过长的字符串，截取并添加省略号（注：半角长度为1，全角长度为2）
		window.autoAddEllipsis = function(pStr, pLen) {
            /* var _ret = cutString(pStr, pLen);
            var _cutFlag = _ret.cutflag;
            var _cutStringn = _ret.cutstring;
            if ("1" == _cutFlag) {
                return _cutStringn + "...";
            } else {
                return _cutStringn;
            } */
            // #147297 建议不截取字符
            return pStr;
		} 
		window.cutString = function(pStr, pLen) { 
		    // 原字符串长度 
		    var _strLen = pStr.length; 
		    var _tmpCode; 
		    var _cutString; 
		    // 默认情况下，返回的字符串是原字符串的一部分 
		    var _cutFlag = "1"; 
		    var _lenCount = 0; 
		    var _ret = false; 
		    if (_strLen <= pLen/2) { 
		        _cutString = pStr; 
		        _ret = true; 
		    } 
		    if (!_ret) { 
		        for (var i = 0; i < _strLen ; i++ ) { 
		            if (isFull(pStr.charAt(i))) { 
		                _lenCount += 2; 
		            } else { 
		                _lenCount += 1; 
		            } 
		            if (_lenCount > pLen) { 
		                _cutString = pStr.substring(0, i); 
		                _ret = true; 
		                break; 
		            } else if (_lenCount == pLen) { 
		                _cutString = pStr.substring(0, i + 1); 
		                _ret = true; 
		                break; 
		            } 
		        } 
		    } 
		    if (!_ret) { 
		        _cutString = pStr; 
		        _ret = true; 
		    } 
		    if (_cutString.length == _strLen) { 
		        _cutFlag = "0"; 
		    } 
		    return {"cutstring":_cutString, "cutflag":_cutFlag}; 
		}
		// 判断是否为全角
		window.isFull = function(pChar) {
			  for (var i = 0; i < pChar.strLen ; i++ ) {     
			    if ((pChar.charCodeAt(i) > 128)) { 
			        return true; 
			    } else { 
			        return false; 
			    }
			}
		}
	});
</script>