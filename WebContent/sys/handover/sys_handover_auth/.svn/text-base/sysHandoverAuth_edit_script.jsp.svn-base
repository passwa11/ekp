<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
	//选择无效的组织
	function selectInvalid() {
		Dialog_AddressList(false,'fdFromId','fdFromName',';','sysHandoverService&orgType=ORG_TYPE_POSTORPERSON,ORG_TYPE_DEPT',changeFromName,
		'sysHandoverService&orgType=ORG_TYPE_POSTORPERSON,ORG_TYPE_DEPT&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
	}
	var _validator = $KMSSValidation();
	_validator.addValidator('handoverNameSame',"<bean:message key='sysHandoverConfigMain.notEq' bundle='sys-handover' />",function(v, e, o) {
		var fdFromId = $("input[name='fdFromId']").val();
		var fdToId = $("input[name='fdToId']").val();
		if(fdFromId != "" && fdToId != "" && fdToId == fdFromId)
			return false;
		else
			return true;
	});
	
	var jstree, treeContent = [];

	// 显示树内容
	function showTree() {
		var isEmpty = true;
		$.each(treeContent, function(i, n) {
			if(n.children.length > 0) {
				isEmpty = false;
				return true;
			}
		});
		if (isEmpty) {
			$("#resultContent").html("<h3 style='text-align:center;'><span class='txtstrong'>${ lfn:message('sys-handover:sysHandoverConfigMain.noResult.info') }</span></h3>");
			$("#submit2").hide();
			return;
		}
		$("#submit2").show();
		// 删除无数据的节点
		var newTreeContent = [];
		$.each(treeContent, function(i, n) {
			if(n.text.length > 0) {
				newTreeContent.push(n);
			}
		});
		$("#operationArea").show();

		// 如果树已存在，需要销毁
		try {
			if (jstree) {
				jstree.destroy();
			}
		} catch (e) {
		}

		// 创建树
		$('#resultContent').jstree( {
			plugins : [ 'checkbox' ],
			checkbox : {
				// 消除行选中样式
				keep_selected_style : false,
				tie_selection : false,
				whole_node : false
			},
			core : {
				themes : {
					// 消除连线
					dots : false,
					// 消除图标
					icons : false
				},
				data : newTreeContent
			}
		})
		.on('click.jstree', function(e) {
			var target = $(e.target);
            if(target.hasClass("a_doc")) {
                return false;
            }
            var id = target.parents('li').attr('id');
			if(id) {
				var checked = jstree.is_checked(id);
				moduleChange(id, checked);
			}
		})
		.on('ready.jstree', function(e, data) {
			jstree = data.instance;
			jstree.open_all();
			jstree.check_all();
			
			$.each(newTreeContent, function(i, n) {
				$.each(jstree.get_node(n).children_d, function(j, m) {
					jstree.hide_node(m + "-hide1");
					jstree.hide_node(m + "-hide2");
				});
			});
		});
	}

	checkSituation = function () 
	{
		
		var checkValue = $('input[name="_fdAuthType"]:checked').length;
		if(checkValue <= 0)
		{
			
			var warmMsg = '<div class="validation-advice" id="advice-_validate_author" _reminder="true">'
			+'<table class="validation-table"><tbody><tr><td>'
			+'<div class="lui_icon_s lui_icon_s_icon_validator"></div></td>'
			+'<td class="validation-advice-msg">'
			+'<span class="validation-advice-title"></span> ${lfn:message("sys-handover-support-config-auth:sysHandoverDocAuth.authAttNotNull")}</td></tr></tbody></table></div>';
		    
			$('#transAuthor').append(warmMsg);
		}else
		{
			$("#advice-_validate_author").remove();
		}
	}
	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
		// 提交前检验
		form_validator = function() {
			if(!_validator.validate()){
				return false;
			}
			
			if($('input[name="searchModuleCheckBox"]:checked').length < 1) {
				dialog.alert("${ lfn:message('sys-handover:sysHandoverConfigMain.searchKeysNotNull') }");
				return false;
			}
			return true;
		}
		
		// 修改交接人
		changeFromName = function () {
			$($("input[name='fdFromName']")[1]).val($("input[name='fdFromName']")[0].value);
		};
		// 修改接收人
		changeToName = function () {
			$($("input[name='fdToName']")[1]).val($("input[name='fdToName']")[0].value);
		};

		// 查询全选
		searchCheckAll = function () {
			var isChecked = $('#_searchSelectAll').is(":checked");
			$("[name = searchModuleCheckBox]:checkbox").each( function() {
				if (isChecked) {
					$(this).prop("checked", 'checked');
				} else {
					$(this).removeAttr("checked");
				}
			});
		};

		oneKeyShowModule = function(isShow) {
			if (isShow) {
				$($(".a_spead_onekey_no")[0]).hide();
				$($(".a_retract_onekey_no")[0]).show();
				$("#noResultContent").show();
			} else {
				$($(".a_spead_onekey_no")[0]).show();
				$($(".a_retract_onekey_no")[0]).hide();
				$("#noResultContent").hide();
			}
		};

		// 显示未数据模块
		showNoResultContent = function (datas) {
			var table = $("<table width='90%' class='tb_simple table_module'></table>");
			var tr;
			for(var i=0; i<datas.length; i++) {
				if(i%3 == 0) {
					tr = $("<tr></tr>");
					tr.appendTo(table);
				}
				var td = $("<td width='33.3%'></td>");
				td.html(datas[i].moduleMessageKey);
				td.appendTo(tr);
			}
			table.appendTo("#noResultContent");
		};

		// 查询页面全选联动
		checkedSearchSelectAll = function(obj) {
			var isChecked = $(obj).is(":checked");
			var selectAll = $("#_searchSelectAll");
			if (!isChecked) {
				if (selectAll.is(":checked")) {
					selectAll.removeAttr("checked");
				}
			} else {
				var checked = true;
				$("#searchDiv").find(":checkbox").each( function() {
					if ($(this).attr("disabled") != "disabled" && $(this).attr("id") != "_searchSelectAll") {
						if (!$(this).is(":checked")) {
							checked = false;
							return;
						}
					}
				});
				if (checked) {
					selectAll.prop("checked", "checked");
				}
			}
		};

		//重置
		resetOperation = function() {
			$("#resultDiv").hide();
			$("#searchDiv").show();
			$("#resultContent").html('');
			$("#noResultContent").html('');
			$("#no_result_div").hide();
			oneKeyShowModule(false);

			$("input[name='fdFromId']").val("");
			$("input[name='fdFromName']").val("");
			$("input[name='fdToId']").val("");
			$("input[name='fdToName']").val("");
			$("#valueDiv").html("");

			$("#from_edit").show();
			$("#from_view").hide();
			$("#to_edit").show();
			$("#to_view").hide();
			$('input[name="_fdAuthType"]').removeAttr("disabled");

			$("#_searchSelectAll").prop("checked", 'checked');
			//全选
			searchCheckAll();
			
			// 适合改版后的地址本
			$("input[name='fdFromName']").parent().find("ol").empty();
			$("input[name='fdToName']").parent().find("ol").empty();
			
			// 更新主ID
			var url_c = Com_SetUrlParameter(location.href, "method", "getMainLogId");
			LUI.$.ajax( {
				url : url_c,
				type : 'post',
				dataType : 'text',
				data : {t: new Date().getTime()},
				success : function(data, textStatus, xhr) {
					if (data != "") {
						$("input[name='fdId']").val(data);
					}
				}
			});
		};
		
		// 提交操作
		submitOperation = function (isAll) {
			if(!form_validator()) {
				return;
			}
			
			var content = {};
			if(!isAll) {
				// 需要获取选择的权限类型及每种类型下的模块
				content.items = [];
				$('input[name="_fdAuthType"]:checked').each(function() {
					var item = jstree.get_node($(this).val());
					if(!item) return true; // 跳过没有数据的类型
					var _type = {};
					_type.id = $(this).val();
					_type.total = 0;
					_type.modules = [];
					$.each(item.children, function(i, n) {
						var __id = n.replace(/\./g, "-");
						var num = parseInt($("#" + __id + "_number").text());
						if(num < 1) {
							// 没有选择交接记录，不处理
							return true;
						}
						
						var module = {"id":n.split("-")[1],"total":0};
						
						var ids = $("#valueDiv input[name=" + __id + "]").val();
						if (ids.length > 0) {
							module.itemIds = ids;
						}
						_type.modules.push(module);
					});
					if(_type.modules.length > 0)
						content.items.push(_type);
				});
				if(content.items.length < 1) {
					dialog.alert("${ lfn:message('sys-handover:sysHandoverConfigMain.searchKeysNotNull') }");
					return false;
				}
			} else {
				content.items = [];
				var modules = [];
				// 只需要获取权限类型和选择的模块
				$('input[name="searchModuleCheckBox"]:checked').each(function() {
					modules.push({"id":$(this).val(), "total":0});
				});
				$('input[name="_fdAuthType"]:checked').each(function() {
					content.items.push({"id":$(this).val(), "total":0, "modules":modules});
				});
			}
			dialog.confirm("${ lfn:message('sys-handover:sysHandoverConfigLog.operation.submitAuth.desc') }", function(val){
				if(val) {
					var fdId = $("input[name='fdId']")[0].value;
					var fdFromId = $("input[name='fdFromId']")[0].value;
					var fdToId = $("input[name='fdToId']")[0].value;
					var data = {
							fdId : fdId,
							fdFromId : fdFromId,
							fdToId : fdToId,
							fdContent : JSON.stringify(content)
						};
					
					LUI.$.ajax( {
						url : Com_SetUrlParameter(location.href, "method", "submitAuth"),
						type : 'post',
						dataType : 'json',
						async : true,
						data : data,
						success : function(data, textStatus, xhr) {
							dialog.success('<bean:message key="return.optSuccess" />', null, function() {
								location.reload();
							});
						}
					});
				}
			});
		}
		
		// 上一步
		previousOperation = function () {
			$("#resultDiv").hide();
			$("#searchDiv").show();
			$("#resultContent").html('');
			$("#noResultContent").html('');
			$("#no_result_div").hide();
			oneKeyShowModule(false);
			$("#submit2").show();

			$("#from_edit").show();
			$("#from_view").hide();
			$("#to_edit").show();
			$("#to_view").hide();
			$('input[name="_fdAuthType"]').removeAttr("disabled");
		}

		// 模块查询（下一步）
		nextOperation = function () {
			if(!form_validator()) {
				return;
			}
			$("#resultContent").html('');

			var fdFromId = $("input[name='fdFromId']")[0].value;
			var fdToId = $("input[name='fdToId']")[0].value;
			// 获取待查询模块
			var keyArr = new Array();
			$('input[name="searchModuleCheckBox"]:checked').each(function() { 
				keyArr.push($(this).val()); 
			}); 
			// 权限类型
			var authType = new Array();
			treeContent = []
			$('input[name="_fdAuthType"]:checked').each(function() {
				authType.push($(this).val()); 
				
				// 需要显示的交接类型
				var node = {};
				node.id = $(this).val();
				node.text = "";
				node.children = [];
				treeContent.push(node);
			});

			// 隐藏搜索区域
			$("#from_edit").hide();
			$("#from_view").show();
			$("#to_edit").hide();
			$("#to_view").show();
			$("#searchDiv").css("display", "none");
			$('input[name="_fdAuthType"]').attr("disabled","disabled");
			// 显示结果区域
			$("#resultDiv").slideDown(1, function() {
				var totalSelect = 0;
				var noResultTotal = 0;
				var noResultDatas = [];
				// 开启进度条
				window.progress = dialog.progress();
				searchData(keyArr, authType.join(","), 0, fdFromId, fdToId, noResultDatas, totalSelect, noResultTotal);
			});
		};

		searchData = function(keyArr, authType, index, fdFromId, fdToId, noResultDatas, totalSelect, noResultTotal) {
			if (index >= keyArr.length) {
				// 显示无数据模块
				showNoResultContent(noResultDatas);
				
				if(window.progress) {
					// 设置进度值
					window.progress.hide();
				}
				
				// 显示树
				showTree();
				return;
			}
			
			var url = Com_SetUrlParameter(location.href, "method", "search");
			var data = {
				fdKey : keyArr[index++],
				fdFromId : fdFromId,
				fdToId : fdToId,
				authType : authType
			};
			LUI.$.ajax( {
				url : url,
				type : 'get',
				dataType : 'json',
				async : true,
				data : data,
				success : function(data, textStatus, xhr) {
					if (data == false) {
						dialog.failure("${ lfn:message('sys-handover:sysHandoverConfigMain.searchFailture') }");
					} else {
						if (data.total > 0) {
							buildTreeModel(data);
							// 所有选中的记录数量
							totalSelect += data.total;
						} else {
							noResultTotal++;
							noResultDatas.push(data);
							// 显示无数据模块隐藏/显示栏
							if ($("#no_result_div").is(':hidden')) {
								$("#no_result_div").show();
							}
						}
					}
					if(window.progress) {
						// 设置进度值
						window.progress.setProgress(index, keyArr.length);
					}
					searchData(keyArr, authType, index, fdFromId, fdToId, noResultDatas, totalSelect, noResultTotal);
				}
			});
		};
		
		// 构建模块树
		buildTreeModel = function(data) {
			$.each(treeContent, function(i, n) {
				if(data.item instanceof Array) {
					$.each(data.item, function(j, m) {
						if(n.id == m.item && m.total > 0) {
							n.text = m.itemMessageKey;
							n.children.push(buildItemText(n, m));
						}
					});
				} else {
					if(n.id == data.item.item && data.item.total > 0) {
						n.text = data.item.itemMessageKey;
						n.children.push(buildItemText(n, data.item));
					}
				}
			});
		};
		
		buildItemText = function(n, m) {
			var itemMark = n.id + '-' + m.module;
			var _itemMark = itemMark.replace(/\./g, "-");
			
			$("#valueDiv").append("<input type='hidden' name='" + _itemMark + "'/>");
			var text = m.moduleMessageKey + '<span class="count_recode">(${ lfn:message("sys-handover:sysHandoverConfigMain.total") }<span class="number_total" id="' + _itemMark + '_number_total">' + m.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }'
				+ '<span class="split"></span>${ lfn:message("sys-handover:sysHandoverConfigMain.total.select") }<span class="number" id="' + _itemMark + '_number">' + m.total + '</span>${ lfn:message("sys-handover:sysHandoverConfigMain.recodes") }'
				+ ')</span>'
				+ '<span class="a_doc" onclick="dialogDetail(\'' + itemMark + '\');">${ lfn:message("sys-handover:sysHandoverConfigMain.detail") }</span>';
			
			var node = {id:n.id + "-" + m.module, text:text};
			// 增加2条无意义的数据，目的是在选择文档明细时，可以设置交接项目的树状态
			node.children = [ {
				text : "hide1",
				id : n.id + "-" + m.module + "-hide1"
			}, {
				text : "hide2",
				id : n.id + "-" + m.module + "-hide2"
			} ];
			return node;
		};
		
		// 文档类交接显示明细表
		dialogDetail = function(itemMark) {
			var url = "/sys/handover/sys_handover_doc/sysHandoverDoc_detail.jsp";
			var fdFromId = $("input[name='fdFromId']")[0].value;
			var fdToId = $("input[name='fdToId']")[0].value;
			var item = itemMark.split("-")[0];
			var moduleName = itemMark.split("-")[1];
			var _itemMark = itemMark.replace(/\./g, "-");
			
			window.selectedIds = $("#valueDiv input[name=" + _itemMark + "]").val();  // 已经选中的记录

			var title = '<bean:message bundle="sys-handover" key="sysHandoverConfigMain.detail"/>';
			switch (item) {
			case ("authReaders"): {
				title += '(<bean:message bundle="sys-handover-support-config-auth" key="sysHandoverDocAuth.authReaders"/>)';
				break;
			}
			case ("authEditors"): {
				title += '(<bean:message bundle="sys-handover-support-config-auth" key="sysHandoverDocAuth.authEditors"/>)';
				break;
			}
			case ("authLbpmReaders"): {
				title += '(<bean:message bundle="sys-handover-support-config-auth" key="sysHandoverDocAuth.authLbpmReaders"/>)';
				break;
			}
			case ("authAttPrints"): {
				title += '(<bean:message bundle="sys-handover-support-config-auth" key="sysHandoverDocAuth.authAttPrints"/>)';
				break;
			}
			case ("authAttCopys"): {
				title += '(<bean:message bundle="sys-handover-support-config-auth" key="sysHandoverDocAuth.authAttCopys"/>)';
				break;
			}
			case ("authAttDownloads"): {
				title += '(<bean:message bundle="sys-handover-support-config-auth" key="sysHandoverDocAuth.authAttDownloads"/>)';
				break;
			}
			}

			url += "?type=auth&fdFromId=" + fdFromId + "&fdToId=" + fdToId + "&moduleName=" + moduleName + "&item=" + item;
			
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
						//交接项修改
						itemChange(itemMark, _itemMark, ids.length);
					}
				}
			}).show();
		};
		
		// 交接项修改
		itemChange = function(itemMark, _itemMark, selectCount) {
			// 修改交接项的数量
			$("#" + _itemMark + "_number").text(selectCount);
			// 修改交接项的样式
			var itemNum = parseInt($("#" + _itemMark + "_number_total").text());
			if (selectCount == itemNum) { // 全选
				jstree.check_node(itemMark + "-hide1");
				jstree.check_node(itemMark + "-hide2");
			} else if (selectCount == 0) { // 全不选
				jstree.uncheck_node(itemMark + "-hide1");
				jstree.uncheck_node(itemMark + "-hide2");
			} else { // 半选
				jstree.uncheck_node(itemMark + "-hide1");
				jstree.check_node(itemMark + "-hide2");
			}
		};
		
		moduleChange = function(id, checked) {
			if (id.indexOf("-") == -1) {
				$.each(jstree.get_node(id).children, function(i, n) {
					__moduleChange(n, checked);
				});
			} else {
				__moduleChange(id, checked);
			}
		};
		
		__moduleChange = function(id, checked) {
            checked = jstree.is_checked(id);
            var itemNum = 0;
			var _itemMark = id.replace(/\./g, "-");
			$("#valueDiv input[name=" + _itemMark + "]").val("");
			if(checked) {
				itemNum = parseInt($("#" + _itemMark + "_number_total").text());
			}
			$("#" + _itemMark + "_number").text(itemNum);
		};
	});
</script>
